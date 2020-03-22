/**
 * Project Looking Glass
 *
 * $RCSfile: JawtEmbeddedToolkit.java,v $
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
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.peer.MouseInfoPeer;
import org.jdesktop.lg3d.jawt.JawtUnsupportedOperationException;
import org.jdesktop.lg3d.jawt.toplevel.JawtDialogPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFileDialogPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFramePeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtWindowPeer;

/**
 * Interface to be implemented by JAWT Container toolkit implementations.
 * These are (a subset of) all the methods from java.awt.Toolkit that the
 * Java Container has to implement. 
 *
 *
 * These methods are called by the corresponding method in {@link JawtToolkit}
 * and if they throw {@link JawtUnsupportedOperationException} then
 * <code>JawtToolkit</code> ends up punting the call to 'super' (the OS Toolkit
 * implementation)
 *
 * @see JawtToolkit
 *
 * @author krishna_gadepalli
 */
public interface JawtEmbeddedToolkit {

    public boolean isEnabled();
    public void setEnabled(boolean enabled);

    /**
     * Special methods.
     */
    public JawtEmbeddedFileDialogPeer createFileDialog(JawtFileDialogPeer peer) throws JawtUnsupportedOperationException;
    public JawtEmbeddedDialogPeer createDialog(JawtDialogPeer peer) throws JawtUnsupportedOperationException;
    public JawtEmbeddedFramePeer createFrame(JawtFramePeer peer) throws JawtUnsupportedOperationException;
    public JawtEmbeddedWindowPeer createWindow(JawtWindowPeer peer) throws JawtUnsupportedOperationException;

    /**
     * Methods punted from java.awt.Toolkit
     */
    public MouseInfoPeer getMouseInfoPeer() throws JawtUnsupportedOperationException;

    public EventQueue getSystemEventQueueImpl() throws JawtUnsupportedOperationException;

    public void loadSystemColors(int[] systemColors) throws JawtUnsupportedOperationException;

    public void setDynamicLayout(boolean dynamic) throws JawtUnsupportedOperationException;

    public boolean isDynamicLayoutSet() throws JawtUnsupportedOperationException;

    public boolean isDynamicLayoutActive() throws JawtUnsupportedOperationException;

    public Dimension getScreenSize() throws JawtUnsupportedOperationException;

    public int getScreenResolution() throws JawtUnsupportedOperationException;

    public Insets getScreenInsets(GraphicsConfiguration gc) throws JawtUnsupportedOperationException;
    
    public void sync() throws JawtUnsupportedOperationException;

    public void beep() throws JawtUnsupportedOperationException;

    public Clipboard getSystemClipboard() throws JawtUnsupportedOperationException;

    public Clipboard getSystemSelection() throws JawtUnsupportedOperationException;

    public int getMenuShortcutKeyMask() throws JawtUnsupportedOperationException;

    public Cursor createCustomCursor(Image cursor, Point hotSpot, String name) throws JawtUnsupportedOperationException;

    public Dimension getBestCursorSize(int preferredWidth, int preferredHeight) throws JawtUnsupportedOperationException;

    public int getMaximumCursorColors() throws JawtUnsupportedOperationException;
    
    public boolean isFrameStateSupported(int state) throws JawtUnsupportedOperationException;

    public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge) throws JawtUnsupportedOperationException;

    public <T extends DragGestureRecognizer> T
	createDragGestureRecognizer(Class<T> abstractRecognizerClass,
				    DragSource ds, Component c, int srcActions,
				    DragGestureListener dgl)
			throws JawtUnsupportedOperationException;

    public boolean isAlwaysOnTopSupported() throws JawtUnsupportedOperationException;

    public boolean isModalityTypeSupported(Dialog.ModalityType modalityType) throws JawtUnsupportedOperationException;

    public boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType modalExclusionType) throws JawtUnsupportedOperationException;
}
