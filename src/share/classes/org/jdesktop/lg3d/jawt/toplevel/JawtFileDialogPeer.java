/**
 * Project Looking Glass
 *
 * $RCSfile: JawtFileDialogPeer.java,v $
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
 * $Date: 2006-11-15 07:10:57 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.toplevel;

import java.awt.FileDialog;
import java.awt.peer.FileDialogPeer;
import java.io.FilenameFilter;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedFileDialogPeer;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedToolkit;

/**
 * All the AWT peers implemented using Swing will extend this class.
 *
 */
public class JawtFileDialogPeer extends JawtDialogPeer implements FileDialogPeer {

    protected FileDialog target;
    protected JawtEmbeddedFileDialogPeer peer;

    public JawtFileDialogPeer(FileDialog target, JawtEmbeddedToolkit containerToolkit) {
	super(target, containerToolkit);

	this.target = target;
	this.peer = (JawtEmbeddedFileDialogPeer)super.peer;

	// TODO: Set default properties for the JawtEmbeddedFileDialogPeer so that
	//	 it behaves like a FileDialogPeer
    }

    public void setFile(String file) {
    }

    public void setDirectory(String dir) {
    }

    public void setFilenameFilter(FilenameFilter filter) {
    }
}
