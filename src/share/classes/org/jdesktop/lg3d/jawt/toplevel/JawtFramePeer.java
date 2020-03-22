/**
 * Project Looking Glass
 *
 * $RCSfile: JawtFramePeer.java,v $
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
 * $Date: 2006-11-15 07:10:57 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.toplevel;

import java.awt.Frame;
import java.awt.MenuBar;
import java.awt.Rectangle;
import java.awt.peer.FramePeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedFramePeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedToolkit;

/**
 * All the AWT peers implemented using Swing will extend this class.
 *
 */
public class JawtFramePeer extends JawtWindowPeer implements FramePeer {

    protected Frame target;
    protected JawtEmbeddedFramePeer peer;

    public JawtFramePeer(Frame target, JawtEmbeddedToolkit containerToolkit) {
	super(target, containerToolkit);

	this.target = target;
	this.peer = (JawtEmbeddedFramePeer)super.peer;
    }

    public void setTitle(String title) {
	peer.setTitle(title);
    }

    public void setMenuBar(MenuBar mb) {
	peer.setMenuBar(mb);
    }

    public void setResizable(boolean resizeable) {
	peer.setResizable(resizeable);
    }

    public void setState(int state) {
	peer.setState(state);
    }

    public int  getState() {
	return peer.getState();
    }

    public void setMaximizedBounds(Rectangle bounds) {
	peer.setMaximizedBounds(bounds);
    }

    public void setBoundsPrivate(int x, int y, int width, int height) {
	setBounds(x, y, width, height, SET_BOUNDS);
    }

    public Rectangle getBoundsPrivate() {
	return getBounds();
    }
}
