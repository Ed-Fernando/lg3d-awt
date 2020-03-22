/**
 * Project Looking Glass
 *
 * $RCSfile: JawtMenuPeer.java,v $
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

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.peer.MenuPeer;
import javax.swing.JMenuItem;
import org.jdesktop.lg3d.jawt.swing.JawtJMenu;

/**
 * Implementation of an AWT Menu peer (java.awt.peer.MenuPeer) using
 * Swing (JMenu)
 */
public class JawtMenuPeer extends JawtMenuItemPeerBase
			  implements MenuPeer {

    Menu target;
    JawtJMenu delegate;

    public JawtMenuPeer(Menu target) {
	super(target, new JawtJMenu(target));

	this.target = target;
	this.delegate = (JawtJMenu)super.delegate;
    }

    /**
     * MenuPeer.addSeparator()
     */
    public void addSeparator() {
	delegate.addSeparator();
    }

    /**
     * MenuPeer.addItem(MenuItem item)
     */
    @SuppressWarnings("deprecation")
    public void addItem(MenuItem item) {
	// This will create the peer...
	item.addNotify();

	if ("-".equals(item.getLabel()))
	    addSeparator();
	else
	    delegate.add((JMenuItem)item.getPeer());
    }

    /**
     * MenuPeer.delItem(int index)
     */
    public void delItem(int index) {
	delegate.remove(index);
    }

    /**
     * MenuItemPeer.setLabel
     */
    public void setLabel(String label) {
	delegate.setText(label);
    }
}
