/**
 * Project Looking Glass
 *
 * $RCSfile: JawtScrollPanePeer.java,v $
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

import java.awt.Adjustable;
import java.awt.ScrollPane;
import java.awt.peer.ScrollPanePeer;
import org.jdesktop.lg3d.jawt.swing.JawtJScrollPane;

/**
 * Implementation of an AWT ScrollPane peer (java.awt.peer.ScrollPanePeer) using
 * Swing (JScrollPane)
 */
public class JawtScrollPanePeer extends JawtContainerPeerBase<JawtJScrollPane>
				implements ScrollPanePeer {

    ScrollPane target;
    JawtJScrollPane delegate;

    public JawtScrollPanePeer(ScrollPane target) {
	super(target, new JawtJScrollPane(target));

	this.target = target;
	this.delegate = (JawtJScrollPane)super.delegate;
    }

    public int getHScrollbarHeight() {
	return delegate.getHorizontalScrollBar().getHeight();
    }

    public int getVScrollbarWidth() {
	return delegate.getVerticalScrollBar().getWidth();
    }

    public void setScrollPosition(int x, int y) {
	// TODO:
    }

    public void childResized(int w, int h) {
	// TODO:
    }

    public void setUnitIncrement(Adjustable adj, int u) {
	switch (adj.getOrientation()) {
	case Adjustable.HORIZONTAL:
	     delegate.getHorizontalScrollBar().setUnitIncrement(u);
	     break;

	case Adjustable.VERTICAL:
	     delegate.getVerticalScrollBar().setUnitIncrement(u);
	     break;
	}
    }

    public void setValue(Adjustable adj, int v) {
	switch (adj.getOrientation()) {
	case Adjustable.HORIZONTAL:
	     delegate.getHorizontalScrollBar().setValue(v);
	     break;

	case Adjustable.VERTICAL:
	     delegate.getVerticalScrollBar().setValue(v);
	     break;
	}
    }
}
