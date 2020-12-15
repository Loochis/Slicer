package Physics;

public class Position {
    // Simple class to hold a few values for pathfinding. Not robust, quick and dirty implementation
    public double x, y;
    public boolean good = false;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Position(double x, double y, boolean goodPos) {
        this.x = x;
        this.y = y;
        this.good = goodPos;
    }
}
