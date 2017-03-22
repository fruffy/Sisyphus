import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LeetcodeTest {
	public List<String> fizzBuzz01(int n) {
        List<String> ret = new ArrayList<String>(n);
        for(int i=1,fizz=0,buzz=0;i<=n ;i++){
            fizz++;
            buzz++;
            if(fizz==3 && buzz==5){
                ret.add("FizzBuzz");
                fizz=0;
                buzz=0;
            }else if(fizz==3){
                ret.add("Fizz");
                fizz=0;
            }else if(buzz==5){
                ret.add("Buzz");
                buzz=0;
            }else{
                ret.add(String.valueOf(i));
            }
        } 
        return ret;
    }
	
	public List<String> fizzBuzz02(int n) {
        
        List<String> result = new ArrayList<>();
        
        if(n < 1) return result;
        
        for(int i = 1, fizz = 3, buzz = 5; i <= n; i++) {
        
            String addVal = null;
            
            if(i == fizz && i == buzz) {
                addVal = "FizzBuzz"; 
                fizz += 3;
                buzz += 5;
            } else if(i == fizz) {
                addVal = "Fizz";
                fizz += 3;
            } else if(i == buzz) {
                addVal ="Buzz";
                buzz += 5;
            } else
                addVal = String.valueOf(i);
            
            result.add(addVal); 
        }
        
        return result;
    }
	
	public List<String> fizzBuzz03(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                list.add("FizzBuzz");
            } else if (i % 3 == 0) {
                list.add("Fizz");
            } else if (i % 5 == 0) {
                list.add("Buzz");
            } else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }
	
	public List<String> fizzBuzz04(int n) {
        
        List<String> ls = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=n;i++){
            sb.setLength(0);
            if(i%3==0){
                sb.append("Fizz");
            }
            if(i%5==0){
                sb.append("Buzz");
            }
            if(sb.length()==0){
                sb.append(String.valueOf(i));
            }
            ls.add(sb.toString());
        }
        return ls;
    }
	
	public List<String> fizzBuzz05(int n) {
        String[] arr = new String[n];
        for (int i = 0, j = 1; i < n; i++, j++) {
            if      (j % 15 == 0) arr[i] = "FizzBuzz";
            else if (j %  3 == 0) arr[i] = "Fizz";
            else if (j %  5 == 0) arr[i] = "Buzz";
            else                  arr[i] = String.valueOf(j);
        }
        return Arrays.asList(arr);
    }
	
	public List<String> fizzBuzz06(int n) {
        List<String> ans = new ArrayList<String>();
        for (int i = 1; i <= n; i++) {
            if (i%15 == 0)
                ans.add("FizzBuzz");
            else if (i%3 == 0)
                ans.add("Fizz");
            else if (i%5 == 0)
                ans.add("Buzz");
            else
                ans.add(Integer.toString(i));
        }
        return ans;
    }
	
	public List<String> fizzBuzz07(int n) {
		List<String> list = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
		String x = i % 15 == 0 ? "FizzBuzz" : i % 3 == 0 ? "Fizz" : i % 5 == 0 ? "Buzz" : ""+i;
		list.add(x);
		}
		return list;
	}
	
	
	public List<String> fizzBuzz08(int n) {
		   List<String> res = new LinkedList<String>();
		      for (int i=1; i<=n ;i++) {
		        if   (i%3 == 0 && i%5 == 0)   res.add("FizzBuzz");
		        else if (i%3 == 0)            res.add("Fizz");
		        else if (i%5 == 0)            res.add("Buzz");
		        else                          res.add(""+i);
		     }
		     return res;
	}
	
	public List<String> fizzBuzz09(int n) {
		List<String> res = new ArrayList<>();
		for(int i = 1; i <= n; i++){
		if((i%3 == 0) && (i%5 == 0)){
		res.add("FizzBuzz");
		} else if(i%3 == 0){
		res.add("Fizz");
		} else if(i%5 == 0){
		res.add("Buzz");
		} else{
		res.add(String.valueOf(i));
		}
		}

		    return res;
		}
	
    public List<String> fizzBuzz10(int n) {
    	String[] strs = {"FizzBuzz", null, null, "Fizz", null, "Buzz", "Fizz", null, null, "Fizz", "Buzz", null, "Fizz", null, null};
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            String str = strs[i % 15];
            result.add(str == null ? String.valueOf(i) : str);
        }
        return result;
    }
    
    /****************************************************************/
    
    public int lengthOfLastWord01(String s) {
        return s.trim().length()-s.trim().lastIndexOf(" ")-1;
    }
	
    public int lengthOfLastWord02(String s) {
        int len=s.length(), lastLength=0;
        
        while(len > 0 && s.charAt(len-1)==' '){
            len--;
        }
        
        while(len > 0 && s.charAt(len-1)!=' '){
            lastLength++;
            len--;
        }
        
        return lastLength;
    }
    
    public int lengthOfLastWord03(String s) {
        String s1=s.trim();
        int x=s1.lastIndexOf(" ");
        if(x==-1)
        return s1.length();
        else
        return s1.substring(x,s1.length()-1).length();
    }
    
    public int lengthOfLastWord04(String s) {
        if (s.length() == 0 || s == null) return 0;
        String[] str = s.split(" ");
        if (str.length == 0 || str == null) return 0;
        return str[str.length - 1].length();
    }
    
    public int lengthOfLastWord05(String s) {
        int len=0;
        for(int i=s.length()-1;i>=0;i--){
            if(s.charAt(i)==' '){
                //If it is not a trailing space, return the length.
               if(len!=0) return len;
            }else{
               len++;
            }
        }
        return len;
     }
    
    public int lengthOfLastWord06(String s) {
    	s = s.trim();
        int lastIndex = s.lastIndexOf(' ') + 1;
        return s.length() - lastIndex;        
    }
    
    public int lengthOfLastWord07(String s) {
        //228ms
        int lenIndex = s.length()-1;
        int len = 0;

        /*can also use while here, resulting in 264ms
        while (lenIndex>=0 && s.charAt(lenIndex)==' ') lenIndex--;*/
        
        /*or use trim - 324ms
        s = s.trim();*/

        for (int i=lenIndex; i>=0 && s.charAt(i)==' '; i--) 
            lenIndex--;
        
        for (int i=lenIndex; i>=0 && s.charAt(i)!=' '; i--) 
            len++;
        return len;
    }
    
    public int lengthOfLastWord08(String s){
    	String[] words = s.split(" ");
    	if (words.length==0) return 0;
    	else return words[words.length-1].length();
    	
    }
    
    
    public int lengthOfLastWord09(String s) {
        String use = s.trim();
        int count = 0;
        for (int i = use.length() - 1; i >= 0; i--) {
            if (use.charAt(i) != ' ') count++;
            else break;
        }
        return count;
    }
    
    public int lengthOfLastWord10(String s) {
        if (null == s || s.trim().length() == 0) return 0;
        
        s = s.trim();
        String lastWord = s.substring(s.lastIndexOf(" ") + 1);
        return lastWord.length();
    }
    
    
    /**********************************************************/
    
    public int removeDuplicates01(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i-2])
                nums[i++] = n;
        return i;
    }
    
    public int removeDuplicates02(int[] nums) {
        int count=1;
        int innerCount=1;
         for(int i=1;i<nums.length;i++){
             if(nums[i]==nums[i-1]){
                 innerCount++;
                 if(innerCount>2)
                     continue;
             }
             else{
                 innerCount=1;
             }
                 nums[count++]=nums[i];
         }
         return count;
     }
    
    public int removeDuplicates03(int[] nums) {
        int i = 0;
        for(int n : nums)
            if(i < 1 || n > nums[i - 1]) 
                nums[i++] = n;
        return i;
    }
    
    public int removeDuplicates04(int[] nums) {
		//define at most k times of duplicate numbers
		final int k = 2;

		//check if it is an empty array
		if(nums.length == 0) return 0;

		//start pointer of new array
		int m = 1;

		// count the time of duplicate numbers occurence
		int count = 1;

		for(int i = 1; i < nums.length; ++i) {
			if(nums[i] == nums[i - 1]) {
				if(count < k) {
					nums[m++] = nums[i];
				}
				count++;
			} else {
				count = 1;
				nums[m++] = nums[i];
			}
		}
		return m;
	}
    
    public int removeDuplicates05(int[] nums) {
        if (nums.length == 0) {return 0;}
        int pointer = 0, flag = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] && flag == 0) {
                flag = 1;
                pointer++;
            } else if (nums[i] != nums[i - 1]) {
                flag = 0;
                pointer++;
            }
            nums[pointer] = nums[i];
        }
        return pointer + 1;
    }
    
    public int removeDuplicates06(int[] nums) {
        if(nums==null || nums.length<3){
            return nums==null?0:nums.length;
        }
        int dif = 2;
        for(int i=2;i<nums.length;i++){
            if(nums[i]!=nums[dif-2]){
                nums[dif++]=nums[i];
            }
        }
        return dif;
    }
    
    public int removeDuplicates07(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int count = 0; // counter of accepted numbers
        int duplicate = 1; //counter of current duplicates
        for (int i = 1; i < A.length; i++) {
            if (A[i] != A[count]) {
                count++;
                A[count] = A[i];
                duplicate = 1;
            } else {
                if (duplicate < 2) { // where apply to k duplicates
                    count++;
                    A[count] = A[i];
                    duplicate++;
                }
            }
        }
        return count+1;
    }
    
    
    public int removeDuplicates08(int[] nums) {
        if(nums.length == 0 || nums.length == 1){
            return nums.length;
        }
        int read = 2;
        int write = 2;
        int count = 1;
        
        while(read < nums.length){
            if(nums[read] == nums[read-1]){
                if(nums[read] == nums[write-2]){
                    read++;
                }
                else{
                    nums[write] = nums[read];
                    read++;
                    write++;
                }
            }
            else{
            nums[write] = nums[read];
            read++;
            write++;
            }
        }
        return write;
    }
    
    public int removeDuplicates09(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int count = 0;
        int k = 2;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] == nums[i]) {
                if (++count == k - 1) {
                    nums[++i] = nums[j];
                } 
            } else { 
                nums[++i] = nums[j];
                count = 0; //reset count 
            }
        }
        return i + 1;
    }
    
    public int removeDuplicates10(int[] nums) {
        if(nums.length <3) return nums.length;
        
        int count = 1;
        // Count duplicate elements
        int dup = 0;
        for(int i=1; i<nums.length; i++) {
            if(nums[i] != nums[i-1]) {
                nums[count] = nums[i];
                count++;
                dup = 0;
            } else if (dup == 0) {
                nums[count] = nums[i];
                count++;
                dup++;
            }
        }
        
        return count;
    }
    
    /*************************************************************/
    //Merge sorted arrays
    public void merge01(int A[], int m, int B[], int n) {
        int i=m-1, j=n-1, k=m+n-1;
        while (i>-1 && j>-1) A[k--]= (A[i]>B[j]) ? A[i--] : B[j--];
        while (j>-1)         A[k--]=B[j--];
    }
    
    public void merge02(int A[], int m, int B[], int n) {
        int length = m+n;
        while(n > 0) A[--length] = (m == 0 || A[m-1] < B[n-1]) ? B[--n] : A[--m];
    }
    
    
    public void merge03(int A[], int m, int B[], int n){
    	while(n>0)A[m+n-1]=(m==0||B[n-1]>A[m-1])?B[--n]:A[--m];
    }
    
    public void merge04(int A[], int m, int B[], int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while(i >= 0 && j >= 0) {
            A[k--] = A[i] > B[j] ? A[i--] : B[j--];
        }
        while(j >= 0) {
            A[k--] = B[j--];
        }
    }
    
    public void merge05(int[] nums1, int m, int[] nums2, int n) {
        int curr1 = m -1, curr2 = n -1, start = m + n-1;
        while (curr1 >= 0 && curr2 >=0){
            if(nums1[curr1] > nums2[curr2]) nums1[start--] = nums1[curr1--];
            else nums1[start--] = nums2[curr2--];
        }
        if(curr1 <= 0) {
            while(curr2>= 0) nums1[start--] = nums2[curr2--];
        }
    }
    
    public void merge06(int[] nums1, int m, int[] nums2, int n) {
        int tail1 = m - 1, tail2 = n - 1, finished = m + n - 1;
        while (tail1 >= 0 && tail2 >= 0) {
            nums1[finished--] = (nums1[tail1] > nums2[tail2]) ? 
                                 nums1[tail1--] : nums2[tail2--];
        }

        while (tail2 >= 0) { //only need to combine with remaining nums2
            nums1[finished--] = nums2[tail2--];
        }
    }
    
    public void merge07(int[] nums1, int m, int[] nums2, int n) {
	    int m2idnex = n - 1;
		int m1index = m - 1;
		for (int i = m + n - 1; i >= 0; i--) {
			if (m2idnex == -1) {
				break;
			} else if (m1index == -1||nums2[m2idnex] >= nums1[m1index]) {
				nums1[i] = nums2[m2idnex--];
			} else {
				nums1[i] = nums1[m1index--];
			}
		}
    }
    
    public void merge08(int[] nums1, int m, int[] nums2, int n) {
        while(n>0){
            if(m>0&&nums1[m-1]>nums2[n-1]){
                nums1[m+n-1] = nums1[m-1];
                m--;
            }
            else{
                nums1[m+n-1] = nums2[n-1];
                n--;
            }
        }
    }
    
    public void merge09(int[] nums1, int m, int[] nums2, int n) {
        // insert from the m+n-1 position at the bigger array
        // keep 3 pointers: one at the insertion point
        // one at the end of nums1; one at the end of nums2
        int insertP = m + n - 1;
        int nums1P = m - 1;
        int nums2P = n - 1;
        
        while (nums1P >= 0 && nums2P >= 0) {
            if (nums1[nums1P] > nums2[nums2P]) {
                nums1[insertP--] = nums1[nums1P--];
            } else {
                nums1[insertP--] = nums2[nums2P--];
            }
        }
        while (nums2P >= 0) {
            nums1[insertP--] = nums2[nums2P--];
        }
    }
    
    public void merge10(int[] nums1, int m, int[] nums2, int n) {
    	int p = m+n, p1 = m-1, p2 = n-1;
    	while(--p>=0) {
    		if(p1<0 || (p2>=0 && nums1[p1]<nums2[p2])) nums1[p] = nums2[p2--];
    		else nums1[p] = nums1[p1--];
    	}
    }
    
    /******************************************************************/
    //Reverse an integer
    public int reverse01(int x)
    {
        int result = 0;

        while (x != 0)
        {
            int tail = x % 10;
            int newResult = result * 10 + tail;
            if ((newResult - tail) / 10 != result)
            { return 0; }
            result = newResult;
            x = x / 10;
        }

        return result;
    }
    
    public int reverse02(int x) {
        long rev= 0;
        while( x != 0){
            rev= rev*10 + x % 10;
            x= x/10;
            if( rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE)
                return 0;
        }
        return (int) rev;
    }
    
    public int reverse03(int x) {
        long result = 0;
        int digit = 0;
		
        while (x != 0) {
        	digit = x % 10;
        	result = (result * 10 + digit);
        	x /= 10;
        }
		
        if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE){
        	return 0;
        }
		
        return (int)result;
    }
    
    public int reverse04(int x) {
        long result = 0l;
        while(x != 0) {
            result = result * 10 + (x % 10);
            x = x/10;
        }
        return (result < (long)Integer.MIN_VALUE || result > (long)Integer.MAX_VALUE)? 0:(int)result; // overflow
    }
    
    public int reverse05(int x) {
        long result =0;
        while(x != 0)
        {
            result = (result*10) + (x%10);
            if(result > Integer.MAX_VALUE) return 0;
            if(result < Integer.MIN_VALUE) return 0;
            x = x/10;
        }
        return (int)result;
        
        
    }
    
    public int reverse06(int x) {
    	if (x == Integer.MIN_VALUE)
    		return 0;
    	boolean minus = false;
    	if (x < 0){
    		x = Math.abs(x);
    		minus = true;
    	}
        long temp = 0;
        while(x != 0){
        	temp *= 10;
        	if (temp > Integer.MAX_VALUE || temp < Integer.MIN_VALUE)
        		return 0;
        	temp +=  x%10;
        	x /= 10;
        }
        if (minus)
        	return (int)(-temp);
        return (int)temp;
    }
    
    public int reverse07(int x) {
        long answer = 0;
        while(x != 0) {
            answer = 10 * answer + x % 10;
            x /= 10;
        }
        return (answer > Integer.MAX_VALUE || answer < Integer.MIN_VALUE) ? 0 : (int) answer;
    }
    
    public int reverse08(int x) {
        long res = 0;
		for (; x != 0; x /= 10)
			res = res * 10 + x % 10;
		return res > Integer.MAX_VALUE || res < Integer.MIN_VALUE ? 0: (int) res;
    }
    
    public int reverse09(int x) {
        long result = 0;
        //long type is for compare. Only larger range can compare Max and Min Integer 

        while(x!=0)
        {
        	int mod = x%10;
        	x = x/10;
        	result = result*10 + mod;
        	if(result>Integer.MAX_VALUE||result<Integer.MIN_VALUE) return 0;
        }
        return (int) result;
    }
    
    public int reverse10(int x) {
		int sum = 0;
		while (Math.abs(x) != 0)
		{
			if(Math.abs(sum) > Integer.MAX_VALUE / 10)
			{
				return 0;
			}
			sum = sum * 10 + x % 10;
			x = x / 10;
		}
		
		return sum;
    }
    
}
