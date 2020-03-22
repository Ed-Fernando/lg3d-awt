/**
 * Project Looking Glass
 *
 * $RCSfile: JawtContainerPeer.java,v $
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

import java.awt.Container;
import java.awt.Insets;
import java.awt.peer.ContainerPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedContainerPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedToolkit;

/**
 * All the AWT peers implemented using Swing will extend this class.
 *
 */
public abstract class JawtContainerPeer extends JawtComponentPeer implements ContainerPeer {

    protected Container target;
    protected JawtEmbeddedContainerPeer peer;

    public JawtContainerPeer(Container target, JawtEmbeddedToolkit containerToolkit) {
	super(target, containerToolkit);

	this.target = target;
	this.peer = (JawtEmbeddedContainerPeer)super.peer;
    }

    public Insets getInsets() {
	return peer.getInsets();
    }

    public void beginValidate() {
	// TODO: What is this used for ?
    }

    public void endValidate() {
	// TODO: What is this used for ?
    }

    public void beginLayout() {
	peer.beginLayout();
    }

    public void endLayout() {
	peer.endLayout();
    }

    public boolean isPaintPending() {
	return peer.isPaintPending();
    }

    /**
     * Restacks native windows - children of this native window - according to Java container order
     * @since 1.5
     */
    public void restack() {
	peer.restack();
    }

    /**
     * Indicates availabiltity of restacking operation in this container.
     * @return Returns true if restack is supported, false otherwise
     * @since 1.5
     */
    public boolean isRestackSupported() {
	return peer.isRestackSupported();
    }

    /**
     * DEPRECATED:  Replaced by getInsets().
     */
    @SuppressWarnings("deprecation")
    public Insets insets() {
	return getInsets();
    }
}
