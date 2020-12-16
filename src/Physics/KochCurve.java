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
        // Colorwheel is an unused feature, may include in future projects
        colorWheel += 0.005f;
        if (colorWheel > 1)
            colorWheel = 0;

        g.setColor(Color.WHITE);
        DrawTri(g, 0, drawPanel.getWidth() / 2.0 - (main.trunk / 2.0), 20, drawPanel.getWidth() / 2.0 + (main.trunk / 2.0), 20, main.trunk);
    }

    private void DrawTri(Graphics g, int iter, double startX, double startY, double endX, double endY, double length) {
        // If iteration ahs reached its end, finally draw the line (this is the only time lines are drawn)
        if (iter > main.maxBranches) {
            g.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
            return;
        }

        // 3 additional points must be calculated to draw the spike, 1/3 of the line, 2/3 of the line, and one protruding from the center as an equilateral triangle
        double x1 = ((endX - startX) / 3.0);
        x1 += startX;
        double x2 = (2.0 * (endX - startX) / 3.0);
        x2 += startX;

        double y1 = ((endY - startY) / 3.0);
        y1 += startY;
        double y2 = (2.0 * (endY - startY) / 3.0);
        y2 += startY;

        // Calculate spike position (I believe rounding errors occur as some spikes on extreme recurses are inverted)
        double angle = Math.atan((endY - startY) / (endX - startX));
        angle %= 2*Math.PI;
        angle += Math.PI/3;

        double spikeX;
        double spikeY;
        spikeX = Math.cos(angle) * length / 3;
        spikeY = Math.sin(angle) * length / 3;

        // Attempt to account for very small number errors causing inverted spikes (doesn't fully work)
        if (endX - startX < -0.000001)
            spikeX *= -1;
        if (endY - startY < -0.000001)
            spikeY *= -1;

        spikeX += x1;
        spikeY += y1;

        // funkymode will never die!
        if (funkyMode) {
            float iterDiv = (float) Math.pow((float)iter / (float)main.maxBranches, 3);
            g.setColor(new Color(iterDiv, 1.0f, 1.0f - iterDiv));
        }

        // Recurse 4 times (once per additional line to be drawn)
        // The koch curve is extremely expensive (4^10 = 1,048,576 lines being drawn at maximum)
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
