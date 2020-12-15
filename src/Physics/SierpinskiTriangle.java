package Physics;

import Graphical.DrawablePanel;
import Standard.Main;

import java.awt.*;
import java.util.Random;

public class SierpinskiTriangle implements DrawableSim{

    private Main main;
    private boolean funkyMode = false;

    double colorWheel = 0;

    /**
     * Default triangle constructor
     * @param main a main object to refer to
     */
    public SierpinskiTriangle(Main main) {
        this.main = main;
    }

    /**
     * Draws the triangle to the screen
     * @param g passed in graphics
     * @param drawPanel drawable panel to draw to
     */
    public void DrawSim(Graphics g, DrawablePanel drawPanel) {
        colorWheel += 0.005f;
        if (colorWheel > 1)
            colorWheel = 0;
        g.setColor(Color.WHITE);
        DrawTri(g, 0, drawPanel.getWidth() / 2, 20);
    }

    private void DrawTri(Graphics g, int iter, int xPos, int yPos) {
        if (iter > main.maxBranches)
            return;

        int x1 = (int) (Math.sin(Math.PI / 6) * main.trunk * Math.pow(0.5, iter));
        x1 += xPos;
        int x2 = (int) (Math.sin(-Math.PI / 6) * main.trunk * Math.pow(0.5, iter));
        x2 += xPos;

        int y1 = (int) (Math.cos(Math.PI / 6) * main.trunk * Math.pow(0.5, iter));
        y1 += yPos;
        int y2 = (int) (Math.cos(-Math.PI / 6) * main.trunk * Math.pow(0.5, iter));
        y2 += yPos;

        if (funkyMode) {
            float iterDiv = (float) Math.pow((float)iter / (float)main.maxBranches, 3);
            g.setColor(new Color(iterDiv, 1.0f, 1.0f - iterDiv));
        }
        g.drawLine(xPos, yPos, x1, y1);
        g.drawLine(xPos, yPos, x2, y2);
        g.drawLine(x1, y1, x2, y2);

        int baseXoffset = (int) (Math.pow(1.0/2.0, iter) * main.trunk) / 2;
        DrawTri(g, iter + 1, xPos + baseXoffset, yPos);
        DrawTri(g, iter + 1, xPos - baseXoffset, yPos);
        DrawTri(g, iter + 1, xPos, y2);
    }

    /**
     * Toggles F U N K Y mode, board side operation
     */
    public void ToggleFunkyMode() {
        funkyMode = !funkyMode;
    }
}
