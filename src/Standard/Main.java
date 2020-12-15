package Standard;

import Graphical.DrawablePanel;
import Graphical.MainFrame;
import Physics.KochCurve;
import Physics.PathfindingSim;
import Physics.SierpinskiTriangle;

public class Main{

    public double angle = Math.PI / 4;
    public double trunk = 100;
    public double coeff = 0.8;
    public int maxBranches = 1;

    public double cutoff = 2;

    // Target FPS and PhysicsUpdates per second
    public static final int TARGET_FPS = 120;

    // Nano constant, used to convert from nanoseconds to seconds and vice versa
    public static final double NANO_CONSTANT = Math.pow(10, 9); // Nano___ per SI unit

    // Delay between each display/physics frame (in nanoseconds)
    public static final double DELAY_FRAME = (1.0 / TARGET_FPS) * NANO_CONSTANT; // delay in ns between frames

    // stored previous update times, used for updating board and screen in controlled manner
    private long prevFrameTime = System.nanoTime();

    private DrawablePanel drawablePanel;
    private KochCurve kochCurve;
    private PathfindingSim pathfinder;

    private double mouseX = 0, mouseY = 0;
    private double deltaX = 0, deltaY = 0;
    private boolean clicking = false;
    private int mouseButton = 0;

    public boolean paused = true;
    public boolean recursiveMode = false;

    // Start here, create a window
    public static void main(String[] args) {
        System.out.println(bud(1));
        MainFrame frame = new MainFrame();
    }


    public static int bud(int n)
    {
        if (n>5)
            return n - 2;
        else
            return n + bud(n +1);
    }

    // On creation of main object, start thread to run engine in
    public Main() {
        (new Thread(this::Run)).start();
    }

    // Dispatch physics update and frame update on their own timers
    public void Run() {
        // Create a new board, set the drawablePanels board
        kochCurve = new KochCurve(this);
        pathfinder = new PathfindingSim(100, 70, this);
        drawablePanel.setBoard(pathfinder);

        // Main sim loop
        while (true) {
            if (clicking && mouseButton == 1)
                pathfinder.PlaceCell();
            if (clicking && mouseButton == 3)
                pathfinder.PlaceGoal();

            // Keep track of delta frame time and delta physics time
            double deltaFrameTime = System.nanoTime() - prevFrameTime;

            // Update frame at constant rate (ideal framerate defined above)
            if (deltaFrameTime > DELAY_FRAME) {
                prevFrameTime = System.nanoTime();
                drawablePanel.repaint();
            }

        }
    }

    /**
     * Gives main and drawablePanel references to each other
     * @param drawablePanel the drawable panel to get reference to
     */
    public void setDrawablePanel(DrawablePanel drawablePanel) {
        this.drawablePanel = drawablePanel;

        // If not done already, link the drawablePanel and this main
        if (drawablePanel.getMain() == null || drawablePanel.getMain() != this)
            drawablePanel.setMain(this);
    }

    /**
     * Toggles F U N K Y mode
     */
    public void ToggleFunkyMode() {
        pathfinder.ToggleFunkyMode();
        //pathfinder.StepSim(g);
    }

    /**
     * Sets the offset of the display (used for panning the board)
     */
    private void SetOffset() {
        if (clicking && mouseButton == 1)
            drawablePanel.setOffset(drawablePanel.getOffsetX() + (int)Math.ceil(deltaX), drawablePanel.getOffsetY() + (int)Math.ceil(deltaY));
    }

    /**
     * Is called by the mouse motion listener. also stores delta position
     * @param mouseX the X component of the mouse pos
     * @param mouseY the Y component of the mouse pos
     */
    public void onMouseMove(double mouseX, double mouseY) {
        deltaX = mouseX - this.mouseX;
        deltaY = mouseY - this.mouseY;
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        pathfinder.ScreenToBoardCoords(mouseX, mouseY);
        SetOffset();
    }

    /**
     * Is called by the mouse listener
     * @param mouse is a button being pressed, true or false
     * @param buttonPressed the button being pressed (1 = left click, 3 = right click)
     */
    public void onMouseListen(boolean mouse, int buttonPressed) {
        clicking = mouse;
        mouseButton = buttonPressed;
    }

    /**
     * Is called by the mouse wheel listener
     * @param scrollAmount is the amount the scroll wheel has spun (negative = up, positive = down)
     */
    public void onScrollListen(int scrollAmount) {
    }
}
