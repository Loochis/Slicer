package Standard.Shapes;

import Standard.Math3.TransformMatrix3;
import Standard.Math3.Tri3;
import Standard.Math3.Vector3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Shape3 {
    public Tri3[] tris;
    private Tri3[] transformedTris;
    public Vector3 pivot;
    private TransformMatrix3 matrix;

    public Shape3(Tri3[] tris, TransformMatrix3 matrix) {
        this.tris = tris;

        transformedTris = new Tri3[tris.length];
        for (int i = 0; i < tris.length; i++) {
            transformedTris[i] = Tri3.CloneOf(tris[i]);
        }

        this.pivot = new Vector3(0, 0, 0);
        if (matrix != null)
            setMatrix(matrix);
    }

    public Shape3(Tri3[] tris, Vector3 pivot, TransformMatrix3 matrix) {
        this.tris = tris;

        transformedTris = new Tri3[tris.length];
        for (int i = 0; i < tris.length; i++) {
            transformedTris[i] = Tri3.CloneOf(tris[i]);
        }

        this.pivot = pivot;
        if (matrix != null)
            setMatrix(matrix);
    }

    public TransformMatrix3 getMatrix() {
        return matrix;
    }

    public void setMatrix(TransformMatrix3 matrix) {
        this.matrix = matrix;
        GetTransformedTris();
    }

    private void GetTransformedTris() {
        transformedTris = new Tri3[tris.length];
        for (int i = 0; i < tris.length; i++) {
            transformedTris[i] = Tri3.CloneOf(tris[i]);
        }

        for (int i = 0; i < transformedTris.length; i++) {
            transformedTris[i] = Tri3.ApplyTransformation(transformedTris[i], matrix, pivot);
        }
    }

    public void DrawShapeFill(Graphics g, Color col) {
        for (Tri3 tri : transformedTris) {
            double viewDot = tri.normal.Dot(new Vector3(0, 0, 1));
            if (viewDot < 0)
                continue;

            double dot = tri.normal.Dot(new Vector3(0.577, 0.577, 0.577));
            if (dot < 0)
                dot = 0;
            if (dot > 1)
                dot = 1;

            Color newCol = new Color((float) (col.getRed() * dot / 255), (float) (col.getGreen() * dot / 255), (float) (col.getBlue() * dot / 255));
            tri.DrawTriFill(g, newCol);
        }
    }

    public void DrawShapeWire(Graphics g, Color col) {
        for (Tri3 tri : transformedTris) {
            tri.DrawTri(g, col);
        }
    }

    public void DrawShapeVerts(Graphics g, Color col) {
        for (Tri3 tri : transformedTris) {
            for(int i = 0; i < 3; i++)
                tri.GetVerts()[i].DrawDot(g, 10, col);
        }
    }

    public void DrawSlice(Graphics g, double depth, Color col) {
        List<Vector3> intersectingPoints = new ArrayList<>();
        for (Tri3 tri : transformedTris) {
            double z1 = tri.vert1.z;
            double z2 = tri.vert2.z;
            double z3 = tri.vert3.z;

            if (z1 < depth && z2 > depth) {
                double dPercent = (depth - z1) / (z2 - z1);
                double newX = (tri.vert2.x - tri.vert1.x) * dPercent + tri.vert1.x;
                double newY = (tri.vert2.y - tri.vert1.y) * dPercent + tri.vert1.y;
                intersectingPoints.add(new Vector3(newX, newY, depth));
            }
            if (z2 < depth && z1 > depth) {
                double dPercent = (depth - z2) / (z1 - z2);
                double newX = (tri.vert1.x - tri.vert2.x) * dPercent + tri.vert2.x;
                double newY = (tri.vert1.y - tri.vert2.y) * dPercent + tri.vert2.y;
                intersectingPoints.add(new Vector3(newX, newY, depth));
            }
            if (z3 < depth && z2 > depth) {
                double dPercent = (depth - z3) / (z2 - z3);
                double newX = (tri.vert2.x - tri.vert3.x) * dPercent + tri.vert3.x;
                double newY = (tri.vert2.y - tri.vert3.y) * dPercent + tri.vert3.y;
                intersectingPoints.add(new Vector3(newX, newY, depth));
            }
            if (z2 < depth && z3 > depth) {
                double dPercent = (depth - z2) / (z3 - z2);
                double newX = (tri.vert3.x - tri.vert2.x) * dPercent + tri.vert2.x;
                double newY = (tri.vert3.y - tri.vert2.y) * dPercent + tri.vert2.y;
                intersectingPoints.add(new Vector3(newX, newY, depth));
            }
            if (z1 < depth && z3 > depth) {
                double dPercent = (depth - z1) / (z3 - z1);
                double newX = (tri.vert3.x - tri.vert1.x) * dPercent + tri.vert1.x;
                double newY = (tri.vert3.y - tri.vert1.y) * dPercent + tri.vert1.y;
                intersectingPoints.add(new Vector3(newX, newY, depth));
            }
            if (z3 < depth && z1 > depth) {
                double dPercent = (depth - z3) / (z1 - z3);
                double newX = (tri.vert1.x - tri.vert3.x) * dPercent + tri.vert3.x;
                double newY = (tri.vert1.y - tri.vert3.y) * dPercent + tri.vert3.y;
                intersectingPoints.add(new Vector3(newX, newY, depth));
            }
        }
        if (intersectingPoints.isEmpty())
            return;

        for (Vector3 v3 : intersectingPoints) {
            v3.DrawDot(g, 5, col);
        }
    }
}
