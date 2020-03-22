/**
 * Project Looking Glass
 *
 * $RCSfile: JawtError.java,v $
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
 * $Date: 2006-09-15 07:29:36 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt;

/**
 * Thrown when a serious JAWT error has occurred. 
 *
 * @version 	1.16 11/17/05
 * @author 	Arthur van Hoff
 */
public class JawtError extends Error {

    /**
     * Constructs an instance of <code>JawtError</code> with the specified 
     * detail message. 
     * @param   msg   the detail message.
     */
    public JawtError(String msg) {
	super(msg);
    }
}
