package Physics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Pathfinder {
    // Searches the board for the goal step by step
    abstract Position SearchStep(int iter, boolean[][] board, int xPos, int yPos, int goalPosX, int goalPosY, List<Position> visitedCells, List<Position> blockedCells);
    // Searches the board for the goal recursively
    abstract List<Position> SearchRecursive(int iter, boolean[][] board, int xPos, int yPos, int goalPosX, int goalPosY, List<Position> visitedCells, List<Position> blockedCells);

    // Performs a traceback step by step
    public Position TraceStep(int startIndex, int iter, boolean[][] board, List<Position> visitedCells, int cellWidth, int cellHeight) {
        // Convert visited cells to an array
        Position[] visitedPos = visitedCells.toArray(new Position[0]);
        Position startPosCell = visitedPos[startIndex];
        Position startPosReal = new Position(startPosCell.x, startPosCell.y);
        startPosReal.x += 0.5;
        startPosReal.y += 0.5;

        Position endPosCell = visitedPos[visitedPos.length - 1 - iter];
        Position endPosReal = new Position(endPosCell.x, endPosCell.y);
        endPosReal.x += 0.5;
        endPosReal.y += 0.5;

        // get the difference in x and y values from the start and end position
        double lineX = endPosReal.x - startPosReal.x;
        double lineY = endPosReal.y - startPosReal.y;
        // sample 100 points across the line (rasterize the line by flooring the sample coords)
        for (int i = 0; i < 100; i ++) {
            double roundedX = (lineX * (i / 100.0)) + (double)startPosReal.x;
            double roundedY = (lineY * (i / 100.0)) + (double)startPosReal.y;
            // If one of the samples lands on a wall, the path is invalid, return point with false bool
            if (board[(int)Math.floor(roundedX)][(int)Math.floor(roundedY)]) {
                return new Position(endPosReal.x, endPosReal.y, false);
            }
        }
        // If path is clear, return the point with true bool
        return new Position(endPosReal.x, endPosReal.y, true);
    }

    // Recursively trace the path, only call once, returns a list of nodes
    public List<Position> TraceRecursive(List<Position> initialNodes, int startIndex, boolean[][] board, List<Position> visitedCells, int cellWidth, int cellHeight) {
        if (visitedCells.isEmpty())
            return null;
        // Convert visited cells to array
        Position[] visitedPos = visitedCells.toArray(new Position[0]);
        Position startPosCell = visitedPos[startIndex];
        Position startPosReal = new Position(startPosCell.x, startPosCell.y);

        startPosReal.x += 0.5;
        startPosReal.y += 0.5;

        Position endPosCell = visitedPos[visitedPos.length - 1];
        Position endPosReal = new Position(endPosCell.x, endPosCell.y);
        endPosReal.x += 0.5;
        endPosReal.y += 0.5;

        // If the starting point is equal to the last point, the goal has been traced, return the nodes
        if (startIndex == visitedPos.length - 1)
            return initialNodes;

        // start cycling back through points from the goal to the current last index.
        for (int ii = visitedPos.length - 1; ii > startIndex; ii--) {

            // get difference in values
            double lineX = visitedPos[ii].x - startPosReal.x;
            double lineY = visitedPos[ii].y - startPosReal.y;

            // Take 200 samples of the line, rasterize each point by flooring
            boolean hitWall = false;
            for (int i = 0; i < 200; i++) {
                double roundedX = (lineX * (i / 200.0)) + (double) startPosReal.x;
                double roundedY = (lineY * (i / 200.0)) + (double) startPosReal.y;
                // If a sample lands on a wall, set hitWall to true and leave the loop
                if (board[(int) Math.floor(roundedX)][(int) Math.floor(roundedY)]) {
                    hitWall = true;
                    break;
                }
            }
            // If no wall has been hit, add a node and return the next recursion
            if (!hitWall) {
                initialNodes.add(new Position(visitedPos[ii].x + 0.5, visitedPos[ii].y + 0.5, true));
                return TraceRecursive(initialNodes, ii, board, visitedCells, cellWidth, cellHeight);
            }
        }
        // If there is no recursion where a new node can be placed, the goal is unreachable and null is returned
        initialNodes.clear();
        return null;
    }

}
