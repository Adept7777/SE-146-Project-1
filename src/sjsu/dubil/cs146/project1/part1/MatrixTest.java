package sjsu.dubil.cs146.project1.part1;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class to handle testing of the Matrix class, specifically the two multiplication methods.
 */
public class MatrixTest {
    /**
     * Make sure the multiplications work with empty matrices.
     */
    @Test
    public void testEmptyMatrices() {
        Matrix m1 = new Matrix(new double[0][0]);
        Matrix m2 = new Matrix(new double[0][0]);

        assertEquals("Failure: Multiplication not resulting in equal arrays", m1.multiply(m2), m1.multiplyStrassen(m2));
    }

    /**
     * Helper method for matrix tests, generates an nxn matrix and fills it with random numbers from 0-10.
     * @param n the value used for the dimensions of the generated matrix
     * @return an nxn matrix filled with random numbers from 0-10
     */
    private Matrix generateRandomNxNMatrix(int n) {
        double[][] temp = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = (Math.random() * 10);
            }
        }

        return new Matrix(temp);
    }

    /**
     * Basic test with 4x4 matrices to make sure both multiplication algorithms work.
     */
    @Test
    public void testRandom4x4Matrices() {
        int n = 4;
        Matrix m1 = generateRandomNxNMatrix(n);
        Matrix m2 = generateRandomNxNMatrix(n);

        assertEquals("Failure: Multiplication not resulting in equal arrays", m1.multiply(m2), m1.multiplyStrassen(m2));
    }

    /**
     * Basic test with 16x16 matrices to make sure both multiplication algorithms work.
     */
    @Test
    public void testRandom16x16Matrices() {
        int n = 16;
        Matrix m1 = generateRandomNxNMatrix(n);
        Matrix m2 = generateRandomNxNMatrix(n);

        assertEquals("Failure: Multiplication not resulting in equal arrays", m1.multiply(m2), m1.multiplyStrassen(m2));
    }

    /**
     * Test that measures the running time in milliseconds for the basic matrix multiplication algorithm on random nxn
     * matrices, for various values of n.
     */
    @Test
    public void testBasicMultiplicationTiming() {
        // n = 4
        Matrix m1 = generateRandomNxNMatrix(4);
        Matrix m2 = generateRandomNxNMatrix(4);
        long begin = System.currentTimeMillis();
        m1.multiply(m2);
        long end = System.currentTimeMillis();
        System.out.println("Time (in ms) to multiply two 4x4 matrices with the basic algorithm: " + (end - begin));

        // n = 16
        m1 = generateRandomNxNMatrix(16);
        m2 = generateRandomNxNMatrix(16);
        begin = System.currentTimeMillis();
        m1.multiply(m2);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) to multiply two 16x16 matrices with the basic algorithm: " + (end - begin));

        // n = 512
        m1 = generateRandomNxNMatrix(512);
        m2 = generateRandomNxNMatrix(512);
        begin = System.currentTimeMillis();
        m1.multiply(m2);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) to multiply two 512x512 matrices with the basic algorithm: " + (end - begin));

        // n = 1024
        m1 = generateRandomNxNMatrix(1024);
        m2 = generateRandomNxNMatrix(1024);
        begin = System.currentTimeMillis();
        m1.multiply(m2);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) to multiply two 1024x1024 matrices with the basic algorithm: " + (end - begin));
    }

    /**
     * Test that measures the running time in milliseconds for the Strassen matrix multiplication algorithm on random nxn
     * matrices, for various values of n.
     */
    @Test
    public void testStrassenMultiplicationTiming() {
        // n = 4
        Matrix m1 = generateRandomNxNMatrix(4);
        Matrix m2 = generateRandomNxNMatrix(4);
        long begin = System.currentTimeMillis();
        m1.multiplyStrassen(m2);
        long end = System.currentTimeMillis();
        System.out.println("Time (in ms) to multiply two 4x4 matrices with the Strassen algorithm: " + (end - begin));

        // n = 16
        m1 = generateRandomNxNMatrix(16);
        m2 = generateRandomNxNMatrix(16);
        begin = System.currentTimeMillis();
        m1.multiplyStrassen(m2);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) to multiply two 16x16 matrices with the Strassen algorithm: " + (end - begin));

        // n = 512
        m1 = generateRandomNxNMatrix(512);
        m2 = generateRandomNxNMatrix(512);
        begin = System.currentTimeMillis();
        m1.multiplyStrassen(m2);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) to multiply two 512x512 matrices with the Strassen algorithm: " + (end - begin));

        // n = 1024
        m1 = generateRandomNxNMatrix(1024);
        m2 = generateRandomNxNMatrix(1024);
        begin = System.currentTimeMillis();
        m1.multiplyStrassen(m2);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) to multiply two 1024x1024 matrices with the Strassen algorithm: " + (end - begin));
    }
}