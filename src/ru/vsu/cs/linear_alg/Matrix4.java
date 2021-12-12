package ru.vsu.cs.linear_alg;

public class Matrix4 {
    private float[] matrix;

    public Matrix4(float[][] matrix) {
        this.matrix = new float[16];
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++)
                this.matrix[row * 4 + col] = matrix[row][col];
    }

    private Matrix4(float[] arr) {
        matrix = arr;
    }

    public float getAt(int row, int col) {
        return matrix[row * 4 + col];
    }

    public void setAt(int row, int col, float value) {
        matrix[row * 4 + col] = value;
    }

    public static Matrix4 zero() {
        return new Matrix4(new float[16]);
    }

    public static Matrix4 one() {
        Matrix4 matrix = zero();
        for (int coord = 0; coord < 4; coord++)
            matrix.setAt(coord, coord, 1);
        return matrix;
    }

    public Matrix4 mul(float number) {
        float[] arr = new float[16];
        for (int idx = 0; idx < arr.length; idx++)
            arr[idx] = this.matrix[idx] * number;
        return new Matrix4(arr);
    }

    public Vector4 mul(Vector4 v) {
        float[] arr = new float[4];
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++)
                arr[row] += this.getAt(row, col) * v.at(col);
        return new Vector4(arr[0], arr[1], arr[2], arr[3]);
    }

    public Matrix4 mul(Matrix4 matrix) {
        Matrix4 zeroMatrix = zero();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    zeroMatrix.setAt(i, j, zeroMatrix.getAt(i, j) +
                            this.getAt(i, k) * matrix.getAt(k, j));
        return zeroMatrix;
    }
}
