/**
 * Project Looking Glass
 *
 * $RCSfile: JawtKeyboardFocusManager.java,v $
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
 * $Date: 2006-12-14 00:33:36 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.peer.ComponentPeer;
import java.util.logging.Logger;
import org.jdesktop.lg3d.jawt.toplevel.JawtWindowPeer;
import sun.awt.SunToolkit;
import org.jdesktop.lg3d.jawt.util.JawtField;
import org.jdesktop.lg3d.jawt.util.JawtMethod;

public class JawtKeyboardFocusManager extends DefaultKeyboardFocusManager {
    
    protected static Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt");

    public boolean dispatchEvent(AWTEvent e) {
	System.out.println("*** dispatchEvent(" + grabbedFocusOwnerName + ") " + e);

        try {
            return super.dispatchEvent(e);
        } catch(Exception ex) {
            System.out.println("Exception caught in JawtKeyboardFocusManager, from event "+e);
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean retargetEvent(AWTEvent e,
		Object source, Component target, int xoffset, int yoffset) {

	AWTEvent ne = null;

	if (e instanceof MouseWheelEvent) {
	    MouseWheelEvent mwe = (MouseWheelEvent)e;

	    ne = new MouseWheelEvent(target,
				     mwe.getID(),
				     mwe.getWhen(),
				     mwe.getModifiers(),
				     mwe.getX() - xoffset,
				     mwe.getY() - yoffset,
				     mwe.getClickCount(),
				     mwe.isPopupTrigger(),
				     mwe.getScrollType(),
				     mwe.getScrollAmount(),
				     mwe.getWheelRotation());

	} else if (e instanceof MouseEvent) {
	    MouseEvent me = (MouseEvent)e;

	    ne = new MouseEvent(target,
				me.getID(),
				me.getWhen(),
				me.getModifiers(),
				me.getX() - xoffset,
				me.getY() - yoffset,
				me.getClickCount(),
				me.isPopupTrigger(),
				me.getButton());
	} else if (e instanceof KeyEvent) {
	    KeyEvent ke = (KeyEvent)e;

	    ne = new KeyEvent(target,
			      ke.getID(),
			      ke.getWhen(),
			      ke.getModifiers(),
			      ke.getKeyCode(),
			      ke.getKeyChar(),
			      ke.getKeyLocation());
	}

	logger.finer((ne == null ? "---processEvent" : "+++retargetEvent") +
	    " : " + e.getClass().getSimpleName() + " : " + source.getClass().getSimpleName() +
	    "[" + e.paramString() + "] => " + e.getSource().getClass().getName());

	if (ne != null)
	    target.dispatchEvent(ne);

	return ne != null;
    }

    private static Component grabbedFocusOwner = null;
    private static String grabbedFocusOwnerName = null;

    private static JawtMethod getCurrentKeyboardFocusManagerMethod = 
		   JawtMethod.create(KeyboardFocusManager.class,
				     "getCurrentKeyboardFocusManager",
				     sun.awt.AppContext.class);

    private static JawtMethod getGlobalFocusOwnerMethod =
		   JawtMethod.create(KeyboardFocusManager.class,
				    "getGlobalFocusOwner");

    private static JawtField isPostedField =
		   JawtField.create(AWTEvent.class, "isPosted");

    // Inspired by processSynchronizedLightweightTransfer in KeyboardFocusManager
    public static boolean processLightweightTransfer(
			    Component heavyweight, Component descendant,
			    boolean temporary, boolean focusedWindowChangeAllowed,
			    long time) {
        KeyboardFocusManager manager = (KeyboardFocusManager)
			getCurrentKeyboardFocusManagerMethod.invoke(null,
					SunToolkit.targetToAppContext(descendant));

        Component currentFocusOwner = (Component)
			getGlobalFocusOwnerMethod.invoke(manager);

        FocusEvent currentFocusOwnerEvent = null;
        FocusEvent newFocusOwnerEvent = null;

	System.out.println("*** processLightweightTransfer(" + grabbedFocusOwnerName + ") " + descendant);

        if (descendant == currentFocusOwner)
            return true;
        
        if (currentFocusOwner != null) {
            currentFocusOwnerEvent =
                new FocusEvent(currentFocusOwner, FocusEvent.FOCUS_LOST, temporary, descendant);
        }
        newFocusOwnerEvent =
            new FocusEvent(descendant, FocusEvent.FOCUS_GAINED, temporary, currentFocusOwner);
        
        boolean result = false;

        synchronized (descendant.getTreeLock()) {
            if (currentFocusOwnerEvent != null && currentFocusOwner != null) {
		isPostedField.set(currentFocusOwnerEvent, true);
                currentFocusOwner.dispatchEvent(currentFocusOwnerEvent);
                /// System.out.println("Dispatching old "+currentFocusOwnerEvent);
                result = true;
            }
            isPostedField.set(newFocusOwnerEvent, true);
            descendant.dispatchEvent(newFocusOwnerEvent);
            /// System.out.println("Dispatching new "+newFocusOwnerEvent);
            /// System.out.println("Descendant "+descendant);
            /// System.out.println("Current "+currentFocusOwner);                        
            result = true;        
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public static boolean grabFocus(Window w) {
	ComponentPeer peer = w.getPeer();

        if ((peer != null)  && (peer instanceof JawtWindowPeer)) {
	    logger.info("*** Grabbing focus = " + w);
	    grabbedFocusOwner = w;
	    grabbedFocusOwnerName = w.getName();
	    return true;
	}

	return false;
    }

    @SuppressWarnings("deprecation")
    public static boolean ungrabFocus(Window w) {
	ComponentPeer peer = w.getPeer();

        if ((peer != null)  && (peer instanceof JawtWindowPeer)) {
	    logger.info("*** Ungrabbing focus = " + w);
	    grabbedFocusOwner = null;
	    grabbedFocusOwnerName = null;
	    return true;
	}

	return false;
    }

    /**
    */
    private static JawtMethod processEventMethod =
		JawtMethod.create(Component.class, "processEvent", AWTEvent.class);

    public static void handleEvent(Component target, AWTEvent e) {
	// logger.info("handleEvent : " + e);

	System.out.println("*** handleEvent(" + grabbedFocusOwnerName + ") " + e);
	/*
	if (grabbedFocusOwner != null) {
	    System.out.println("*** Grabbed focus(retarget) = " + e);
	    retargetEvent(e, e.getSource(), grabbedFocusOwner, 0, 0);
	} else
	    processEventMethod.invoke(target, e);
	*/
	processEventMethod.invoke(target, e);
    }
}
