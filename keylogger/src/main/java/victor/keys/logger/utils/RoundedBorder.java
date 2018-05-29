package victor.keys.logger.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {

    private final Color color;
    private final int gap;

    public RoundedBorder(Color c, int g) {
        color = c;
        gap = g;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, gap, gap));
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return (getBorderInsets(c, new Insets(gap, gap, gap, gap)));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = gap / 2;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}