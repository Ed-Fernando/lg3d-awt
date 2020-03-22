/**
 * Project Looking Glass
 *
 * $RCSfile: TestEmbeddedToolkit.java,v $
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

package org.jdesktop.lg3d.jawt.test;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.peer.MouseInfoPeer;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import org.jdesktop.lg3d.jawt.JawtUnsupportedOperationException;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedDialogPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedFileDialogPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedFramePeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedToolkitImpl;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedWindowPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtDialogPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFileDialogPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFramePeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtWindowPeer;

/**
 * An abstract class implementing the JawtContainerToolkit interface by
 * simply throwing an JawtUnsupportedOperationException by default.
 *
 * @author krishna_gadepalli
 */
public class TestEmbeddedToolkit extends JawtEmbeddedToolkitImpl {

    private static Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt.test");

    JDesktopPane desktop;

    public TestEmbeddedToolkit() {
	super();
    }

    void setDesktopPane(JDesktopPane desktop) {
	this.desktop = desktop;
	setEnabled(desktop != null);
    }

    JDesktopPane getDesktopPane() {
	return desktop;
    }

    public void addToDesktopPane(final JInternalFrame delegate) {
	if ((desktop == null) || (delegate == null))
	    return;

	if (delegate.getParent() != desktop) {
	    if (SwingUtilities.isEventDispatchThread())
		desktop.add(delegate);
	    else
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			desktop.add(delegate);
		    }
		});
	}
    }

    public JawtEmbeddedFileDialogPeer createFileDialog(JawtFileDialogPeer peer) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
        return new TestEmbeddedFileDialogPeer(this, peer);
    }

    public JawtEmbeddedDialogPeer createDialog(JawtDialogPeer peer) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
        return new TestEmbeddedDialogPeer(this, peer);
    }

    public JawtEmbeddedFramePeer createFrame(JawtFramePeer peer) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
        return new TestEmbeddedFramePeer(this, peer);
    }

    public JawtEmbeddedWindowPeer createWindow(JawtWindowPeer peer) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
        return new TestEmbeddedWindowPeer(this, peer);
    }

    public MouseInfoPeer getMouseInfoPeer() {
	if (desktop == null) throw new JawtUnsupportedOperationException();
        return null;
    }

    public EventQueue getSystemEventQueueImpl() {
	throw new JawtUnsupportedOperationException();
    }

    public void setDynamicLayout(boolean dynamic) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
    }

    public boolean isDynamicLayoutSet() {
	if (desktop == null) throw new JawtUnsupportedOperationException();
	return false;
    }

    public boolean isDynamicLayoutActive() {
	if (desktop == null) throw new JawtUnsupportedOperationException();
	return false;
    }

    public Dimension getScreenSize() {
	if (desktop == null) throw new JawtUnsupportedOperationException();

	// We report twice the screen size and then draw everything at half the size
	Dimension d = desktop.getSize();
	d.setSize(d.getWidth()*2, d.getHeight()*2);
	return d;
    }

    public int getScreenResolution() {
	if (desktop == null) throw new JawtUnsupportedOperationException();
	return 1;
    }

    public Insets getScreenInsets(GraphicsConfiguration gc) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
	return new Insets(0, 0, 0, 0);
    }
    
    public boolean isFrameStateSupported(int state) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
	return true;
    }

    public boolean isAlwaysOnTopSupported() {
	if (desktop == null) throw new JawtUnsupportedOperationException();
	return false;
    }

    public boolean isModalityTypeSupported(Dialog.ModalityType modalityType) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
	return false;
    }

    public boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType modalExclusionType) {
	if (desktop == null) throw new JawtUnsupportedOperationException();
	return false;
    }
}
