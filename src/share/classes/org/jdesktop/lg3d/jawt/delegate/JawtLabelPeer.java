/**
 * Project Looking Glass
 *
 * $RCSfile: JawtLabelPeer.java,v $
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
 * $Date: 2006-09-22 21:10:23 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.delegate;

import java.awt.Label;
import java.awt.peer.LabelPeer;
import org.jdesktop.lg3d.jawt.swing.JawtJLabel;

/**
 * Implementation of an AWT Label peer (java.awt.peer.LabelPeer) using
 * Swing (JLabel)
 */
public class JawtLabelPeer extends JawtComponentPeerBase<JawtJLabel>
			   implements LabelPeer {

    Label target;
    JawtJLabel delegate;

    public JawtLabelPeer(Label target) {
	super(target, new JawtJLabel(target));

	this.target = target;
	this.delegate = (JawtJLabel)super.delegate;
    }

    public void setText(String label) {
	delegate.setText(label);
    }

    public void setAlignment(int alignment) {
	delegate.setLabelAlignment(alignment);
    }
}
