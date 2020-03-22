/**
 * Project Looking Glass
 *
 * $RCSfile: TestEmbeddedComponentPeer.java,v $
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.util.logging.Logger;
import javax.swing.JComponent;
import org.jdesktop.lg3d.jawt.JawtBackBuffer;
import org.jdesktop.lg3d.jawt.JawtKeyboardFocusManager;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedComponentPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtComponentPeer;

/**
 * Interface to be implemented by JAWT Container component peers.
 *
 * @see JawtToolkit
 *
 * @author krishna_gadepalli
 */
public class TestEmbeddedComponentPeer
       implements JawtEmbeddedComponentPeer {
    protected static Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt.test");

    protected TestEmbeddedToolkit toolkit;
    protected JawtComponentPeer peer;

    protected Component target;
    protected JComponent delegate;

    public TestEmbeddedComponentPeer(TestEmbeddedToolkit toolkit,
				     JawtComponentPeer peer) {
	this.toolkit = toolkit;
	this.peer = peer;
	this.target = peer.getTarget();

	delegate = createDelegate(toolkit, peer);
    }

    protected JComponent createDelegate(TestEmbeddedToolkit toolkit,
					JawtComponentPeer peer) {
        return null;
    }

    public void dispose() {
    }

    public void updateCursorImmediately() {
    }

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

    public void setBounds(int x, int y, int width, int height) {
	delegate.setBounds(x, y, width, height);
    }

    public Dimension getPreferredSize() {
	return delegate.getPreferredSize();
    }

    public Dimension getMinimumSize() {
	return delegate.getMinimumSize();
    }

    public boolean requestFocus(Component lightweightChild,
			 boolean temporary, boolean focusedWindowChangeAllowed,
			 long time, sun.awt.CausedFocusEvent.Cause cause) {
	return JawtKeyboardFocusManager.processLightweightTransfer(
		    target, lightweightChild, temporary, focusedWindowChangeAllowed, time);
    }

    public GraphicsConfiguration getGraphicsConfiguration() {
	return delegate.getGraphicsConfiguration();
    }

    public void renderBuffer(JawtBackBuffer buffer, Rectangle rect) {
    }

    public void bufferResized(JawtBackBuffer buffer) {
	Rectangle r = delegate.getBounds();

	int w = buffer.getWidth();
	int h = buffer.getHeight();

	if ((w == r.width) && (h == r.height))
	    return;

	delegate.setBounds(r.x, r.y, w, h);
    }
}
