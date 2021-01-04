package Graphical;

import Standard.Main;
import Standard.Shapes.Shape3;

import javax.swing.*;
import java.awt.*;

public class DrawablePanel extends JPanel {

    // Reference to board and main
    private Main main;

    // The offset values for drawing the board (panning)
    private int offsetX = 0, offsetY = 0;

    public void setMain(Main main) {this.main = main;}
    public Main getMain() {return main;}

    /**
     * Sets the offset of the boards draw (used to pan the board), drawablePanel side operation
     * @param x the new X offset
     * @param y the new Y offset
     */
    public void setOffset(int x, int y) {

        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;

        // Set the offset to the wrapped values
        offsetX = x;
        offsetY = y;
    }

    // gets the offset x/y
    public int getOffsetX() {return offsetX;}
    public int getOffsetY() {return offsetY;}

    // Draws the board to the screen
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(29, 29, 29));
        g.fillRect(0,0,getWidth(),getHeight());
        DrawShapes3(g);
    }

    private void DrawShapes3(Graphics g) {
        if (this.main == null)
            return;

        for (Shape3 shape : main.shapes3) {
            if (main.wire)
                shape.DrawShapeFill(g, Color.WHITE);
            if (main.verts)
                shape.DrawShapeVerts(g, new Color(255, 224, 0, 57));
            if (main.intersections)
                shape.DrawSlice(g, main.depth, new Color(255, 0, 0, 57));
        }
    }
}
