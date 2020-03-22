/**
 * Project Looking Glass
 *
 * $RCSfile: JawtListPeer.java,v $
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

import java.awt.Dimension;
import java.awt.List;
import java.awt.peer.ListPeer;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import org.jdesktop.lg3d.jawt.swing.JawtJList;

/**
 * Implementation of an AWT List peer (java.awt.peer.ListPeer) using
 * Swing (JList)
 */
public class JawtListPeer extends JawtComponentPeerBase<JawtJList>
			  implements ListPeer {

    List target;
    JawtJList delegate;
    DefaultListModel listModel;

    public JawtListPeer(List target) {
	super(target, new JawtJList(target));

	this.target = target;
	this.delegate = (JawtJList)super.delegate;

	listModel = delegate.getListModel();
    }

    public int[] getSelectedIndexes() {
	return delegate.getSelectedIndices();
    }

    public void add(String item, int index) {
	if ((index < 0) || (index >= listModel.getSize()))
	    listModel.addElement(item);
	else
	    listModel.insertElementAt(item, index);
    }

    public void delItems(int start, int end) {
	if (start < 0) start = 0;
	if (end >= listModel.getSize()) end = listModel.getSize() - 1;

	for (int i = end; i >= start; i--)
	    listModel.removeElementAt(i);
    }

    public void removeAll() {
	listModel.removeAllElements();
    }

    public void select(int index) {
	delegate.setSelectedIndex(index);
    }

    public void deselect(int index) {
	delegate.removeSelectionInterval(index, index);
    }

    public void makeVisible(int index) {
	delegate.ensureIndexIsVisible(index);
    }

    public void setMultipleMode(boolean b) {
	delegate.setSelectionMode(
		b ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
		  : ListSelectionModel.SINGLE_SELECTION);
    }

    public Dimension getPreferredSize(int rows) {
	// TODO: what is 'rows' for ?
	return delegate.getPreferredSize();
    }

    public Dimension getMinimumSize(int rows) {
	// TODO: what is 'rows' for ?
	return delegate.getMinimumSize();
    }

    /**
     * DEPRECATED:  Replaced by add(String, int).
     */
    public void addItem(String item, int index) {
	add(item, index);
    }

    /**
     * DEPRECATED:  Replaced by removeAll().
     */
    public void clear() {
	removeAll();
    }

    /**
     * DEPRECATED:  Replaced by setMultipleMode(boolean).
     */
    public void setMultipleSelections(boolean v) {
	setMultipleMode(v);
    }

    /**
     * DEPRECATED:  Replaced by getPreferredSize(int).
     */
    public Dimension preferredSize(int v) {
	return getPreferredSize(v);
    }

    /**
     * DEPRECATED:  Replaced by getMinimumSize(int).
     */
    public Dimension minimumSize(int v) {
	return getPreferredSize(v);
    }
}
