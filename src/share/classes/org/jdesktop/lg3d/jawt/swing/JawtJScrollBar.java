/**
 * Project Looking Glass
 *
 * $RCSfile: JawtJScrollBar.java,v $
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
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import org.jdesktop.lg3d.jawt.delegate.JawtDelegateInterface;

/**
 * A wrapper of the the Swing equivalent to expose some methods
 */
public class JawtJScrollBar extends JScrollBar
		implements JawtDelegateInterface, AdjustmentListener {

    Scrollbar target;

    public JawtJScrollBar(Scrollbar target) {
	super();
	this.target = target;

	setUnitIncrement(target.getUnitIncrement());
	setBlockIncrement(target.getBlockIncrement());
	setValues(target.getValue(),target.getVisibleAmount(),
		  target.getMinimum(),target.getMaximum());

	setOrientation(target.getOrientation());

	enableEvents(~0L);
	addAdjustmentListener(this);
    }

    public void processAWTEvent(AWTEvent e) {
	super.processEvent(e);
    }

    /*
     * Private method.
     */
    public int getOrientation(int targetOrientation) {
	return (targetOrientation == Scrollbar.HORIZONTAL) ?
		    SwingConstants.HORIZONTAL : SwingConstants.VERTICAL;
    }

    public int getOrientation() {
	int newOrientation = getOrientation(target.getOrientation());

	if (newOrientation != super.getOrientation())
	    setOrientation(newOrientation);

	return newOrientation;
    }

    /**
     * AdjustmentListener.adjustmentValueChanged
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
	target.setValue(e.getValue());
    }
}
