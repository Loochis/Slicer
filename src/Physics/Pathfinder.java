package Physics;

import java.awt.*;
import java.util.List;

public abstract class Pathfinder {
    abstract Position SearchStep(int iter, boolean[][] board, int xPos, int yPos, int goalPosX, int goalPosY, List<Position> visitedCells, List<Position> blockedCells);
    abstract List<Position> SearchRecursive(int iter, boolean[][] board, int xPos, int yPos, int goalPosX, int goalPosY, List<Position> visitedCells, List<Position> blockedCells);

    public Position TraceStep(int startIndex, int iter, boolean[][] board, List<Position> visitedCells, int cellWidth, int cellHeight) {
        Position[] visitedPos = visitedCells.toArray(new Position[0]);
        Position startPosCell = visitedPos[startIndex];
        Position startPosReal = new Position(startPosCell.x, startPosCell.y);
        startPosReal.x += 0.5;
        startPosReal.y += 0.5;

        Position endPosCell = visitedPos[visitedPos.length - 1 - iter];
        Position endPosReal = new Position(endPosCell.x, endPosCell.y);
        endPosReal.x += 0.5;
        endPosReal.y += 0.5;

        double lineX = endPosReal.x - startPosReal.x;
        double lineY = endPosReal.y - startPosReal.y;
        for (int i = 0; i < 100; i ++) {
            double roundedX = (lineX * (i / 100.0)) + (double)startPosReal.x;
            double roundedY = (lineY * (i / 100.0)) + (double)startPosReal.y;
            if (board[(int)Math.floor(roundedX)][(int)Math.floor(roundedY)]) {
                return new Position(endPosReal.x, endPosReal.y, false);
            }
        }
        return new Position(endPosReal.x, endPosReal.y, true);
    }

    public List<Position> TraceRecursive(List<Position> initialNodes, int startIndex, boolean[][] board, List<Position> visitedCells, int cellWidth, int cellHeight) {
        Position[] visitedPos = visitedCells.toArray(new Position[0]);
        Position startPosCell = visitedPos[startIndex];
        Position startPosReal = new Position(startPosCell.x, startPosCell.y);

        startPosReal.x += 0.5;
        startPosReal.y += 0.5;

        Position endPosCell = visitedPos[visitedPos.length - 1];
        Position endPosReal = new Position(endPosCell.x, endPosCell.y);
        endPosReal.x += 0.5;
        endPosReal.y += 0.5;

        if (startIndex == visitedPos.length - 1)
            return initialNodes;

        for (int ii = visitedPos.length - 1; ii > startIndex; ii--) {

            double lineX = visitedPos[ii].x - startPosReal.x;
            double lineY = visitedPos[ii].y - startPosReal.y;

            boolean hitWall = false;
            for (int i = 0; i < 200; i++) {
                double roundedX = (lineX * (i / 200.0)) + (double) startPosReal.x;
                double roundedY = (lineY * (i / 200.0)) + (double) startPosReal.y;
                if (board[(int) Math.floor(roundedX)][(int) Math.floor(roundedY)]) {
                    hitWall = true;
                    break;
                }
            }
            if (!hitWall) {
                initialNodes.add(new Position(visitedPos[ii].x + 0.5, visitedPos[ii].y + 0.5, true));
                return TraceRecursive(initialNodes, ii, board, visitedCells, cellWidth, cellHeight);
            }
        }
        return null;
    }

}
