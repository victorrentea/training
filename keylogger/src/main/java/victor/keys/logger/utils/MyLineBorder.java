package victor.keys.logger.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;
import java.beans.ConstructorProperties;

import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 * A class which implements a line border of arbitrary thickness
 * and of a single color.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * @author David Kloba
 */
public class MyLineBorder extends AbstractBorder
{
    private static Border blackLine;
    private static Border grayLine;

    protected int thickness;
    protected int radius;
    protected Color lineColor;

    public MyLineBorder(Color color, int thickness, int radius)  {
        lineColor = color;
        this.thickness = thickness;
    }

    /**
     * Paints the border for the specified component with the
     * specified position and size.
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if ((this.thickness > 0) && (g instanceof Graphics2D)) {
            Graphics2D g2d = (Graphics2D) g;

            Color oldColor = g2d.getColor();
            g2d.setColor(this.lineColor);

            RoundRectangle2D.Float outer = new RoundRectangle2D.Float(x, y, width, height, radius, radius);
            RoundRectangle2D.Float inner = new RoundRectangle2D.Float(x + thickness, y + thickness, width - thickness * 2, height - thickness * 2, 10, 10);

            Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
            path.append(outer, false);
            path.append(inner, false);
            g2d.fill(path);
            g2d.setColor(oldColor);
        }
    }

    /**
     * Reinitialize the insets parameter with this Border's current Insets.
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(thickness, thickness, thickness, thickness);
        return insets;
    }

    /**
     * Returns the color of the border.
     */
    public Color getLineColor()     {
        return lineColor;
    }

    /**
     * Returns the thickness of the border.
     */
    public int getThickness()       {
        return thickness;
    }


}
