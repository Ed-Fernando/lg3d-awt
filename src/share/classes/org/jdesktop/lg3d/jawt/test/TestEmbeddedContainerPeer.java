/**
 * Project Looking Glass
 *
 * $RCSfile: TestEmbeddedContainerPeer.java,v $
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
 * $Date: 2006-11-15 07:10:55 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.test;

import java.awt.Insets;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedContainerPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtContainerPeer;

/**
 * Interface to be implemented by JAWT Container component peers.
 *
 * @see JawtToolkit
 *
 * @author krishna_gadepalli
 */
public class TestEmbeddedContainerPeer extends TestEmbeddedComponentPeer
				       implements JawtEmbeddedContainerPeer {
    protected JawtContainerPeer peer;

    public TestEmbeddedContainerPeer(TestEmbeddedToolkit toolkit,
				     JawtContainerPeer peer) {
	super(toolkit, peer);
	this.peer = peer;
    }

    public Insets getInsets() {
	return delegate.getInsets();
    }

    public void beginLayout() {
    }

    public void endLayout() {
    }

    public boolean isPaintPending() {
	return true;
    }

    /**
     * Restacks native windows - children of this native window - according to Java container order
     * @since 1.5
     */
    public void restack() {
    }

    /**
     * Indicates availabiltity of restacking operation in this container.
     * @return Returns true if restack is supported, false otherwise
     * @since 1.5
     */
    public boolean isRestackSupported() {
	return false;
    }
}
