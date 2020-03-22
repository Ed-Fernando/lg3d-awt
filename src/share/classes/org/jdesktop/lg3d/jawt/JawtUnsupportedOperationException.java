/**
 * Project Looking Glass
 *
 * $RCSfile: JawtUnsupportedOperationException.java,v $
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
 * $Date: 2006-11-15 07:10:51 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt;

/**
 * Exception thrown by unsupported methods on the JawtEmbeddedToolkit interface.
 *
 * @author krishna_gadepalli
 */
public class JawtUnsupportedOperationException extends UnsupportedOperationException {
    public JawtUnsupportedOperationException() {}

    public JawtUnsupportedOperationException(String message) {
	super(message);
    }
}
