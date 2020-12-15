package Physics;

import java.util.List;

public class DumbSearcher extends Pathfinder{

    @Override
    public Position SearchStep(int iter, boolean[][] board, int xPos, int yPos, int goalPosX, int goalPosY, List<Position> visitedCells, List<Position> blockedCells) {
        if (xPos == goalPosX && yPos == goalPosY)
            return new Position(xPos, yPos, true);

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

        if (!left && !right && !up && !down) {
            blockedCells.add(new Position(xPos, yPos));
            Position[] positions = visitedCells.toArray(new Position[0]);
            Position prevPos = positions[positions.length - 1];
            xPos = (int) Math.floor(prevPos.x);
            yPos = (int) Math.floor(prevPos.y);
            visitedCells.remove(prevPos);
            return new Position(xPos, yPos);
        }

        Position posBeforeMove = new Position(xPos, yPos);

        visitedCells.add(new Position(xPos, yPos));
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

        if (wantsRight == 0) {
            boolean ableToMove = false;
            if (wantsUp == 1) {
                if (up) {
                    yPos += 1;
                    ableToMove = true;
                }
                else if (down) {
                    yPos -= 1;
                    ableToMove = true;
                }
            } else {
                if (down) {
                    yPos -= 1;
                    ableToMove = true;
                }
                else if (up) {
                    yPos += 1;
                    ableToMove = true;
                }
            }
            if (!ableToMove) {
                if (right) {
                    xPos += 1;
                } else {
                    xPos -= 1;
                }
            }
        }
        else if (wantsRight == 1) {
            if (right) {
                xPos += 1;
            } else {
                boolean ableToMove = false;
                if (wantsUp == 1) {
                    if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                    else if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                } else {
                    if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    else if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                }
                if (!ableToMove) {
                    xPos -= 1;
                }
            }
        }
        else if (wantsRight == -1) {
            if (left) {
                xPos -= 1;
            } else {
                boolean ableToMove = false;
                if (wantsUp == 1) {
                    if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                    else if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                } else {
                    if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    else if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                }
                if (!ableToMove){
                    xPos += 1;
                }
            }
        }
        return new Position(xPos, yPos);
    }

    @Override
    public List<Position> SearchRecursive(int iter, boolean[][] board, int xPos, int yPos, int goalPosX, int goalPosY, List<Position> visitedCells, List<Position> blockedCells) {
        if (xPos == goalPosX && yPos == goalPosY)
            return visitedCells;

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

        Position posBeforeMove = new Position(xPos, yPos);

        visitedCells.add(new Position(xPos, yPos));
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

        if (wantsRight == 0) {
            boolean ableToMove = false;
            if (wantsUp == 1) {
                if (up) {
                    yPos += 1;
                    ableToMove = true;
                }
                else if (down) {
                    yPos -= 1;
                    ableToMove = true;
                }
            } else {
                if (down) {
                    yPos -= 1;
                    ableToMove = true;
                }
                else if (up) {
                    yPos += 1;
                    ableToMove = true;
                }
            }
            if (!ableToMove) {
                if (right) {
                    xPos += 1;
                } else {
                    xPos -= 1;
                }
            }
        }
        else if (wantsRight == 1) {
            if (right) {
                xPos += 1;
            } else {
                boolean ableToMove = false;
                if (wantsUp == 1) {
                    if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                    else if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                } else {
                    if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    else if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                }
                if (!ableToMove) {
                    xPos -= 1;
                }
            }
        }
        else if (wantsRight == -1) {
            if (left) {
                xPos -= 1;
            } else {
                boolean ableToMove = false;
                if (wantsUp == 1) {
                    if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                    else if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                } else {
                    if (down) {
                        yPos -= 1;
                        ableToMove = true;
                    }
                    else if (up) {
                        yPos += 1;
                        ableToMove = true;
                    }
                }
                if (!ableToMove){
                    xPos += 1;
                }
            }
        }
        try {
            return SearchRecursive(iter + 1, board, xPos, yPos, goalPosX, goalPosY, visitedCells, blockedCells);
        } catch (StackOverflowError e) {
            return null;
        }

    }
}
