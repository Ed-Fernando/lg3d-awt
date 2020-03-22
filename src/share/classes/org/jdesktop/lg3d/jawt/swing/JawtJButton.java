/**
 * Project Looking Glass
 *
 * $RCSfile: JawtJButton.java,v $
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
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.jdesktop.lg3d.jawt.delegate.JawtDelegateInterface;
import org.jdesktop.lg3d.jawt.delegate.JawtPeerHelper;

/**
 * A wrapper of the the Swing equivalent to expose some methods
 */
public class JawtJButton extends JButton
		implements JawtDelegateInterface, ActionListener  {
    
    Button target;
    
    public JawtJButton(Button target) {
        super();
        this.target = target;
	
	setText(target.getLabel());

	enableEvents(~0L);
	addActionListener(this);
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
     * ActionListener.actionPerformed
     */
    public void actionPerformed(ActionEvent event) {
	target.dispatchEvent(
	    new ActionEvent(target,
			    ActionEvent.ACTION_PERFORMED,
			    target.getActionCommand(),
			    event.getWhen(),
			    event.getModifiers()));
    }
}
