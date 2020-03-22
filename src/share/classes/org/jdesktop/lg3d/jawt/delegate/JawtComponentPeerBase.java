/**
 * Project Looking Glass
 *
 * $RCSfile: JawtComponentPeerBase.java,v $
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
 * $Revision: 1.1 $
 * $Date: 2006-09-22 21:10:23 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.delegate;

import java.awt.AWTEvent;
import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextComponent;
import java.awt.Toolkit;
import java.awt.event.PaintEvent;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.VolatileImage;
import java.awt.peer.ComponentPeer;
import java.awt.peer.ContainerPeer;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.RootPaneContainer;
import javax.swing.text.JTextComponent;
import sun.awt.CausedFocusEvent;

/**
 * All the AWT peers implemented using Swing will extend this class.
 *
 */
public abstract class JawtComponentPeerBase<T extends JComponent & JawtDelegateInterface>
		    implements ComponentPeer {

    protected Component target;
    protected T delegate;

    public JawtComponentPeerBase(Component target, T delegate) {
	this.target = target;
	this.delegate = delegate;

	copyState(target, delegate);
	addToContainer(target, delegate);
    }     

    /**
     * Copy the current state from the AWT component to the Swing Component.
     */
    public static final void copyState(Component from, JComponent to) {
	Color bg = from.getBackground();
	Color fg = from.getForeground();

	if (bg != null) to.setBackground(bg);
	if (fg != null) to.setForeground(fg);

	Font font = from.getFont();

	if (font != null) to.setFont(font);

	to.setBounds(from.getBounds());
	to.setEnabled(from.isEnabled());
	to.setVisible(from.isVisible());

	if ((from instanceof TextComponent) && (to instanceof JTextComponent)) {
	    TextComponent fromTC = (TextComponent)from;
	    JTextComponent toJTC = (JTextComponent)to;

	    toJTC.setText(fromTC.getText());
	    toJTC.setEditable(fromTC.isEditable());
	    toJTC.setCaretPosition(fromTC.getCaretPosition());
	}
    }

    /**
     * If the target Component's parent is some sort of a Container,
     * then add the peer to it.
     */
    public static final void addToContainer(Component target, JComponent peer) {
	@SuppressWarnings("deprecation")
	ComponentPeer targetParentPeer = target.getParent().getPeer();

	Container targetParentContainer = null;

	if (targetParentPeer instanceof RootPaneContainer) {
	    targetParentContainer = ((RootPaneContainer)targetParentPeer).getContentPane();

	} else if (targetParentPeer instanceof JScrollPane) {
	    targetParentContainer = ((JScrollPane)targetParentPeer).getViewport();

	} else if (targetParentPeer instanceof Container) {
	    targetParentContainer = (Container)targetParentPeer;
	}

	if (targetParentContainer != null)
	    targetParentContainer.add(peer);
    }

    /* -------------------------------------------------------------------------- *
     * Provide default implementation of the ComponentPeer interface
     * -------------------------------------------------------------------------- */

    /**
     * Change the source of the event to be the delegate and then have it
     * processed.
     */
    public void handleEvent(AWTEvent e) {
	if ((e = JawtPeerHelper.setEventSource(e, delegate)) != null)
	    delegate.processAWTEvent(e);
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
    }

    public void updateCursorImmediately() {
    }

    public boolean requestFocus(Component lightweightChild,
                                boolean temporary,
                                boolean focusedWindowChangeAllowed,
                                long time, CausedFocusEvent.Cause cause) {
	return true;
    }

    public void createBuffers(int numBuffers, BufferCapabilities caps) {
    }

    public Image getBackBuffer() {
	return null;
    }

    public void flip(BufferCapabilities.FlipContents flipAction) {
    }


    public void destroyBuffers() {
    }

    /**
     * Reparents this peer to the new parent referenced by <code>newContainer</code> peer
     * Implementation depends on toolkit and container.
     * @param newContainer peer of the new parent container
     * @since 1.5
     */
    public void reparent(ContainerPeer newContainer) {
    }

    /**
     * Returns whether this peer supports reparenting to another parent withour destroying the peer
     * @return true if appropriate reparent is supported, false otherwise
     * @since 1.5
     */
    public boolean isReparentSupported() {
	return false;
    }

    /* -------------------------------------------------------------------------- *
     * Punt to calls directly in the delegate JComponent
     * -------------------------------------------------------------------------- */

    public void setVisible(boolean b) {
	delegate.setVisible(b);
    }

    public void setEnabled(boolean b) {
	delegate.setEnabled(b);
    }

    public void paint(Graphics g) {
	delegate.paint(g);
    }

    public void repaint(long tm, int x, int y, int width, int height) {
	delegate.repaint(tm, x, y, width, height);
    }

    public void print(Graphics g) {
	delegate.print(g);
    }

    public void setBounds(int x, int y, int width, int height, int op) {
	delegate.setBounds(x, y, width, height);
    }

    public Dimension getPreferredSize() {
	return delegate.getPreferredSize();
    }

    public Dimension getMinimumSize() {
	return delegate.getMinimumSize();
    }

    public Graphics getGraphics() {
	return delegate.getGraphics();
    }

    public FontMetrics getFontMetrics(Font font) {
	return delegate.getFontMetrics(font);
    }

    public void setForeground(Color c) {
	delegate.setForeground(c);
    }

    public void setBackground(Color c) {
	delegate.setBackground(c);
    }

    public void setFont(Font f) {
	delegate.setFont(f);
    }

    public Rectangle getBounds() {
	return delegate.getBounds();
    }

    /* -------------------------------------------------------------------------- *
     * Punt to calls to somewhere up in the delegate - not in JComponent but
     * somewhere above that.
     * -------------------------------------------------------------------------- */

    /**
     * Used by lightweight implementations to tell a ComponentPeer to layout
     * its sub-elements.  For instance, a lightweight Checkbox needs to layout
     * the box, as well as the text label.
     */
    @SuppressWarnings("deprecation")
    public void layout() {
	delegate.layout();
    }

    public Point getLocationOnScreen() {
	return delegate.getLocationOnScreen();
    }

    public ColorModel getColorModel() {
	return delegate.getColorModel();
    }

    public Toolkit getToolkit() {
	return delegate.getToolkit();
    }

    public boolean isFocusable() {
	return delegate.isFocusable();
    }

    public Image createImage(ImageProducer producer) {
	return delegate.createImage(producer);
    }

    public Image createImage(int width, int height) {
	return delegate.createImage(width, height);
    }

    public VolatileImage createVolatileImage(int width, int height) {
	return delegate.createVolatileImage(width, height);
    }

    public boolean prepareImage(Image img, int w, int h, ImageObserver o) {
	return delegate.prepareImage(img, w, h, o);
    }

    public int checkImage(Image img, int w, int h, ImageObserver o) {
	return delegate.checkImage(img, w, h, o);
    }

    public GraphicsConfiguration getGraphicsConfiguration() {
	return delegate.getGraphicsConfiguration();
    }

    /* -------------------------------------------------------------------------- *
     * Deprecated methods.
     * -------------------------------------------------------------------------- */

    /**
     * DEPRECATED:  Replaced by getPreferredSize().
     */
    @SuppressWarnings("deprecation")
    public Dimension preferredSize() {
	return delegate.getPreferredSize();
    }

    /**
     * DEPRECATED:  Replaced by getMinimumSize().
     */
    @SuppressWarnings("deprecation")
    public Dimension minimumSize() {
	return delegate.getMinimumSize();
    }

    /**
     * DEPRECATED:  Replaced by setVisible(boolean).
     */
    @SuppressWarnings("deprecation")
    public void show() {
	delegate.setVisible(true);
    }

    /**
     * DEPRECATED:  Replaced by setVisible(boolean).
     */
    @SuppressWarnings("deprecation")
    public void hide() {
	delegate.setVisible(false);
    }

    /**
     * DEPRECATED:  Replaced by setEnabled(boolean).
     */
    @SuppressWarnings("deprecation")
    public void enable() {
	delegate.setEnabled(true);
    }

    /**
     * DEPRECATED:  Replaced by setEnabled(boolean).
     */
    @SuppressWarnings("deprecation")
    public void disable() {
	delegate.setEnabled(false);
    }

    /**
     * DEPRECATED:  Replaced by setBounds(int, int, int, int).
     */
    @SuppressWarnings("deprecation")
    public void reshape(int x, int y, int width, int height) {
	delegate.setBounds(x, y, width, height);
    }
}
