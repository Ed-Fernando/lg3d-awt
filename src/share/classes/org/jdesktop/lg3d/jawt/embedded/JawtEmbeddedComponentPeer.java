/**
 * Project Looking Glass
 *
 * $RCSfile: JawtEmbeddedComponentPeer.java,v $
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
 * $Date: 2006-11-15 07:10:53 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.embedded;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.rmi.RemoteException;

/**
 * Interface to be implemented by JAWT Container component peers.
 *
 * @see JawtToolkit
 *
 * @author krishna_gadepalli
 */
public interface JawtEmbeddedComponentPeer extends JawtEmbeddedBasePeer {
    void dispose() throws RemoteException;
    void updateCursorImmediately() throws RemoteException;
    void setVisible(boolean b) throws RemoteException;
    void setEnabled(boolean b) throws RemoteException;
    void paint(Graphics g) throws RemoteException;
    void repaint(long tm, int x, int y, int width, int height) throws RemoteException;
    void print(Graphics g) throws RemoteException;
    void setBounds(int x, int y, int width, int height) throws RemoteException;
    Dimension getPreferredSize() throws RemoteException;
    Dimension getMinimumSize() throws RemoteException;
    boolean requestFocus(Component lightweightChild,
			 boolean temporary, boolean focusedWindowChangeAllowed,
			 long time, sun.awt.CausedFocusEvent.Cause cause) throws RemoteException;
    GraphicsConfiguration getGraphicsConfiguration() throws RemoteException;
}
