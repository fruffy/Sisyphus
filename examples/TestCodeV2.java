
import java.util.ArrayList;
import java.util.List;

public class TestCodeV2 {

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueM(int val) {
		if (val < 0) {
			val = -val;
		}
		return val;
	}

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueG(int val) {
		return val > 0 ? val : -val;
	}

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueCl(int val) {
		// YOUR CODE HERE

		if (val < 0) {
			return (-1) * val;
		}

		return val;

	}

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueX(int val) {
		// YOUR CODE HERE
		int mul = 1;
		if (val < 0) {
			mul = -1;
		}
		return val * mul;
	}

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueAl(int val) {
		return val >= 0 ? val : -val;
	}

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueD(int val) {
		if (val < 0) {
			return -1 * val;
		}
		return val;
	}

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueJ(int val) {
		return (val < 0) ? -1 * val : val;
	}

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public static int absoluteValueC(int val) {
		// YOUR CODE HERE
		if (val > 0)
			return val;
		else
			return -val;
	}

	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueA(int val) {
		return val > 0 ? val : -val;
	}

	/** 
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueAt(int val) {
		return (val < 0) ? -val : val;
	}
	
	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueFP(int val){
		//YOUR CODE HERE
		if( val < 0) val = -1 * val;
		return val;
	}
	
	/**
	 * Returns the absolute value an int value
	 * @param val
	 * @return the absolute value of val.
	 */
	public int absoluteValueAJ(int val){
		//YOUR CODE HERE
		if (val < 0) return val * -1;
		else return val;
	}


	/******************************************************************/

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumM(int val1, int val2) {
		if (val1 > val2) {
			return val1;
		} else {
			return val2;
		}
	}

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2±±
	 * @return the larger of val1 and val2
	 */
	public static int maximumG(int val1, int val2) {
		return val1 > val2 ? val1 : val2;
	}

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumCl(int val1, int val2) {
		// YOUR CODE HERE

		// if there is a tie, either works, so return val1
		if (val1 >= val2) {
			return val1;
		}

		return val2;

	}

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumX(int val1, int val2) {
		// YOUR CODE HERE
		if (val1 > val2) {
			return val1;
		}
		return val2;
	}

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumAl(int val1, int val2) {
		return val1 > val2 ? val1 : val2;
	}

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumD(int val1, int val2) {
		if (val1 > val2) {
			return val1;
		}
		return val2;
	}

	/**
	 * Jenny Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumJ(int val1, int val2) {
		return (val1 >= val2) ? val1 : val2;
	}

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumC(int val1, int val2) {
		// YOUR CODE HERE
		if (val1 > val2)
			return val1;
		else
			return val2;
	}

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumA(int val1, int val2) {
		return val1 > val2 ? val1 : val2;
	}

	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumAt(int val1, int val2) {
		return (val1 > val2) ? val1 : val2;
	}
	
	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumFP(int val1, int val2){
		//YOUR CODE HERE
		int s1 = val1 - val2;
		int s2 = val2- val1;
		return (s2>>31)*val1 + (s1 >>31)*val2;
	}
	
	/**
	 * Returns the maximum of two int values
	 * @param val1
	 * @param val2
	 * @return the larger of val1 and val2
	 */
	public static int maximumAJ(int val1, int val2){
		//YOUR CODE HERE
		if (val1 > val2) return val1;
		else if (val2 > val1) return val2;
		else return val1; // if they are equal just return the first number
	}

	/******************************************************************/

	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumM(int val1, int val2) {
		if (val1 < val2) {
			return val1;
		} else {
			return val2;
		}
	}

	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumG(int val1, int val2) {
		return val1 > val2 ? val2 : val1;
	}

	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumCl(int val1, int val2) {
		// YOUR CODE HERE

		if (val1 <= val2) {
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
	public static int minimumX(int val1, int val2) {
		// YOUR CODE HERE
		if (val1 < val2) {
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
	public static int minimumAl(int val1, int val2) {
		return val1 < val2 ? val1 : val2;
	}

	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumD(int val1, int val2) {
		if (val1 < val2) {
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
	public static int minimumJ(int val1, int val2) {
		return (val1 <= val2) ? val1 : val2;
	}

	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumC(int val1, int val2) {
		// YOUR CODE HERE
		if (val1 < val2)
			return val1;
		else
			return val2;
	}

	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumA(int val1, int val2) {
		return val1 < val2 ? val1 : val2;
	}

	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumAt(int val1, int val2) {
		return (val1 < val2) ? val1 : val2;
	}
	
	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumFP(int val1, int val2){
		//YOUR CODE HERE
		if(val2 <val1) return val2;
		return val1;
 	}
	
	/**
	 * Returns the minimum of two int values
	 * @param val1
	 * @param val2
	 * @return the smaller of val1 and val2
	 */
	public static int minimumAJ(int val1, int val2){
		//YOUR CODE HERE
		if (val1 < val2) return val1;
		else if (val2 < val1) return val2;
		else return val1; // if they are equal just return the first number
	}

	/*******************************************************************/

	/**
	 * Returns the value of the first argument raised to the
	 *         power of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerM(double val1, double val2) {
		return Math.pow(val1, val2);
	}

	/**
	 * Returns the value of the first argument raised to the
	 *         power of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerG(double val1, double val2) {
		return val2 == 0 ? 1 : val1 * powerG(val1, val2 - 1);
	}

	/**
	 * Returns the value of the first argument raised to the
	 *         power of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerCl(double val1, double val2) {
		// YOUR CODE HERE

		// I don't actually know how to do this if val2 is not an int...
		double accum = 1;

		if (val2 >= 0) {

			// this also handles the zero case... just don't enter loop, return
			// 1
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
	 * Returns the value of the first argument raised to the power
	 *         of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerX(double val1, double val2) {
		// YOUR CODE HERE
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
	 * Returns the value of the first argument raised to the power
	 *         of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerAl(double val1, double val2) {
		if (val2 == 0)
			return 1;
		if (val2 == 1)
			return val1;
		if (val2 % 2 == 0)
			return powerAl(val1 * val1, val2 / 2);
		else
			return val1 * powerAl(val1, val2 - 1);
	}

	/**
	 * Returns the value of the first argument raised to the power
	 *         of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerD(double val1, double val2) {
		double temp = 1;
		for (int i = 0; i < val2; i++) {
			temp = temp * val1;
		}
		return temp;
	}

	/**
	 * Returns the value of the first argument raised to the power
	 *         of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerJ(double val1, double val2) {
		// YOUR CODE HERE
		double result = 1;
		for (int i = 1; i < val2; i++) {
			result = result * result;
		}
		return result;
	}

	/**
	 * Returns the value of the first argument raised to the
	 *         power of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerC(double val1, double val2) {
		// YOUR CODE HERE
		return Math.pow(val1, val2);
	}

	/**
	 * Returns the value of the first argument raised to the
	 *         power of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerA(double val1, double val2) {
		return Math.pow(val1, val2);
	}

	/**
	 * Returns the value of the first argument raised to the
	 *         power of the second argument
	 * @param val1
	 *            - the base
	 * @param val2
	 *            - the exponent
	 * @return val1^val2
	 */
	public static double powerAt(double val1, double val2) {
		// Rounds to integer powers
		double working = val1;
		int exp = (int) val2;
		if (exp == 0) {
			return 1;
		}
		for (int i = 1; i < exp; i++) {
			working *= val1;
		}
		return working;
	}
	
	/**
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double powerFP(double val1, double val2){
		//YOUR CODE HERE
		return 1;

	}
	
	/**
	 * Returns the value of the first argument raised to the power
	 * of the second argument
	 * @param val1 - the base
	 * @param val2 - the exponent
	 * @return val1^val2
	 */
	public static double powerAJ(double val1, double val2){
		//YOUR CODE HERE
		double result = 1;
		// check if exponent is 0
		if (val2 == 0) return result;
		
		result = val1;
		
		if (val2 > 0) {
			// if val2 is positive
			for (int i = 0; i < val2; i++) {
				result = result*val1;
			}
		} else {
			// if val2 is negative then return 1/result
			val2 = val2*-1.0;
			for (int i = 0; i < val2; i++) {
				result = result*val1;
			}
			result = 1.0/result;
		}
		return result;
	}


	/**************************************************************/

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapM(int pos1, int pos2, int[] array) {
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapG(int pos1, int pos2, int[] array) {
		int tmp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = tmp;
	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapCl(int pos1, int pos2, int[] array) {
		// YOUR CODE HERE

		int elem1 = array[pos1];
		int elem2 = array[pos2];

		array[pos1] = elem2;
		array[pos2] = elem1;

	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapX(int pos1, int pos2, int[] array) {
		// YOUR CODE HERE
		int temp;
		temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapAl(int pos1, int pos2, int[] array) {
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapD(int pos1, int pos2, int[] array) {
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapJ(int pos1, int pos2, int[] array) {
		if (pos1 < array.length && pos2 < array.length && pos1 >= 0 && pos2 >= 0) {
			int temp = array[pos1];
			array[pos1] = array[pos2];
			array[pos2] = temp;
		}
	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapC(int pos1, int pos2, int[] array) {
		// YOUR CODE HERE
		int temp;
		temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapA(int pos1, int pos2, int[] array) {
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1
	 *            - the position of one element to be swapped
	 * @param pos2
	 *            - the position of another element to be swapped
	 * @param array
	 *            - the array where the elements are swapped
	 */
	public static void swapAt(int pos1, int pos2, int[] array) {
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}
	
	
	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swapFP(int pos1, int pos2, int[] array){
		//YOUR CODE HERE
		int tmp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = tmp;
	}
	
	/**
	 * Swaps the element at pos1 with element at pos2 in array
	 * @param pos1 - the position of one element to be swapped
	 * @param pos2 - the position of another element to be swapped
	 * @param array - the array where the elements are swapped
	 */
	public static void swapAJ(int pos1, int pos2, int[] array){
		//YOUR CODE HERE
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	/*********************************************************/

	/**
	 * Returns true if the two specified arrays of ints are
	 *         equal to one another. Two arrays are considered equal if both
	 *         arrays contain the same number of elements, and all corresponding
	 *         pairs of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsM(int[] array1, int[] array2) {
		if (array1.length != array2.length) {
			return false;
		} else {
			for (int i = 0; i < array1.length; i++) {
				if (array1[i] != array2[i]) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Returns true if the two specified arrays of ints are
	 *         equal to one another. Two arrays are considered equal if both
	 *         arrays contain the same number of elements, and all corresponding
	 *         pairs of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsG(int[] array1, int[] array2) {
		if (array1.length != array2.length) {
			return false;
		}
		int i = 0;
		while ((array1[i] == array2[i]) && (i < array1.length)) {
			i++;
		}
		return i == array1.length;
	}

	/**
	 * Returns true if the two specified arrays of ints are
	 *         equal to one another. Two arrays are considered equal if both
	 *         arrays contain the same number of elements, and all corresponding
	 *         pairs of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsCl(int[] array1, int[] array2) {
		// YOUR CODE HERE

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
	 * Returns true if the two specified arrays of ints are equal
	 *         to one another. Two arrays are considered equal if both arrays
	 *         contain the same number of elements, and all corresponding pairs
	 *         of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsX(int[] array1, int[] array2) {
		// YOUR CODE HERE
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
	 * Returns true if the two specified arrays of ints are equal
	 *         to one another. Two arrays are considered equal if both arrays
	 *         contain the same number of elements, and all corresponding pairs
	 *         of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsAl(int[] array1, int[] array2) {
		if (array1.length != array2.length)
			return false;
		for (int i = 0; i < array1.length; i++)
			if (array1[i] != array2[i])
				return false;
		return true;
	}

	/**
	 * Returns true if the two specified arrays of ints are equal
	 *         to one another. Two arrays are considered equal if both arrays
	 *         contain the same number of elements, and all corresponding pairs
	 *         of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsD(int[] array1, int[] array2) {
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
	 * Returns true if the two specified arrays of ints are equal
	 *         to one another. Two arrays are considered equal if both arrays
	 *         contain the same number of elements, and all corresponding pairs
	 *         of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsJ(int[] array1, int[] array2) {
		if (array1.length != array2.length)
			return false;

		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i])
				return false;
		}

		return true;
	}

	/**
	 * Returns true if the two specified arrays of ints are
	 *         equal to one another. Two arrays are considered equal if both
	 *         arrays contain the same number of elements, and all corresponding
	 *         pairs of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsC(int[] array1, int[] array2) {
		// YOUR CODE HERE
		int length = array2.length;

		if (array1.length != length)
			return false;

		for (int i = 0; i < length; i++) {
			if (array1[i] != array2[i])
				return false;
		}

		return true;

	}

	/**
	 * Returns true if the two specified arrays of ints are
	 *         equal to one another. Two arrays are considered equal if both
	 *         arrays contain the same number of elements, and all corresponding
	 *         pairs of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsA(int[] array1, int[] array2) {
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
	 * Returns true if the two specified arrays of ints are
	 *         equal to one another. Two arrays are considered equal if both
	 *         arrays contain the same number of elements, and all corresponding
	 *         pairs of elements in the two arrays are equal.
	 * @param array1
	 *            - one array to be tested for equality
	 * @param array2
	 *            - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsAt(int[] array1, int[] array2) {
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
	 * Returns true if the two specified arrays of ints
	 * are equal to one another. Two arrays are considered equal 
	 * if both arrays contain the same number of elements, and all 
	 * corresponding pairs of elements in the two arrays are equal. 
	 * @param array1 -  one array to be tested for equality
	 * @param array2 - the other array to be tested for equality
	 * @return true if the two arrays are equal
	 */
	public static boolean equalsFP(int[] array1, int[] array2){
		//YOUR CODE HERE
		if(array1.length != array2.length) return false;
		for(int i = 0; i < array1.length; i++) {
			if(array1[i] != array2[i]) return false;
		}
		return true;
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
	public static boolean equalsAJ(int[] array1, int[] array2){
		//YOUR CODE HERE
		boolean result = false;
		
		// first check the arrays are of the same size
		if(array1.length != array2.length) return result;
		
		result = true;
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				result = false;
				break;
			}
		}
		
		return result;		
	}

	/*************************************************************/

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayM(int[] array, int val) {
		for (int i = 0; i < array.length; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayG(int[] array, int val) {
		int i = 0;
		while (i < array.length) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayCl(int[] array, int val) {
		// YOUR CODE HERE

		for (int i = 0; i < array.length; i++) {
			array[i] = val;
		}

	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayX(int[] array, int val) {
		// YOUR CODE HERE
		for (int i = 0; i < array.length; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayAl(int[] array, int val) {
		for (int i = 0; i < array.length; i++)
			array[i] = val;
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayD(int[] array, int val) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayJ(int[] array, int val) {
		for (int i = 0; i < array.length; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayC(int[] array, int val) {
		// YOUR CODE HERE
		for (int i = 0; i < array.length; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayA(int[] array, int val) {
		for (int i = 0; i < array.length; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified array of ints.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 */
	public static void fillArrayAt(int[] array, int val) {
		for (int i = 0; i < array.length; i++) {
			array[i] = val;
		}
	}
	
	/**
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArrayFP(int[] array, int val){
		//YOUR CODE HERE
		for(int i = 0; i < array.length; i++) array[i] = val;

	}
	
	/**
	 * Assigns the specified int value to each element of the specified 
	 * array of ints.
	 * @param array - the array to be filled
	 * @param val - the value to be stored in all elements of the array
	 */
	public static void fillArrayAJ(int[] array, int val){
		//YOUR CODE HERE
		assert(array.length != 0);
		
		for (int i = 0; i<array.length; i++) {
			array[i] = val;
		}
	}

	/*************************************************************/

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyM(int[] array, int val, int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyG(int[] array, int val, int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyCl(int[] array, int val, int startIndex, int endIndex) {
		// YOUR CODE HERE

		// avoid degenerate cases
		if (array.length < endIndex || endIndex < startIndex) {
			return;
		}

		for (int i = startIndex; i < endIndex; i++) {
			array[i] = val;
		}

	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyX(int[] array, int val, int startIndex, int endIndex) {
		// YOUR CODE HERE
		for (int i = startIndex; i < endIndex + 1; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyAl(int[] array, int val, int startIndex, int endIndex) {
		if (startIndex < array.length && endIndex < array.length && startIndex <= endIndex)
			for (int i = startIndex; i < endIndex; i++)
				array[i] = val;
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyD(int[] array, int val, int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyJ(int[] array, int val, int startIndex, int endIndex) {
		for (int i = startIndex; i < minimumJ(array.length, endIndex); i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyC(int[] array, int val, int startIndex, int endIndex) {
		// YOUR CODE HERE
		for (int i = startIndex; i < endIndex; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyA(int[] array, int val, int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			array[i] = val;
		}
	}

	/**
	 * Assigns the specified int value to each element of the
	 *         specified range of the specified array of ints. The range to be
	 *         filled extends from index startIndex, inclusive, to index
	 *         endIndex, exclusive.
	 * @param array
	 *            - the array to be filled
	 * @param val
	 *            - the value to be stored in all elements of the array
	 * @param startIndex
	 *            - the index of the first element (inclusive) to be filled with
	 *            the specified value
	 * @param endIndex
	 *            - the index of the last element (exclusive) to be filled with
	 *            the specified value
	 */

	public static void fillArrayPartiallyAt(int[] array, int val, int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
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
	
	public static void fillArrayPartiallyFP(int[] array, int val, int startIndex, int endIndex){
		//YOUR CODE HERE
		if(endIndex >= array.length) return;
		for(int i = startIndex; i <= endIndex; i++) {
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
	
	public static void fillArrayPartiallyAJ(int[] array, int val, int startIndex, int endIndex){
		//YOUR CODE HERE
		
		assert (startIndex < endIndex); // since exclusive
		assert (array.length != 0);
		assert (array.length >= endIndex);
		for (int i = startIndex; i < endIndex; i++) {
			array[i] = val;
		}
	}
	/*************************************************************/

	/**
	 * Copies the specified array, truncating or padding with
	 *         zeros (if necessary) so the copy has the specified length. For
	 *         all indices that are valid in both the original array and the
	 *         copy, the two arrays will contain identical values. For any
	 *         indices that are valid in the copy but not the original, the copy
	 *         will contain 0. Such indices will exist if and only if the
	 *         specified length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyM(int[] array, int newLength) {
		int arrayLength = array.length;
		int[] array1 = new int[newLength];
		if (arrayLength <= newLength) {
			for (int i = 0; i < newLength; i++) {
				array1[i] = array[i];
			}
		} else {
			for (int i = 0; i < arrayLength; i++) {
				array1[i] = array[i];
			}
			for (int j = newLength; j < newLength - arrayLength; j++) {
				array1[j] = 0;
			}
		}
		return array1;
	}

	/**
	 * Copies the specified array, truncating or padding with
	 *         zeros (if necessary) so the copy has the specified length. For
	 *         all indices that are valid in both the original array and the
	 *         copy, the two arrays will contain identical values. For any
	 *         indices that are valid in the copy but not the original, the copy
	 *         will contain 0. Such indices will exist if and only if the
	 *         specified length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyG(int[] array, int newLength) {
		int[] copy = new int[newLength];
		int i = 0;
		while ((i < array.length) && (i < newLength)) {
			copy[i] = array[i];
			i++;
		}
		if (i == array.length) {
			while (i < newLength) {
				copy[i++] = 0;
			}
		}
		return copy;
	}

	/**
	 * Copies the specified array, truncating or padding with
	 *         zeros (if necessary) so the copy has the specified length. For
	 *         all indices that are valid in both the original array and the
	 *         copy, the two arrays will contain identical values. For any
	 *         indices that are valid in the copy but not the original, the copy
	 *         will contain 0. Such indices will exist if and only if the
	 *         specified length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyCl(int[] array, int newLength) {
		// YOUR CODE HERE

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
	 * Copies the specified array, truncating or padding with zeros
	 *         (if necessary) so the copy has the specified length. For all
	 *         indices that are valid in both the original array and the copy,
	 *         the two arrays will contain identical values. For any indices
	 *         that are valid in the copy but not the original, the copy will
	 *         contain 0. Such indices will exist if and only if the specified
	 *         length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyX(int[] array, int newLength) {
		// YOUR CODE HERE
		int fillLength = TestCodeTemplate.minimum(newLength, array.length);
		int[] newArray = new int[newLength];
		for (int i = 0; i < fillLength; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	/**
	 * Copies the specified array, truncating or padding with zeros
	 *         (if necessary) so the copy has the specified length. For all
	 *         indices that are valid in both the original array and the copy,
	 *         the two arrays will contain identical values. For any indices
	 *         that are valid in the copy but not the original, the copy will
	 *         contain 0. Such indices will exist if and only if the specified
	 *         length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyAL(int[] array, int newLength) {
		int[] copy = new int[newLength];
		for (int i = 0; i < newLength; i++) {
			if (i < array.length)
				copy[i] = array[i];
			else
				copy[i] = 0;
		}
		return copy;
	}

	/**
	 * Copies the specified array, truncating or padding with
	 *         zeros (if necessary) so the copy has the specified length. For
	 *         all indices that are valid in both the original array and the
	 *         copy, the two arrays will contain identical values. For any
	 *         indices that are valid in the copy but not the original, the copy
	 *         will contain 0. Such indices will exist if and only if the
	 *         specified length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyD(int[] array, int newLength) {
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
	 * Copies the specified array, truncating or padding with
	 *         zeros (if necessary) so the copy has the specified length. For
	 *         all indices that are valid in both the original array and the
	 *         copy, the two arrays will contain identical values. For any
	 *         indices that are valid in the copy but not the original, the copy
	 *         will contain 0. Such indices will exist if and only if the
	 *         specified length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyJ(int[] array, int newLength) {
		int[] newArr = new int[newLength];
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = (i < array.length) ? array[i] : 0;
		}
		return newArr;
	}

	/**
	 * Copies the specified array, truncating or padding with
	 *         zeros (if necessary) so the copy has the specified length. For
	 *         all indices that are valid in both the original array and the
	 *         copy, the two arrays will contain identical values. For any
	 *         indices that are valid in the copy but not the original, the copy
	 *         will contain 0. Such indices will exist if and only if the
	 *         specified length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyC(int[] array, int newLength) {
		// YOUR CODE HERE
		int[] newArray = new int[newLength];
		int length = array.length;
		if (newLength < length) {
			for (int i = 0; i < newLength; i++)
				newArray[i] = array[i];
		} else {
			for (int i = 0; i < length; i++)
				newArray[i] = array[i];
			for (int i = length; i < newLength; i++)
				newArray[i] = 0;
		}
		return newArray;
	}

	/**
	 * Copies the specified array, truncating or padding with
	 *         zeros (if necessary) so the copy has the specified length. For
	 *         all indices that are valid in both the original array and the
	 *         copy, the two arrays will contain identical values. For any
	 *         indices that are valid in the copy but not the original, the copy
	 *         will contain 0. Such indices will exist if and only if the
	 *         specified length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyA(int[] array, int newLength) {
		int[] arr = new int[newLength];
		for (int i = 0; i < newLength; i++) {
			arr[i] = i < array.length ? array[i] : 0;
		}
		return arr;
	}

	/**
	 * Copies the specified array, truncating or padding with
	 *         zeros (if necessary) so the copy has the specified length. For
	 *         all indices that are valid in both the original array and the
	 *         copy, the two arrays will contain identical values. For any
	 *         indices that are valid in the copy but not the original, the copy
	 *         will contain 0. Such indices will exist if and only if the
	 *         specified length is greater than that of the original array.
	 * @param array
	 *            - the array to be copied
	 * @param newLength
	 *            - the length of the copy to be returned
	 * @return a copy of the original array, truncated or padded with zeros to
	 *         obtain the specified length
	 */
	public static int[] returnCopyAt(int[] array, int newLength) {
		int[] out = new int[newLength];
		int arrayEnd = minimumAt(array.length, newLength);
		for (int i = 0; i < arrayEnd; i++) {
			out[i] = array[i];
		}
		for (int i = arrayEnd; i < newLength; i++) {
			out[i] = 0;
		}
		return out;
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
	public static int[] returnCopyFP(int[] array, int newLength){
		//YOUR CODE HERE
		int[] res = new int[newLength];
		for(int i = 0; i < newLength; i++) res[i] = (array.length <= i? 0:array[i]);
			return res;
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
	public static int[] returnCopyAJ(int[] array, int newLength){
		//YOUR CODE HERE
		assert (array.length != 0);
		
		int [] newArray = new int[newLength];
		
		for (int i = 0; i < newLength; i++) {
			if (i <= array.length) {
				newArray[i] = array[i]; // this will auto truncate if newLength < original size
			} else {
				newArray[i] = 0; // add padding
			}
		}
		return newArray;
	}
	

	/*************************************************************/

	/**
	 * Copies the specified range of the specified array into a
	 *         new array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array, truncated or padded with zeros to obtain the required
	 *         length
	 */
	public static int[] returnCopyRangeM(int[] array, int startIndex, int endIndex) {
		int length = endIndex - startIndex;
		int[] array1 = new int[length];
		for (int i = 0; i < length; i++) {
			array1[i] = array[i + startIndex];
		}
		return array1;
	}

	/**
	 * Copies the specified range of the specified array into a
	 *         new array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array, truncated or padded with zeros to obtain the required
	 *         length
	 */
	public static int[] returnCopyRangeG(int[] array, int startIndex, int endIndex) {
		int newLength = endIndex - startIndex;
		int[] range = new int[newLength];
		int i = 0;
		while (i < newLength) {
			range[i] = array[startIndex + i];
		}
		return range;
	}

	/**
	 * Copies the specified range of the specified array into a
	 *         new array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array,
	 */
	public static int[] returnCopyRangeCl(int[] array, int startIndex, int endIndex) {
		// YOUR CODE HERE

		int length = endIndex - startIndex;
		int newArray[] = new int[length];

		for (int i = 0; i < length; i++) {
			newArray[i] = array[startIndex + i];
		}

		return newArray;

	}

	/**
	 * Copies the specified range of the specified array into a new
	 *         array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array, truncated or padded with zeros to obtain the required
	 *         length
	 */
	public static int[] returnCopyRangeX(int[] array, int startIndex, int endIndex) {
		// YOUR CODE HERE
		int arrayLength = endIndex - startIndex + 1;
		int[] newArray = new int[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			newArray[i] = array[i + startIndex];
		}
		return newArray;
	}

	/**
	 * Copies the specified range of the specified array into a new
	 *         array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array, truncated or padded with zeros to obtain the required
	 *         length
	 */
	public static int[] returnCopyRangeAl(int[] array, int startIndex, int endIndex) {
		if (endIndex >= startIndex) {
			int len = endIndex - startIndex;
			int[] copy = new int[len];
			if (startIndex < array.length) {
				for (int i = 0; i < len; i++) {
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
	 * Copies the specified range of the specified array into a
	 *         new array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array, truncated or padded with zeros to obtain the required
	 *         length
	 */
	public static int[] returnCopyRangeD(int[] array, int startIndex, int endIndex) {
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
	 * Copies the specified range of the specified array into a
	 *         new array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array, truncated or padded with zeros to obtain the required
	 *         length
	 */
	public static int[] returnCopyRangeJ(int[] array, int startIndex, int endIndex) {
		int[] newArr = new int[endIndex - startIndex];
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = (startIndex + i < array.length) ? array[startIndex + i] : 0;
		}
		return newArr;
	}

	/**
	 * Copies the specified range of the specified array into a
	 *         new array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array, truncated or padded with zeros to obtain the required
	 *         length
	 */
	public static int[] returnCopyRangeC(int[] array, int startIndex, int endIndex) {
		// YOUR CODE HERE
		int[] newArray = new int[endIndex - startIndex];
		for (int i = startIndex; i < endIndex; i++)
			newArray[i - startIndex] = array[i];
		return newArray;
	}

	/**
	 * Copies the specified range of the specified array into a
	 *         new array and returns the new array
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array, truncated or padded with zeros to obtain the required
	 *         length
	 */
	public static int[] returnCopyRangeA(int[] array, int startIndex, int endIndex) {
		int[] arr = new int[endIndex - startIndex];
		for (int i = startIndex; i < endIndex; i++) {
			arr[i - startIndex] = i < array.length ? array[i] : 0;
		}
		return arr;
	}

	/**
	 * Copies the specified range of the specified array into a
	 *         new array and returns the new array of length endIndex -
	 *         startIndex
	 * @param array
	 *            - the array from which a range is to be copied
	 * @param startIndex
	 *            - the initial index of the range to be copied, inclusive
	 * @param endIndex
	 *            - the final index of the range to be copied, exclusive. (This
	 *            index may lie outside the array.)
	 * @return a new array containing the specified range from the original
	 *         array
	 */
	public static int[] returnCopyRangeAt(int[] array, int startIndex, int endIndex) {
		if (endIndex < startIndex) {
			return null;
		}
		int[] out = new int[endIndex - startIndex];
		int arrayEnd = minimumAt(array.length, endIndex);
		for (int i = startIndex; i < arrayEnd; i++) {
			out[i] = array[i];
		}
		return out;
	}
	
	/**
	 * Copies the specified range of the specified array into a new array and 
	 * returns the new array of length endIndex - startIndex
	 * @param array - the array from which a range is to be copied
	 * @param startIndex - the initial index of the range to be copied, inclusive
	 * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array
	 */
	public static int[] returnCopyRangeFP(int[] array, int startIndex, int endIndex){
		//YOUR CODE HERE
		if(endIndex >= array.length) endIndex = array.length -1;
		int[] res = new int[endIndex - startIndex + 1];
		for(int i = startIndex; i <= endIndex; i++) res[i- startIndex] = array[i];
			return res;
	}
	
	
	/**
	 * Copies the specified range of the specified array into a new array and 
	 * returns the new array of length endIndex - startIndex
	 * @param array - the array from which a range is to be copied
	 * @param startIndex - the initial index of the range to be copied, inclusive
	 * @param endIndex -  the final index of the range to be copied, exclusive. (This index may lie outside the array.)
	 * @return a new array containing the specified range from the original array
	 */
	public static int[] returnCopyRangeAJ(int[] array, int startIndex, int endIndex){
		//YOUR CODE HERE
		// determine the length of the new array
		if (endIndex >= array.length) {
			endIndex = array.length;
		}
		assert (startIndex <= array.length);
		int newLength = endIndex - startIndex;
		
		int[] newArray = new int[newLength];
		
		int j = 0;
		for (int i = startIndex; i < endIndex; i++) {
			newArray[j] = array[i];
			j++;
		}
		return newArray;
	}
	/************************************************************/

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListM(int[] array) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListG(int[] array) {
		List<Integer> list = new ArrayList<Integer>();
		int i = 0;
		while (i < array.length) {
			list.add(array[i]);
		}
		return list;
	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListCl(int[] array) {
		// YOUR CODE HERE

		List<Integer> li = new ArrayList<Integer>();

		for (int i = 0; i < array.length; i++) {
			li.add(array[i]);
		}

		return li;

	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListX(int[] array) {
		// YOUR CODE HERE
		List<Integer> newArray = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			newArray.add(array[i]);
		}
		return newArray;
	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListAl(int[] array) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++)
			list.add(array[i]);
		return list;
	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListD(int[] array) {
		List<Integer> myList = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			myList.add(array[i]);
		}
		return myList;
	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListJ(int[] array) {
		ArrayList<Integer> newList = new ArrayList<>(array.length);
		for (int i = 0; i < array.length; i++) {
			newList.add(array[i]);
		}
		return newList;
	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListC(int[] array) {
		// YOUR CODE HERE
		List<Integer> intList = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++)
			intList.add(array[i]);
		return intList;
	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListA(int[] array) {
		List<Integer> l = new ArrayList<Integer>(array.length);
		for (int i = 0; i < array.length; i++) {
			l.add(array[i]);
		}
		return l;
	}

	/**
	 * Returns a list version of the array
	 * @param array
	 *            the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListAt(int[] array) {
		ArrayList<Integer> out = new ArrayList<Integer>(array.length);
		for (int i = 0; i < array.length; i++) {
			out.set(i, array[i]);
		}
		return out;
	}
	
	/**
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListFP(int[] array){
		//YOUR CODE HERE
		List<Integer> res = new ArrayList<Integer>();
		for(int i = 0 ; i< array.length; i++) res.add(array[i]);
		return res;
	}
	
	/**
	 * Returns a list version of the array
	 * @param array the array to be converted
	 * @return the list version of array
	 */
	public static List<Integer> convertToListAJ(int[] array){
		//YOUR CODE HERE
		
		List<Integer> arr = new ArrayList<Integer>(array.length);
		for (int i = 0; i<array.length; i++) {
			arr.add(array[i]);
		}
		return arr;		
	}
	

	/*************************************************************/

	/**
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringM(int[] array) {
		String str = "";
		for (int i = 0; i < array.length; i++) {
			str = "" + Integer.toString(array[i]);

		}
		return str;
	}

	/**
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String[] arrToStringG(int[] array) {
		int newLength = 2 * array.length + 1;
		String[] str = new String[newLength];
		str[0] = "[";
		for (int j = 1; (j - 1) / 2 < array.length; j += 2) {
			str[j] = String.valueOf(array[(j - 1) / 2]);
			if (j + 2 <= newLength) {
				str[j + 1] = ",";
			}
		}
		str[newLength - 1] = "]";
		return array == null ? null : str;
	}

	/**
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringCl(int[] array) {
		// YOUR CODE HERE

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
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringX(int[] array) {
		// YOUR CODE HERE
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
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringAl(int[] array) {
		if (array == null)
			return null;
		String s = "[";
		for (int i = 0; i < array.length; i++) {
			s += String.valueOf(array[i]);
			if (i + 1 < array.length)
				s += ", ";
		}
		s += "]";
		return s;
	}

	/**
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringD(int[] array) {
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
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringJ(int[] array) {
		if (array == null)
			return null;

		String arr = "[";
		for (int i = 0; i < array.length - 1; i++) {
			arr += array[i] + ", ";
		}

		if (array.length != 0)
			arr += array[array.length - 1];

		arr += "]";
		return arr;
	}

	/**
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringC(int[] array) {
		// YOUR CODE HERE
		if (array == null)
			return null;

		String newString = "[";
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				newString = newString + ",";
			String newElement = String.valueOf(array[i]);
			newString = newString + newElement;
		}
		newString = newString + "]";

		return newString;
	}

	/**
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringA(int[] array) {
		if (array == null) {
			return "null";
		}

		StringBuilder s = new StringBuilder();
		s.append("[");

		for (int i = 0; i < array.length; i++) {
			s.append(i);
			if (i != array.length - 1) {
				s.append(", ");
			}
		}
		s.append("]");

		return s.toString();
	}

	/**
	 * Returns a string representation of the contents of the
	 *         specified array. The string representation consists of a list of
	 *         the array's elements, enclosed in square brackets ("[]").
	 *         Adjacent elements are separated by the characters ", " (a comma
	 *         followed by a space). Elements are converted to strings as by
	 *         String.valueOf(int). Returns "null" if a is null.
	 * @param array
	 *            - the array whose string representation to return
	 * @return a string representation of array
	 */
	public static String arrToStringAt(int[] array) {
		if (array == null) {
			return "null";
		}
		if (array.length == 0) {
			return "[]";
		}
		// Assuming we can't use StringBuilder
		int len = 2 + (array.length - 1) * 2 + 1;
		for (int i = 0; i < array.length; i++) {
			len += String.valueOf(array[i]).length();
		}
		char[] out = new char[len];
		out[0] = '[';
		out[len - 2] = ']';
		out[len - 1] = '\0';
		int offset = 1;
		for (int i = 0; i < array.length; i++) {
			String nStr = String.valueOf(array[i]);
			for (int j = 0; j < nStr.length(); j++) {
				out[offset + j] = nStr.charAt(j);
				offset += j;
			}
			if (i < array.length - 1) {
				out[offset++] = ',';
				out[offset++] = ' ';
			}
		}
		return out.toString();
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
	public static String arrToStringFP(int[] array){
		//YOUR CODE HERE
		StringBuffer sb = new StringBuffer("[");
		for(int i = 0; i < array.length;i++) {
			sb.append(String.valueOf(array[i]));
			if(i != array.length -1) sb.append(",");
		}
		return sb.append("]").toString();
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
	public static String arrToStringAJ(int[] array){
		//YOUR CODE HERE
		String result = "";
		if (array.length == 0) return null;
		result = "[";
		for (int i = 0; i < array.length - 1; i++) {
			result = result + String.valueOf(array[i]) + ", ";
		}
		result = result + String.valueOf(array[array.length - 1]) + "]";
		return result;
	}
}
