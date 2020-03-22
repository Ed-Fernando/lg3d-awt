/**
 * Project Looking Glass
 * 
 * $RCSfile: TestEmbeddedInternalFrame.java,v $
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
 * $Date: 2006-12-14 00:33:37 $
 * $State: Exp $
 */
package org.jdesktop.lg3d.jawt.test;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import org.jdesktop.lg3d.jawt.JawtKeyboardFocusManager;
import org.jdesktop.lg3d.jawt.toplevel.JawtWindowPeer;

public class TestEmbeddedInternalFrame extends JInternalFrame
			       implements InternalFrameListener {

    protected static Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt.test");

    Window target;
    JawtWindowPeer peer;

    TestLabel imageLabel;

    public TestEmbeddedInternalFrame(TestEmbeddedToolkit toolkit, JawtWindowPeer peer) {
	this.peer = peer;
	this.target = (Window)peer.getTarget();

	setContentPane(imageLabel = new TestLabel());

	int op = WindowConstants.DO_NOTHING_ON_CLOSE;

	if (target instanceof JFrame) {
	    op = ((JFrame)target).getDefaultCloseOperation();
	} else if (target instanceof JDialog) {
	    op = ((JDialog)target).getDefaultCloseOperation();
	}

	if (op == WindowConstants.EXIT_ON_CLOSE)
	    op = WindowConstants.DISPOSE_ON_CLOSE;

	setDefaultCloseOperation(op);
	setLocation(target.getLocation());
	setSize(target.getSize());

	if (target instanceof Frame) {
	    Frame frame = (Frame)target;

	    setTitle(frame.getTitle());
	    setResizable(frame.isResizable());
	    setMaximizable(frame.isResizable());
	    setIconifiable(true);
	    setClosable(true);
	} else {
	    setResizable(false);
	    setMaximizable(false);
	    setIconifiable(false);
	    setClosable(false);
	}

	// enableEvents(~0L);
	toolkit.addToDesktopPane(this);
	addInternalFrameListener(this);
    }

    public void setSize(Dimension d) {
	super.setSize(d);
	setName("TestEmbeddedInternalFrame[" + d.width + "x" + d.height + "]");
    }

    public boolean retargetEvent(AWTEvent e, JComponent source) {
	int xoffset = 0;
	int yoffset = 0;

	if (source == this) {
	    Rectangle rb = getRootPane().getBounds();
	    Rectangle cb = getContentPane().getBounds();

	    xoffset = rb.x + cb.x;
	    yoffset = rb.y + cb.y;
	} else if (source == getRootPane()) {
	    Rectangle cb = getContentPane().getBounds();

	    xoffset = cb.x;
	    yoffset = cb.y;
	}

	return JawtKeyboardFocusManager.retargetEvent(
				e, source, target, xoffset, yoffset);
    }

    public void processEvent(AWTEvent e) {
	if (!retargetEvent(e, this)) {
	    super.processEvent(e);
	}
    }

    public void updateImage(BufferedImage image, Rectangle rect) {
	// delegate.getContentPane().getGraphics().drawImage(
	// image, 0, 0, image.getWidth(), image.getHeight(), null);
	imageLabel.setIcon(new ImageIcon(image));
    }

    public void setPeerBounds(int x, int y, int width, int height) {

	// Compensate for the offsets/border/insets of the root & content panes
	Rectangle rb = getRootPane().getBounds();
	Rectangle cb = getContentPane().getBounds();

	Insets    ri = getInsets();
	Insets    ci = getRootPane().getInsets();

	width  += rb.x + cb.x + ri.right + ci.right;
	height += rb.y + cb.y + ri.bottom + ci.bottom;

	setBounds(x, y, width, height);
    }

    public void paint(Graphics g) {
	// logger.info("TestEmbeddedInternalFrame.paint");
	super.paint(g);
    }

    public void repaint(long tm, int x, int y, int width, int height) {
	// logger.info("TestEmbeddedInternalFrame.repaint[" + tm + "," + x + "," + y + "," + width + "," + height + "]");
	super.repaint(tm, x, y, width, height);
    }

    /* -------------------- InternalFrameListener interface -------------------- */

    /**
     * Invoked when a internal frame has been opened.
     * @see javax.swing.JInternalFrame#show
     */
    public void internalFrameOpened(InternalFrameEvent e) {
	target.dispatchEvent(new WindowEvent(target, WindowEvent.WINDOW_OPENED));
    }

    /**
     * Invoked when an internal frame is in the process of being closed.
     * The close operation can be overridden at this point.
     * @see javax.swing.JInternalFrame#setDefaultCloseOperation
     */
    public void internalFrameClosing(InternalFrameEvent e) {
	target.dispatchEvent(new WindowEvent(target, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Invoked when an internal frame has been closed.
     * @see javax.swing.JInternalFrame#setClosed
     */
    public void internalFrameClosed(InternalFrameEvent e) {
	target.dispatchEvent(new WindowEvent(target, WindowEvent.WINDOW_CLOSED));
    }

    /**
     * Invoked when an internal frame is iconified.
     * @see javax.swing.JInternalFrame#setIcon
     */
    public void internalFrameIconified(InternalFrameEvent e) {
	target.dispatchEvent(new WindowEvent(target, WindowEvent.WINDOW_ICONIFIED));
    }

    /**
     * Invoked when an internal frame is de-iconified.
     * @see javax.swing.JInternalFrame#setIcon
     */
    public void internalFrameDeiconified(InternalFrameEvent e) {
	target.dispatchEvent(new WindowEvent(target, WindowEvent.WINDOW_DEICONIFIED));
    }

    /**
     * Invoked when an internal frame is activated.
     * @see javax.swing.JInternalFrame#setSelected
     */
    public void internalFrameActivated(InternalFrameEvent e) {
	target.dispatchEvent(new WindowEvent(target, WindowEvent.WINDOW_ACTIVATED));
    }

    /**
     * Invoked when an internal frame is de-activated.
     * @see javax.swing.JInternalFrame#setSelected
     */
    public void internalFrameDeactivated(InternalFrameEvent e) {
	target.dispatchEvent(new WindowEvent(target, WindowEvent.WINDOW_DEACTIVATED));
    }

    public class TestLabel extends JLabel {

	public TestLabel() {
	    // enableEvents(~0L);
	}

	public void processEvent(AWTEvent e) {
	    if (!retargetEvent(e, this)) {
		super.processEvent(e);
	    }
	}
    }
}
