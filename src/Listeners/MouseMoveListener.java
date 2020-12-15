package Listeners;

import Standard.Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMoveListener implements MouseMotionListener {

    // Reference to main
    private Main main;

    /**
     * Constructor requires reference to main to send signals to
     * @param main the main object to send signals to
     */
    public MouseMoveListener(Main main) {
        this.main = main;
    }

    // Send amount mouse moved by to main
    @Override
    public void mouseDragged(MouseEvent e) {
        main.onMouseMove(e.getX(), e.getY());
    }

    // Send amount mouse moved by to main
    @Override
    public void mouseMoved(MouseEvent e) {
        main.onMouseMove(e.getX(), e.getY());
    }
}
