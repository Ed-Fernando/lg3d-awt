/**
 * Project Looking Glass
 *
 * $RCSfile: JawtOSToolkit.java,v $
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
 * $Date: 2006-12-14 00:33:38 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt;

import java.awt.Window;
import sun.awt.X11.XToolkit;
import org.jdesktop.lg3d.jawt.JawtKeyboardFocusManager;
import org.jdesktop.lg3d.jawt.toplevel.JawtWindowPeer;

public class JawtOSToolkit extends XToolkit {

    public JawtOSToolkit() {
	super();
    }

    public void run(boolean loop) {
	boolean errors = true;

	while (true) {
	    try {
		super.run(loop);
		break;
	    } catch (Exception e) {
		e.printStackTrace();
		break;
	    }
	}
    }

    public void grab(Window w) {
	if (!JawtKeyboardFocusManager.grabFocus(w))
            super.grab(w);
    }

    public void ungrab(Window w) {
	if (!JawtKeyboardFocusManager.ungrabFocus(w))
            super.ungrab(w);
    }
}
