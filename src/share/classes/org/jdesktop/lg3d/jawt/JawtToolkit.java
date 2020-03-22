/**
 * Project Looking Glass
 *
 * $RCSfile: JawtToolkit.java,v $
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
 * $Revision: 1.5 $
 * $Date: 2006-12-14 00:33:36 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.*;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.event.AWTEventListener;
import java.awt.im.InputMethodHighlight;
import java.awt.image.*;
import java.awt.peer.*;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import org.jdesktop.lg3d.jawt.embedded.JawtEmbeddedToolkit;
import org.jdesktop.lg3d.jawt.delegate.JawtButtonPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtCanvasPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtCheckboxMenuItemPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtCheckboxPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtChoicePeer;
import org.jdesktop.lg3d.jawt.delegate.JawtLabelPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtListPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtMenuBarPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtMenuItemPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtMenuPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtPanelPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtPopupMenuPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtScrollPanePeer;
import org.jdesktop.lg3d.jawt.delegate.JawtScrollbarPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtTextAreaPeer;
import org.jdesktop.lg3d.jawt.delegate.JawtTextFieldPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtDialogPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFileDialogPeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtFramePeer;
import org.jdesktop.lg3d.jawt.toplevel.JawtWindowPeer;

/**
 * JawtToolkit is a AWT implementation for running AWT/Swing applications
 * and applets inside an already running JVM. The primary goal of this
 * toolkit is to allow running AWT/Swing apps unchanged inside
 * <a href="http://lg3d.dev.java.net">Project Looking Glass</a> but
 * an attempt has been made to make this generic enough that it can be
 * run inside any Java container.
 *
 * JawtToolkit extends the OS specific toolkit implementation
 * ({@link sun.awt.X11.XToolkit} or {@link sun.awt.windows.WToolkit})
 * and then modifies its behavior in the following ways.
 *
 * a. All of the AWT peers except for the top-level ones (Frame, Window, Dialog etc.)
 *    are implemented using Swing
 *
 * b. All the top-level peers (Frame, Window, Dialog etc.) and some other
 *    methods (sync, beep, dynamic-layout etc.) are abstracted out
 *    into JawtEmbeddedToolkit and needs to be implemented by the Java Container
 *    (for e.g. Looking Glass)
 *
 * c. The rest of the methods (PrintJob, Image etc.) are simply punted to the OS
 *    Toolkit's implementation.
 *
 * TODO: Will work only with JDK 1.6 (Mustang)
 *
 * Much of this work is built on top of Paul Bryne's initial Looking Glass
 * AWT toolkit work, the AWT-on-Swing work from the Java ME team and
 * as well as from Guillaume Desnoix's LaOS toolkit in
 * <a href="http://jdistro.org">JDistro</a>.
 *
 * @author krishna_gadepalli
 */
public class JawtToolkit extends JawtOSToolkit {

    private static Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt");

    private static JawtEmbeddedToolkit toolkit;

    private int menuShortcutKeyMask;

    public JawtToolkit() {
	getDefaultEmbeddedToolkit();

	try {
	    menuShortcutKeyMask = toolkit.getMenuShortcutKeyMask();
	} catch (JawtUnsupportedOperationException juoe) {
	    menuShortcutKeyMask = super.getMenuShortcutKeyMask();
	}
    }

    private KeyboardFocusManager defaultKBFM = null;
    private KeyboardFocusManager newKBFM = null;

    public void setEmbeddedToolkitEnabled(boolean enabled) {
	if (defaultKBFM == null)
	    defaultKBFM = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	if (newKBFM == null)
	    newKBFM = new JawtKeyboardFocusManager();

	KeyboardFocusManager kbfm = enabled ? newKBFM : defaultKBFM;

	KeyboardFocusManager.setCurrentKeyboardFocusManager(kbfm);
	logger.info("Setting KeyboardFocusManager : " + kbfm.getClass().getName());
    }

    public boolean isEmbeddedToolkitEnabled() {
	return (toolkit != null) && toolkit.isEnabled();
    }

    /* -------------------------------------------------------------------------- *
     * Added functionality on top of the OS Toolkit
     * -------------------------------------------------------------------------- */

    /**
     * Gets the default JawtEmbeddedToolkit set by the "jawt.toolkit" system
     * property.
     */
    public static synchronized JawtEmbeddedToolkit getDefaultEmbeddedToolkit() {
        if (toolkit == null) {
	    java.security.AccessController.doPrivileged(
		    new java.security.PrivilegedAction<Object>() {
		public Object run() {
		    String nm = null;
		    Class cls = null;
		    try {
			if ((nm = System.getProperty("jawt.toolkit",
				    "org.jdesktop.lg3d.jawt.lg.LgToolkit")) == null)
			    return null;

			try {
			    cls = Class.forName(nm);
			} catch (ClassNotFoundException e) {
			    ClassLoader cl = ClassLoader.getSystemClassLoader();
			    if (cl != null) {
				try {
				    cls = cl.loadClass(nm);
				} catch (ClassNotFoundException ee) {
				    throw new AWTError("JAWT Toolkit not found: " + nm);
				}
			    }
			}
			if (cls != null) {
			    toolkit = (JawtEmbeddedToolkit)cls.newInstance();
			    logger.info("Instantiated JAWT Toolkit : " + cls.getName());
			}
		    } catch (InstantiationException e) {
			throw new JawtError("Could not instantiate JAWT Toolkit: " + nm);
		    } catch (IllegalAccessException e) {
			throw new JawtError("Could not access JAWT Toolkit: " + nm);
		    }
		    return null;
		}
	    });
        }

        return toolkit;
    }

    /**
     * Give native peers the ability to query the native container
     * given a native component (eg the direct parent may be lightweight).
     */
    public static Container getNativeContainer(Component c) {
	// TODO: Should this be toolkit.getNativeContainer(c) ?
	return JawtOSToolkit.getNativeContainer(c);
    }

    /**
     * Get the real (physical screen size)
     */
    public Dimension getRealScreenSize() {
	return super.getScreenSize();
    }

    /* -------------------------------------------------------------------------- *
     * AWT Peers and other methods that need to be implemented by the Container
     * (by implementing JawtEmbeddedToolkit).
     *
     * If the method throws JawtUnsupportedOperationException then the call is
     * punted to 'super' - which will be the OS toolkit implementation
     * -------------------------------------------------------------------------- */

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public FramePeer createFrame(Frame target) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return new JawtFramePeer(target, toolkit);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.createFrame(target);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public WindowPeer createWindow(Window target) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return new JawtWindowPeer(target, toolkit);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.createWindow(target);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public DialogPeer createDialog(Dialog target) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return new JawtDialogPeer(target, toolkit);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.createDialog(target);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public FileDialogPeer createFileDialog(FileDialog target) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return new JawtFileDialogPeer(target, toolkit);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.createFileDialog(target);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public MouseInfoPeer getMouseInfoPeer() {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.getMouseInfoPeer();
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.getMouseInfoPeer();
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public EventQueue getSystemEventQueueImpl() {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.getSystemEventQueueImpl();
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.getSystemEventQueueImpl();
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public void loadSystemColors(int[] systemColors) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		toolkit.loadSystemColors(systemColors);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	super.loadSystemColors(systemColors);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public void setDynamicLayout(boolean dynamic) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		toolkit.setDynamicLayout(dynamic);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	super.setDynamicLayout(dynamic);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public boolean isDynamicLayoutSet() {
        if (this != Toolkit.getDefaultToolkit()) {
            // TODO: return Toolkit.getDefaultToolkit().isDynamicLayoutSet();
	    return super.isDynamicLayoutSet();
        } else {
	    if (isEmbeddedToolkitEnabled()) {
		try {
		    return toolkit.isDynamicLayoutSet();
		} catch (JawtUnsupportedOperationException juoe) {
		}
	    }
	    return super.isDynamicLayoutSet();
        }
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public boolean isDynamicLayoutActive() {
        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().isDynamicLayoutActive();
        } else {
	    if (isEmbeddedToolkitEnabled()) {
		try {
		    return toolkit.isDynamicLayoutActive();
		} catch (JawtUnsupportedOperationException juoe) {
		}
	    }
	    return super.isDynamicLayoutActive();
        }
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public Dimension getScreenSize() {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.getScreenSize();
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.getScreenSize();
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public int getScreenResolution() {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.getScreenResolution();
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.getScreenResolution();
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public Insets getScreenInsets(GraphicsConfiguration gc) {
        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().getScreenInsets(gc);
        } else {
	    if (isEmbeddedToolkitEnabled()) {
		try {
		    return toolkit.getScreenInsets(gc);
		} catch (JawtUnsupportedOperationException juoe) {
		}
	    }
	    return super.getScreenInsets(gc);
        }
    }
    
    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     *
     * TODO: Check if this method is called often and needs to happen fast
     *	     since the JawtUnsupportedOperationException checking might be slow...
     */
    public void sync() {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		toolkit.sync();
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	super.sync();
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public void beep() {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		toolkit.beep();
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	super.beep();
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public Clipboard getSystemClipboard() {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.getSystemClipboard();
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.getSystemClipboard();
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public Clipboard getSystemSelection() {
        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().getSystemSelection();
        } else {
	    if (isEmbeddedToolkitEnabled()) {
		try {
		    return toolkit.getSystemSelection();
		} catch (JawtUnsupportedOperationException juoe) {
		}
	    }
	    return super.getSystemSelection();
        }
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public int getMenuShortcutKeyMask() {
        return menuShortcutKeyMask;
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public Cursor createCustomCursor(Image cursor, Point hotSpot, String name) {
        if (this != Toolkit.getDefaultToolkit()) {
	    return Toolkit.getDefaultToolkit()
			  .createCustomCursor(cursor, hotSpot, name);
	} else {
	    if (isEmbeddedToolkitEnabled()) {
		try {
		    return toolkit.createCustomCursor(cursor, hotSpot, name);
		} catch (JawtUnsupportedOperationException juoe) {
		}
	    }
	    return super.createCustomCursor(cursor, hotSpot, name);
	}
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public Dimension getBestCursorSize(int preferredWidth, int preferredHeight) {
        if (this != Toolkit.getDefaultToolkit()) {
	    return Toolkit.getDefaultToolkit()
			  .getBestCursorSize(preferredWidth, preferredHeight);
	} else {
	    if (isEmbeddedToolkitEnabled()) {
		try {
		    return toolkit.getBestCursorSize(preferredWidth, preferredHeight);
		} catch (JawtUnsupportedOperationException juoe) {
		}
	    }
	    return super.getBestCursorSize(preferredWidth, preferredHeight);
	}
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public int getMaximumCursorColors() {
        if (this != Toolkit.getDefaultToolkit()) {
	    return Toolkit.getDefaultToolkit().getMaximumCursorColors();
	} else {
	    if (isEmbeddedToolkitEnabled()) {
		try {
		    return toolkit.getMaximumCursorColors();
		} catch (JawtUnsupportedOperationException juoe) {
		}
	    }
	    return super.getMaximumCursorColors();
	}
    }
    
    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public boolean isFrameStateSupported(int state) {
        if (this != Toolkit.getDefaultToolkit()) {
	    return Toolkit.getDefaultToolkit().isFrameStateSupported(state);
	} else {
	    if (isEmbeddedToolkitEnabled()) {
		try {
		    return toolkit.isFrameStateSupported(state);
		} catch (JawtUnsupportedOperationException juoe) {
		}
	    }
	    return super.isFrameStateSupported(state);
	}
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.createDragSourceContextPeer(dge);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.createDragSourceContextPeer(dge);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public <T extends DragGestureRecognizer> T
	createDragGestureRecognizer(Class<T> abstractRecognizerClass,
				    DragSource ds, Component c, int srcActions,
				    DragGestureListener dgl)
    {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.createDragGestureRecognizer(abstractRecognizerClass, ds, c, srcActions, dgl);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.createDragGestureRecognizer(abstractRecognizerClass, ds, c, srcActions, dgl);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public boolean isAlwaysOnTopSupported() {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.isAlwaysOnTopSupported();
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.isAlwaysOnTopSupported();
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public boolean isModalityTypeSupported(Dialog.ModalityType modalityType) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.isModalityTypeSupported(modalityType);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.isModalityTypeSupported(modalityType);
    }

    /**
     * Call this method on JawtEmbeddedToolkit first and if that throws
     * an JawtUnsupportedOperationException then punt to super.
     */
    public boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType modalExclusionType) {
	if (isEmbeddedToolkitEnabled()) {
	    try {
		return toolkit.isModalExclusionTypeSupported(modalExclusionType);
	    } catch (JawtUnsupportedOperationException juoe) {
	    }
	}
	return super.isModalExclusionTypeSupported(modalExclusionType);
    }

    /* -------------------------------------------------------------------------- *
     * AWT Component peers implemented on top of Swing
     * -------------------------------------------------------------------------- */

    /**
     * Create using Swing.
     */
    public ButtonPeer createButton(Button target) {
	return new JawtButtonPeer(target);
    }

    /**
     * Create using Swing.
     */
    public TextFieldPeer createTextField(TextField target) {
	return new JawtTextFieldPeer(target);
    }

    /**
     * Create using Swing.
     */
    public LabelPeer createLabel(Label target) {
	return new JawtLabelPeer(target);
    }

    /**
     * Create using Swing.
     */
    public ListPeer createList(java.awt.List target) {
	return new JawtListPeer(target);
    }

    /**
     * Create using Swing.
     */
    public CheckboxPeer createCheckbox(Checkbox target) {
	return new JawtCheckboxPeer(target);
    }

    /**
     * Create using Swing.
     */
    public ScrollbarPeer createScrollbar(Scrollbar target) {
	return new JawtScrollbarPeer(target);
    }

    /**
     * Create using Swing.
     */
    public ScrollPanePeer createScrollPane(ScrollPane target) {
	return new JawtScrollPanePeer(target);
    }

    /**
     * Create using Swing.
     */
    public TextAreaPeer createTextArea(TextArea target) {
	return new JawtTextAreaPeer(target);
    }

    /**
     * Create using Swing.
     */
    public ChoicePeer createChoice(Choice target) {
	return new JawtChoicePeer(target);
    }

    /**
     * Create using Swing.
     */
    public CanvasPeer createCanvas(Canvas target) {
	return new JawtCanvasPeer(target);
    }

    /**
     * Create using Swing.
     */
    public PanelPeer createPanel(Panel target) {
	return new JawtPanelPeer(target);
    }

    /**
     * Create using Swing.
     */
    public MenuBarPeer createMenuBar(MenuBar target) {
	return new JawtMenuBarPeer(target);
    }

    /**
     * Create using Swing.
     */
    public MenuPeer createMenu(Menu target) {
	return new JawtMenuPeer(target);
    }

    /**
     * Create using Swing.
     */
    public PopupMenuPeer createPopupMenu(PopupMenu target) {
	return new JawtPopupMenuPeer(target);
    }

    /**
     * Create using Swing.
     */
    public MenuItemPeer createMenuItem(MenuItem target) {
	return new JawtMenuItemPeer(target);
    }

    /**
     * Create using Swing.
     */
    public CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem target) {
	return new JawtCheckboxMenuItemPeer(target);
    }

    /* -------------------------------------------------------------------------- *
     * Punt directly to 'super' - the OS Toolkit implementation 
     *
     * TODO: Comment these out eventually since we are really doing nothing
     *       of value here except adding another level of method call.
     * -------------------------------------------------------------------------- */

    /**
     * Punt to super.
     */
    public DesktopPeer createDesktopPeer(Desktop target) {
	return super.createDesktopPeer(target);
    }

    /**
     * Punt to super.
     */
    public LightweightPeer createComponent(Component target) {
	return super.createComponent(target);
    }

    /**
     * Punt to super.
     */
    @SuppressWarnings("deprecation")
    public FontPeer getFontPeer(String name, int style) {
	return super.getFontPeer(name, style);
    }

    /**
     * Punt to super.
     */
    public ColorModel getColorModel() {
	return super.getColorModel();
    }

    /**
     * Punt to super.
     */
    @SuppressWarnings("deprecation")
    public String[] getFontList() {
	return super.getFontList();
    }

    /**
     * Punt to super.
     */
    @SuppressWarnings("deprecation")
    public FontMetrics getFontMetrics(Font font) {
	return super.getFontMetrics(font);
    }

    /**
     * Punt to super.
     */
    public Image getImage(String filename) {
	return super.getImage(filename);
    }

    /**
     * Punt to super.
     */
    public Image getImage(URL url) {
	return super.getImage(url);
    }

    /**
     * Punt to super.
     */
    public Image createImage(String filename) {
	return super.createImage(filename);
    }

    /**
     * Punt to super.
     */
    public Image createImage(URL url) {
	return super.createImage(url);
    }

    /**
     * Punt to super.
     */
    public boolean prepareImage(Image image, int width, int height, ImageObserver observer) {
	return super.prepareImage(image, width, height, observer);
    }

    /**
     * Punt to super.
     */
    public int checkImage(Image image, int width, int height, ImageObserver observer) {
	return super.checkImage(image, width, height, observer);
    }

    /**
     * Punt to super.
     */
    public Image createImage(ImageProducer producer) {
	return super.createImage(producer);
    }

    /**
     * Punt to super.
     */
    public Image createImage(byte[] imagedata) {
	return createImage(imagedata, 0, imagedata.length);
    }

    /**
     * Punt to super.
     */
    public Image createImage(byte[] imagedata, int imageoffset, int imagelength) {
	return super.createImage(imagedata, imageoffset, imagelength);
    }

    /**
     * Punt to super.
     */
    public PrintJob getPrintJob(Frame frame, String jobtitle, Properties props) {
	return super.getPrintJob(frame, jobtitle, props);
    }


    /**
     * Punt to super.
     */
    public boolean getLockingKeyState(int keyCode) {
        return super.getLockingKeyState(keyCode);
    }

    /**
     * Punt to super.
     */
    public void setLockingKeyState(int keyCode, boolean on) {
        super.setLockingKeyState(keyCode, on);
    }


    /**
     * Punt to super.
     */
    public Object lazilyLoadDesktopProperty(String name) {
	return super.lazilyLoadDesktopProperty(name);
    }

    /**
     * Punt to super.
     */
    public void initializeDesktopProperties() {
	super.initializeDesktopProperties();
    }

    /**
     * Punt to super.
     */
    public synchronized void addPropertyChangeListener(String name, PropertyChangeListener pcl) {
	super.addPropertyChangeListener(name, pcl);
    }

    /**
     * Punt to super.
     */
    public synchronized void removePropertyChangeListener(String name, PropertyChangeListener pcl) {
	super.removePropertyChangeListener(name, pcl);
    }

    /**
     * Punt to super.
     */
    public PropertyChangeListener[] getPropertyChangeListeners() {
	return super.getPropertyChangeListeners();
    }

    /**
     * Punt to super.
     */
    public synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
	return super.getPropertyChangeListeners(propertyName);
    }


    /**
     * Punt to super.
     */
    public void addAWTEventListener(AWTEventListener listener, long eventMask) {
	super.addAWTEventListener(listener, eventMask);
    }

    /**
     * Punt to super.
     */
    public void removeAWTEventListener(AWTEventListener listener) {
	super.removeAWTEventListener(listener);
    }

    /**
     * Punt to super.
     */
    public AWTEventListener[] getAWTEventListeners() {
	return super.getAWTEventListeners();
    }

    /**
     * Punt to super.
     */
    public AWTEventListener[] getAWTEventListeners(long eventMask) {
	return super.getAWTEventListeners(eventMask);
    }

    /**
     * Punt to super.
     */
    public Map<java.awt.font.TextAttribute, ?>
		mapInputMethodHighlight(InputMethodHighlight highlight) {
	return super.mapInputMethodHighlight(highlight);
    }
}
