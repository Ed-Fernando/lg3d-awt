/**
 * Project Looking Glass
 *
 * $RCSfile: JawtContainerPeerBase.java,v $
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
 * $Date: 2006-11-15 07:10:52 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.delegate;

import java.awt.Container;
import java.awt.Insets;
import java.awt.peer.ContainerPeer;
import javax.swing.JComponent;

/**
 * All the AWT peers implemented using Swing will extend this class.
 *
 */
public abstract class JawtContainerPeerBase<T extends JComponent & JawtDelegateInterface>
		extends JawtComponentPeerBase<T>
		implements ContainerPeer {

    protected Container target;
    protected T delegate;

    public JawtContainerPeerBase(Container target, T delegate) {
	super(target, delegate);

	this.target = target;
	this.delegate = delegate;
    }     

    /* -------------------------------------------------------------------------- *
     * Provide default implementation of the ContainerPeer interface
     * -------------------------------------------------------------------------- */

    public Insets getInsets() {
	return delegate.getInsets();
    }

    public void beginValidate() {
	// TODO:
    }

    public void endValidate() {
	// TODO:
    }

    public void beginLayout() {
	// TODO:
    }

    public void endLayout() {
	// TODO:
    }

    public boolean isPaintPending() {
	// TODO:
	return false;
    }


    /**
     * Restacks native windows - children of this native window - according to Java container order
     * @since 1.5
     */
    public void restack() {
	// TODO:
    }

    /**
     * Indicates availabiltity of restacking operation in this container.
     * @return Returns true if restack is supported, false otherwise
     * @since 1.5
     */
    public boolean isRestackSupported() {
	// TODO:
	return false;
    }

    /**
     * DEPRECATED:  Replaced by getInsets().
     */
    @SuppressWarnings("deprecation")
    public Insets insets() {
	return getInsets();
    }

}
