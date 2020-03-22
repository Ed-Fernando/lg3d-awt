/**
 * Project Looking Glass
 *
 * $RCSfile: JawtField.java,v $
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
 * $Date: 2006-11-15 07:10:58 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt.util;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * We need to call some private/package access fields in java.awt.* classes
 * This is a wrapper to do that...
 *
 * TODO: The setAccessible stuff might break with webstart/JNLP...
 */
public class JawtField {

    protected static final Logger logger = Logger.getLogger("lg.awt.peer");

    Class  clazz;
    String fieldName;
    Field  field;
    private boolean accessible;

    public static JawtField create(Class clazz, String fieldName) {
	JawtField f = new JawtField(clazz, fieldName);

	if (f.init() == true)
	    return f;

	return null;
    }

    public static String toString(Object obj, Class clazz, String fieldName) {
	try {
	    JawtField f = create(clazz, fieldName);
	    Object retval = f.get(obj);
	    return "" + retval;

	} catch (Exception e) {
	    e.printStackTrace();
	    return "*** ERROR ***";
	}
    }

    public static String toString(Object obj, String fieldName) {
	return toString(obj, obj.getClass(), fieldName);
    }

    private JawtField(Class clazz, String fieldName) {
	this.clazz = clazz;
	this.fieldName = fieldName;
    }

    private boolean init() {
	if (field == null) {
	    try {
		field = clazz.getDeclaredField(fieldName);

	    } catch (Exception e) {
		logger.severe("Unable to find field [" + fieldName +
					"] in class [" + clazz.getName() + "]");
		return false;
	    }

	    // Disable accessibility checks
	    java.security.AccessController.doPrivileged
		(new java.security.PrivilegedAction<Object>() {
			public Object run() {
			    field.setAccessible(true);
			    return null;
			}
		    });

	    accessible = field.isAccessible();
	}

	return accessible;
    }

    public void set(Object obj, Object value) {
	if (init()) {
	    try {
		field.set(obj, value);
	    } catch (Exception e) {
		logger.severe("Unable to set field [" + fieldName +
					"] on class [" + clazz.getName() + "]");
		e.printStackTrace();
	    }
	}
    }

    public Object get(Object obj) {
	if (init()) {
	    try {
		return field.get(obj);
	    } catch (Exception e) {
		logger.severe("Unable to get field [" + fieldName +
					"] on class [" + clazz.getName() + "]");
		e.printStackTrace();
	    }
	}
        
        return null;
    }
}
