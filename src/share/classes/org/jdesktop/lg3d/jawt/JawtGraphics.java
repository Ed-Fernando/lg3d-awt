/**
 * Project Looking Glass
 *
 * $RCSfile: JawtGraphics.java,v $
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

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * JawtGraphics is a wrapper on an existing Graphics/Graphics2D object that computes
 * the region affected by each call.
 *
 * Warning: This is not thread-safe!
 *
 * @author krishna_gadepalli
 */
public class JawtGraphics extends Graphics2D
			  implements JawtBackBuffer.BufferResizeListener {

    private static Logger logger = Logger.getLogger("org.jdesktop.lg3d.jawt");

    /**
     * How are the client (backBuffer) updates made ...
     *	PUSH - every update is pushed to the client for an update.
     *	PULL - all updates are coalesced until the client requests and clear it.
     */
    public enum UpdateModel { PUSH, PULL };

    private Graphics    delegate;
    private Graphics2D  delegate2D;
    private Rectangle   updateRect;
    private UpdateModel updateModel;

    JawtBackBuffer backBuffer;

    /**
     * Same as JawtGraphics(backBuffer, null, PUSH);
     */
    public JawtGraphics(JawtBackBuffer backBuffer) {
	this(backBuffer, null, UpdateModel.PUSH);
    }

    /**
     * Same as JawtGraphics(backBuffer, delegate, PUSH);
     */
    public JawtGraphics(JawtBackBuffer backBuffer, Graphics delegate) {
	this(backBuffer, delegate, UpdateModel.PUSH);
    }

    /**
     * Same as JawtGraphics(backBuffer, null, updateModel);
     */
    public JawtGraphics(JawtBackBuffer backBuffer, UpdateModel updateModel) {
	this(backBuffer, null, updateModel);
    }

    /**
     * Create a JawtGraphics
     *
     * @param backBuffer the buffer to which the actual rendering is done.
     * @param delegate the <code>Graphics</code> to use. If <code>null</code> use
     *		       <code>backBuffer.createGraphics()</code>
     * @param updateModel the update model to use.
     */
    public JawtGraphics(JawtBackBuffer backBuffer, Graphics delegate, UpdateModel updateModel) {

	setupDelegate(backBuffer, delegate);

	this.updateModel = updateModel;

	if (updateModel == UpdateModel.PULL)
	    this.updateRect = new Rectangle(0, 0, backBuffer.getWidth(),
						  backBuffer.getHeight());
    }

    private void setupDelegate(JawtBackBuffer backBuffer, Graphics delegate) {
	if (backBuffer == null)
	    throw new NullPointerException("backBuffer is null");

	this.backBuffer = backBuffer;

	if (delegate == null)
	    this.delegate = backBuffer.createGraphics();
	else
	    this.delegate = delegate.create();

	if (delegate instanceof Graphics2D)
	    this.delegate2D = (Graphics2D)delegate;
	else
	    this.delegate2D = null;

    }

    /**
     * Reset the current update region to be empty.
     */
    public void clearUpdateRegion(boolean clear) {
	updateRect = new Rectangle();
    }

    /**
     * Get the Rectangle defining the bounds of the region that needs to be
     * updated. If the <code>UpdateModel</code> is <code>UpdateModel.PUSH</code>
     * then this will always return null;
     *
     * @param clear should the update region be cleared after returning it ?
     */
    public Rectangle getUpdateRegion(boolean clear) {
	Rectangle rect = updateRect;

	if (clear)
	    updateRect = new Rectangle();

	return rect;
    }

    /**
     * Mark the specified region as dirty (to be updated).
     * If the <code>UpdateModel</code> is <code>UpdateModel.PUSH</code>
     * then pass this update down to the client (backBuffer). If not, merge
     * it with the current update region and wait for the client to request
     * it to use and then clear.
     *
     * @param rect the rectangle defining the bounds of the update region.
     *		   if rect is <code>null</code> the update the entire available area.
     */
    public void updateRegion(Rectangle rect) {
	if (rect == null)
	    rect = new Rectangle(0, 0, backBuffer.getWidth(), backBuffer.getHeight());

	if (updateModel == UpdateModel.PUSH)
	    backBuffer.updateRegion(rect);
	else
	    updateRect = updateRect.union(rect);
    }

    private void updateRegion(int x, int y, int width, int height) {
	updateRegion(new Rectangle(x, y, width, height));
    }

    /**
     * Update the region equivalent to the bounding rectangle of the Polygon.
     */
    private void updateRegion(int xPoints[], int yPoints[], int nPoints) {
	updateRegion((new Polygon(xPoints, yPoints, nPoints)).getBounds());
    }

    private void updateRegion(Image img, int x, int y, ImageObserver observer) {
	updateRegion(new Rectangle(x, y, img.getWidth(observer),
					 img.getHeight(observer)));
    }

    private void updateRegion(String str, float x, float y) {
	TextLayout tl = new TextLayout(str, getFont(), getFontRenderContext());

	updateRegion(tl.getPixelBounds(null, x, y));
    }

    private void updateRegion(AttributedCharacterIterator iterator, float x, float y) {
	TextLayout tl = new TextLayout(iterator, getFontRenderContext());

	updateRegion(tl.getPixelBounds(null, x, y));
    }

    /* -------------------------------------------------------------------------- *
     * From java.awt.Graphics
     * -------------------------------------------------------------------------- */

    /**
     * {@inheritDoc}
     */
    public Graphics create() {
	return new JawtGraphics(backBuffer, null, updateModel);
    }

    /**
     * {@inheritDoc}
     */
    public Graphics create(int x, int y, int width, int height) {
	Graphics g = create();
	if (g == null) return null;
	g.translate(x, y);
	g.clipRect(0, 0, width, height);
	return g;
    }

    /**
     * {@inheritDoc}
     */
    public void translate(int x, int y) {
	delegate.translate(x, y);
    }

    /**
     * {@inheritDoc}
     */
    public Color getColor() {
	return delegate.getColor();
    }

    /**
     * {@inheritDoc}
     */
    public void setColor(Color c) {
	delegate.setColor(c);
    }

    /**
     * {@inheritDoc}
     */
    public void setPaintMode() {
	delegate.setPaintMode();
    }

    /**
     * {@inheritDoc}
     */
    public void setXORMode(Color c1) {
	delegate.setXORMode(c1);
    }

    /**
     * {@inheritDoc}
     */
    public Font getFont() {
	return delegate.getFont();
    }

    /**
     * {@inheritDoc}
     */
    public void setFont(Font font) {
	delegate.setFont(font);
    }

    /**
     * {@inheritDoc}
     */
    public FontMetrics getFontMetrics() {
	return getFontMetrics(getFont());
    }

    /**
     * {@inheritDoc}
     */
    public FontMetrics getFontMetrics(Font f) {
	return delegate.getFontMetrics(f);
    }

    /**
     * {@inheritDoc}
     */
    public Rectangle getClipBounds() {
	return delegate.getClipBounds();
    }

    /**
     * {@inheritDoc}
     */
    public void clipRect(int x, int y, int width, int height) {
	delegate.clipRect(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void setClip(int x, int y, int width, int height) {
	delegate.setClip(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public Shape getClip() {
	return delegate.getClip();
    }

    /**
     * {@inheritDoc}
     */
    public void setClip(Shape clip) {
	delegate.setClip(clip);
    }

    /**
     * {@inheritDoc}
     */
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
	delegate.copyArea(x, y, width, height, dx, dy);
	updateRegion(dx, dy, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
	delegate.drawLine(x1, y1, x2, y2);
	updateRegion(new int[]{x1, x2}, new int[]{y1, y2}, 2);
    }

    /**
     * {@inheritDoc}
     */
    public void fillRect(int x, int y, int width, int height) {
	delegate.fillRect(x, y, width, height);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void drawRect(int x, int y, int width, int height) {
	delegate.drawRect(x, y, width, height);
	updateRegion(x, y, width, height);
    }
    
    /**
     * {@inheritDoc}
     */
    public void clearRect(int x, int y, int width, int height) {
	delegate.clearRect(x, y, width, height);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
	delegate.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
	delegate.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void draw3DRect(int x, int y, int width, int height, boolean raised) {
	delegate.draw3DRect(x, y, width, height, raised);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void fill3DRect(int x, int y, int width, int height, boolean raised) {
	delegate.fill3DRect(x, y, width, height, raised);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void drawOval(int x, int y, int width, int height) {
	delegate.drawOval(x, y, width, height);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void fillOval(int x, int y, int width, int height) {
	delegate.fillOval(x, y, width, height);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
	delegate.drawArc(x, y, width, height, startAngle, arcAngle);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
	delegate.fillArc(x, y, width, height, startAngle, arcAngle);
	updateRegion(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public void drawPolyline(int xPoints[], int yPoints[], int nPoints) {
	delegate.drawPolyline(xPoints, yPoints, nPoints);
	updateRegion(xPoints, yPoints, nPoints);
    }

    /**
     * {@inheritDoc}
     */
    public void drawPolygon(int xPoints[], int yPoints[], int nPoints) {
	delegate.drawPolygon(xPoints, yPoints, nPoints);
	updateRegion(xPoints, yPoints, nPoints);
    }

    /**
     * {@inheritDoc}
     */
    public void drawPolygon(Polygon p) {
	delegate.drawPolygon(p);
	updateRegion(p.getBounds());
    }

    /**
     * {@inheritDoc}
     */
    public void fillPolygon(int xPoints[], int yPoints[], int nPoints) {
	delegate.fillPolygon(xPoints, yPoints, nPoints);
	updateRegion(xPoints, yPoints, nPoints);
    }

    /**
     * {@inheritDoc}
     */
    public void fillPolygon(Polygon p) {
	delegate.fillPolygon(p);
	updateRegion(p.getBounds());
    }

    /**
     * {@inheritDoc}
     */
    public void drawString(String str, int x, int y) {
	delegate.drawString(str, x, y);
	updateRegion(str, (float)x, (float)y);
    }

    /**
     * {@inheritDoc}
     */
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
	delegate.drawString(iterator, x, y);
	updateRegion(iterator, (float)x, (float)y);
    }

    /**
     * {@inheritDoc}
     */
    public void drawChars(char data[], int offset, int length, int x, int y) {
	drawString(new String(data, offset, length), x, y);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("deprecation")
    public void drawBytes(byte data[], int offset, int length, int x, int y) {
	drawString(new String(data, 0, offset, length), x, y);
    }

    /**
     * {@inheritDoc}
     */
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
	boolean retval = delegate.drawImage(img, x, y, observer);
	updateRegion(img, x, y, observer);
	return retval;
    }

    /**
     * {@inheritDoc}
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
	boolean retval = delegate.drawImage(img, x, y, width, height, observer);
	updateRegion(x, y, width, height);
	return retval;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
	boolean retval = delegate.drawImage(img, x, y, bgcolor, observer);
	updateRegion(img, x, y, observer);
	return retval;
    }

    /**
     * {@inheritDoc}
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
	boolean retval = delegate.drawImage(img, x, y, width, height, bgcolor, observer);
	updateRegion(x, y, width, height);
	return retval;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
	boolean retval = delegate.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
	updateRegion(new int[]{dx1, dx2}, new int[]{dy1, dy2}, 2);
	return retval;
    }

    /**
     * {@inheritDoc}
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
	boolean retval = delegate.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
	updateRegion(new int[]{dx1, dx2}, new int[]{dy1, dy2}, 2);
	return retval;
    }

    /**
     * {@inheritDoc}
     */
    public void dispose() {
	delegate.dispose();
    }

    /**
     * {@inheritDoc}
     */
    public void finalize() {
	dispose();
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {	
	return getClass().getName() + "[font=" + getFont() + ",color=" + getColor() + "]";
    }

    @SuppressWarnings("deprecation")
    /**
     * {@inheritDoc}
     */
    public Rectangle getClipRect() {
	return delegate.getClipBounds();
    }

    /**
     * {@inheritDoc}
     */
    public boolean hitClip(int x, int y, int width, int height) {
	return delegate.hitClip(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    public Rectangle getClipBounds(Rectangle r) {
        return delegate.getClipBounds(r);
    }

    /* -------------------------------------------------------------------------- *
     * From java.awt.Graphics2D
     * -------------------------------------------------------------------------- */

    /**
     * {@inheritDoc}
     */
    public void draw(Shape s) {
	if (delegate2D != null) {
	    delegate2D.draw(s);
	    updateRegion(s.getBounds());
	}
    }

    /**
     * {@inheritDoc}
     */
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
	if (delegate2D != null) {
	    boolean retval = delegate2D.drawImage(img, xform, obs);
	    updateRegion(null); // TODO: figure out the correct update rectanle
	    return retval;
	}
        
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
	if (delegate2D != null) {
	    delegate2D.drawImage(img, op, x, y);
	    updateRegion(null); // TODO: figure out the correct update rectanle
	}
    }

    /**
     * {@inheritDoc}
     */
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
	if (delegate2D != null) {
	    delegate2D.drawRenderedImage(img, xform);
	    updateRegion(null); // TODO: figure out the correct update rectanle
	}
    }

    /**
     * {@inheritDoc}
     */
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
	if (delegate2D != null) {
	    delegate2D.drawRenderableImage(img, xform);
	    updateRegion(null); // TODO: figure out the correct update rectanle
	}
    }

    /**
     * {@inheritDoc}
     */
    public void drawString(String str, float x, float y) {
	if (delegate2D != null) {
	    delegate2D.drawString(str, x, y);
	    updateRegion(str, x, y);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
	if (delegate2D != null) {
	    delegate2D.drawString(iterator, x, y);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void drawGlyphVector(GlyphVector g, float x, float y) {
	if (delegate2D != null) {
	    delegate2D.drawGlyphVector(g, x, y);
	    updateRegion(g.getOutline(x, y).getBounds());
	}
    }

    /**
     * {@inheritDoc}
     */
    public void fill(Shape s) {
	if (delegate2D != null) {
	    delegate2D.fill(s);
	    updateRegion(s.getBounds());
	}
    }

    /**
     * {@inheritDoc}
     */
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
	if (delegate2D != null) {
	    return delegate2D.hit(rect, s, onStroke);
	}
	return false;
    }

    /**
     * {@inheritDoc}
     */
    public GraphicsConfiguration getDeviceConfiguration() {
	if (delegate2D != null) {
	    return delegate2D.getDeviceConfiguration();
	}
	return null;
    }

    /**
     * {@inheritDoc}
     */
    public void setComposite(Composite comp) {
	if (delegate2D != null) {
	    delegate2D.setComposite(comp);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void setPaint(Paint paint) {
	if (delegate2D != null) {
	    delegate2D.setPaint(paint);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void setStroke(Stroke s) {
	if (delegate2D != null) {
	    delegate2D.setStroke(s);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void setRenderingHint(Key hintKey, Object hintValue) {
	if (delegate2D != null) {
	    delegate2D.setRenderingHint(hintKey, hintValue);
	}
    }

    /**
     * {@inheritDoc}
     */
    public Object getRenderingHint(Key hintKey) {
	if (delegate2D != null) {
	    delegate2D.getRenderingHint(hintKey);
	}
        
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void setRenderingHints(Map<?,?> hints) {
	if (delegate2D != null) {
	    delegate2D.setRenderingHints(hints);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void addRenderingHints(Map<?,?> hints) {
	if (delegate2D != null) {
	    delegate2D.addRenderingHints(hints);
	}
    }

    /**
     * {@inheritDoc}
     */
    public RenderingHints getRenderingHints() {
	if (delegate2D != null) {
	    delegate2D.getRenderingHints();
	}
        
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void translate(double tx, double ty) {
	if (delegate2D != null) {
	    delegate2D.translate(tx, ty);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void rotate(double theta) {
	if (delegate2D != null) {
	    delegate2D.rotate(theta);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void rotate(double theta, double x, double y) {
	if (delegate2D != null) {
	    delegate2D.rotate(theta, x, y);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void scale(double sx, double sy) {
	if (delegate2D != null) {
	    delegate2D.scale(sx, sy);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void shear(double shx, double shy) {
	if (delegate2D != null) {
	    delegate2D.shear(shx, shy);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void transform(AffineTransform Tx) {
	if (delegate2D != null) {
	    delegate2D.transform(Tx);
	}
    }

    /**
     * {@inheritDoc}
     */
    public void setTransform(AffineTransform Tx) {
	if (delegate2D != null) {
	    delegate2D.setTransform(Tx);
	}
    }

    /**
     * {@inheritDoc}
     */
    public AffineTransform getTransform() {
	if (delegate2D != null) {
	    return delegate2D.getTransform();
	}
	return null;
    }

    /**
     * {@inheritDoc}
     */
    public Paint getPaint() {
	if (delegate2D != null) {
	    return delegate2D.getPaint();
	}
	return null;
    }

    /**
     * {@inheritDoc}
     */
    public Composite getComposite() {
	if (delegate2D != null) {
	    return delegate2D.getComposite();
	}
	return null;
    }

    /**
     * {@inheritDoc}
     */
    public void setBackground(Color color) {
	if (delegate2D != null) {
	    delegate2D.setBackground(color);
	}
    }

    /**
     * {@inheritDoc}
     */
    public Color getBackground() {
	if (delegate2D != null) {
	    return delegate2D.getBackground();
	}
	return null;
    }

    /**
     * {@inheritDoc}
     */
    public Stroke getStroke() {
	if (delegate2D != null) {
	    return delegate2D.getStroke();
	}
	return null;
    }

    /**
     * {@inheritDoc}
     */
    public void clip(Shape s) {
	if (delegate2D != null) {
	    delegate2D.clip(s);
	}
    }

    /**
     * {@inheritDoc}
     */
    public FontRenderContext getFontRenderContext() {
	if (delegate2D != null) {
	    return delegate2D.getFontRenderContext();
	}
	return null;
    }

    /**
     * JawtBackBuffer.BufferFlipListener
     */
    public void bufferResized(JawtBackBuffer buffer) {
        setupDelegate(buffer, null);
    }    
}
