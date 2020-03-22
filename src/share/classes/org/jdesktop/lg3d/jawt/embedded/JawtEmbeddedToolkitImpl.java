/**
 * Project Looking Glass
 *
 * $RCSfile: JawtEmbeddedToolkitImpl.java,v $
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
 * $Revision: 1.2 $
 * $Date: 2006-12-14 00:33:37 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.embedded;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.peer.MouseInfoPeer;
import org.jdesktop.lg3d.jawt.JawtToolkit;
import org.jdesktop.lg3d.jawt.JawtUnsupportedOperationException;
import org.jdesktop.lg3d.jawt.toplevel.JawtDialogPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFileDialogPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFramePeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtWindowPeer;

/**
 * An abstract class implementing the JawtEmbeddedToolkit interface by
 * simply throwing an JawtUnsupportedOperationException by default.
 *
 * @author krishna_gadepalli
 */
public abstract class JawtEmbeddedToolkitImpl implements JawtEmbeddedToolkit {

    private boolean enabled;

    protected JawtEmbeddedToolkitImpl() {
	this.enabled = false;
    }

    public boolean isEnabled() {
	return enabled;
    }

    public void setEnabled(boolean enabled) {
	this.enabled = enabled;

	Toolkit tk = Toolkit.getDefaultToolkit();
	if (tk instanceof JawtToolkit)
	    ((JawtToolkit)tk).setEmbeddedToolkitEnabled(enabled);
    }

    public JawtEmbeddedFileDialogPeer createFileDialog(JawtFileDialogPeer peer) {
	throw new JawtUnsupportedOperationException();
    }

    public JawtEmbeddedDialogPeer createDialog(JawtDialogPeer peer) {
	throw new JawtUnsupportedOperationException();
    }

    public JawtEmbeddedFramePeer createFrame(JawtFramePeer peer) {
	throw new JawtUnsupportedOperationException();
    }

    public JawtEmbeddedWindowPeer createWindow(JawtWindowPeer peer) {
	throw new JawtUnsupportedOperationException();
    }

    public MouseInfoPeer getMouseInfoPeer() {
	throw new JawtUnsupportedOperationException();
    }

    public EventQueue getSystemEventQueueImpl() {
	throw new JawtUnsupportedOperationException();
    }

    public void loadSystemColors(int[] systemColors) {
	throw new JawtUnsupportedOperationException();
    }

    public void setDynamicLayout(boolean dynamic) {
	throw new JawtUnsupportedOperationException();
    }

    public boolean isDynamicLayoutSet() {
	throw new JawtUnsupportedOperationException();
    }

    public boolean isDynamicLayoutActive() {
	throw new JawtUnsupportedOperationException();
    }

    public Dimension getScreenSize() {
	throw new JawtUnsupportedOperationException();
    }

    public int getScreenResolution() {
	throw new JawtUnsupportedOperationException();
    }

    public Insets getScreenInsets(GraphicsConfiguration gc) {
	throw new JawtUnsupportedOperationException();
    }
    
    public void sync() {
	throw new JawtUnsupportedOperationException();
    }

    public void beep() {
	throw new JawtUnsupportedOperationException();
    }

    public Clipboard getSystemClipboard() {
	throw new JawtUnsupportedOperationException();
    }

    public Clipboard getSystemSelection() {
	throw new JawtUnsupportedOperationException();
    }

    public int getMenuShortcutKeyMask() {
	throw new JawtUnsupportedOperationException();
    }

    public Cursor createCustomCursor(Image cursor, Point hotSpot, String name) {
	throw new JawtUnsupportedOperationException();
    }

    public Dimension getBestCursorSize(int preferredWidth, int preferredHeight) {
	throw new JawtUnsupportedOperationException();
    }

    public int getMaximumCursorColors() {
	throw new JawtUnsupportedOperationException();
    }
    
    public boolean isFrameStateSupported(int state) {
	throw new JawtUnsupportedOperationException();
    }

    public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge) {
	throw new JawtUnsupportedOperationException();
    }

    public <T extends DragGestureRecognizer> T
	createDragGestureRecognizer(Class<T> abstractRecognizerClass,
				    DragSource ds, Component c, int srcActions,
				    DragGestureListener dgl) {
	throw new JawtUnsupportedOperationException();
    }

    public boolean isAlwaysOnTopSupported() {
	throw new JawtUnsupportedOperationException();
    }

    public boolean isModalityTypeSupported(Dialog.ModalityType modalityType) {
	throw new JawtUnsupportedOperationException();
    }

    public boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType modalExclusionType) {
	throw new JawtUnsupportedOperationException();
    }
}
