package Listeners;

import Standard.Main;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseScrollListener implements MouseWheelListener {

    private Main main;

    /**
     * Constructor requires reference to main to send signals to
     * @param main the main object to send signals to
     */
    public MouseScrollListener(Main main) {
        this.main = main;
    }

    // Sends main the amount the mouse wheel scrolled by (used to scale the board)
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        main.onScrollListen(e.getUnitsToScroll());
    }
}
