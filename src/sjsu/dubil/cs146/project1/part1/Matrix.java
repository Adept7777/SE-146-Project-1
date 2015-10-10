package sjsu.dubil.cs146.project1.part1;

import java.util.Arrays;

/**
 * Class that uses a 2-D array to model a matrix, and implements two different algorithms for matrix multiplication.
 */
public class Matrix {
    private double[][] data;

    public Matrix(double[][] array) {
        this.data = array;
    }

    /**
     * Implements the basic matrix multiplication algorithm. Runs in O(n^3).
     * @param other another square matrix of the same size as the current matrix
     * @return the matrix that results from the multiplication of the two matrices
     */
    public Matrix multiply(Matrix other) {
        int n = this.data.length;
        Matrix result = new Matrix(new double[n][n]);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result.data[i][j] += (this.data[i][k] * other.data[k][j]);
                }
            }
        }

        return result;
    }

    /**
     * Helper method for Strassen's matrix multiplication algorithm to make addition simpler.
     * @param other another square matrix of the same size as the current matrix
     * @return the matrix that results from the addition of the two matrices
     */
    private Matrix add(Matrix other) {
        int n = this.data.length;
        Matrix result = new Matrix(new double[n][n]);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.data[i][j] = this.data[i][j] + other.data[i][j];
            }
        }

        return result;
    }

    /**
     * Helper method for Strassen's matrix multiplication algorithm to make subtraction simpler.
     * @param other another square matrix of the same size as the current matrix
     * @return the matrix that results from the subtraction of the two matrices
     */
    private Matrix sub(Matrix other) {
        int n = this.data.length;
        Matrix result = new Matrix(new double[n][n]);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.data[i][j] = this.data[i][j] - other.data[i][j];
            }
        }

        return result;
    }

    /**
     * Implements Strassen's algorithm for matrix multiplication. Runs in approximately O(n^2.807).
     * @param other another square matrix of the same size as the current matrix
     * @return the matrix that results from the multiplication of the two matrices
     */
    public Matrix multiplyStrassen(Matrix other) {
        int n = this.data.length;
        Matrix result = new Matrix(new double[n][n]);

        // base cases where recursion ends
        if (n < 1) {
            return result;
        }

        if (n == 1) {
            result.data[0][0] = this.data[0][0] * other.data[0][0];
            return result;
        }

        // setting up new matrices
        int halfSize = n / 2; // for splitting the matrices
        Matrix a00 = new Matrix(new double[halfSize][halfSize]); // top left
        Matrix a01 = new Matrix(new double[halfSize][halfSize]); // top right
        Matrix a10 = new Matrix(new double[halfSize][halfSize]); // bottom left
        Matrix a11 = new Matrix(new double[halfSize][halfSize]); // bottom right

        Matrix b00 = new Matrix(new double[halfSize][halfSize]);
        Matrix b01 = new Matrix(new double[halfSize][halfSize]);
        Matrix b10 = new Matrix(new double[halfSize][halfSize]);
        Matrix b11 = new Matrix(new double[halfSize][halfSize]);

        // dividing the matrices
        for (int i = 0; i < halfSize; i++) {
            for (int j = 0; j < halfSize; j++) {
                a00.data[i][j] = this.data[i][j];
                a01.data[i][j] = this.data[i][halfSize + j];
                a10.data[i][j] = this.data[halfSize + i][j];
                a11.data[i][j] = this.data[halfSize + i][halfSize + j];

                b00.data[i][j] = other.data[i][j];
                b01.data[i][j] = other.data[i][halfSize + j];
                b10.data[i][j] = other.data[halfSize + i][j];
                b11.data[i][j] = other.data[halfSize + i][halfSize + j];
            }
        }

        // calculate m1 through m7
        Matrix temp1; // used for intermediate operations
        Matrix temp2;

        // m1 = (a00 + a11) * (b00 + b11)
        temp1 = a00.add(a11);
        temp2 = b00.add(b11);
        Matrix m1 = temp1.multiplyStrassen(temp2);

        // m2 = (a10 + a11) * b00
        temp1 = a10.add(a11);
        Matrix m2 = temp1.multiplyStrassen(b00);

        // m3 = a00 * (b01 - b11)
        temp1 = b01.sub(b11);
        Matrix m3 = a00.multiplyStrassen(temp1);

        // m4 = a11 * (b10 - b00)
        temp1 = b10.sub(b00);
        Matrix m4 = a11.multiplyStrassen(temp1);

        // m5 = (a00 + a01) * b11
        temp1 = a00.add(a01);
        Matrix m5 = temp1.multiplyStrassen(b11);

        // m6 = (a10 - a00) * (b00 + b01)
        temp1 = a10.sub(a00);
        temp2 = b00.add(b01);
        Matrix m6 = temp1.multiplyStrassen(temp2);

        // m7 = (a01 - a11) * (b10 + b11)
        temp1 = a01.sub(a11);
        temp2 = b10.add(b11);
        Matrix m7 = temp1.multiplyStrassen(temp2);

        // using these to obtain the quadrants of the resulting matrix
        // r00 = m1 + m4 - m5 + m7
        temp1 = m1.add(m4);
        temp2 = temp1.add(m7);
        Matrix r00 = temp2.sub(m5);

        // r01 = m3 + m5
        Matrix r01 = m3.add(m5);

        // r10 = m2 + m4
        Matrix r10 = m2.add(m4);

        // r11 = m1 + m3 - m2 + m6
        temp1 = m1.add(m3);
        temp2 = temp1.add(m6);
        Matrix r11 = temp2.sub(m2);

        // combining these to form the resulting array
        for (int i = 0; i < halfSize; i++) {
            for (int j = 0; j < halfSize; j++) {
                result.data[i][j] = r00.data[i][j];
                result.data[i][halfSize + j] = r01.data[i][j];
                result.data[halfSize + i][j] = r10.data[i][j];
                result.data[halfSize + i][halfSize + j] = r11.data[i][j];
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "Matrix{data=" + Arrays.deepToString(data) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Matrix other = (Matrix) o;
        double epsilon = 0.001;
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data.length; j++) {
                if (Math.abs(this.data[i][j] - other.data[i][j]) > epsilon) {
                    return false;
                }
            }
        }
        return true;
    }
}
