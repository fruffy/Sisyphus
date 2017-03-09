package examples;

import java.util.ArrayList;
import java.util.List;

public class TestCodeV2 {
	
	/**
	 * @author Clement
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueCl(int val){
		//YOUR CODE HERE

        if (val < 0) {
            return (-1) * val;
        }

        return val;

	}

	
	/**
	 * @author Xing
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueX(int val){
		//YOUR CODE HERE
        int mul = 1;
        if (val < 0) {
            mul = -1;
        }
        return val * mul;
	}
	
	
	/**
	 * @author Alex
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueAl(int val){
		return val >= 0? val : -val;
	}
	
	
	/**
	 * @author Devon
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueD(int val){
        if (val < 0) {
            return -1 * val;
        }
        return val;
	}
	
	/**
	 * @author Jenny
     * Returns the absolute value an int value
     * @param val
     * @return the absolute value of val.
     */
    public int absoluteValueJ(int val){
        return (val < 0) ? -1 * val : val;
    }
	
	/**
	 * @author Candace
     * Returns the absolute value an int value
     * @param val
     * @return the absolute value of val.
     */
    public static int absoluteValueC(int val){
        //YOUR CODE HERE
        if (val > 0)
            return val;
        else
            return -val;
    }
	
	/**
	 * @author Arabelle
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueA(int val){
		return val > 0 ? val : -val;
	}
	
	
	/**
	 * @author Itrat
	 * Returns the absolute value of val
	 * @param val
	 * @return
	 */
	public static int absoluteValueI(int val){
		if(val < 0){
			return -val;
		}
		return val;
	}
	
	
/******************************************************************/
	
	/**
	 * @author Clement
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumCl(int val1, int val2){
		//YOUR CODE HERE

        // if there is a tie, either works, so return val1
        if (val1 >= val2) {
            return val1;
        }

        return val2;

	}

	
	/**
	 * @author Xing
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumX(int val1, int val2){
		//YOUR CODE HERE
        if (val1 > val2) {
            return val1;
        }
        return val2;
	}

	
	/**
	 * @author Alex
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumAl(int val1, int val2){
		return val1 > val2 ? val1 : val2;
	}

	
	/**
	 * @author Devon
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumD(int val1, int val2){
        if (val1 > val2) {
            return val1;
        }
        return val2;
	}

	
	/**
	 * @author Jenny
     * Returns the maximum of two int values
     * @param val1
     * @param val2
     * @return the larger of val1 and val2
     */
    public static int maximumJ(int val1, int val2){
        return (val1 >= val2) ? val1 : val2;
    }

	
	
	/**
     * @author Candace
     * Returns the maximum of two int values
     * @param val1
     * @param val2
     * @return the larger of val1 and val2
     */
    public static int maximumC(int val1, int val2){
        //YOUR CODE HERE
        if (val1 > val2)
            return val1;
        else
            return val2;
    }
	
	/**
	 * @author Arabelle
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumA(int val1, int val2){
		return val1 > val2 ? val1 : val2;
	}
	
	
	/**
	 * @author Itrat
	 * Returns the maximum of val1 and val2
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximumI(int val1, int val2){
		if(val1 < val2){
			return val2;
		}
		return val1;
	}
	
	
/******************************************************************/
	
	/**
	 * @author Clement
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumCl(int val1, int val2){
		//YOUR CODE HERE

        if (val1 <= val2) {
            return val1;
        }

        return val2;

	}

	/**
	 * @author Xing
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumX(int val1, int val2){
		//YOUR CODE HERE
        if (val1 < val2) {
            return val1;
        }
        return val2;
	}

	
	/**
	 * @author Alex
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumAl(int val1, int val2){
		return val1 < val2 ? val1 : val2;
	}
	
	/**
	 * @author Devon
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumD(int val1, int val2){
        if (val1 < val2) {
            return val1;
        }
        return val2;
	}
	
	
	/**
	 * @author Jenny
     * Returns the minimum of two int values
     * @param val1
     * @param val2
     * @return the smaller of val1 and val2
     */
    public static int minimumJ(int val1, int val2){
        return (val1 <= val2) ? val1 : val2;
    }

	
	/**
     * @author Candace
     * Returns the minimum of two int values
     * @param val1
     * @param val2
     * @return the smaller of val1 and val2
     */
    public static int minimumC(int val1, int val2){
        //YOUR CODE HERE
        if (val1 < val2)
            return val1;
        else
            return val2;
    }
	
	/**
	 * @author Arabelle
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumA(int val1, int val2){
		return val1 < val2 ? val1 : val2;
	}
	
	/**
	 * @author Itrat
	 * Returns the minimum of val1 and val2
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimumI(int val1, int val2){
		if(val1 < val2){
			return val1;
		}
		return val2;
	}
	
	
/*******************************************************************/
	
	/**
	 * @author Clement
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double powerCl(double val1, double val2){
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
	 * @author Xing
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double powerX(double val1, double val2){
		//YOUR CODE HERE
        if (val2 < 0) {
            return 1.0 / TestCodeTemplate.power(val1, -val2);
        }
        if (val2 == 0) {
            return 1;
        }
        if (val2 <= 1) {
            return val1;
        }
        double val2_rm = val2 % 2;
        double val2_m = (val2 - val2_rm) / 2;
        double rcpower = powerX(val1, val2_m);
        double rcremain = powerX(val1, val2_rm);
        return rcpower * rcpower * rcremain;
	}
	
	/**
	 * @author Alex
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double powerAl(double val1, double val2){
		if (val2 == 0)  
			return 1;
	    if (val2 == 1)      
	    	return val1;
	    if (val2%2 == 0)
	    	return powerAl(val1*val1, val2/2);
	    else
	    	return val1 * powerAl(val1, val2-1);
	}
	
	/**
	 * @author Devon
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double powerD(double val1, double val2){
        double temp = 1;
        for (int i = 0; i < val2; i++) {
            temp  = temp* val1;
        }
        return temp;
	}

	
	/**
     * @author Candace
     * Returns the value of the first argument raised to the power
     * of the second argument
     * @param val1 - the base
     * @param val2 - the exponent
     * @return val1^val2
     */
    public static double powerC(double val1, double val2){
        //YOUR CODE HERE
        return Math.pow(val1, val2);
    }
	
	/**
	 * @author Arabelle
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double powerA(double val1, double val2){
		return Math.pow(val1, val2);
	}
	
	
	/**************************************************************/
	
	/**
	 * @author Clement
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swapCl(int pos1, int pos2, int[] array){
		//YOUR CODE HERE

        int elem1 = array[pos1];
        int elem2 = array[pos2];

        array[pos1] = elem2;
        array[pos2] = elem1;

	}

	
	/**
	 * @author Xing
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swapX(int pos1, int pos2, int[] array){
		//YOUR CODE HERE
        int temp;
        temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
	}
	
	/**
	 * @author Alex
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swapAl(int pos1, int pos2, int[] array){
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	
	/**
	 * @author Devon
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swapD(int pos1, int pos2, int[] array){
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
	}

	
	/**
     * @author Candace
     * Swaps the element at pos1 with element at pos2 in array
     * @param pos1 - the position of one element to be swapped
     * @param pos2 - the position of another element to be swapped
     * @param array - the array where the elements are swapped
     */
    public static void swapC(int pos1, int pos2, int[] array){
        //YOUR CODE HERE
        int temp;
        temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
	
	/**
	 * @author Arabelle
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swapA(int pos1, int pos2, int[] array){
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}
	
	/*********************************************************/
	
	/**
	 * @author Clement
	 * Returns true if the two specified arrays of ints
	 * are equal to one another. Two arrays are considered equal 
	 * if both arrays contain the same number of elements, and all 
	 * corresponding pairs of elements in the two arrays are equal. 
	 * @param array1 -  one array to be tested for equality
	 * @param array2 - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsCl(int[] array1, int[] array2){
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
	 * @author Xing
	 * Returns true if the two specified arrays of ints
	 * are equal to one another. Two arrays are considered equal 
	 * if both arrays contain the same number of elements, and all 
	 * corresponding pairs of elements in the two arrays are equal. 
	 * @param array1 -  one array to be tested for equality
	 * @param array2 - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsX(int[] array1, int[] array2){
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
	 * @author Alex
	 * Returns true if the two specified arrays of ints
	 * are equal to one another. Two arrays are considered equal 
	 * if both arrays contain the same number of elements, and all 
	 * corresponding pairs of elements in the two arrays are equal. 
	 * @param array1 -  one array to be tested for equality
	 * @param array2 - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsAl(int[] array1, int[] array2){
		if (array1.length != array2.length)
			return false;
		for (int i = 0; i < array1.length; i++)
			if (array1[i] != array2[i])
				return false;
		return true;
	}
	
	
	/**
	 * @author Devon
	 * Returns true if the two specified arrays of ints
	 * are equal to one another. Two arrays are considered equal 
	 * if both arrays contain the same number of elements, and all 
	 * corresponding pairs of elements in the two arrays are equal. 
	 * @param array1 -  one array to be tested for equality
	 * @param array2 - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsD(int[] array1, int[] array2){
        int len = array1.length;
        if (len != array2.length) {
            return false;
        }
        for (int i = 1; i < len; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
	}

	
	/**
	 * @author Jenny
     * Returns true if the two specified arrays of ints
     * are equal to one another. Two arrays are considered equal
     * if both arrays contain the same number of elements, and all
     * corresponding pairs of elements in the two arrays are equal.
     * @param array1 -  one array to be tested for equality
     * @param array2 - the other array to be tested for equality
     * @return true if the two arrays are equal
     */
    public static boolean equalsJ(int[] array1, int[] array2){
        if (array1.length != array2.length)
            return false;
        
        for(int i = 0; i < array1.length; i++){
            if(array1[i] != array2[i])
                return false;
        }
        
        return true;
    }

	
	/**
     * @author Candace
     * Returns true if the two specified arrays of ints
     * are equal to one another. Two arrays are considered equal
     * if both arrays contain the same number of elements, and all
     * corresponding pairs of elements in the two arrays are equal.
     * @param array1 -  one array to be tested for equality
     * @param array2 - the other array to be tested for equality
     * @return true if the two arrays are equal
     */
    public static boolean equalsC(int[] array1, int[] array2){
        //YOUR CODE HERE
        int length = array2.length;

        if (array1.length != length)
            return false;

        for (int i = 0; i < length; i++){
            if (array1[i] != array2[i])
                return false;
        }

        return true;

    }
	
	/**
	 * @author Arabelle
	 * Returns true if the two specified arrays of ints
	 * are equal to one another. Two arrays are considered equal 
	 * if both arrays contain the same number of elements, and all 
	 * corresponding pairs of elements in the two arrays are equal. 
	 * @param array1 -  one array to be tested for equality
	 * @param array2 - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsA(int[] array1, int[] array2){
		if (array1.length != array2.length) {
		    return false;
		}
		
		for (int i = 0; i < array1.length; i++)
		{
			if (array1[i] != array2[i]) {
			    return false;
			}
			    
		}
		return true;
	}
	
	/**
	 * @author Itrat
	 * Check if array1 equals array2
	 * @param array1
	 * @param array2
	 * @return 
	 */
	public static boolean equalsI(int[] array1, int[] array2){
		if(array1.length != array2.length){
			return false;
		}
		for(int i = 0; i<array1.length; i++){
			if(array1[i]!=array2[i]){
				return false;
			}
		}
		return true;
	}
	
	
	/*************************************************************/
	
	/**
	 * @author Clement
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArrayCl(int[] array, int val){
		//YOUR CODE HERE

        for (int i = 0; i < array.length; i++) {
            array[i] = val;
        }

	}
	
	
	/**
	 * @author Xing
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArrayX(int[] array, int val){
		//YOUR CODE HERE
        for (int i = 0; i < array.length; i++) {
            array[i] = val;
        }
	}
	
	/**
	 * @author Alex
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArrayAl(int[] array, int val){
		for (int i = 0; i < array.length; i++)
			array[i] = val;
	}
	
	/**
	 * @author Devon
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArrayD(int[] array, int val){
        int len = array.length;
        for (int i = 0; i < len; i++) {
            array[i] = val;
        }
	}
	
	/**
	 * @author Jenny
     * Assigns the specified int value to each element of the specified
     * array of ints.
     * @param array - the array to be filled
     * @param val - the value to be stored in all elements of the array
     */
    public static void fillArrayJ(int[] array, int val){
        for(int i = 0; i < array.length; i++){
            array[i] = val;
        }
    }

	
	/**
	 * @author Candace
     * Assigns the specified int value to each element of the specified
     * array of ints.
     * @param array - the array to be filled
     * @param val - the value to be stored in all elements of the array
     */
    public static void fillArrayC(int[] array, int val){
        //YOUR CODE HERE
        for(int i = 0; i < array.length; i++){
            array[i] = val;
        }
    }
	
	/**
	 * @author Arabelle
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArrayA(int[] array, int val){
		for (int i = 0; i < array.length; i++)
		{
			array[i] = val;
		}
	}
	
	/**
	 * @author Itrat
	 * Fill array  with val
	 * @param array
	 * @param val
	 */
	public static void fillArrayI(int[] array, int val){
		for(int i = 0; i<array.length; i++){
			array[i] = val;
		}
	}
	
	
	/*************************************************************/
	
	/**
	 * @author Clement
	 * Assigns the specified int value to each element of the specified 
	 * range of the specified array of ints. The range to be filled 
	 * extends from index startIndex, inclusive, to index endIndex, 
	 * exclusive.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 * @param startIndex - the index of the first element (inclusive) to be filled with the specified value
	 * @param endIndex - the index of the last element (exclusive) to be filled with the specified value
	 */
	
	public static void fillArrayPartiallyCl(int[] array, int val, int startIndex, int endIndex){
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
	 * @author Xing
	 * Assigns the specified int value to each element of the specified 
	 * range of the specified array of ints. The range to be filled 
	 * extends from index startIndex, inclusive, to index endIndex, 
	 * exclusive.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 * @param startIndex - the index of the first element (inclusive) to be filled with the specified value
	 * @param endIndex - the index of the last element (exclusive) to be filled with the specified value
	 */
	
	public static void fillArrayPartiallyX(int[] array, int val, int startIndex, int endIndex){
		//YOUR CODE HERE
        for (int i = startIndex; i < endIndex + 1; i++) {
            array[i] = val;
        }
	}

	
	/**
	 * @author Alex
	 * Assigns the specified int value to each element of the specified 
	 * range of the specified array of ints. The range to be filled 
	 * extends from index startIndex, inclusive, to index endIndex, 
	 * exclusive.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 * @param startIndex - the index of the first element (inclusive) to be filled with the specified value
	 * @param endIndex - the index of the last element (exclusive) to be filled with the specified value
	 */
	
	public static void fillArrayPartiallyAl(int[] array, int val, int startIndex, int endIndex){
		if (startIndex < array.length && endIndex < array.length && startIndex <= endIndex)
			for (int i = startIndex; i < endIndex; i++)
				array[i] = val;
	}
	
	
	/**
	 * @author Devon
	 * Assigns the specified int value to each element of the specified 
	 * range of the specified array of ints. The range to be filled 
	 * extends from index startIndex, inclusive, to index endIndex, 
	 * exclusive.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 * @param startIndex - the index of the first element (inclusive) to be filled with the specified value
	 * @param endIndex - the index of the last element (exclusive) to be filled with the specified value
	 */
	
	public static void fillArrayPartiallyD(int[] array, int val, int startIndex, int endIndex){
        for (int i = startIndex; i < endIndex; i++) {
            array[i] = val;
        }
	}

	
	/**
	 * @author Jenny
     * Assigns the specified int value to each element of the specified
     * range of the specified array of ints. The range to be filled
     * extends from index startIndex, inclusive, to index endIndex,
     * exclusive.
     * @param array - the array to be filled
     * @param val - the value to be stored in all elements of the array
     * @param startIndex - the index of the first element (inclusive) to be filled with the specified value
     * @param endIndex - the index of the last element (exclusive) to be filled with the specified value
     */
    
    public static void fillArrayPartiallyJ(int[] array, int val, int startIndex, int endIndex){
        for(int i = startIndex; i < minimumJ(array.length, endIndex); i++){
            array[i] = val;
        }
    }

	
	/**
	 * @author Candace
     * Assigns the specified int value to each element of the specified
     * range of the specified array of ints. The range to be filled
     * extends from index startIndex, inclusive, to index endIndex,
     * exclusive.
     * @param array - the array to be filled
     * @param val - the value to be stored in all elements of the array
     * @param startIndex - the index of the first element (inclusive) to be filled with the specified value
     * @param endIndex - the index of the last element (exclusive) to be filled with the specified value
     */

    public static void fillArrayPartiallyC(int[] array, int val, int startIndex, int endIndex){
        //YOUR CODE HERE
        for (int i = startIndex; i < endIndex; i++){
            array[i] = val;
        }
    }

	
	/**
	 * @author Arabelle
	 * Assigns the specified int value to each element of the specified 
	 * range of the specified array of ints. The range to be filled 
	 * extends from index startIndex, inclusive, to index endIndex, 
	 * exclusive.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 * @param startIndex - the index of the first element (inclusive) to be filled with the specified value
	 * @param endIndex - the index of the last element (exclusive) to be filled with the specified value
	 */
	
	public static void fillArrayPartiallyA(int[] array, int val, int startIndex, int endIndex){
		for (int i = startIndex; i < endIndex; i++)
		{
			array[i] = val;
		}
	}
	
	/**
	 * @author Itrat
	 * Fill array with val from startIndex to endIndex(not inclusive)
	 * @param array
	 * @param val
	 * @param startIndex
	 * @param endIndex
	 */
	
	public static void fillArrayPartiallyI(int[] array, int val, int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= array.length || endIndex <0 || endIndex >= array.length){
			return;
		}
		for(int i = startIndex; i<endIndex; i++){
			array[i] = val;
		}
	}
	
	
	/*************************************************************/
	 
	/**
	 * @author Clement
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
	public static int[] returnCopyCl(int[] array, int newLength){
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
	 * @author Xing
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
	public static int[] returnCopyX(int[] array, int newLength){
		//YOUR CODE HERE
        int fillLength = TestCodeTemplate.minimum(newLength, array.length);
        int [] newArray = new int[newLength];
        for (int i = 0; i < fillLength; i++) {
            newArray[i] = array[i];
        }
        return newArray;
	}

	
	/**
	 * @author Alex
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
	public static int[] returnCopyAL(int[] array, int newLength){
		int[] copy = new int[newLength];
		for (int i = 0; i < newLength; i++){
			if (i < array.length)
				copy[i] = array[i];
			else
				copy[i] = 0;
		}
		return copy;
	}
	
	
	/**
	 * @author Devon
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
	public static int[] returnCopyD(int[] array, int newLength){
		int[] newArray = new int[newLength];
        int len = array.length;
        if (len < newLength) {
            for (int i = 0; i < len; i++) {
                newArray[i] = array[i];
            }
        } else {
            for (int i = 0; i < newLength; i++) {
                newArray[i] = array[i];
            }
        }
        return newArray;
	}

	
	/**
     * @author Jenny
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
    public static int[] returnCopyJ(int[] array, int newLength){
        int[] newArr = new int[newLength];
        for(int i = 0; i < newArr.length; i++){
            newArr[i] = (i < array.length) ? array[i] : 0;
        }
        return newArr;
    }	
	
	/**
	 * @author Candace
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
    public static int[] returnCopyC(int[] array, int newLength){
        //YOUR CODE HERE
        int[] newArray = new int[newLength];
        int length = array.length;
        if (newLength < length) {
            for (int i = 0; i < newLength; i++)
                newArray[i] = array[i];
        }
        else{
            for (int i = 0; i < length; i++)
                newArray[i] = array[i];
            for (int i = length; i < newLength; i++)
                newArray[i] = 0;
        }
        return newArray;
    }

	
	/**
	 * @author Arabelle
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
	public static int[] returnCopyA(int[] array, int newLength){
		int[] arr = new int[newLength];
		for (int i = 0; i < newLength; i++)
		{
			arr[i] = i < array.length ? array[i] : 0;
		}
		return arr;
	}
	
	/**
	 * @author Itrat
	 * Returns a copy of array
	 * @param array
	 * @return
	 */
	public static int[] returnCopyI(int[] array){
		int[] newArray = new int[array.length];
		for(int i = 0; i<newArray.length; i++){
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	/*************************************************************/
	
	/**
	 * @author Clement
	 * Copies the specified range of the specified array into a new array and 
	 * returns the new array
	 * @param array - the array from which a range is to be copied
	 * @param startIndex - the initial index of the range to be copied, inclusive
	 * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array, 
	 */
	public static int[] returnCopyRangeCl(int[] array, int startIndex, int endIndex){
		//YOUR CODE HERE

        // I'm not sure I finally understand... is the newLength just the range? or the length of original array?

        int newArray[] = new int[array.length];

        for (int i = 0; i < array.length; i++) {

            if (i >= startIndex && i < endIndex) {
                newArray[i] = array[i];
            } else {
                newArray[i] = 0;
            }
        }

        return newArray;

	}
	
	/**
	 * @author Xing
	 * Copies the specified range of the specified array into a new array and 
	 * returns the new array
	 * @param array - the array from which a range is to be copied
	 * @param startIndex - the initial index of the range to be copied, inclusive
	 * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array, truncated or padded with zeros to obtain the required length
	 */
	public static int[] returnCopyRangeX(int[] array, int startIndex, int endIndex){
		//YOUR CODE HERE
        int arrayLength = endIndex - startIndex + 1;
        int [] newArray = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            newArray[i] = array[i + startIndex];
        }
        return newArray;
	}

	
	/**
	 * @author Alex
	 * Copies the specified range of the specified array into a new array and 
	 * returns the new array
	 * @param array - the array from which a range is to be copied
	 * @param startIndex - the initial index of the range to be copied, inclusive
	 * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array, truncated or padded with zeros to obtain the required length
	 */
	public static int[] returnCopyRangeAl(int[] array, int startIndex, int endIndex){
		if (endIndex >= startIndex){
			int len = endIndex - startIndex;
			int[] copy = new int[len];
			if (startIndex < array.length){
				for (int i = 0; i < len; i++){
					if (i + startIndex < array.length)
						copy[i] = array[i + startIndex];
					else
						copy[i] = 0;
				}
			} else {
				for (int i = 0; i < len; i++)
					copy[i] = 0;
			}	
			return copy;
		}
		return null;
	}

	
	/**
	 * @author Devon
	 * Copies the specified range of the specified array into a new array and 
	 * returns the new array
	 * @param array - the array from which a range is to be copied
	 * @param startIndex - the initial index of the range to be copied, inclusive
	 * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array, truncated or padded with zeros to obtain the required length
	 */
	public static int[] returnCopyRangeD(int[] array, int startIndex, int endIndex){
        int len = array.length;
        int newLen = endIndex - startIndex + 1;
        int[] newArray = new int[newLen];
        if (len < newLen) {
            for (int i = 0; i < len; i++) {
                newArray[i] = array[i];
            }
        } else {
            for (int i = 0; i < newLen; i++) {
                newArray[i] = array[i];
            }
        }
        return newArray;
	}

	
	/**
	 * @author Jenny
     * Copies the specified range of the specified array into a new array and
     * returns the new array
     * @param array - the array from which a range is to be copied
     * @param startIndex - the initial index of the range to be copied, inclusive
     * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
     * @return a new array containing the specified range from the original array, truncated or padded with zeros to obtain the required length
     */
    public static int[] returnCopyRangeJ(int[] array, int startIndex, int endIndex){
        int[] newArr = new int[endIndex-startIndex];
        for(int i = 0; i < newArr.length; i++){
            newArr[i] = (startIndex+i < array.length) ? array[startIndex+i] : 0;
        }
        return newArr;
    }

	
	/**
	 * @author Candace
     * Copies the specified range of the specified array into a new array and
     * returns the new array
     * @param array - the array from which a range is to be copied
     * @param startIndex - the initial index of the range to be copied, inclusive
     * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
     * @return a new array containing the specified range from the original array, truncated or padded with zeros to obtain the required length
     */
    public static int[] returnCopyRangeC(int[] array, int startIndex, int endIndex){
        //YOUR CODE HERE
        int[] newArray = new int[endIndex - startIndex];
        for (int i = startIndex; i < endIndex; i++)
            newArray[i-startIndex] = array[i];
        return newArray;
    }

	
	/**
	 * @author Arabelle
	 * Copies the specified range of the specified array into a new array and 
	 * returns the new array
	 * @param array - the array from which a range is to be copied
	 * @param startIndex - the initial index of the range to be copied, inclusive
	 * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array, truncated or padded with zeros to obtain the required length
	 */
	public static int[] returnCopyRangeA(int[] array, int startIndex, int endIndex){
		int[] arr = new int[endIndex - startIndex];
		for (int i = startIndex; i < endIndex; i++)
		{
			arr[i - startIndex] = i < array.length ? array[i] : 0;
		}
		return arr;
	}
	
	/**
	 * @author Itrat
	 * Returns a partial copy of the array (specified by startIndex and endIndex(not inclusive))
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static int[] returnCopyRangeI(int[] array, int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= array.length || endIndex <0 || endIndex >= array.length){
			System.out.println("startIndex and endIndex do not make sense");
			return new int[0];
		}
		int newLength = endIndex - startIndex;
		int[] newArray = new int[newLength];
		int j = 0;
		for(int i = startIndex; i<endIndex; i++){
			newArray[j] = array[i];
			j++;
		}
		return newArray;
	}
	
	
	
	/************************************************************/
	
	/**
	 * @author Clement
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListCl(int[] array){
		//YOUR CODE HERE

        List<Integer> li = new ArrayList<Integer>();

        for (int i = 0; i < array.length; i++) {
            li.add(array[i]);
        }

        return li;

	}
	
	/**
	 * @author Xing
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListX(int[] array){
		//YOUR CODE HERE
        List<Integer> newArray = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++) {
            newArray.add(array[i]);
        }
        return newArray;
	}
	
	/**
	 * @author Alex
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListAl(int[] array){
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++)
			list.add(array[i]);
		return list;
	}
	
	/**
	 * @author Devon
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListD(int[] array){
		List<Integer> myList = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++) {
            myList.add(array[i]);
        }
        return myList;
	}

	
	/**
	 * @author Jenny
     * Returns a list version of the array
     * @param array the array to be converted
     * @return the list version of array
     */
    public static List<Integer> convertToListJ(int[] array){
        ArrayList<Integer> newList= new ArrayList<>(array.length);
        for(int i = 0; i < array.length; i++){
            newList.add(array[i]);
        }
        return newList;
    }

	
	/**
	 * @author Candace
     * Returns a list version of the array
     * @param array the array to be converted
     * @return the list version of array
     */
    public static List<Integer> convertToListC(int[] array){
        //YOUR CODE HERE
        List<Integer> intList = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++)
            intList.add(array[i]);
        return intList;
    }

	
	
	/**
	 * @author Arabelle
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListA(int[] array){
		List<Integer> l = new ArrayList<Integer>(array.length);
		for (int i = 0; i < array.length; i++)
		{
			l.add(array[i]);
		}
		return l;
	}
	
	/**
	 * @author Itrat
	 * Returns a list version of the array
	 * @param array
	 * @return
	 */
	public static List<Integer> convertToListI(int[] array){
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		for(int a: array){
			arrList.add(a);
		}
		return arrList;
	}
	
	
	/*************************************************************/
	
	/**
	 * @author Clement
	 * Returns a string representation of the contents of the specified 
	 * array. The string representation consists of a list of the array's 
	 * elements, enclosed in square brackets ("[]"). Adjacent elements are
	 * separated by the characters ", " (a comma followed by a space). 
	 * Elements are converted to strings as by String.valueOf(int). 
	 * Returns "null" if a is null.
	 * @param array - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringCl(int[] array){
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

	
	/**
	 * @author Xing
	 * Returns a string representation of the contents of the specified 
	 * array. The string representation consists of a list of the array's 
	 * elements, enclosed in square brackets ("[]"). Adjacent elements are
	 * separated by the characters ", " (a comma followed by a space). 
	 * Elements are converted to strings as by String.valueOf(int). 
	 * Returns "null" if a is null.
	 * @param array - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringX(int[] array){
		//YOUR CODE HERE
        String newString = "[";
        for (int i = 0; i < array.length; i++) {
            newString += array[i];
            if (i != array.length - 1) {
                newString += ",";
            }
        }
        newString += "]";
        return newString;
	}
	

	
	/**
	 * @author Alex
	 * Returns a string representation of the contents of the specified 
	 * array. The string representation consists of a list of the array's 
	 * elements, enclosed in square brackets ("[]"). Adjacent elements are
	 * separated by the characters ", " (a comma followed by a space). 
	 * Elements are converted to strings as by String.valueOf(int). 
	 * Returns "null" if a is null.
	 * @param array - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringAl(int[] array){
		if (array == null)
			return null;
		String s = "[";
		for (int i = 0; i < array.length; i++){
			s += String.valueOf(array[i]);
			if (i + 1 < array.length)
				s += ", ";
		}
		s += "]";
		return s;
	}	

	
	/**
	 * @author Devon
	 * Returns a string representation of the contents of the specified 
	 * array. The string representation consists of a list of the array's 
	 * elements, enclosed in square brackets ("[]"). Adjacent elements are
	 * separated by the characters ", " (a comma followed by a space). 
	 * Elements are converted to strings as by String.valueOf(int). 
	 * Returns "null" if a is null.
	 * @param array - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringD(int[] array){
        if (array == null) {
            return "null";
        }
        int len = array.length;
        String str = "[";
        for (int i = 0; i < len - 1; i++) {
            str += String.valueOf(array[i]);
            str += ", ";
        }
        str += String.valueOf(array[len]) + "]";
        return str;
	}

	
	/**
	 * @author Jenny
     * Returns a string representation of the contents of the specified
     * array. The string representation consists of a list of the array's
     * elements, enclosed in square brackets ("[]"). Adjacent elements are
     * separated by the characters ", " (a comma followed by a space).
     * Elements are converted to strings as by String.valueOf(int).
     * Returns "null" if a is null.
     * @param array - the array whose string representation to return
     * @return a string representation of array
     */
    public static String arrToStringJ(int[] array){
        if(array == null)
            return null;
        
        String arr = "[";
        for(int i = 0; i < array.length - 1; i++){
            arr += array[i] + ", ";
        }
        
        if(array.length != 0)
            arr += array[array.length-1];
        
        arr += "]";
        return arr;
    }
	

	
	/**
	 * @author Candace
     * Returns a string representation of the contents of the specified
     * array. The string representation consists of a list of the array's
     * elements, enclosed in square brackets ("[]"). Adjacent elements are
     * separated by the characters ", " (a comma followed by a space).
     * Elements are converted to strings as by String.valueOf(int).
     * Returns "null" if a is null.
     * @param array - the array whose string representation to return
     * @return a string representation of array
     */
    public static String arrToStringC(int[] array){
        //YOUR CODE HERE
        if (array == null)
            return null;

        String newString = "[";
        for (int i = 0; i < array.length; i++){
            if (i > 0)
                newString = newString + ",";
            String newElement = String.valueOf(array[i]);
            newString = newString + newElement;
        }
        newString = newString + "]";

        return newString;
    }

	
	
	/**
	 * @author Arabelle
	 * Returns a string representation of the contents of the specified 
	 * array. The string representation consists of a list of the array's 
	 * elements, enclosed in square brackets ("[]"). Adjacent elements are
	 * separated by the characters ", " (a comma followed by a space). 
	 * Elements are converted to strings as by String.valueOf(int). 
	 * Returns "null" if a is null.
	 * @param array - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringA(int[] array){
		if (array == null)
		{
			return "null";
		}

		StringBuilder s = new StringBuilder();
		s.append("[");

		for (int i = 0; i < array.length; i++)
		{
			s.append(i);
			if (i != array.length - 1)
			{
				s.append(", ");
			}
		}
		s.append("]");

		return s.toString();
	}
	
	
	/**
	 * @author Itrat
	 * Return a string version of the array
	 * @param array
	 * @return
	 */
	public static String arrToStringI(int[] array){
		String arrString = "[";
		for(int i = 0; i<array.length; i++){
			int a = array[i];
			arrString += a;
			if(i!=array.length-1){
				arrString += ", ";
			}
		}
		arrString += "]";
		return arrString;
	}
	

	public static void main(String[] args){
		/*int val = -1;
		System.out.println("Absolute value of "+ val +" is "+absoluteValue(val));
		System.out.println("Minimum of 2 and 3 is "+minimum(2,3));
		System.out.println("Maximum of 2 and 3 is "+maximum(2,3));
		
		System.out.println("Testing the array methods");
		int[] array1 = {1,2,3,4,5,6};
		int[] array2 = {1,2,3,4,5,6};
		System.out.println("array1: " + arrToString(array1));
		System.out.println("array2: " + arrToString(array2));
		System.out.println("array1 equal to array2? "+ equals(array1,array2));
		array2[3] = 0;
		System.out.println("array1: " + arrToString(array1));
		System.out.println("array2: " + arrToString(array2));
		System.out.println("array1 equal to array2? "+ equals(array1,array2));
		fillArray(array1,-1);
		System.out.println("After filling array1 with -1: "+arrToString(array1));
		fillArrayPartially(array1,0,0,3);
		System.out.println("After filling array1 with 0 from index 0 to 3: "+arrToString(array1));
		int[] arrayCopy = returnCopy(array1);
		System.out.println("After copying array1 to arrayCopy: "+arrToString(arrayCopy));
		arrayCopy = returnPartialCopy(array1,0,3);
		System.out.println("After copying array1 to arrayCopy paritally from index 0 to 3: "+arrToString(arrayCopy));
		List<Integer> arrList = convertToList(array1);
		System.out.println("ArrayList version of array1: "+ arrList);*/
	
	}
}
