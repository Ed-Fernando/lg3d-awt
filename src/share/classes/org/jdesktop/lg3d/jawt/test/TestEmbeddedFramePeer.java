/**
 * Project Looking Glass
 *
 * $RCSfile: TestEmbeddedFramePeer.java,v $
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

import java.awt.Frame;
import java.awt.MenuBar;
import java.awt.Rectangle;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedFramePeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFramePeer;

/**
 * Interface to be implemented by JAWT Container component peers.
 *
 * @see JawtToolkit
 *
 * @author krishna_gadepalli
 */
public class TestEmbeddedFramePeer extends TestEmbeddedDecoratedPeer
				   implements JawtEmbeddedFramePeer {
    protected JawtFramePeer peer;
    protected Frame target;

    public TestEmbeddedFramePeer(TestEmbeddedToolkit toolkit,
				 JawtFramePeer peer) {
	super(toolkit, peer);
	this.peer = peer;
	this.target = (Frame)peer.getTarget();

	setState(target.getState());
	setMenuBar(target.getMenuBar());
    }

    public void setMenuBar(MenuBar mb) {
    }

    public void setState(int state) {
    }

    public int getState() {
        return 0;
    }

    public void setMaximizedBounds(Rectangle bounds) {
    }
}
