/**
 * Project Looking Glass
 *
 * $RCSfile: JawtPopupMenuPeer.java,v $
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
 * $Date: 2006-09-22 21:10:25 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.delegate;

import java.awt.Component;
import java.awt.Event;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.peer.PopupMenuPeer;
import javax.swing.JMenuItem;
import org.jdesktop.lg3d.jawt.swing.JawtJPopupMenu;

/**
 * Implementation of an AWT PopupMenu peer (java.awt.peer.PopupMenuPeer) using
 * Swing (JPopupMenu)
 */
public class JawtPopupMenuPeer extends JawtMenuItemPeerBase
			       implements PopupMenuPeer {

    PopupMenu target;
    JawtJPopupMenu delegate;

    public JawtPopupMenuPeer(PopupMenu target) {
	super(target, new JawtJPopupMenu(target));

	this.target = target;
	this.delegate = (JawtJPopupMenu)super.delegate;
    }

    /**
     * PopupMenuPeer.show
     */
    @SuppressWarnings("deprecation")
    public void show(Event e) {
	Component invoker = (Component)target.getPeer();
	delegate.show(invoker, e.x, e.y);
    }

    /**
     * PopupMenuPeer.MenuPeer.addSeparator()
     */
    public void addSeparator() {
	delegate.addSeparator();
    }

    /**
     * PopupMenuPeer.MenuPeer.addItem(MenuItem item)
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
     * PopupMenuPeer.MenuPeer.delItem(int index)
     */
    public void delItem(int index) {
	delegate.remove(index);
    }

    /**
     * MenuItemPeer.setLabel
     */
    public void setLabel(String label) {
	delegate.setLabel(label);
    }
}
