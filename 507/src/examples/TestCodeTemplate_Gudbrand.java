package examples;

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
        return val > 0 ? val : -val;
	}
	
	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2±±
	 * @return the larger of val1 and val2
	 */
	public static int maximum(int val1, int val2){
        return val1 > val2 ? val1 : val2;
	}
	
	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimum(int val1, int val2){
		return val1 > val2 ? val2 : val1;
	}
	
	
	/**
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double power(double val1, double val2){
        return val2 == 0 ? 1 : val1*power(val1,val2-1);
	}
	
	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swap(int pos1, int pos2, int[] array){
        int tmp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = tmp;
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
        if (array1.length != array2.length) { return false; }
        int i=0;
        while ((array1[i] == array2[i]) && (i < array1.length)) {i++;}
        return i == array1.length;
	}
	
	/**
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArray(int[] array, int val){
        int i=0;
        while (i < array.length) {array[i] = val;}
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
        for (int i=startIndex; i<endIndex; i++) {array[i] = val;}
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
        int [] copy = new int[newLength]; int i=0;
        while ((i < array.length) && (i < newLength)) { copy[i] = array[i]; i++: }
        if (i == array.length) {
            while (i < newLength) { copy[i++] = 0; }
        }
        return copy;
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
        int newLength = endIndex - startIndex;
        int [] range = new int[newLength];
        int i=0;
        while (i < newLength) { range[i] = array[startIndex+i]; }
        return range;
	}
	
	/**
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToList(int[] array){
        List<Integer> list = new ArrayList<Integer>();
        int i=0;
        while (i < array.length) { list.add(array[i]); }
        return list;
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
        int newLength = 2*array.length + 1;
        String str = new String[newLength];
        str[0] = "[";
        for (int j=1; (j-1)/2<array.length; j+=2) {
            str[j] = String.valueOf(array[(j-1)/2]);
            if (j+2 <= newLength) {
             str[j+1] = ",";
            }
        }
        string[newLength-1] = "]";
        return array == null ? null : str;
	}
	
	
}
