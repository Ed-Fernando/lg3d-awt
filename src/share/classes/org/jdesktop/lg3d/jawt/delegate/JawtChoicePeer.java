/**
 * Project Looking Glass
 *
 * $RCSfile: JawtChoicePeer.java,v $
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

import java.awt.Choice;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.peer.ChoicePeer;
import org.jdesktop.lg3d.jawt.swing.JawtJComboBox;

/**
 * Implementation of an AWT Choice peer (java.awt.peer.ChoicePeer) using
 * Swing (JComboBox)
 */
public class JawtChoicePeer extends JawtComponentPeerBase<JawtJComboBox>
        implements ChoicePeer {
    
    Choice target;
    JawtJComboBox delegate;
    
    public JawtChoicePeer(Choice target) {
        super(target, new JawtJComboBox(target));
        
        this.target = target;
        this.delegate = (JawtJComboBox)super.delegate;
    }
    
    public void add(String item, int index) {
        if ((index < 0) || (index >= delegate.getItemCount()))
            delegate.addItem(item);
        else
            delegate.insertItemAt(item, index);
    }
    
    public void remove(int index) {
        delegate.removeItemAt(index);
    }
    
    public void removeAll() {
        delegate.removeAllItems();
    }
    
    public void select(int index) {
        delegate.setSelectedIndex(index);
    }
    
    /*
     * DEPRECATED:  Replaced by add(String, int).
     */
    @SuppressWarnings("deprecation")
    public void addItem(String item, int index) {
        add(item, index);
    }
}
