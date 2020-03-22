/**
 * Project Looking Glass
 *
 * $RCSfile: JawtDialogPeer.java,v $
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
import java.awt.peer.DialogPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedDialogPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedToolkit;

/**
 * All the AWT peers implemented using Swing will extend this class.
 *
 */
public class JawtDialogPeer extends JawtWindowPeer implements DialogPeer {

    protected Dialog target;
    protected JawtEmbeddedDialogPeer peer;

    public JawtDialogPeer(Dialog target, JawtEmbeddedToolkit containerToolkit) {
	super(target, containerToolkit);

	this.target = target;
	this.peer = (JawtEmbeddedDialogPeer)super.peer;

	// TODO: Set default properties for the JawtEmbeddedDialogPeer so that
	//	 it behaves like a DialogPeer
    }

    public void setTitle(String title) {
	peer.setTitle(title);
    }

    public void setResizable(boolean resizeable) {
	peer.setResizable(resizeable);
    }
}
