package Physics;

import Graphical.DrawablePanel;
import Standard.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Alright, comments comments comments here we come
public class PathfindingSim implements DrawableSim{

    // Defines the amount of frames between steps of the pathfinding algorithm (non-recursive mode)
    private static final int FRAMES_PER_STEP = 1;
    // Frame counter
    private int frames = 0;
    // Allows flexibility in the choice of pathfinding algorithm
    private Pathfinder pathfinder;

    // Reference to main for settings
    private Main main;
    // cell holder
    private boolean[][] cells;
    // Holds selected (traceback algorithm) and recently selected (user modification) cell coords
    private int selectedX = 0, selectedY = 0;
    private int recentX = 0, recentY = 0;

    // cell width and height in pixels (seperate in case of weird aspec ratios)
    private int cellWidth, cellHeight;

    // Of course funky mode is here
    private boolean funkyMode = false;

    // A bunch of lazily implemented variables for pathfinding
    private List<Position> visitedCells;
    private List<Position> blockedCells;
    private List<Position> nodes;
    private Position tempNode;
    private boolean onGoal = false;
    private boolean tracedGoal = false;
    private int lastNodeIndex = 0;
    private int tracingIndex = 0;

    // Holds the current position and the goal position (initial pathfinding)
    private int currentX = 0, currentY = 0;
    private int goalX, goalY;

    private boolean goalChanged = true;

    /**
     * Default board constructor
     * @param x the number of cells on the X
     * @param y the number of cells on the Y
     * @param main a main object to refer to
     */
    public PathfindingSim(int x, int y, Main main) {
        // Create a new pathfinder, initialize pathfinding variables
        pathfinder = new DumbSearcher();
        visitedCells = new ArrayList<>();
        blockedCells = new ArrayList<>();
        nodes = new ArrayList<>();
        nodes.add(new Position(0, 0));
        tempNode = nodes.get(0);

        // Initialize cells and references
        cells = new boolean[x][y];
        this.main = main;

        goalX = GetBoardWidth() - 1;
        goalY = GetBoardHeight() - 1;

        // Empty out all cells
        for (int x2 = 0; x2 < cells.length; x2++) { // x is length of array of arrays
            for (int y2 = 0; y2 < cells[0].length; y2++) { // y is length of array of booleans within array of arrays
                cells[x2][y2] = false;
            }
        }
    }

    // Gets the board width / height
    public int GetBoardWidth() {
        return cells.length;
    }
    public int GetBoardHeight() {
        return cells[0].length;
    }

    // Simulates one step of the pathfinder (visual purposes)
    public void StepSim() {
        // If the pathfinder hasnt reached its goal, keep searching
        if (!onGoal) {
            // Update local position variables with information from the pathfinder
            Position newPos = pathfinder.SearchStep(0, cells, currentX, currentY, goalX, goalY, visitedCells, blockedCells);
            currentX = (int)Math.floor(newPos.x);
            currentY = (int)Math.floor(newPos.y);
            onGoal = newPos.good;
        } else if (!tracedGoal) {
            // If the goal has been reached but not traced, start placing nodes based on tracer
            tempNode = pathfinder.TraceStep(lastNodeIndex, tracingIndex, cells, visitedCells, cellWidth, cellHeight);
            if (tempNode.good) {
                nodes.add(tempNode);
                lastNodeIndex = visitedCells.toArray(new Position[0]).length - 1 - tracingIndex;
                tracingIndex = 0;
                // IF goal has been traced, end the pathfinding process
                if ((int)Math.floor(tempNode.x) == goalX && (int) Math.floor(tempNode.y) == goalY)
                    tracedGoal = true;
            } else {
                // Otherwise increment the index of the tracer (the index of the visited cell)
                tracingIndex++;
            }
        }
    }

    /**
     * Draws the board to the screen (cells, boundaries)
     * @param g passed in graphics
     * @param drawPanel drawable panel to draw to
     */
    public void DrawSim(Graphics g, DrawablePanel drawPanel) {
        // If the goal has been changed reset all pathfinding variables
        if (goalChanged) {
            visitedCells = new ArrayList<>();
            blockedCells = new ArrayList<>();
            nodes = new ArrayList<>();
            nodes.add(new Position(0, 0));
            lastNodeIndex = 0;
            tracingIndex = 0;
            currentX = 0;
            currentY = 0;
            onGoal = false;
            tracedGoal = false;
            goalChanged = false;
        }

        // If in not-recursive mode
        if (!main.recursiveMode) {
            frames++;
            // update the simulation based on frame
            if (frames >= FRAMES_PER_STEP && !main.paused) {
                frames = 0;
                StepSim();
            }
            // Otherwise, do recursive pathfind
        } else {
            visitedCells = new ArrayList<>();
            blockedCells = new ArrayList<>();
            nodes = new ArrayList<>();
            nodes.add(new Position(0, 0));

            pathfinder.SearchRecursive(0, cells, 0, 0, goalX, goalY, visitedCells, blockedCells);
            if (visitedCells == null)
                System.err.println("Path cannot be found");
            else
                pathfinder.TraceRecursive(nodes, 0, cells, visitedCells, cellWidth, cellHeight);
        }

        cellWidth = drawPanel.getWidth() / GetBoardWidth();
        cellHeight = drawPanel.getHeight() / GetBoardHeight();

        // Cycle through every cell on the x, get the working value (modulated to create infinite board)
        for (int x = 0; x < GetBoardWidth(); x++) { // x is length of array of arrays

            // Cycle through every cell on the y, get the working value (modulated to create infinite board)
            for (int y = 0; y < GetBoardHeight(); y++) { // y is length of array of booleans within array of arrays

                // If the cell is alive, draw it
                if (cells[x][y])
                    // if F U N K Y mode is enabled, make the F U N K Y colors
                    if (!funkyMode)
                        g.setColor(new Color(255, 224, 89));
                    else {
                        // use screenspace coords to create a color gradient
                        double yPercent = (y * cellHeight) / (double)drawPanel.getHeight();
                        if (yPercent > 1)
                            yPercent = 1;
                        if (yPercent < 0)
                            yPercent = 0;

                        double xPercent = (x * cellWidth) / (double)drawPanel.getWidth();
                        if (xPercent > 1)
                            xPercent = 1;
                        if (xPercent < 0)
                            xPercent = 0;

                        // Compile screenspace percents as a color
                        g.setColor(new Color((float)yPercent, (float)xPercent, (float)(1.0 - yPercent)));
                    }
                else
                    g.setColor(new Color(29, 29, 29)); // If the cell is dead, color it dark grey
                if (selectedX == x && selectedY == y)
                    g.setColor(new Color(255, 0, 0));  // If the cell is selected, color it red

                // Draw pathfinder position
                if (currentX == x && currentY == y)
                    g.setColor(new Color(0, 34, 255));  // If the cell is the pathfinders position, color it blue

                // Draw goal
                if (goalX == x && goalY == y)
                    g.setColor(new Color(247, 255, 0));  // If the cell is the pathfinders position, color it blue

                // Draw the cell to the screen
                g.fillRect((x) * (int)Math.floor(cellWidth), (y) * (int)Math.floor(cellHeight), (int)Math.floor(cellWidth) - 1, (int)Math.floor(cellHeight) - 1);
            }
        }

        // Draw nodes
        g.setColor(new Color(89, 255, 0));
        for (Position blockedPos : nodes) {
            g.fillOval((int)Math.floor(blockedPos.x) * (int)Math.floor(cellWidth), (int)Math.floor(blockedPos.y) * (int)Math.floor(cellHeight), (int)Math.floor(cellWidth) - 1, (int)Math.floor(cellHeight) - 1);
        }

        // Draw lines between nodes
        Position[] nodePos = nodes.toArray(new Position[0]);
        if (nodePos.length > 1) {
            for (int i = 1; i < nodePos.length; i++) {
                g.drawLine((int) Math.floor(nodePos[i - 1].x * (int)Math.floor(cellWidth)), (int) Math.floor(nodePos[i - 1].y  * (int)Math.floor(cellHeight)), (int) Math.floor(nodePos[i].x  * (int)Math.floor(cellWidth)), (int) Math.floor(nodePos[i].y  * (int)Math.floor(cellHeight)));
            }
        }

        if (main.recursiveMode)
            return;

        // Draw visited cells
        g.setColor(new Color(143, 56, 208));
        for (Position blockedPos : visitedCells) {
            g.fillRect((int)Math.floor(blockedPos.x) * (int)Math.floor(cellWidth), (int)Math.floor(blockedPos.y) * (int)Math.floor(cellHeight), (int)Math.floor(cellWidth) - 1, (int)Math.floor(cellHeight) - 1);
        }

        // Draw blocked cells
        g.setColor(new Color(62, 62, 62));
        for (Position blockedPos : blockedCells) {
            g.fillRect((int)Math.floor(blockedPos.x) * (int)Math.floor(cellWidth), (int)Math.floor(blockedPos.y) * (int)Math.floor(cellHeight), (int)Math.floor(cellWidth) - 1, (int)Math.floor(cellHeight) - 1);
        }

        // Draw invalid (searching) node rays
        g.setColor(new Color(255, 0, 0));
        g.drawLine((int) Math.floor(nodePos[nodePos.length-1].x * (int)Math.floor(cellWidth)), (int) Math.floor(nodePos[nodePos.length-1].y * (int)Math.floor(cellHeight)), (int) Math.floor(tempNode.x * (int)Math.floor(cellWidth)), (int) Math.floor(tempNode.y * (int)Math.floor(cellHeight)));
    }

    /**
     * A corrected modulus operation (does not return negative values)
     * @param op1 the original number
     * @param op2 the "modulator" (idk what its called)
     * @return the corrected modulus operations output
     */
    private int CorrectedModulo(int op1, int op2) {
        return (((op1 % op2) + op2) % op2);
    }

    /**
     * Converts screenspace coords to board coords (wrapping and all)
     * @param screenX the screen X position
     * @param screenY the screen Y position
     */
    public void ScreenToBoardCoords(double screenX, double screenY) {
        selectedX = (int) Math.floor((screenX) / cellWidth);
        selectedY = (int) Math.floor((screenY) / cellHeight);
    }

    /**
     * Toggles the living status of the cell at given coords
     */
    public void PlaceCell() {
        if (selectedX >= GetBoardWidth() || selectedY >= GetBoardHeight())
            return;
        if (selectedX < 0 || selectedY < 0)
            return;
        if (selectedX == goalX && selectedY == goalY)
            return;

        if (!(selectedX == recentX && selectedY == recentY)) {
            cells[selectedX][selectedY] = !cells[selectedX][selectedY];
            recentX = selectedX;
            recentY = selectedY;
        }
    }

    // Places the goal elsewhere
    public void PlaceGoal() {
        if (selectedX >= GetBoardWidth() || selectedY >= GetBoardHeight())
            return;
        if (selectedX < 0 || selectedY < 0)
            return;
        if (selectedX == goalX && selectedY == goalY)
            return;

        goalX = selectedX;
        goalY = selectedY;
        goalChanged = true;
    }

    /**
     * Toggles F U N K Y mode, board side operation
     */
    public void ToggleFunkyMode() {
        // Gotta have F U N K Y mode
        // I mean, it's a staple at this point
        funkyMode = !funkyMode;
    }

    @Override
    public String toString() {
        return "Pathfinder";
    }

}
