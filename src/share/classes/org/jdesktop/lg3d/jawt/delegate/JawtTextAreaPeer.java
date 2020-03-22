/**
 * Project Looking Glass
 *
 * $RCSfile: JawtTextAreaPeer.java,v $
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

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.peer.TextAreaPeer;
import org.jdesktop.lg3d.jawt.swing.JawtJTextArea;

/**
 * Implementation of an AWT TextArea peer (java.awt.peer.TextAreaPeer) using
 * Swing (JTextArea)
 */
public class JawtTextAreaPeer extends JawtTextComponentPeerBase<JawtJTextArea>
			      implements TextAreaPeer {

    TextArea target;
    JawtJTextArea delegate;

    public JawtTextAreaPeer(TextArea target) {
	super(target, new JawtJTextArea(target));

	this.target = target;
	this.delegate = (JawtJTextArea)super.delegate;

	addToContainer(delegate, delegate.getPane());
    }

    public void insert(String text, int pos) {
	delegate.insert(text, pos);
    }

    public void replaceRange(String text, int start, int end) {
	delegate.replaceRange(text, start, end);
    }

    public Dimension getPreferredSize(int rows, int columns) {
	// TODO: What is the 'columns' arg ?
	return delegate.getPreferredSize();
    }

    public Dimension getMinimumSize(int rows, int columns) {
	// TODO: What is the 'columns' arg ?
	return delegate.getMinimumSize();
    }

    /**
     * DEPRECATED:  Replaced by insert(String, int).
     */
    public void insertText(String txt, int pos) {
	insert(txt, pos);
    }

    /**
     * DEPRECATED:  Replaced by ReplaceRange(String, int, int).
     */
    public void replaceText(String txt, int start, int end) {
	replaceRange(txt, start, end);
    }

    /**
     * DEPRECATED:  Replaced by getPreferredSize(int, int).
     */
    public Dimension preferredSize(int rows, int cols) {
	return getPreferredSize(rows, cols);
    }

    /**
     * DEPRECATED:  Replaced by getMinimumSize(int, int).
     */
    public Dimension minimumSize(int rows, int cols) {
	return getMinimumSize(rows, cols);
    }
}
