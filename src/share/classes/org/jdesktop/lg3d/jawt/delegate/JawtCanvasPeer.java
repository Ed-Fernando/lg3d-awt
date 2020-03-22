/**
 * Project Looking Glass
 *
 * $RCSfile: JawtCanvasPeer.java,v $
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
 * $Date: 2006-09-22 21:10:22 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.delegate;

import java.awt.Canvas;
import java.awt.peer.CanvasPeer;
import org.jdesktop.lg3d.jawt.swing.JawtJPanel;

/**
 * Implementation of an AWT Canvas peer (java.awt.peer.CanvasPeer) using
 * Swing (JPanel)
 */
public class JawtCanvasPeer extends JawtComponentPeerBase<JawtJPanel>
				      implements CanvasPeer {

    Canvas target;
    JawtJPanel delegate;

    public JawtCanvasPeer(Canvas target) {
	super(target, new JawtJPanel(target));

	this.target = (Canvas)super.target;
	this.delegate = (JawtJPanel)super.delegate;
    }
}
