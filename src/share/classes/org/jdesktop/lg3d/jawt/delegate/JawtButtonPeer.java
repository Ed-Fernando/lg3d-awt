/**
 * Project Looking Glass
 *
 * $RCSfile: JawtButtonPeer.java,v $
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

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.peer.ButtonPeer;
import org.jdesktop.lg3d.jawt.swing.JawtJButton;

/**
 * Implementation of an AWT Button peer (java.awt.peer.ButtonPeer) using
 * Swing
 */
public class JawtButtonPeer extends JawtComponentPeerBase<JawtJButton>
			    implements ButtonPeer {

    Button target;
    JawtJButton delegate;

    public JawtButtonPeer(Button target) {
	super(target, new JawtJButton(target));

	this.target = (Button)super.target;
	this.delegate = (JawtJButton)super.delegate;
    }

    /**
     * ButtonPeer.setLabel
     */
    public void setLabel(String label) {
	delegate.setText(label);
    }

    /**
     * Change the source of the event to be the delegate and then have it
     * processed.
     */
    public void handleEvent(AWTEvent e) {
	if (e.getSource() == target) {
	    super.handleEvent(e);
	}
    }
}
