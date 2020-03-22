/**
 * Project Looking Glass
 *
 * $RCSfile: JawtClass.java,v $
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
 * $Date: 2006-09-15 07:29:43 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.util;

import java.util.logging.Logger;

/**
 * We need to do some instanceOf checks on private/package access classes...
 *
 * TODO: This stuff might break with webstart/JNLP...
 */
public class JawtClass {

    protected static final Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt");

    private Class clazz;
    private Class[] declaredClasses;

    public static JawtClass create(Class clazz) {
	JawtClass c = new JawtClass(clazz);

	if (c.init() == true)
	    return c;

	return null;
    }

    private JawtClass(Class clazz) {
	this.clazz = clazz;
    }

    private boolean init() {
	if (declaredClasses == null) {
	    try {
		declaredClasses = clazz.getDeclaredClasses();

	    } catch (Exception e) {
		logger.severe("Unable to get declared classes in class [" + clazz.getName() + "]");
		return false;
	    }
	}

	return true;
    }

    public boolean isInstance(Object obj, String innerClass) {
	if (init()) {
	    String innerClassName = clazz.getName() + "$" + innerClass;

	    for (Class c : declaredClasses) {
		if (c.getName().equals(innerClassName))
		    return c.isInstance(obj);
	    }

	    logger.severe("Unable to find inner class [" + innerClass + "] in class [" + clazz.getName() + "]");
	}
	return false;
    }
}
