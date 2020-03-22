/**
 * Project Looking Glass
 *
 * $RCSfile: JawtJMenuItem.java,v $
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
 * $Date: 2006-09-22 21:10:33 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.swing;

import java.awt.AWTEvent;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import org.jdesktop.lg3d.jawt.delegate.JawtDelegateInterface;

/**
 * A wrapper of the the Swing equivalent to expose some methods
 */
public class JawtJMenuItem extends JMenuItem
			   implements JawtDelegateInterface, ActionListener {

    MenuItem target;

    public JawtJMenuItem(MenuItem target) {
	super();
	this.target = target;

	setText(target.getLabel());
	addActionListener(this);
    }

    public void processAWTEvent(AWTEvent e) {
	super.processEvent(e);
    }

    public String getActionCommand() {
	return target.getActionCommand();
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
