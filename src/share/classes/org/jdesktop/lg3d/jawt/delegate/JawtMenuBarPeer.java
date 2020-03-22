/**
 * Project Looking Glass
 *
 * $RCSfile: JawtMenuBarPeer.java,v $
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
import java.awt.MenuBar;
import java.awt.peer.MenuBarPeer;
import javax.swing.JMenu;
import org.jdesktop.lg3d.jawt.swing.JawtJMenuBar;

/**
 * Implementation of an AWT MenuBar peer (java.awt.peer.MenuBarPeer) using
 * Swing (JMenuBar)
 */
public class JawtMenuBarPeer extends JawtMenuComponentPeerBase
			     implements MenuBarPeer {

    MenuBar target;
    JawtJMenuBar delegate;

    public JawtMenuBarPeer(MenuBar target) {
	super(target, new JawtJMenuBar(target));

	this.target = target;
	this.delegate = (JawtJMenuBar)super.delegate;

	addToContainer(target, delegate);

	for (int i = 0; i < target.getMenuCount(); i++)
	    addMenu(target.getMenu(i));
    }

    @SuppressWarnings("deprecation")
    public void addMenu(Menu m) {
	m.addNotify();

	delegate.add((JMenu)m.getPeer());
    }

    public void delMenu(int index) {
	delegate.remove(index);
    }

    public void addHelpMenu(Menu m) {
	addMenu(m);
    }
}
