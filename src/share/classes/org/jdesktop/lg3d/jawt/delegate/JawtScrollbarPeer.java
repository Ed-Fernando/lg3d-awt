/**
 * Project Looking Glass
 *
 * $RCSfile: JawtScrollbarPeer.java,v $
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

import java.awt.Scrollbar;
import java.awt.peer.ScrollbarPeer;
import org.jdesktop.lg3d.jawt.swing.JawtJScrollBar;

/**
 * Implementation of an AWT Scrollbar peer (java.awt.peer.ScrollbarPeer) using
 * Swing (JScrollBar)
 */
public class JawtScrollbarPeer extends JawtComponentPeerBase<JawtJScrollBar>
			       implements ScrollbarPeer {

    Scrollbar target;
    JawtJScrollBar delegate;

    public JawtScrollbarPeer(Scrollbar target) {
	super(target, new JawtJScrollBar(target));

	target = (Scrollbar)target;
	delegate = (JawtJScrollBar)delegate;
    }

    /**
     * private method.
     */
    private void setOrientation(int targetOrientation) {
	delegate.setOrientation(delegate.getOrientation(targetOrientation));
    }

    /**
     * ScrollbarPeer.setValues
     */
    public void setValues(int value, int visible, int minimum, int maximum) {
	delegate.setMinimum(minimum);
	delegate.setMaximum(maximum);
	delegate.setValue(value);
	delegate.setVisibleAmount(visible);
    }

    public void setLineIncrement(int l) {
	delegate.setUnitIncrement(l);
    }

    public void setPageIncrement(int l) {
	delegate.setBlockIncrement(l);
    }
}
