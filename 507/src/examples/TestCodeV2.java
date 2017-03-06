package examples;

import java.util.ArrayList;
import java.util.List;

public class TestCodeV2 {
	
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
	 * @author Jenny
     * Returns the absolute value an int value
     * @param val
     * @return the absolute value of val.
     */
    public int absoluteValueJ(int val){
        return (val < 0) ? -1 * val : val;
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
        for(int i = startIndex; i < minimum(array.length, endIndex); i++){
            array[i] = val;
        }
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
	 * @author Itrat
	 * Returns the absolute value of val
	 * @param val
	 * @return
	 */
	public static int absoluteValue(int val){
		if(val < 0){
			return -val;
		}
		return val;
	}
	
	/**
	 * @author Itrat
	 * @param value
	 * @return
	 */
	public static int absoluteValue2(int value){
	  if (value < 0) {
		    return -value;
		  }
		  else {
		    return value;  
		  }
	}

	/**
	 * @author Itrat
	 * Returns the maximum of val1 and val2
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximum(int val1, int val2){
		if(val1 < val2){
			return val2;
		}
		return val1;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximum2(int val1, int val2){
		int maxVal = val1;
		if(maxVal < val2){
			maxVal = val2;
		}
		return maxVal;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximum3(int val1, int val2){
		int maxVal = 0;
		if(val1 < val2){
			maxVal = val2;
		}
		else if(val1 >= val2){
			maxVal = val1;
		}
		return maxVal;
	}
	
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximum4(int val1, int val2){
		int maxVal = 0;
		if(val1 <= val2){
			maxVal = val2;
		}
		else if(val1 > val2){
			maxVal = val1;
		}
		return maxVal;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximum5(int val1, int val2){
		if(val1 <= val2){
			return val2;
		}
		return val1;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximum6(int val1, int val2){
		int maxVal = val1;
		if(maxVal <= val2){
			maxVal = val2;
		}
		return maxVal;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximum7(int val1, int val2){
		if(val1 >= val2){
			return val1;
		}
		return val2;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int maximum8(int val1, int val2){
		int maxVal = val2;
		if(val1 >= maxVal){
			maxVal = val1;
		}
		return maxVal;
	}
	
	/**
	 * @author Itrat
	 * Returns the minimum of val1 and val2
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimum(int val1, int val2){
		if(val1 < val2){
			return val1;
		}
		return val2;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimum2(int val1, int val2){
		int minVal = val1;
		if(minVal > val2){
			minVal = val2;
		}
		return minVal;
	}
	
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimum3(int val1, int val2){
		int minVal = 0;
		if(val1 < val2){
			minVal = val1;
		}
		else if(val1 >= val2){
			minVal = val2;
		}
		return minVal;
	}
	
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimum4(int val1, int val2){
		int minVal = 0;
		if(val1 <= val2){
			minVal = val1;
		}
		else if(val1 > val2){
			minVal = val2;
		}
		return minVal;
	}
	
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimum5(int val1, int val2){
		if(val1 <= val2){
			return val1;
		}
		return val2;
	}
	
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimum6(int val1, int val2){
		int minVal = val1;
		if(val2 <= minVal){
			minVal = val2;
		}
		return minVal;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimum7(int val1, int val2){
		if(val1 >= val2){
			return val2;
		}
		return val1;
	}
	
	/**
	 * @author Itrat
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int minimum8(int val1, int val2){
		int minVal = val2;
		if(minVal >= val1){
			minVal = val1;
		}
		return minVal;
	}
	
	
	/**
	 * @author Itrat
	 * Check if array1 equals array2
	 * @param array1
	 * @param array2
	 * @return 
	 */
	public static boolean equals(int[] array1, int[] array2){
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
	
	/**
	 * @author Itrat
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals2(int[] array1, int[] array2){
		int arr1Length = array1.length;
		int arr2Length = array2.length;
		if(arr1Length != arr2Length){
			return false;
		}
		for(int i = 0; i<arr1Length; i++){
			if(array1[i]!=array2[i]){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @author Itrat
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals3(int[] array1, int[] array2){
		boolean ifEqual = true;
		if(array1.length != array2.length){
			ifEqual = false;
		}
		for(int i = 0; i<array1.length; i++){
			if(array1[i]!=array2[i]){
				ifEqual = false;
				break;
			}
		}
		return ifEqual;
	}
	
	/**
	 * @author Itrat
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals4(int[] array1, int[] array2){
		boolean ifEqual = true;
		int arr1Length = array1.length;
		int arr2Length = array2.length;
		if(arr1Length != arr2Length){
			ifEqual = false;
		}
		for(int i = 0; i<arr1Length; i++){
			if(array1[i]!=array2[i]){
				ifEqual =  false;
				break;
			}
		}
		return ifEqual;
	}
	
	/**
	 * @author Itrat
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals5(int[] array1, int[] array2){
		boolean ifEqual = true;
		if(array1.length != array2.length){
			ifEqual = false;
		}
		for(int i = 0; i<array1.length; i++){
			if(array1[i]!=array2[i]){
				ifEqual = false;
			}
		}
		return ifEqual;
	}
	
	/**
	 * @author Itrat
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals6(int[] array1, int[] array2){
		boolean ifEqual = true;
		int arr1Length = array1.length;
		int arr2Length = array2.length;
		if(arr1Length != arr2Length){
			ifEqual = false;
		}
		for(int i = 0; i<arr1Length; i++){
			if(array1[i]!=array2[i]){
				ifEqual =  false;
			}
		}
		return ifEqual;
	}
	
	/**
	 * @author Itrat
	 * Fill array  with val
	 * @param array
	 * @param val
	 */
	public static void fillArray(int[] array, int val){
		for(int i = 0; i<array.length; i++){
			array[i] = val;
		}
	}
	
	/**
	 * @author Itrat
	 * @param array
	 * @param val
	 */
	public static void fillArray2(int[] array, int val){
		int i = 0;
		while(i<array.length){
			array[i] = val;
			i = i+1;
		}
	}
	
	/**
	 * @author Itrat
	 * @param array
	 * @param val
	 */
	public static void fillArray3(int[] array, int val){
		int i = 0;
		while(i<array.length){
			array[i] = val;
			i++;
		}
	}
	
	/**
	 * Fill array with val from startIndex to endIndex(not inclusive)
	 * @param array
	 * @param val
	 * @param startIndex
	 * @param endIndex
	 */
	
	public static void fillArrayPartially(int[] array, int val, int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= array.length || endIndex <0 || endIndex >= array.length){
			return;
		}
		for(int i = startIndex; i<endIndex; i++){
			array[i] = val;
		}
	}
	
	/**
	 * @author Itrat
	 * @param array
	 * @param val
	 * @param startIndex
	 * @param endIndex
	 */
	public static void fillArrayPartially2(int[] array, int val, int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= array.length){
			return;
		}
		if( endIndex <0 || endIndex >= array.length){
			return;
		}
		for(int i = startIndex; i<endIndex; i++){
			array[i] = val;
		}
	}
	
	/**
	 * @author Itrat
	 * @param array
	 * @param val
	 * @param startIndex
	 * @param endIndex
	 */
	public static void fillArrayPartially3(int[] array, int val, int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= array.length || endIndex <0 || endIndex >= array.length){
			return;
		}
		int i = startIndex;
		while(i<endIndex){
			array[i] = val;
			i++;
		}
	}
	
	/**
	 * @author Itrat
	 * Returns a copy of array
	 * @param array
	 * @return
	 */
	public static int[] returnCopy(int[] array){
		int[] newArray = new int[array.length];
		for(int i = 0; i<newArray.length; i++){
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	/**
	 * @author Itrat
	 * Returns a partial copy of the array (specified by startIndex and endIndex(not inclusive))
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static int[] returnPartialCopy(int[] array, int startIndex, int endIndex){
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
	
	/**
	 * @author Itrat
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static int[] returnPartialCopy2(int[] array, int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= array.length || endIndex <0 || endIndex >= array.length){
			System.out.println("startIndex and endIndex do not make sense");
			return new int[0];
		}
		int[] newArray = new int[endIndex - startIndex];
		for(int i = startIndex; i<endIndex; i++){
			newArray[i-startIndex] = array[i];
		}
		return newArray;
	}
	
	/**
	 * @author Itrat
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static int[] returnPartialCopy3(int[] array, int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= array.length || endIndex <0 || endIndex >= array.length){
			System.out.println("startIndex and endIndex do not make sense");
			return new int[0];
		}
		int[] newArray = new int[endIndex - startIndex];
		for(int i = 0; i<endIndex - startIndex; i++){
			newArray[i] = array[i+startIndex];
		}
		return newArray;
	}
	
	/**
	 * @author Itrat
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static int[] returnPartialCopy4(int[] array, int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= array.length || endIndex <0 || endIndex >= array.length){
			System.out.println("startIndex and endIndex do not make sense");
			return new int[0];
		}
		int newLength = endIndex - startIndex;
		int[] newArray = new int[newLength];
		for(int i = 0; i<newLength; i++){
			newArray[i] = array[i+startIndex];
		}
		return newArray;
	}
	
	/**
	 * @author Itrat
	 * Returns a list version of the array
	 * @param array
	 * @return
	 */
	public static List<Integer> convertToList(int[] array){
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		for(int a: array){
			arrList.add(a);
		}
		return arrList;
	}
	
	/**
	 * @author Itrat
	 * @param array
	 * @return
	 */
	public static List<Integer> convertToList2(int[] array){
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		for(int i = 0; i<array.length; i++){
			arrList.add(array[i]);
		}
		return arrList;
	}
	
	/**
	 * @author Itrat
	 * @param array
	 * @return
	 */
	public static List<Integer> convertToList3(int[] array){
		ArrayList<Integer> arrList = new ArrayList<Integer>(array.length);
		for(int i = 0; i<array.length; i++){
			arrList.set(i,array[i]);
		}
		return arrList;
	}
	
	/**
	 * @author Itrat
	 * Return a string version of the array
	 * @param array
	 * @return
	 */
	public static String arrToString(int[] array){
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
	
	/**
	 * @author Itrat
	 * @param array
	 * @return
	 */
	public static String arrToString2(int[] array){
		String arrString = "[";
		for(int i = 0; i<array.length; i++){
			arrString += array[i];
			if(i!=array.length-1){
				arrString += ", ";
			}
		}
		arrString += "]";
		return arrString;
	}
	
	/*NON LIBRARY METHODS*/
	
	/*
	 * from http://www.java-made-easy.com/java-methods.html
	 */
	public static int five(){
		int num = 5;
		return num;
	}
	
	public static boolean ifEven(int num){
		if(num%2==0){
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args){
		int val = -1;
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
		System.out.println("ArrayList version of array1: "+ arrList);
	
	}
}
