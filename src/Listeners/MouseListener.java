package Listeners;

import Standard.Main;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    // reference to main
    private Main main;

    /**
     * Constructor requires reference to main to send signals to
     * @param main the main object to send signals to
     */
    public MouseListener(Main main) {
        this.main = main;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // Send main a mousePressed event when the mouse is pressed
    @Override
    public void mousePressed(MouseEvent e) {
        main.onMouseListen(true, e.getButton());
    }

    // Send main a mouseReleased event when the mouse is released
    @Override
    public void mouseReleased(MouseEvent e) {
        main.onMouseListen(false, e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
