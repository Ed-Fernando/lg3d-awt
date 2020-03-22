/**
 * Project Looking Glass
 *
 * $RCSfile: JawtCheckboxMenuItemPeer.java,v $
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
 * $Date: 2006-09-22 21:10:22 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.delegate;

import java.awt.CheckboxMenuItem;
import java.awt.peer.CheckboxMenuItemPeer;
import org.jdesktop.lg3d.jawt.swing.JawtJCheckBoxMenuItem;

/**
 * Implementation of an AWT CheckboxMenuItem peer (java.awt.peer.CheckboxMenuItemPeer) using
 * Swing (JCheckBoxMenuItem)
 */
public class JawtCheckboxMenuItemPeer extends JawtMenuItemPeerBase
	    implements CheckboxMenuItemPeer {

    CheckboxMenuItem target;
    JawtJCheckBoxMenuItem delegate;

    public JawtCheckboxMenuItemPeer(CheckboxMenuItem target) {
	super(target, new JawtJCheckBoxMenuItem(target));

	this.target = target;
	this.delegate = (JawtJCheckBoxMenuItem)super.delegate;
    }

    /**
     * CheckboxMenuItemPeer.setState
     */
    public void setState(boolean t) {
	delegate.setState(t);
    }

    /**
     * CheckboxMenuItemPeer.MenuItemPeer.setLabel
     */
    public void setLabel(String label) {
	delegate.setText(label);
    }
}
