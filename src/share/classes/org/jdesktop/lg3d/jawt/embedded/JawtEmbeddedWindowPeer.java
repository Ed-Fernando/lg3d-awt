/**
 * Project Looking Glass
 *
 * $RCSfile: JawtEmbeddedWindowPeer.java,v $
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
 * $Date: 2006-11-15 07:10:54 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.embedded;

import java.awt.Dialog;

/**
 * Interface to be implemented by JAWT Container component peers.
 *
 * @see JawtToolkit
 *
 * @author krishna_gadepalli
 */
public interface JawtEmbeddedWindowPeer extends JawtEmbeddedComponentPeer {
    void toFront();
    void toBack();
    void setAlwaysOnTop(boolean alwaysOnTop);
    void updateFocusableWindowState();
    boolean requestWindowFocus();
    void setModalBlocked(Dialog blocker, boolean blocked);
    void updateMinimumSize();
    void updateIconImages();
}
