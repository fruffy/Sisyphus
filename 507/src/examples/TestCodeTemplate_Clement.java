package example;

import java.util.ArrayList;
import java.util.List;

/**
 * Please implement the following methods without using any
 * method from Java libraries that achieves the same objective.
 * For example, your first task is to write code for "absoluteValue" 
 * that returns the absolute value of an int. Please do not use Java
 * Math abs() to write this method.
 * @author iaakhter
 *
 */
public class TestCodeTemplate {
	
	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValue(int val){
		//YOUR CODE HERE

        if (val < 0) {
            return (-1) * val;
        }

        return val;

	}
	
	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximum(int val1, int val2){
		//YOUR CODE HERE

        // if there is a tie, either works, so return val1
        if (val1 >= val2) {
            return val1;
        }

        return val2;

	}
	
	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimum(int val1, int val2){
		//YOUR CODE HERE

        if (val1 <= val2) {
            return val1;
        }

        return val2;

	}
	
	
	/**
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double power(double val1, double val2){
		//YOUR CODE HERE

        // I don't actually know how to do this if val2 is not an int...
        double accum = 1;

        if (val2 >= 0) {

            // this also handles the zero case... just don't enter loop, return 1
            for (int i = 0; i < val2; i++) {
                accum *= val1;
            }
        } else {

            // handle negative exponents
            for (int i = 0; i > val2; i--) {
                accum /= val1;
            }
        }

        return accum;

	}
	
	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swap(int pos1, int pos2, int[] array){
		//YOUR CODE HERE

        int elem1 = array[pos1];
        int elem2 = array[pos2];

        array[pos1] = elem2;
        array[pos2] = elem1;

	}
	
	/**
	 * Returns true if the two specified arrays of ints
	 * are equal to one another. Two arrays are considered equal 
	 * if both arrays contain the same number of elements, and all 
	 * corresponding pairs of elements in the two arrays are equal. 
	 * @param array1 -  one array to be tested for equality
	 * @param array2 - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equals(int[] array1, int[] array2){
		//YOUR CODE HERE

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
	}
	
	/**
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArray(int[] array, int val){
		//YOUR CODE HERE

        for (int i = 0; i < array.length; i++) {
            array[i] = val;
        }

	}
	
	/**
	 * Assigns the specified int value to each element of the specified 
	 * range of the specified array of ints. The range to be filled 
	 * extends from index startIndex, inclusive, to index endIndex, 
	 * exclusive.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 * @param startIndex - the index of the first element (inclusive) to be filled with the specified value
	 * @param endIndex - the index of the last element (exclusive) to be filled with the specified value
	 */
	
	public static void fillArrayPartially(int[] array, int val, int startIndex, int endIndex){
		//YOUR CODE HERE

        // avoid degenerate cases
        if (array.length < endIndex || endIndex < startIndex) {
            return;
        }

        for (int i = startIndex; i < endIndex; i++) {
            array[i] = val;
        }

	}
	
	/**
	 * Copies the specified array, truncating or padding with zeros (if necessary) 
	 * so the copy has the specified length. For all indices that are valid in both
	 * the original array and the copy, the two arrays will contain identical values.
	 * For any indices that are valid in the copy but not the original, the copy will 
	 * contain 0. Such indices will exist if and only if the specified length is greater 
	 * than that of the original array.
	 * @param array - the array to be copied
	 * @param newLength - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to obtain the specified length
	 */
	public static int[] returnCopy(int[] array, int newLength){
		//YOUR CODE HERE

        int newArray[] = new int[newLength];

        for (int i = 0; i < newLength; i++) {

            if (i < array.length) {
                newArray[i] = array[i];
            } else {
                newArray[i] = 0;
            }
        }

        return newArray;

	}
	
	
	/**
	 * Copies the specified range of the specified array into a new array and 
	 * returns the new array
	 * @param array - the array from which a range is to be copied
	 * @param startIndex - the initial index of the range to be copied, inclusive
	 * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array, truncated or padded with zeros to obtain the required length
	 */
	public static int[] returnCopyRange(int[] array, int startIndex, int endIndex){
		//YOUR CODE HERE

        int length = endIndex - startIndex;
        int newArray[] = new int[length];

        for (int i = 0; i < length; i++) {
            newArray[i] = array[startIndex + i];
        }

        return newArray;

	}
	
	/**
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToList(int[] array){
		//YOUR CODE HERE

        List<Integer> li = new ArrayList<Integer>();

        for (int i = 0; i < array.length; i++) {
            li.add(array[i]);
        }

        return li;

	}
	
	/**
	 * Returns a string representation of the contents of the specified 
	 * array. The string representation consists of a list of the array's 
	 * elements, enclosed in square brackets ("[]"). Adjacent elements are
	 * separated by the characters ", " (a comma followed by a space). 
	 * Elements are converted to strings as by String.valueOf(int). 
	 * Returns "null" if a is null.
	 * @param array - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToString(int[] array){
		//YOUR CODE HERE

        if (array == null) {
            return "null";
        }

        StringBuilder accum = new StringBuilder();
        accum.append("[");

        for (int i = 0; i < array.length; i++) {

            if (i != 0) {
                accum.append(", ").append(array[i]);
            } else {
                accum.append(array[i]);
            }

        }

        accum.append("]");
        return accum.toString();

	}
	
	
}
