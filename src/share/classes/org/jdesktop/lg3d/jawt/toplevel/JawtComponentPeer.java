/**
 * Project Looking Glass
 *
 * $RCSfile: JawtComponentPeer.java,v $
 *
 * Copyright (c) 2004, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.2 $
 * $Date: 2006-12-14 00:33:38 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.toplevel;

import java.awt.AWTEvent;
import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.PaintEvent;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.VolatileImage;
import java.awt.peer.ComponentPeer;
import java.awt.peer.ContainerPeer;
import java.rmi.RemoteException;
import org.jdesktop.lg3d.jawt.JawtBackBuffer;
import org.jdesktop.lg3d.jawt.JawtGraphics;
import org.jdesktop.lg3d.jawt.JawtToolkit;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedComponentPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedToolkit;
import java.util.logging.Logger;
import org.jdesktop.lg3d.jawt.JawtKeyboardFocusManager;
import org.jdesktop.lg3d.jawt.JawtVolatileImage;

/**
 * All the AWT peers implemented using Swing will extend this class.
 */
public abstract class JawtComponentPeer implements ComponentPeer {

    protected static Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt");

    protected Component target;
    protected JawtEmbeddedComponentPeer peer;

    protected JawtEmbeddedToolkit containerToolkit;
    protected JawtToolkit jawtToolkit;

    protected JawtBackBuffer backBuffer;
    protected JawtGraphics graphics;

    public JawtComponentPeer(Component target, JawtEmbeddedToolkit containerToolkit) {
	this.target = target;
	this.containerToolkit = containerToolkit;
	this.jawtToolkit = (JawtToolkit)Toolkit.getDefaultToolkit();

	if (this instanceof JawtFileDialogPeer) {
	    this.peer = containerToolkit.createFileDialog((JawtFileDialogPeer)this);
	} else if (this instanceof JawtDialogPeer) {
	    this.peer = containerToolkit.createDialog((JawtDialogPeer)this);
	} else if (this instanceof JawtFramePeer) {
	    this.peer = containerToolkit.createFrame((JawtFramePeer)this);
	} else if (this instanceof JawtWindowPeer) {
	    this.peer = containerToolkit.createWindow((JawtWindowPeer)this);
	} else {
	    logger.severe("Can not create peer! " + getClass().getName());
	    return;
	}

	logger.info("Created peer : " + this.peer.getClass().getName());
    }     

    public Component getTarget() {
	return target;
    }

    void setBufferSize(int width, int height) {
        if (backBuffer == null) {
            backBuffer = new JawtBackBuffer(width, height);

            graphics = new JawtGraphics(backBuffer);
            backBuffer.addBufferResizeListener(graphics);
            backBuffer.addBufferFlipListener(peer);
            backBuffer.addBufferResizeListener(peer);

        } else if (backBuffer.ensureSize(width, height)) {
                try {
		    peer.repaint(0, 0, 0, width, height);
		} catch (RemoteException re) {
		}
        }
                
    }    

    /* -------------------------------------------------------------------------- *
     * Provide default implementation of the ComponentPeer interface
     * -------------------------------------------------------------------------- */

    public void handleEvent(AWTEvent e) {
	JawtKeyboardFocusManager.handleEvent(target, e);
    }

    /**
     * Does this peer handle wheel scrolling ? By default no.
     * Peers such as List, TextArea etc. for which wheel scrolling makes sense
     * must override it and return true.
     */
    public boolean handlesWheelScrolling() {
	return false;
    }

    /**
     * TODO: is this correct ?
     */
    public boolean isObscured() {
	return false;
    }

    /**
     * TODO: is this correct ?
     */
    public boolean canDetermineObscurity() {
	return false;
    }

    /**
     * TODO: is this correct ?
     */
    public void coalescePaintEvent(PaintEvent e) {
    }

    public void dispose() {
	try {
	    peer.dispose();
	} catch (RemoteException re) {
	}
    }

    public void updateCursorImmediately() {
	try {
	    peer.updateCursorImmediately();
	} catch (RemoteException re) {
	}
    }

    public boolean requestFocus(Component lightweightChild,
                                boolean temporary,
                                boolean focusedWindowChangeAllowed,
                                long time, sun.awt.CausedFocusEvent.Cause cause) {
	try {
	    return peer.requestFocus(lightweightChild, temporary, focusedWindowChangeAllowed, time, cause);
	} catch (RemoteException re) {
	    return false;
	}
    }

    public void createBuffers(int numBuffers, BufferCapabilities caps) {
	// TODO:
    }

    public Image getBackBuffer() {
	return (backBuffer != null) ? backBuffer.getImage() : null;
    }

    public void flip(BufferCapabilities.FlipContents flipAction) {
	if (backBuffer != null)
	    backBuffer.updateRegion(null);
    }


    public void destroyBuffers() {
	// TODO:
    }

    /**
     * Reparents this peer to the new parent referenced by <code>newContainer</code> peer
     * Implementation depends on toolkit and container.
     * @param newContainer peer of the new parent container
     * @since 1.5
     */
    public void reparent(ContainerPeer newContainer) {
	// TODO:
    }

    /**
     * Returns whether this peer supports reparenting to another parent withour destroying the peer
     * @return true if appropriate reparent is supported, false otherwise
     * @since 1.5
     */
    public boolean isReparentSupported() {
	return false;
    }

    public void setVisible(boolean b) {
	try {
	    peer.setVisible(b);
	} catch (RemoteException re) {
	}
    }

    public void setEnabled(boolean b) {
	try {
	    peer.setEnabled(b);
	} catch (RemoteException re) {
	}
    }

    public void paint(Graphics g) {
	try {
	    peer.paint(g);
	} catch (RemoteException re) {
	}
    }

    public void repaint(long tm, int x, int y, int width, int height) {
	try {
	    peer.repaint(tm, x, y, width, height);
	} catch (RemoteException re) {
	}
    }

    public void print(Graphics g) {
	try {
	    peer.print(g);
	} catch (RemoteException re) {
	}
    }

    public void setBounds(int x, int y, int width, int height, int op) {
        setBufferSize(width, height);
	try {
	    peer.setBounds(x, y, width, height);
	} catch (RemoteException re) {
	}
    }

    public Dimension getPreferredSize() {
	try {
	    return peer.getPreferredSize();
	} catch (RemoteException re) {
	    return null;
	}
    }

    public Dimension getMinimumSize() {
	try {
	    return peer.getMinimumSize();
	} catch (RemoteException re) {
	    return null;
	}
    }

    public GraphicsConfiguration getGraphicsConfiguration() {
	try {
	    return peer.getGraphicsConfiguration();
	} catch (RemoteException re) {
	    return null;
	}
    }

    public Graphics getGraphics() {
	return (graphics != null) ? graphics.create() : null;
    }

    public FontMetrics getFontMetrics(Font font) {
	return jawtToolkit.getFontMetrics(font);
    }

    public void setForeground(Color c) {
	if (graphics != null)
	    graphics.setColor(c);
    }

    public void setBackground(Color c) {
	if (graphics != null)
	    graphics.setBackground(c);
    }

    public void setFont(Font f) {
	if (graphics != null)
	    graphics.setFont(f);
    }

    public Rectangle getBounds() {
	return target.getBounds();
    }

    /**
     * Used by lightweight implementations to tell a ComponentPeer to layout
     * its sub-elements.  For instance, a lightweight Checkbox needs to layout
     * the box, as well as the text label.
     */
    public void layout() {
	target.doLayout();
    }

    public Point getLocationOnScreen() {
	return target.getBounds().getLocation();
    }

    public ColorModel getColorModel() {
	return jawtToolkit.getColorModel();
    }

    public Toolkit getToolkit() {
	return jawtToolkit;
    }

    public boolean isFocusable() {
	return true;
    }

    public Image createImage(ImageProducer producer) {
	return jawtToolkit.createImage(producer);
    }

    public Image createImage(int width, int height) {
	return new sun.awt.image.OffScreenImage(target, width, height);
    }

    public VolatileImage createVolatileImage(int width, int height) {
        // return new sun.awt.image.SunVolatileImage(null, width, height);
        return new JawtVolatileImage(width, height);
    }

    public boolean prepareImage(Image img, int w, int h, ImageObserver o) {
	return jawtToolkit.prepareImage(img, w, h, o);
    }

    public int checkImage(Image img, int w, int h, ImageObserver o) {
	return jawtToolkit.checkImage(img, w, h, o);
    }

    /* -------------------------------------------------------------------------- *
     * Deprecated methods.
     * -------------------------------------------------------------------------- */

    /**
     * DEPRECATED:  Replaced by getPreferredSize().
     */
    @SuppressWarnings("deprecation")
    public Dimension preferredSize() {
	return getPreferredSize();
    }

    /**
     * DEPRECATED:  Replaced by getMinimumSize().
     */
    @SuppressWarnings("deprecation")
    public Dimension minimumSize() {
	return getMinimumSize();
    }

    /**
     * DEPRECATED:  Replaced by setVisible(boolean).
     */
    @SuppressWarnings("deprecation")
    public void show() {
	setVisible(true);
    }

    /**
     * DEPRECATED:  Replaced by setVisible(boolean).
     */
    @SuppressWarnings("deprecation")
    public void hide() {
	setVisible(false);
    }

    /**
     * DEPRECATED:  Replaced by setEnabled(boolean).
     */
    @SuppressWarnings("deprecation")
    public void enable() {
	setEnabled(true);
    }

    /**
     * DEPRECATED:  Replaced by setEnabled(boolean).
     */
    @SuppressWarnings("deprecation")
    public void disable() {
	setEnabled(false);
    }

    /**
     * DEPRECATED:  Replaced by setBounds(int, int, int, int).
     */
    @SuppressWarnings("deprecation")
    public void reshape(int x, int y, int width, int height) {
	try {
	    peer.setBounds(x, y, width, height);
	} catch (RemoteException re) {
	}
    }
}
