package Physics;

import Graphical.DrawablePanel;
import Standard.Main;

import java.awt.*;

// This interface lets me swap between types of sim more easily
public interface DrawableSim {
    void DrawSim(Graphics g, DrawablePanel drawPanel);
    void ToggleFunkyMode();
}
