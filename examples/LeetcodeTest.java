import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LeetcodeTest {
	public List<String> fizzBuzz1(int n) {
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
	
	public List<String> fizzBuzz2(int n) {
        
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
	
	public List<String> fizzBuzz3(int n) {
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
	
	public List<String> fizzBuzz4(int n) {
        
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
	
	public List<String> fizzBuzz5(int n) {
        String[] arr = new String[n];
        for (int i = 0, j = 1; i < n; i++, j++) {
            if      (j % 15 == 0) arr[i] = "FizzBuzz";
            else if (j %  3 == 0) arr[i] = "Fizz";
            else if (j %  5 == 0) arr[i] = "Buzz";
            else                  arr[i] = String.valueOf(j);
        }
        return Arrays.asList(arr);
    }
	
	public List<String> fizzBuzz6(int n) {
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
	
	public List<String> fizzBuzz7(int n) {
		List<String> list = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
		String x = i % 15 == 0 ? "FizzBuzz" : i % 3 == 0 ? "Fizz" : i % 5 == 0 ? "Buzz" : ""+i;
		list.add(x);
		}
		return list;
	}
	
	
	public List<String> fizzBuzz8(int n) {
		   List<String> res = new LinkedList<String>();
		      for (int i=1; i<=n ;i++) {
		        if   (i%3 == 0 && i%5 == 0)   res.add("FizzBuzz");
		        else if (i%3 == 0)            res.add("Fizz");
		        else if (i%5 == 0)            res.add("Buzz");
		        else                          res.add(""+i);
		     }
		     return res;
	}
	
	public List<String> fizzBuzz9(int n) {
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
	
	
	
}
