/**
 * Project Looking Glass
 *
 * $RCSfile: JawtBackBuffer.java,v $
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
 * $Revision: 1.3 $
 * $Date: 2006-12-14 00:33:36 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.jawt;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * JawtBackBuffer is the buffer to which all the Swing/AWT rendering is done.
 *
 * @author krishna_gadepalli
 */
public class JawtBackBuffer {

    private static Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt");
    
    private BufferedImage backImage;
    private ArrayList<BufferFlipListener> flipListener = new ArrayList<BufferFlipListener>();
    private ArrayList<BufferResizeListener> resizeListener = new ArrayList<BufferResizeListener>();
    
    /**
     * Creates a new instance of JawtBackBuffer 
     */
    public JawtBackBuffer(int width, int height) {
	logger.info("Creating backbuffer[" + width + "x" + height + "]");
        backImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    
    public void addBufferFlipListener(BufferFlipListener listener) {
        if (listener != null)
	    flipListener.add(listener);
    }
    
    public void addBufferResizeListener(BufferResizeListener listener) {
	if (listener != null)
	    resizeListener.add(listener);
    }
    
    /**
     * Ensure the back buffer is this size, resize if necessary
     *
     * Returns true if the buffer was resized
     */
    public boolean ensureSize(int width, int height) {
        if ((backImage.getWidth() != width) || (backImage.getHeight() != height)) { 
	    logger.info("Resizing backbuffer[" + width + "x" + height + "]");

            BufferedImage oldImage = backImage;
            backImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            backImage.setData(oldImage.getData());   
            notifyResize();

            return true;
        }
        return false;
    }
    
    /**
     * Notify all <code>BufferResizeListener</code>s that the buffer has been resized.
     */
    private void notifyResize() {
        for (BufferResizeListener listener : resizeListener)
            listener.bufferResized(this);
    }
    
    /**
     * Inform the buffer that it's contents have changed, this will result in the buffer being
     * rendered.
     */
    public synchronized void updateRegion(Rectangle rect) {
        for (BufferFlipListener listener : flipListener)
            listener.renderBuffer(this, rect);
    }
    
    public BufferedImage getImage() {
        return backImage;
    }
    
    public int getWidth() {
	return backImage.getWidth();
    }

    public int getHeight() {
	return backImage.getHeight();
    }

    /**
     * Create and return a new graphics2D
     */
    public Graphics2D createGraphics() {
        return backImage.createGraphics();
    }
    
    public interface BufferFlipListener {
        public void renderBuffer(JawtBackBuffer buffer, Rectangle rect);
    }
    
    public interface BufferResizeListener {
        public void bufferResized(JawtBackBuffer buffer);
    }
}
