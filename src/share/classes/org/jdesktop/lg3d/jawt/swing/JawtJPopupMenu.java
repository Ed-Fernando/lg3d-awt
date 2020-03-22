/**
 * Project Looking Glass
 *
 * $RCSfile: JawtJPopupMenu.java,v $
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
import java.awt.PopupMenu;
import javax.swing.JPopupMenu;
import org.jdesktop.lg3d.jawt.delegate.JawtDelegateInterface;

/**
 * A wrapper of the the Swing equivalent to expose some methods
 */
public class JawtJPopupMenu extends JPopupMenu implements JawtDelegateInterface {

    PopupMenu target;

    public JawtJPopupMenu(PopupMenu target) {
	super();
	this.target = target;

	setLabel(target.getLabel());
    }

    public void processAWTEvent(AWTEvent e) {
	super.processEvent(e);
    }
}
