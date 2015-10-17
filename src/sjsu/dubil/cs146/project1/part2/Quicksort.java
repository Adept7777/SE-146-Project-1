package sjsu.dubil.cs146.project1.part2;

import java.util.Arrays;

/**
 * Class that implements two methods of Quicksort, one that chooses the partition as the last element, and one that
 * uses the median of the array as the partition.
 */
public class Quicksort {
    private long comparisonCount;

    /**
     * First implementation of quicksort, always uses the last element as the pivot for partitioning.
     * @param array the array to be sorted
     * @param start the index signifying the start of the area in the array, should be 0 for whole array
     * @param end the index signifying the end of the area in the array, should be length - 1 for whole array
     * @return the sorted array
     */
    public int[] quickSort1(int[] array, int start, int end) {
        if (start < end) {
            int q = this.partition(array, start, end, array[end]); // Just using last element of array for pivot
            this.quickSort1(array, start, (q - 1));
            this.quickSort1(array, (q + 1), end);
        }

        return array;
    }

    /**
     * Second implementation of quicksort, uses the select algorithm to find the median and then uses the median as the
     * pivot for partitioning.
     * @param array the array to be sorted
     * @param start the index signifying the start of the area in the array, should be 0 for whole array
     * @param end the index signifying the end of the area in the array, should be length - 1 for whole array
     * @return the sorted array
     */
    public int[] quickSort2(int[] array, int start, int end) {
        if (start < end) {
            int pivot = this.select(array, start, end, ((end - start + 1) / 2)); // Finding median using select algorithm
            int q = this.partition(array, start, end, pivot); // Use median as pivot
            this.quickSort2(array, start, (q - 1));
            this.quickSort2(array, (q + 1), end);
        }

        return array;
    }

    /**
     * Algorithm that returns the k-th smallest element between the specified boundaries of the given array. Used to
     * select the median as a pivot in the second implementation of quicksort.
     * @param array the array in which the element is selected from
     * @param start the index marking the start of the area in the array in which the element is selected
     * @param end the index marking the end of the area in the array in which the element is selected
     * @param k the order of the element to be selected
     * @return the k-th smallest element within the limits of the array
     */
    public int select(int[] array, int start, int end, int k) {
        int n = end - start + 1;

        if (n <= 5) { // Handle case where not enough elements to make a group of 5
            Arrays.sort(array, start, end + 1);
            return array[start + k];
        }

        int numberOfGroups = n / 5;

        for (int i = 0; i < numberOfGroups; i++) { // Loop through the groups of 5 and move medians to front of array
            int shiftAmount = i * 5;
            int newStart = start + shiftAmount;
            int newEnd = newStart + 4;
            Arrays.sort(array, newStart, newEnd + 1);
            this.swap(array, start + i, newStart + 2);
        }

        int medianOfMedians = this.select(array, start, start + numberOfGroups - 1, numberOfGroups / 2);
        int position = this.partition(array, start, end, medianOfMedians); // Partition around median of medians

        if (position == (start + k)) { // The median of medians is the median of the whole array
            return array[position];
        }
        else if (position >= (start + k)) { // Median of array is less than the median of medians
            return this.select(array, start, position - 1, k);
        }
        else { // position < (start + k - 1) Median of array is greater than median of medians
            int numSmaller = position - start + 1;
            return this.select(array, position + 1, end, k - numSmaller);
        }
    }

    /**
     * Helper method created for the different types of partitioning used in the two implementations of quicksort, so
     * that two different methods did not need to be created just to use different pivots.
     * @param array the array to be partitioned
     * @param start the index signifying the start of the area in the array being partitioned
     * @param end the index signifying the end of the area in the array being partitioned
     * @param pivot the value of the pivot used in the partition
     * @return the index of the pivot after the partition is complete and the pivot is in its sorted location
     */
    private int partition(int[] array, int start, int end, int pivot) {
        if (array[end] != pivot) { // Check if need to swap pivot to the end of the array, and do so if needed
            for (int i = start; i <= end; i++) {
                if (array[i] == pivot) {
                    this.swap(array, i, end);
                    break;
                }
            }
        }
        int lowerBarrier = start - 1;
        for (int upperBarrier = start; upperBarrier < end; upperBarrier++) {
            if (array[upperBarrier] <= pivot) { // Only need to swap if the current element is smaller than the pivot
                lowerBarrier++;
                this.swap(array, lowerBarrier, upperBarrier);
            }
            this.comparisonCount++;
        }

        this.swap(array, (lowerBarrier + 1), end); // Move pivot to correct position
        return (lowerBarrier + 1);
    }

    /**
     * Method to return the number of comparisons after calling one of the implementations of quicksort.
     * @return the number of comparisons used in a run of quicksort
     */
    public long getComparisonCount() {
        return this.comparisonCount;
    }

    /**
     * Resets the current count of comparisons to 0, so that an accurate count can be reached for a new run of quicksort.
     */
    public void resetComparisonCount() {
        this.comparisonCount = 0;
    }

    /**
     * Simple helper method for swapping two elements of an array.
     * @param array the array in which to swap the elements
     * @param i the index of the first element to be swapped
     * @param j the index of the second element to be swapped
     */
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
