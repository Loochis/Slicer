package Graphical;

import Physics.DrawableSim;
import Physics.SierpinskiTriangle;
import Standard.Main;

import javax.swing.*;
import java.awt.*;

public class DrawablePanel extends JPanel {

    // Reference to board and main
    private DrawableSim drawableSimulator;
    private Main main;

    // The offset values for drawing the board (panning)
    private int offsetX = 0, offsetY = 0;

    public void setMain(Main main) {this.main = main;}
    public Main getMain() {return main;}
    public void setBoard(DrawableSim drawableSimulator) {
        this.drawableSimulator = drawableSimulator;
    }

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
        g.setColor(new Color(44, 44, 44));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(new Color(29, 29, 29));
        drawableSimulator.DrawSim(g, this);
    }
}
