/**
 * Project Looking Glass
 *
 * $RCSfile: JawtMenuComponentPeerBase.java,v $
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
 * $Date: 2006-09-22 21:10:24 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.delegate;

import java.awt.Font;
import java.awt.MenuComponent;
import java.awt.peer.MenuComponentPeer;
import javax.swing.JComponent;

public abstract class JawtMenuComponentPeerBase implements MenuComponentPeer {

    protected MenuComponent target;
    protected JComponent delegate;

    public JawtMenuComponentPeerBase(MenuComponent target, JComponent delegate) {
	this.target = target;
	this.delegate = delegate;
    }     

    /**
     * If the target Component's parent is some sort of a Container,
     * then add the peer to it.
     */
    public static final void addToContainer(MenuComponent target, JComponent peer) {
	// TODO:
    }

    /**
     * MenuComponentPeer.dispose
     */
    public void dispose() {
	// TODO: ??
    }

    /**
     * MenuComponentPeer.setFont
     */
    public void setFont(Font f) {
	delegate.setFont(f);
    }

}
