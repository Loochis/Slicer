package Standard.Math3;

import java.awt.*;

public class Vector3 {
    // x, y, z components of the vector
    public double x;
    public double y;
    public double z;

    /**
     * Constructor
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Performs the vector dot product on this vector and another
     * @param B the other vector to operate on
     * @return the dot product
     */
    public double Dot(Vector3 B) {
        return x * B.x + y * B.y + z * B.z;
    }

    /**
     * Performs the vector cross product on this vector and another
     * @param B the other vector to operate on
     * @return the cross product vector
     */
    public Vector3 Cross(Vector3 B) {
        Vector3 out = new Vector3(0, 0, 0);
        out.x = y * B.z - z * B.y;
        out.y = z * B.x - x * B.z;
        out.z = x * B.y - y * B.x;

        return out;
    }

    /**
     * Subtracts another vector from this one
     * @param B the vector to subtract
     * @return the new vector
     */
    public Vector3 Subtract(Vector3 B) {
        return new Vector3(x - B.x, y - B.y, z - B.z);
    }

    /**
     * Normalize this vector
     */
    public void Normalized() {
        double length = Math.sqrt(x*x + y*y + z*z);
        x /= length;
        y /= length;
        z /= length;
    }

    /**
     * Clones this vector as a new object
     * @param vector3 the vector to clone
     * @return the cloned vector
     */
    public static Vector3 CloneOf(Vector3 vector3) {
        return new Vector3(vector3.x, vector3.y, vector3.z);
    }

    /**
     * Draws the vector as a point
     * @param g graphics to be passed in
     * @param radius the radius of the dot
     * @param col the color of the dot
     */
    public void DrawDot(Graphics g, double radius, Color col) {
        g.setColor(col);
        g.fillOval((int)(x - radius/2), (int)(y - radius/2), (int)radius, (int)radius);
    }

    /**
     * Gets the distance to another vector squared
     * @param v3 the other vector
     * @return the distance squared
     */
    public double DistanceTo2(Vector3 v3) {
        // sqrt is a heavy operation
        return (Math.pow(x-v3.x, 2) + Math.pow(y-v3.y, 2) + Math.pow(z-v3.z, 2));
    }
}
