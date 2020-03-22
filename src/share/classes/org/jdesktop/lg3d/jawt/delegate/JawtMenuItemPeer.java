/**
 * Project Looking Glass
 *
 * $RCSfile: JawtMenuItemPeer.java,v $
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

import java.awt.MenuItem;
import java.awt.peer.MenuItemPeer;
import org.jdesktop.lg3d.jawt.swing.JawtJMenuItem;

/**
 * Implementation of an AWT MenuItem peer (java.awt.peer.MenuItemPeer) using
 * Swing (JMenuItem)
 */
public class JawtMenuItemPeer extends JawtMenuItemPeerBase
			      implements MenuItemPeer {

    MenuItem target;
    JawtJMenuItem delegate;

    public JawtMenuItemPeer(MenuItem target) {
	super(target, new JawtJMenuItem(target));

	this.target = target;
	this.delegate = (JawtJMenuItem)super.delegate;
    }

    /**
     * MenuItemPeer.setLabel
     */
    public void setLabel(String label) {
	delegate.setText(label);
    }
}
