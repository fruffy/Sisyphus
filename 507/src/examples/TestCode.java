package examples;

public class TestCode {

	public int absoluteVal(int number) {
		//hello?
		//System.out.println("functionCall");
		/*testing comments
		Mogambo
		*/
		int temp = 4;
		if (number < 0) {
			return 0 - number; //Check1
		} else {
			return number;
		}	/*
		Check2
		*/

	}
	
	public int absolute(int num) {
		//System.out.println("hello");
		int temp2 = 5;
		if (num < 0) {
			return 0 - num; 
		} 
		else {
			return num;
		}

	}
	
	public int absolute2(int num) {
		System.out.println("hello");
		int temp2 = 5;
		if (num < 0) {
			return 0 - num; 
		} 
		return num;

	}
	public int absoluteLib(int num) {
		return Math.abs(num);

	}
	
	public double absoluteLib(double num) {
		return Math.abs(num);
	}
	
	public int noOfLegs() {
		int test = 3 + 3;
		int test2 = 2;
		return test + test2;
	}
	
	//Exact copy of version from math, with different variable names
	public static int abs(int x){
		return (x < 0) ? -x : x;
	}
}
