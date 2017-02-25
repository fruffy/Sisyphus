package examples;

import java.util.ArrayList;
import java.util.List;

public class TestCodeV2 {
	
	/**
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
	
	public static int absoluteValue2(int value){
	  if (value < 0) {
		    return -value;
		  }
		  else {
		    return value;  
		  }
	}

	/**
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
	
	public static int maximum2(int val1, int val2){
		int maxVal = val1;
		if(maxVal < val2){
			maxVal = val2;
		}
		return maxVal;
	}
	
	
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
	
	public static int maximum5(int val1, int val2){
		if(val1 <= val2){
			return val2;
		}
		return val1;
	}
	
	public static int maximum6(int val1, int val2){
		int maxVal = val1;
		if(maxVal <= val2){
			maxVal = val2;
		}
		return maxVal;
	}
	
	public static int maximum7(int val1, int val2){
		if(val1 >= val2){
			return val1;
		}
		return val2;
	}
	
	public static int maximum8(int val1, int val2){
		int maxVal = val2;
		if(val1 >= maxVal){
			maxVal = val1;
		}
		return maxVal;
	}
	
	/**
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
	
	public static int minimum2(int val1, int val2){
		int minVal = val1;
		if(minVal > val2){
			minVal = val2;
		}
		return minVal;
	}
	
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
	
	public static int minimum5(int val1, int val2){
		if(val1 <= val2){
			return val1;
		}
		return val2;
	}
	
	public static int minimum6(int val1, int val2){
		int minVal = val1;
		if(val2 <= minVal){
			minVal = val2;
		}
		return minVal;
	}
	
	public static int minimum7(int val1, int val2){
		if(val1 >= val2){
			return val2;
		}
		return val1;
	}
	
	public static int minimum8(int val1, int val2){
		int minVal = val2;
		if(minVal >= val1){
			minVal = val1;
		}
		return minVal;
	}
	
	
	/**
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
	 * Fill array  with val
	 * @param array
	 * @param val
	 */
	public static void fillArray(int[] array, int val){
		for(int i = 0; i<array.length; i++){
			array[i] = val;
		}
	}
	
	public static void fillArray2(int[] array, int val){
		int i = 0;
		while(i<array.length){
			array[i] = val;
			i = i+1;
		}
	}
	

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
	
	public static List<Integer> convertToList2(int[] array){
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		for(int i = 0; i<array.length; i++){
			arrList.add(array[i]);
		}
		return arrList;
	}
	
	public static List<Integer> convertToList3(int[] array){
		ArrayList<Integer> arrList = new ArrayList<Integer>(array.length);
		for(int i = 0; i<array.length; i++){
			arrList.set(i,array[i]);
		}
		return arrList;
	}
	
	/**
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
