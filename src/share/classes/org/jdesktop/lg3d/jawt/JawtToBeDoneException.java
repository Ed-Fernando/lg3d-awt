/**
 * Project Looking Glass
 *
 * $RCSfile: JawtToBeDoneException.java,v $
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
 * $Date: 2006-11-15 07:10:51 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt;

/**
 * Exception thrown by unimplemented methods which need to be implemented in
 * the near future for complete functionality (to have a working system)
 *
 * @author krishna_gadepalli
 */
public class JawtToBeDoneException extends UnsupportedOperationException {
    public JawtToBeDoneException() {}

    public JawtToBeDoneException(String message) {
	super(message);
    }
}
