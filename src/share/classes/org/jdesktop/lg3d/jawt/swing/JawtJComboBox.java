/**
 * Project Looking Glass
 *
 * $RCSfile: JawtJComboBox.java,v $
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
 * $Revision: 1.4 $
 * $Date: 2006-09-22 21:10:32 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.swing;

import java.awt.AWTEvent;
import java.awt.Choice;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import org.jdesktop.lg3d.jawt.delegate.JawtDelegateInterface;
import org.jdesktop.lg3d.jawt.delegate.JawtPeerHelper;

/**
 * A wrapper of the the Swing equivalent to expose some methods
 */
public class JawtJComboBox extends JComboBox
		implements JawtDelegateInterface, ItemListener {

    Choice target;

    public JawtJComboBox(Choice target) {
	super();
	this.target = target;

	for (int i = 0; i < target.getItemCount(); i++)
	    addItem(target.getItem(i));

	addItemListener(this);
    }

    public void processAWTEvent(AWTEvent e) {
	super.processEvent(e);
    }

    /**
     * Change the source of the event to be the delegate and then have it
     * processed.
     */
    public void processEvent(AWTEvent e) {
	if (e.getSource() == target) {
	    if ((e = JawtPeerHelper.setEventSource(e, this)) != null)
		target.dispatchEvent(e);
	} else 
	    super.processEvent(e);
    }

    /**
     * ItemListener.itemStateChanged
     */
    public void itemStateChanged(ItemEvent event) {
	target.dispatchEvent(new ItemEvent(target,
			     event.getID(),
			     event.getItem(),
			     event.getStateChange()));
    }
}
