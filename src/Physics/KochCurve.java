package Physics;

import Graphical.DrawablePanel;
import Standard.Main;

import java.awt.*;

public class KochCurve implements DrawableSim{

    private Main main;
    private boolean funkyMode = false;

    double colorWheel = 0;

    /**
     * Default triangle constructor
     * @param main a main object to refer to
     */
    public KochCurve(Main main) {
        this.main = main;
    }

    /**
     * Draws the curve to the screen
     * @param g passed in graphics
     * @param drawPanel drawable panel to draw to
     */
    public void DrawSim(Graphics g, DrawablePanel drawPanel) {
        colorWheel += 0.005f;
        if (colorWheel > 1)
            colorWheel = 0;

        g.setColor(Color.WHITE);
        DrawTri(g, 0, drawPanel.getWidth() / 2.0 - (main.trunk / 2.0), 20, drawPanel.getWidth() / 2.0 + (main.trunk / 2.0), 20, main.trunk);
    }

    private void DrawTri(Graphics g, int iter, double startX, double startY, double endX, double endY, double length) {
        if (iter > main.maxBranches) {
            g.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
            return;
        }

        double x1 = ((endX - startX) / 3.0);
        x1 += startX;
        double x2 = (2.0 * (endX - startX) / 3.0);
        x2 += startX;

        double y1 = ((endY - startY) / 3.0);
        y1 += startY;
        double y2 = (2.0 * (endY - startY) / 3.0);
        y2 += startY;

        double angle = Math.atan((endY - startY) / (endX - startX));
        angle %= 2*Math.PI;
        angle += Math.PI/3;

        double spikeX;
        double spikeY;
        spikeX = Math.cos(angle) * length / 3;
        spikeY = Math.sin(angle) * length / 3;

        if (endX - startX < -0.000001)
            spikeX *= -1;
        if (endY - startY < -0.000001)
            spikeY *= -1;

        spikeX += x1;
        spikeY += y1;

        if (funkyMode) {
            float iterDiv = (float) Math.pow((float)iter / (float)main.maxBranches, 3);
            g.setColor(new Color(iterDiv, 1.0f, 1.0f - iterDiv));
        }

        //g.drawLine(startX, startY, x1, y1);
        //g.drawLine(x2, y2, endX, endY);
        //g.drawLine(x1, y1, (int) spikeX, (int) spikeY);
        //g.drawLine(x2, y2, (int) spikeX, (int) spikeY);

        DrawTri(g, iter + 1, startX, startY, x1, y1, length / 3);
        DrawTri(g, iter + 1, x2, y2, endX, endY, length / 3);
        DrawTri(g, iter + 1, x1, y1, spikeX, spikeY, length / 3);
        DrawTri(g, iter + 1, spikeX, spikeY, x2, y2, length / 3);
    }

    /**
     * Toggles F U N K Y mode, board side operation
     */
    public void ToggleFunkyMode() {
        funkyMode = !funkyMode;
    }

    @Override
    public String toString() {
        return "Koch Curve";
    }
}
