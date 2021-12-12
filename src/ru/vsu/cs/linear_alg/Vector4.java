package ru.vsu.cs.linear_alg;


public class Vector4 {
    private float[] values;

    public Vector4(float x, float y, float z, float w) {
        values = new float[]{x, y, z, w};
    }

    public Vector4(float x, float y, float z) {
        this(x, y, z, 0);
    }

    public Vector4(Vector3 vector, float w) {
        this(vector.getX(), vector.getY(), vector.getZ(), w);
    }

    public Vector4(Vector3 vector) {
        this(vector, 0);
    }

    private Vector4(float[] array) {
        this.values = array;
    }

    public static Vector4 zero() {
        return new Vector4(new float[4]);
    }

    public Vector4 mul(float number) {
        float[] array = new float[4];
        for (int i = 0; i < array.length; i++)
            array[i] = number * this.at(i);
        return new Vector4(array);
    }

    public Vector4 add(Vector4 vector) {
        float[] array = new float[4];
        for (int idx = 0; idx < array.length; idx++)
            array[idx] = this.at(idx) + vector.at(idx);
        return new Vector4(array);
    }

    private static final float EPSILON = 1e-10f;

    public Vector4 normalized() {
        if (Math.abs(getW()) < EPSILON)
            return new Vector4(this.getX(), this.getY(), this.getZ(), 0);
        return new Vector4(this.getX() / this.getW(), this.getY() / this.getW(), this.getZ() / this.getW(), 1);
    }

    public Vector3 asVector3() {
        Vector4 normalized = this.normalized();
        return new Vector3(normalized.getX(), normalized.getY(), normalized.getZ());
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

    public float getW() {
        return values[3];
    }

    public float at(int idx) {
        return values[idx];
    }
}
