/**
 * @file PlotterPoint.java
 * @brief Represents a single colored point on the Plotter canvas.
 *        Maps a creature's char color label to a Java AWT Color and
 *        handles both solid and trailing (translucent) rendering.
 */
package prowl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public class PlotterPoint {

    // display constants
    public static final int POINT_WIDTH   = 10;
    public static final int POINT_HEIGHT  = 10;
    public static final int OUTLINE_WIDTH = 2;

    // trailing alpha (out of 255)
    private static final int TRAIL_ALPHA = 64;

    // state
    private Point     point;
    private Dimension dimension;
    private Color     color;

    // constructor — maps char color label to AWT Color
    public PlotterPoint(Point point, char color) {
        this.point     = point;
        this.dimension = new Dimension(POINT_WIDTH, POINT_HEIGHT);

        switch (Character.toLowerCase(color)) {
            case 'b': this.color = Color.BLUE;    break;
            case 'r': this.color = Color.RED;     break;
            case 'y': this.color = Color.YELLOW;  break;
            case 'o': this.color = Color.ORANGE;  break;
            case 'p': this.color = Color.PINK;    break;
            case 'm': this.color = Color.MAGENTA; break;
            case 'g': this.color = Color.GREEN;   break;
            case 'c': this.color = Color.CYAN;    break;
            case 'e': this.color = Color.GRAY;    break;
            case 'k':
            default:  this.color = Color.BLACK;   break;
        }
    }

    // draws point with solid fill and outline
    public void drawPoint(Graphics2D g) {
        this.drawPoint(g, false);
    }

    // draws point — trailing renders as translucent fill with no outline
    public void drawPoint(Graphics2D g, boolean trailing) {
        if (!trailing) {
            // outline color contrasts with fill
            g.setColor(color.equals(Color.black) ? Color.white : Color.black);
            g.setStroke(new BasicStroke(OUTLINE_WIDTH));
            g.drawRect(point.x, point.y, dimension.width, dimension.height);
        }

        Color fillColor = trailing
            ? new Color(color.getRed(), color.getGreen(), color.getBlue(), TRAIL_ALPHA)
            : color;

        g.setColor(fillColor);
        g.fillRect(point.x, point.y, dimension.width, dimension.height);
    }

    // --- display ---

    @Override
    public String toString() {
        return point.x + " " + point.y + " " + color;
    }
}