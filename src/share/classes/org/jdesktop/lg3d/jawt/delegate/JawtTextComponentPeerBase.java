/**
 * Project Looking Glass
 *
 * $RCSfile: JawtTextComponentPeerBase.java,v $
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

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextComponent;
import java.awt.im.InputMethodRequests;
import java.awt.peer.TextComponentPeer;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 * All the AWT peers implemented using Swing will extend this class.
 *
 */
public abstract class JawtTextComponentPeerBase<T extends JTextComponent & JawtDelegateInterface>
		extends JawtComponentPeerBase<T>
		implements TextComponentPeer {

    protected TextComponent target;
    protected T delegate;

    public JawtTextComponentPeerBase(TextComponent target, T delegate) {
	super(target, delegate);

	this.target = target;
	this.delegate = delegate;
    }     

    /* -------------------------------------------------------------------------- *
     * Provide default implementation of the TextComponentPeer interface
     * -------------------------------------------------------------------------- */

    public void setEditable(boolean editable) {
	delegate.setEditable(editable);
    }

    public String getText() {
	return delegate.getText();
    }

    public void setText(String l) {
	delegate.setText(l);
    }

    public int getSelectionStart() {
	return delegate.getSelectionStart();
    }

    public int getSelectionEnd() {
	return delegate.getSelectionEnd();
    }

    public void select(int selStart, int selEnd) {
	delegate.select(selStart, selEnd);
    }

    public void setCaretPosition(int pos) {
	delegate.setCaretPosition(pos);
    }

    public int getCaretPosition() {
	return delegate.getCaretPosition();
    }

    public int getIndexAtPoint(int x, int y) {
	return delegate.viewToModel(new Point(x,y));
    }

    public Rectangle getCharacterBounds(int i) {
	try {
	    return delegate.modelToView(i);

	} catch (BadLocationException ble) {
	    return null;
	}
    }

    public long filterEvents(long mask) {
	// TODO:
	return 0L;
    }

    public InputMethodRequests getInputMethodRequests() {
	return delegate.getInputMethodRequests();
    }
}
