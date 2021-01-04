package Standard.Math3;

public class TransformMatrix3 {
    // Vector defining translation
    public Vector3 translation = new Vector3(0, 0, 0);
    // Vector defining rotation
    public Vector3 rotation = new Vector3(0, 0, 0);
    // Vector defining scale
    public Vector3 scale = new Vector3(0, 0, 0);

    /**
     * Creates a transformationMatrix
     * @param translation the amount to move the base by
     * @param rotation the amount to rotate the base by
     * @param scale the amount to scale the base by
     */
    public TransformMatrix3(Vector3 translation, Vector3 rotation, Vector3 scale) {
        // Check for nulls
        if (translation != null)
            this.translation = translation;
        if (rotation != null)
            this.rotation = rotation;
        if (scale != null)
            this.scale = scale;
    }
}
