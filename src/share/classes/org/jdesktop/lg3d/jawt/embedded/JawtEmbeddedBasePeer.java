/**
 * Project Looking Glass
 *
 * $RCSfile: JawtEmbeddedBasePeer.java,v $
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
 * $Date: 2006-11-15 07:10:52 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.embedded;

import org.jdesktop.lg3d.jawt.JawtBackBuffer;

/**
 * Interface to be implemented by JAWT Container component peers.
 *
 * @see JawtToolkit
 *
 * @author krishna_gadepalli
 */
public interface JawtEmbeddedBasePeer
	 extends JawtBackBuffer.BufferFlipListener,
		 JawtBackBuffer.BufferResizeListener,
		 java.rmi.Remote {
}
