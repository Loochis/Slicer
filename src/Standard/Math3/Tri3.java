package Standard.Math3;

import java.awt.*;

/**
 * Defines a triangle in 3D
 */
public class Tri3 {
    // the 3 vertices of the triangle
    public Vector3 vert1 = new Vector3(0,0,0);
    public Vector3 vert2 = new Vector3(0,0,0);
    public Vector3 vert3 = new Vector3(0,0,0);

    // The normal of the face of the tri
    public Vector3 normal;

    /**
     * Creates a tri, normal is calculated
     * @param vert1 vertex 1
     * @param vert2 vertex 2
     * @param vert3 vertex 3
     */
    public Tri3(Vector3 vert1, Vector3 vert2, Vector3 vert3) {
        this.vert1 = vert1;
        this.vert2 = vert2;
        this.vert3 = vert3;

        // Get the vectors of 2 lines of the triangle
        Vector3 triLine1 = vert2.Subtract(vert1);
        Vector3 triLine2 = vert3.Subtract(vert1);

        // Calculate normal using cross product, normalize resultant
        normal = triLine2.Cross(triLine1);
        normal.Normalized();
    }

    /**
     * Creates a tri with normal
     * @param vert1 vertex 1
     * @param vert2 vertex 2
     * @param vert3 vertex 3
     * @param normal normal of the triangle face
     */
    public Tri3(Vector3 vert1, Vector3 vert2, Vector3 vert3, Vector3 normal) {
            this.vert1 = vert1;
            this.vert2 = vert2;
            this.vert3 = vert3;
            this.normal = normal;
    }

    /**
     * Gets the vertices as an array of Vector3s
     * @return the array of vertices (length 3)
     */
    public Vector3[] GetVerts() {
        return new Vector3[] {vert1, vert2, vert3};
    }

    /**
     * Applies the transformations defined by a transformationMatrix
     * @param baseTri the triangle to transform
     * @param matrix the matrix defining transformations
     * @param pivot the pivot point defined
     * @return the transformed triangle
     */
    public static Tri3 ApplyTransformation(Tri3 baseTri, TransformMatrix3 matrix, Vector3 pivot) {
        // Create caches of vertices and normal
        Vector3[] verts = baseTri.GetVerts();
        Vector3 normal = baseTri.normal;

        // Cache the rotational values
        double angleXAxis = Math.toRadians(matrix.rotation.x);
        double angleYAxis = Math.toRadians(matrix.rotation.y);
        double angleZAxis = Math.toRadians(matrix.rotation.z);

        // Cache old values
        double nY = normal.y;
        double nZ = normal.z;

        // Rotate normal around X Axis
        normal.y = nY * Math.cos(angleXAxis) - nZ * Math.sin(angleXAxis);
        normal.z = nZ * Math.cos(angleXAxis) + nY * Math.sin(angleXAxis);

        // Cache old values
        double nX = normal.x;
        nZ = normal.z;

        // Rotate normal around Y Axis
        normal.x = nX * Math.cos(angleYAxis) + nZ * Math.sin(angleYAxis);
        normal.z = nZ * Math.cos(angleYAxis) - nX * Math.sin(angleYAxis);

        // Cache old values
        nX = normal.x;
        nY = normal.y;

        // Rotate normal around Z Axis
        normal.x = nX * Math.cos(angleZAxis) - nY * Math.sin(angleZAxis);
        normal.y = nY * Math.cos(angleZAxis) + nX * Math.sin(angleZAxis);

        for (int i = 0; i < 3; i++) {
            // Move vertices in relation to pivot
            verts[i].x -= pivot.x;
            verts[i].y -= pivot.y;
            verts[i].z -= pivot.z;

            // --- SCALING LOGIC --- //

            // Scale first
            // (Multiplies in local space as opposed to world space)
            verts[i].x *= matrix.scale.x;
            verts[i].y *= matrix.scale.y;
            verts[i].z *= matrix.scale.z;

            // --- ROTATIONAL LOGIC --- //

            double y = verts[i].y;
            double z = verts[i].z;

            // Rotate around X Axis
            verts[i].y = y * Math.cos(angleXAxis) - z * Math.sin(angleXAxis);
            verts[i].z = z * Math.cos(angleXAxis) + y * Math.sin(angleXAxis);

            double x = verts[i].x;
            z = verts[i].z;

            // Rotate around Y Axis
            verts[i].x = x * Math.cos(angleYAxis) + z * Math.sin(angleYAxis);
            verts[i].z = z * Math.cos(angleYAxis) - x * Math.sin(angleYAxis);


            x = verts[i].x;
            y = verts[i].y;

            // Rotate around Z Axis
            verts[i].x = x * Math.cos(angleZAxis) - y * Math.sin(angleZAxis);
            verts[i].y = y * Math.cos(angleZAxis) + x * Math.sin(angleZAxis);

            // --- TRANSLATIONAL LOGIC --- //

            // Translate in world space
            verts[i].x += matrix.translation.x;
            verts[i].y += matrix.translation.y;
            verts[i].z += matrix.translation.z;
        }
        // Return the transformed triangle
        return new Tri3(verts[0], verts[1], verts[2], normal);
    }

    /**
     * Draws the triangle as a wireframe mesh
     * @param g graphics to be passed in
     * @param col the color of the wireframe
     */
    public void DrawTri(Graphics g, Color col) {
        // Set color, draw lines
        g.setColor(col);
        g.drawLine((int)vert1.x, (int)vert1.y, (int)vert2.x, (int)vert2.y);
        g.drawLine((int)vert1.x, (int)vert1.y, (int)vert3.x, (int)vert3.y);
        g.drawLine((int)vert3.x, (int)vert3.y, (int)vert2.x, (int)vert2.y);
    }

    /**
     * Draws the triangle as a filled mesh
     * @param g graphics to be passed in
     * @param col the color of the triangle
     */
    public void DrawTriFill(Graphics g, Color col) {
        // Set color, create and draw fill polygon
        g.setColor(col);
        g.fillPolygon(new int[] {(int)vert1.x, (int)vert2.x, (int)vert3.x}, new int[] {(int)vert1.y, (int)vert2.y, (int)vert3.y}, 3);
    }

    /**
     * Creates a clone of this triangle (to be used as a pass by value)
     * @param tri3 the triangle to clone
     * @return the cloned triangle object
     */
    public static Tri3 CloneOf(Tri3 tri3) {
        return new Tri3(Vector3.CloneOf(tri3.vert1), Vector3.CloneOf(tri3.vert2), Vector3.CloneOf(tri3.vert3), Vector3.CloneOf(tri3.normal));
    }
}
