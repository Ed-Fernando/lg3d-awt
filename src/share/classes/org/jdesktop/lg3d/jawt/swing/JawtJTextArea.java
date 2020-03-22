/**
 * Project Looking Glass
 *
 * $RCSfile: JawtJTextArea.java,v $
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
 * $Revision: 1.4 $
 * $Date: 2006-09-22 21:10:33 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.swing;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.TextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import org.jdesktop.lg3d.jawt.delegate.JawtDelegateInterface;

/**
 * A wrapper of the the Swing equivalent to expose some methods
 */
public class JawtJTextArea extends JTextArea implements JawtDelegateInterface {

    TextArea target;
    JScrollPane scrollPane;

    public JawtJTextArea(TextArea target) {
	super();
	this.target = target;

	setColumns(target.getColumns());
	setRows   (target.getRows());

	createPane();
    }

    private void createPane() {
	int vp, hp;

	switch(target.getScrollbarVisibility()) {
	case TextArea.SCROLLBARS_HORIZONTAL_ONLY:
	     vp = ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
	     hp = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
	     break;
	case TextArea.SCROLLBARS_VERTICAL_ONLY:
	     vp = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
	     hp = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
	     break;
	case TextArea.SCROLLBARS_NONE:
	     vp = ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
	     hp = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
	     break;
	case TextArea.SCROLLBARS_BOTH:
	default:
	     vp = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
	     hp = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
	     break;
	}

	scrollPane = new JScrollPane(this, vp, hp);
	scrollPane.setBackground(getBackground());
    }

    public JScrollPane getPane() {
	return scrollPane;
    }

    public void processAWTEvent(AWTEvent e) {
	super.processEvent(e);
    }

    public void setBackground(Color bg) {
	super.setBackground(bg);
	scrollPane.setBackground(bg);
    }
}
