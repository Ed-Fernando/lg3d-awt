/**
 * Project Looking Glass
 *
 * $RCSfile: TestEmbeddedWindowPeer.java,v $
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
 * $Date: 2006-11-15 07:10:56 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.test;

import java.awt.Dialog;
import java.awt.Rectangle;
import java.awt.Window;
import javax.swing.JComponent;
import org.jdesktop.lg3d.jawt.JawtBackBuffer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedWindowPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtComponentPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtWindowPeer;

/**
 * Interface to be implemented by JAWT Container component peers.
 *
 * @see JawtToolkit
 *
 * @author krishna_gadepalli
 */
public class TestEmbeddedWindowPeer extends TestEmbeddedContainerPeer
				    implements JawtEmbeddedWindowPeer {
    protected JawtWindowPeer peer;
    protected Window target;

    protected TestEmbeddedInternalFrame delegate;

    public TestEmbeddedWindowPeer(TestEmbeddedToolkit toolkit,
				  JawtWindowPeer peer) {
	super(toolkit, peer);
	this.peer = peer;
	this.target = (Window)peer.getTarget();
	this.delegate = (TestEmbeddedInternalFrame)super.delegate;
    }

    protected JComponent createDelegate(TestEmbeddedToolkit toolkit,
					JawtComponentPeer peer) {
	return new TestEmbeddedInternalFrame(toolkit, (JawtWindowPeer)peer);
    }

    public void dispose() {
	delegate.dispose();
    }

    public void setBounds(int x, int y, int width, int height) {
	delegate.setPeerBounds(x, y, width, height);
    }

    public void renderBuffer(JawtBackBuffer buffer, Rectangle rect) {
	logger.finer(
	      "Image[" + buffer.getWidth() + "," + buffer.getHeight() + "] " +
	       "Rect[" + rect.width + "x" + rect.height + "+" + rect.x + "+" + rect.y + "] ");

	delegate.updateImage(buffer.getImage(), rect);
    }

    public void toFront() {
    }

    public void toBack() {
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
    }

    public void updateFocusableWindowState() {
    }

    public boolean requestWindowFocus() {
	return false;
    }

    public void setModalBlocked(Dialog blocker, boolean blocked) {
    }

    public void updateMinimumSize() {
    }

    public void updateIconImages() {
    }
}
