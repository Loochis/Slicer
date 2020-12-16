package Physics;

import java.util.List;

public class DumbSearcher extends Pathfinder{

    @Override
    public Position SearchStep(int iter, boolean[][] board, int xPos, int yPos, int goalPosX, int goalPosY, List<Position> visitedCells, List<Position> blockedCells) {
        // IF the tracer has reached the goal, return the final position
        if (xPos == goalPosX && yPos == goalPosY)
            return new Position(xPos, yPos, true);

        // create a list of possible moves by cancelling the impossible moves (moving into a wall / into a previously visited/blocked cell)
        boolean left = true, right = true, up = true, down = true;
        if(xPos == 0 || board[xPos - 1][yPos])
            left = false;
        if(xPos == board.length-1 || board[xPos + 1][yPos])
            right = false;
        if(yPos == 0 || board[xPos][yPos - 1])
            down = false;
        if(yPos == board[0].length-1 || board[xPos][yPos + 1])
            up = false;

        for (Position blockedCell: blockedCells) {
            if (xPos - 1 == blockedCell.x && yPos == blockedCell.y)
                left = false;
            if (xPos + 1 == blockedCell.x && yPos == blockedCell.y)
                right = false;
            if (yPos - 1 == blockedCell.y && xPos == blockedCell.x)
                down = false;
            if (yPos + 1 == blockedCell.y && xPos == blockedCell.x)
                up = false;
        }

        for (Position blockedCell: visitedCells) {
            if (xPos - 1 == blockedCell.x && yPos == blockedCell.y)
                left = false;
            if (xPos + 1 == blockedCell.x && yPos == blockedCell.y)
                right = false;
            if (yPos - 1 == blockedCell.y && xPos == blockedCell.x)
                down = false;
            if (yPos + 1 == blockedCell.y && xPos == blockedCell.x)
                up = false;
        }

        // If no moves are possible, step back and block the previous cell (blocked cells cannot be traversed under any circumstance)
        if (!left && !right && !up && !down) {
            blockedCells.add(new Position(xPos, yPos));
            Position[] positions = visitedCells.toArray(new Position[0]);
            Position prevPos = positions[positions.length - 1];
            xPos = (int) Math.floor(prevPos.x);
            yPos = (int) Math.floor(prevPos.y);
            visitedCells.remove(prevPos);
            return new Position(xPos, yPos);
        }

        // add the current position to the visited cells
        visitedCells.add(new Position(xPos, yPos));

        // Determine the direction the tracer wants to move in by determining which side the goal is on relatively
        int wantsRight = 0;
        int wantsUp = 0;
        if (goalPosX - xPos < 0)
            wantsRight = -1;
        if (goalPosX - xPos > 0)
            wantsRight = 1;
        if (goalPosY - yPos < 0)
            wantsUp = -1;
        if (goalPosY - yPos > 0)
            wantsUp = 1;

        // In the case that the tracer does not want to move laterally
        if (wantsRight == 0) {
            // Case that it wants to move up
            boolean ableToMove = false;
            if (wantsUp == 1) {
                if (up) {
                    yPos += 1;
                    ableToMove = true;
                }
                // Case that it cannot move up, but wants to, move down
                else if (down) {
                    yPos -= 1;
                    ableToMove = true;
                }
                // Case that it wants to move down
            } else {
                if (down) {
                    yPos -= 1;
                    ableToMove = true;
                }
                // Case that it cannot move down, but wants to, move up
                else if (up) {
                    yPos += 1;
                    ableToMove = true;
                }
            }
            // As a fallback, move in an arbitrary way laterally
            if (!ableToMove) {
                if (right) {
                    xPos += 1;
                } else {
                    xPos -= 1;
                }
            }
        }
        // Case that the tracer wants to move right
        else if (wantsRight == 1) {
            if (right) {
                xPos += 1;
            } else {
                // Case that it cannot do desired move, use vertical desire as next move
                boolean ableToMove = false;
                // Case that it wants to go up
                if (wantsUp == 1) {
                    if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                    // Case that it is forced to move down
                    else if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    // Case that it wants to move down
                } else {
                    if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    // Case that it is forced up
                    else if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                }
                // As a fallback, move left (moving right has been deemed impossible earlier in the statement)
                if (!ableToMove) {
                    xPos -= 1;
                }
            }
        }
        // Case that the tracer wants to move left
        else if (wantsRight == -1) {
            if (left) {
                xPos -= 1;
            } else {
                // Case that it cannot move in the desired direction
                boolean ableToMove = false;
                // Case that it wants to move up
                if (wantsUp == 1) {
                    if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                    // Case that it is forced down
                    else if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    // Case that it wants to move down
                } else {
                    if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    // Case that it is forced up
                    else if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                }
                // As a fallback, move right (moving left was deemed impossible earlier in the statement)
                if (!ableToMove){
                    xPos += 1;
                }
            }
        }
        // Return the new position of the marker
        return new Position(xPos, yPos);
    }

    @Override
    public List<Position> SearchRecursive(int iter, boolean[][] board, int xPos, int yPos, int goalPosX, int goalPosY, List<Position> visitedCells, List<Position> blockedCells) {
        // If the tracer has reached the goal, return the list of visited cells
        if (xPos == goalPosX && yPos == goalPosY)
            return visitedCells;

        // create a list of possible moves by cancelling the impossible moves (moving into a wall / into a previously visited/blocked cell)
        boolean left = true, right = true, up = true, down = true;
        if(xPos == 0 || board[xPos - 1][yPos])
            left = false;
        if(xPos == board.length-1 || board[xPos + 1][yPos])
            right = false;
        if(yPos == 0 || board[xPos][yPos - 1])
            down = false;
        if(yPos == board[0].length-1 || board[xPos][yPos + 1])
            up = false;

        for (Position blockedCell: blockedCells) {
            if (xPos - 1 == blockedCell.x && yPos == blockedCell.y)
                left = false;
            if (xPos + 1 == blockedCell.x && yPos == blockedCell.y)
                right = false;
            if (yPos - 1 == blockedCell.y && xPos == blockedCell.x)
                down = false;
            if (yPos + 1 == blockedCell.y && xPos == blockedCell.x)
                up = false;
        }

        for (Position blockedCell: visitedCells) {
            if (xPos - 1 == blockedCell.x && yPos == blockedCell.y)
                left = false;
            if (xPos + 1 == blockedCell.x && yPos == blockedCell.y)
                right = false;
            if (yPos - 1 == blockedCell.y && xPos == blockedCell.x)
                down = false;
            if (yPos + 1 == blockedCell.y && xPos == blockedCell.x)
                up = false;
        }

        // If no moves are possible, step back and block the previous cell (blocked cells cannot be traversed under any circumstance)
        // Iterate again using the previously visited cells coords (check for stack overflow error)
        if (!left && !right && !up && !down) {
            blockedCells.add(new Position(xPos, yPos));
            Position[] positions = visitedCells.toArray(new Position[0]);
            Position prevPos = positions[positions.length - 1];
            xPos = (int) Math.floor(prevPos.x);
            yPos = (int) Math.floor(prevPos.y);
            visitedCells.remove(prevPos);
            try {
                return SearchRecursive(iter + 1, board, xPos, yPos, goalPosX, goalPosY, visitedCells, blockedCells);
            } catch (StackOverflowError e) {
                return null;
            }
        }

        // add the current position to the visited cells
        visitedCells.add(new Position(xPos, yPos));

        // Determine the direction the tracer wants to move in by determining which side the goal is on relatively
        int wantsRight = 0;
        int wantsUp = 0;
        if (goalPosX - xPos < 0)
            wantsRight = -1;
        if (goalPosX - xPos > 0)
            wantsRight = 1;
        if (goalPosY - yPos < 0)
            wantsUp = -1;
        if (goalPosY - yPos > 0)
            wantsUp = 1;

        // In the case that the tracer does not want to move laterally
        if (wantsRight == 0) {
            // Case that it wants to move up
            boolean ableToMove = false;
            if (wantsUp == 1) {
                if (up) {
                    yPos += 1;
                    ableToMove = true;
                }
                // Case that it cannot move up, but wants to, move down
                else if (down) {
                    yPos -= 1;
                    ableToMove = true;
                }
                // Case that it wants to move down
            } else {
                if (down) {
                    yPos -= 1;
                    ableToMove = true;
                }
                // Case that it cannot move down, but wants to, move up
                else if (up) {
                    yPos += 1;
                    ableToMove = true;
                }
            }
            // As a fallback, move in an arbitrary way laterally
            if (!ableToMove) {
                if (right) {
                    xPos += 1;
                } else {
                    xPos -= 1;
                }
            }
        }
        // Case that the tracer wants to move right
        else if (wantsRight == 1) {
            if (right) {
                xPos += 1;
            } else {
                // Case that it cannot do desired move, use vertical desire as next move
                boolean ableToMove = false;
                // Case that it wants to go up
                if (wantsUp == 1) {
                    if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                    // Case that it is forced to move down
                    else if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    // Case that it wants to move down
                } else {
                    if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    // Case that it is forced up
                    else if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                }
                // As a fallback, move left (moving right has been deemed impossible earlier in the statement)
                if (!ableToMove) {
                    xPos -= 1;
                }
            }
        }
        // Case that the tracer wants to move left
        else if (wantsRight == -1) {
            if (left) {
                xPos -= 1;
            } else {
                // Case that it cannot move in the desired direction
                boolean ableToMove = false;
                // Case that it wants to move up
                if (wantsUp == 1) {
                    if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                    // Case that it is forced down
                    else if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    // Case that it wants to move down
                } else {
                    if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    // Case that it is forced up
                    else if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                }
                // As a fallback, move right (moving left was deemed impossible earlier in the statement)
                if (!ableToMove){
                    xPos += 1;
                }
            }
        }
        // Attempt to recurse again using new coordinates, if unable to assume the target is unreachable and return null
        try {
            return SearchRecursive(iter + 1, board, xPos, yPos, goalPosX, goalPosY, visitedCells, blockedCells);
        } catch (StackOverflowError e) {
            visitedCells.clear();
            return null;
        }

    }
}
