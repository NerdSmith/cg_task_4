package ru.vsu.cs.linear_alg;

public class Vector3 {
    private float[] values;

    public Vector3(float x, float y, float z) {
        values = new float[]{x, y, z};
    }


    public float getX() {
        return values[0];
    }

    public float getY() {
        return values[1];
    }

    public float getZ() {
        return values[2];
    }

    public float atIdx(int idx) {
        return values[idx];
    }
    
    private static final float EPSILON = 1e-10f;

    public float length() {
        float lenSqr = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
        if (lenSqr < EPSILON)
            return 0;
        return (float)Math.sqrt(lenSqr);
    }
    
}
