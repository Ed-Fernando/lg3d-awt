/**
 * Project Looking Glass
 *
 * $RCSfile: JawtTextFieldPeer.java,v $
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
 * $Date: 2006-09-22 21:10:26 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.delegate;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.peer.TextFieldPeer;
import org.jdesktop.lg3d.jawt.swing.JawtJTextField;

/**
 * Implementation of an AWT TextField peer (java.awt.peer.TextFieldPeer) using
 * Swing (JTextField)
 */
public class JawtTextFieldPeer extends JawtTextComponentPeerBase<JawtJTextField>
			       implements TextFieldPeer {

    TextField target;
    JawtJTextField delegate;

    public JawtTextFieldPeer(TextField target) {
	super(target, new JawtJTextField(target));

	this.target = target;
	this.delegate = (JawtJTextField)super.delegate;
    }

    public void setEchoChar(char echoChar) {
	// TODO: 
    }

    public Dimension getPreferredSize(int columns) {
	// TODO: What is the 'columns' arg ?
	return delegate.getPreferredSize();
    }

    public Dimension getMinimumSize(int columns) {
	// TODO: What is the 'columns' arg ?
	return delegate.getMinimumSize();
    }

    /**
     * DEPRECATED:  Replaced by setEchoChar(char echoChar).
     */
    public void setEchoCharacter(char c) {
	setEchoChar(c);
    }

    /**
     * DEPRECATED:  Replaced by getPreferredSize(int).
     */
    public Dimension preferredSize(int cols) {
	return getPreferredSize(cols);
    }

    /**
     * DEPRECATED:  Replaced by getMinimumSize(int).
     */
    public Dimension minimumSize(int cols) {
	return getMinimumSize(cols);
    }
}
