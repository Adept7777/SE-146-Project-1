package sjsu.dubil.cs146.project1.part2;

import org.junit.Test; 
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * Class to handle testing of the two different quicksort implementations, along with measuring time and comparisons
 * for various length randomized arrays.
 */
public class QuicksortTest {
    private Quicksort QS;

    @Before
    public void before() throws Exception {
        QS = new Quicksort();
        QS.resetComparisonCount();
    }

    /**
     * Testing the edge case of the array being completely empty.
     */
    @Test
    public void testEmptyArray() {
        int[] testArray = new int[0];
        int[] correctArray = new int[0];

        testArray = QS.quickSort1(testArray, 0, testArray.length - 1);
        assertArrayEquals(correctArray, testArray);

        testArray = QS.quickSort2(testArray, 0, testArray.length - 1);
        assertArrayEquals(correctArray, testArray);
    }

    /**
     * Testing the edge case of the array having only a single element.
     */
    @Test
    public void testSingleElementArray() {
        int[] testArray = new int[1];
        testArray[0] = (int) (Math.random() * 10);
        int[] correctArray = Arrays.copyOf(testArray, testArray.length);

        testArray = QS.quickSort1(testArray, 0, testArray.length - 1);
        assertArrayEquals(correctArray, testArray);

        testArray = QS.quickSort2(testArray, 0, testArray.length - 1);
        assertArrayEquals(correctArray, testArray);
    }

    /**
     * Basic test with small, random arrays to ensure that the algorithms are functioning.
     */
    @Test
    public void testSmallRandomArray() {
        int[] testArray = new int[10];
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = (int) (Math.random() * 100);
        }
        int[] testArray2 = Arrays.copyOf(testArray, testArray.length);
        int[] correctArray = Arrays.copyOf(testArray, testArray.length);
        Arrays.sort(correctArray);

        testArray = QS.quickSort1(testArray, 0, testArray.length - 1);
        assertArrayEquals(correctArray, testArray);

        testArray2 = QS.quickSort2(testArray2, 0, testArray2.length - 1);
        assertArrayEquals(correctArray, testArray2);
    }

    /**
     * Basic test with small, already sorted arrays to check that edge case.
     */
    @Test
    public void testSortedArray() {
        int[] testArray1 = new int[10];
        int[] testArray2 = new int[10];
        int[] correctArray = new int[10];

        for (int i = 0; i < testArray1.length; i++) {
            testArray1[i] = i;
            testArray2[i] = i;
            correctArray[i] = i;
        }
        Arrays.sort(correctArray);

        testArray1 = QS.quickSort1(testArray1, 0, testArray1.length - 1);
        assertArrayEquals(correctArray, testArray1);

        testArray2 = QS.quickSort2(testArray2, 0, testArray2.length - 1);
        assertArrayEquals(correctArray, testArray2);
    }

    /**
     * Basic test with small arrays sorted in reverse order, to check that edge case.
     */
    @Test
    public void testReverseSortedArray() {
        int[] testArray1 = new int[10];
        int[] testArray2 = new int[10];
        int[] correctArray = new int[10];

        for (int i = 0; i < testArray1.length; i++) {
            testArray1[i] = (50 - i);
            testArray2[i] = (50 - i);
            correctArray[i] = (50 - i);
        }
        Arrays.sort(correctArray);

        testArray1 = QS.quickSort1(testArray1, 0, testArray1.length - 1);
        assertArrayEquals(correctArray, testArray1);

        testArray2 = QS.quickSort2(testArray2, 0, testArray2.length - 1);
        assertArrayEquals(correctArray, testArray2);
    }

    /**
     * Test of a specific case to ensure that the select algorithm is working properly and finding the median.
     */
    @Test
    public void testSelect() {
        int[] testArray = new int[100];
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = i;
        }

        // Median will be 50 in this array
        int median = QS.select(testArray, 0, testArray.length - 1, testArray.length / 2);
        assertEquals(50, median);
    }

    /**
     * Test of number of comparisons for a specific case to ensure they are being counted properly.
     */
    @Test
    public void testSortedComparisonCount() {
        int[] testArray = new int[10];
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = (i * 20);
        }

        // In an already-sorted array of 10 elements, there will be 45 comparisons for QS1
        QS.quickSort1(testArray, 0, testArray.length - 1);
        assertEquals(45, QS.getComparisonCount());
        QS.resetComparisonCount();

        // There will be 34 comparisons for my implementation of QS2 in the same array
        QS.quickSort2(testArray, 0, testArray.length - 1);
        assertEquals(34, QS.getComparisonCount());
    }

    /**
     * Test to measure the timing for randomly-filled large arrays with the first implementation of quicksort, which
     * uses the last element of the array as a pivot for partitioning.
     */
    @Test
    public void testQS1TimingAndComparisons() {
        int[] array10000 = this.generateRandomNLengthArray(10000);
        int[] array100000 = this.generateRandomNLengthArray(100000);
        int[] array1000000 = this.generateRandomNLengthArray(1000000);
        int[] array10000000 = this.generateRandomNLengthArray(10000000);
        int[] array100000000 = this.generateRandomNLengthArray(100000000);

        // Array with 10000 elements
        long begin = System.currentTimeMillis();
        QS.quickSort1(array10000, 0, array10000.length - 1);
        long end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS1 to sort an array with 10000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS1 with 10000 elements: " + QS.getComparisonCount());
        QS.resetComparisonCount();

        // Array with 100000 elements
        begin = System.currentTimeMillis();
        QS.quickSort1(array100000, 0, array100000.length - 1);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS1 to sort an array with 100000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS1 with 100000 elements: " + QS.getComparisonCount());
        QS.resetComparisonCount();

        // Array with 1000000 elements
        begin = System.currentTimeMillis();
        QS.quickSort1(array1000000, 0, array1000000.length - 1);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS1 to sort an array with 1000000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS1 with 1000000 elements: " + QS.getComparisonCount());
        QS.resetComparisonCount();

        // Array with 10000000 elements
        begin = System.currentTimeMillis();
        QS.quickSort1(array10000000, 0, array10000000.length - 1);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS1 to sort an array with 10000000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS1 with 10000000 elements: " + QS.getComparisonCount());
        QS.resetComparisonCount();

        // Array with 100000000000 elements
        begin = System.currentTimeMillis();
        QS.quickSort1(array100000000, 0, array100000000.length - 1);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS1 to sort an array with 100000000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS1 with 100000000 elements: " + QS.getComparisonCount());
    }

    /**
     * Test to measure the timing for randomly-filled large arrays with the second implementation of quicksort, which
     * uses the median of the array as a pivot
     */
    @Test
    public void testQS2TimingAndComparisons() {
        int[] array10000 = this.generateRandomNLengthArray(10000);
        int[] array100000 = this.generateRandomNLengthArray(100000);
        int[] array1000000 = this.generateRandomNLengthArray(1000000);
        int[] array10000000 = this.generateRandomNLengthArray(10000000);
        int[] array100000000 = this.generateRandomNLengthArray(100000000);

        // Array with 10000 elements
        long begin = System.currentTimeMillis();
        QS.quickSort2(array10000, 0, array10000.length - 1);
        long end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS2 to sort an array with 10000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS2 with 10000 elements: " + QS.getComparisonCount());
        QS.resetComparisonCount();

        // Array with 100000 elements
        begin = System.currentTimeMillis();
        QS.quickSort2(array100000, 0, array100000.length - 1);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS2 to sort an array with 100000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS2 with 100000 elements: " + QS.getComparisonCount());
        QS.resetComparisonCount();

        // Array with 1000000 elements
        begin = System.currentTimeMillis();
        QS.quickSort2(array1000000, 0, array1000000.length - 1);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS2 to sort an array with 1000000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS2 with 1000000 elements: " + QS.getComparisonCount());
        QS.resetComparisonCount();

        // Array with 10000000 elements
        begin = System.currentTimeMillis();
        QS.quickSort2(array10000000, 0, array10000000.length - 1);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS2 to sort an array with 10000000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS2 with 10000000 elements: " + QS.getComparisonCount());
        QS.resetComparisonCount();

        // Array with 100000000000 elements
        begin = System.currentTimeMillis();
        QS.quickSort2(array100000000, 0, array100000000.length - 1);
        end = System.currentTimeMillis();
        System.out.println("Time (in ms) for QS2 to sort an array with 100000000 elements: " + (end - begin));
        System.out.println("Number of comparisons for QS2 with 100000000 elements: " + QS.getComparisonCount());
    }

    /**
     * Helper method to create the large, random arrays for testing timings and comparisons. Fills the arrays from with
     * numbers from 0 to n / 10 since it is only intended to be used for large multiples of 10, and so that there is
     * still a large amount of variance in the numbers in the arrays.
     * @param n the length of the array to be generated
     * @return an array of length n filled with random numbers from 0 to (n/2 - 1)
     */
    private int[] generateRandomNLengthArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * (n / 10));
        }
        return array;
    }
}
