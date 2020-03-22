/**
 * Project Looking Glass
 *
 * $RCSfile: JawtMenuItemPeerBase.java,v $
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
import javax.swing.JComponent;

public abstract class JawtMenuItemPeerBase extends JawtMenuComponentPeerBase
					   implements MenuItemPeer {

    protected MenuItem target;
    protected JComponent delegate;

    public JawtMenuItemPeerBase(MenuItem target, JComponent delegate) {
	super(target, delegate);

	this.target = target;
	this.delegate = delegate;
    }     

    /**
     * MenuItemPeer.setLabel
     */
    public abstract void setLabel(String label);

    /**
     * MenuItemPeer.setEnabled
     */
    public void setEnabled(boolean b) {
	delegate.setEnabled(b);
    }

    /**
     * DEPRECATED:  Replaced by setEnabled(boolean).
     */
    public void enable() {
	setEnabled(true);
    }

    /**
     * DEPRECATED:  Replaced by setEnabled(boolean).
     */
    public void disable() {
	setEnabled(false);
    }
}
