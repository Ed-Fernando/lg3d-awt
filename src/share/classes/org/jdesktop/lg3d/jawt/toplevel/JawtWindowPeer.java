/**
 * Project Looking Glass
 *
 * $RCSfile: JawtWindowPeer.java,v $
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

import java.awt.Dialog;
import java.awt.Window;
import java.awt.peer.WindowPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedToolkit;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedWindowPeer;
import sun.awt.CausedFocusEvent;

/**
 * All the AWT peers implemented using Swing will extend this class.
 *
 */
public class JawtWindowPeer extends JawtContainerPeer implements WindowPeer {

    protected Window target;
    protected JawtEmbeddedWindowPeer peer;

    public JawtWindowPeer(Window target, JawtEmbeddedToolkit containerToolkit) {
	super(target, containerToolkit);

	this.target = target;
	this.peer = (JawtEmbeddedWindowPeer)super.peer;
    }

    public void toFront() {
	peer.toFront();
    }

    public void toBack() {
	peer.toBack();
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
	peer.setAlwaysOnTop(alwaysOnTop);
    }

    public void updateFocusableWindowState() {
	peer.updateFocusableWindowState();
    }

    public boolean requestWindowFocus() {
	return peer.requestWindowFocus();
    }

    public void setModalBlocked(Dialog blocker, boolean blocked) {
	peer.setModalBlocked(blocker, blocked);
    }

    public void updateMinimumSize() {
	peer.updateMinimumSize();
    }

    public void updateIconImages() {
	peer.updateIconImages();
    }
}
