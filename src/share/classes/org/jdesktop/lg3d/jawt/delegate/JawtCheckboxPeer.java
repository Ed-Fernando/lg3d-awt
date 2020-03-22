/**
 * Project Looking Glass
 *
 * $RCSfile: JawtCheckboxPeer.java,v $
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

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.peer.CheckboxPeer;
import java.util.Hashtable;
import javax.swing.ButtonGroup;
import org.jdesktop.lg3d.jawt.swing.JawtJCheckBox;

/**
 * Implementation of an AWT Checkbox peer (java.awt.peer.CheckboxPeer) using
 * Swing (JCheckBox)
 */
public class JawtCheckboxPeer extends JawtComponentPeerBase<JawtJCheckBox>
			      implements CheckboxPeer {

    Checkbox target;
    JawtJCheckBox delegate;

    public JawtCheckboxPeer(Checkbox target) {
	super(target, new JawtJCheckBox(target));

	this.target = target;
	this.delegate = (JawtJCheckBox)super.delegate;
    }

    /**
     * CheckboxPeer.setState
     */
    public void setState(boolean t) {
	delegate.setSelected(t);
    }

    /**
     * CheckboxPeer.setLabel
     */
    public void setLabel(String label) {
	delegate.setText(label);
    }

    /**
     * CheckboxPeer.setCheckboxGroup
     *
     * Map the AWT CheckboxGroup to the Swing ButtonGroup.
     *
     * TODO: What happens when the Checkbox gets deleted - wont we have to 
     *	     remove it from the static Map ?
     */
    private static Hashtable<CheckboxGroup, ButtonGroup> checkBoxGroupMap =
				    new Hashtable<CheckboxGroup, ButtonGroup>();

    public void setCheckboxGroup(CheckboxGroup newGroup) {
	CheckboxGroup oldGroup = target.getCheckboxGroup();

	if (oldGroup != newGroup) {
	    ButtonGroup buttonGroup;

	    if (oldGroup != null) {
		if ((buttonGroup = checkBoxGroupMap.get(oldGroup)) != null)
		    checkBoxGroupMap.remove(delegate);

		if (oldGroup.getSelectedCheckbox() == target) {
		    oldGroup.setSelectedCheckbox(null);
		}
	    }

	    if (newGroup != null) {
		if ((buttonGroup = checkBoxGroupMap.get(newGroup)) == null)
		    checkBoxGroupMap.put(newGroup, (buttonGroup = new ButtonGroup()));

		buttonGroup.add(delegate);
	    }
	}
    }
}
