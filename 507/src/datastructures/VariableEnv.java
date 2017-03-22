package datastructures;

import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaparser.utils.Pair;

/**
 * Environments for mapping variable names to some string value,
 * such as a type or a new variable name
 * Similar to the Gamma often seen in type rules
 *
 */
public class VariableEnv implements Iterable<Pair<String, String>> {

	//private static int nextInt = 0;

	//	private int getNextInt(){
	//		int ret = VariableEnv.nextInt;
	//		VariableEnv.nextInt++;
	//		return ret;
	//	}

	class VarEnvIter implements Iterator<Pair<String, String>>{

		private VarEnvIter(VariableEnv init){
			this.current = init;
		}

		private VariableEnv current;
		@Override
		public boolean hasNext() {
			return !current.isEmpty();
		}

		@Override
		public Pair<String, String> next() {
			if (!hasNext()){
				throw new RuntimeException("Can't call Next on an empty environment");
			}
			if (this.current.headVar == null || this.current.headElem == null){
				throw new RuntimeException("Null var or elem: " + headVar + ", " + headElem);
			}

			Pair<String,String> ret = new Pair<String, String>(this.current.headVar, this.current.headElem);
			this.current = current.tail;
			return ret;
		}

	}

	//We store key, value, and the rest of the list
	//If our list is empty, these are all set to null
	private final String headVar;
	private final String headElem;
	private final VariableEnv tail;

	private VariableEnv(String headVar, String headElem, VariableEnv tail){
		this.headVar = headVar;
		this.headElem = headElem;
		this.tail = tail;
	}

	public static VariableEnv empty(){
		return new VariableEnv(null, null, null);
	}

	public VariableEnv cons(String headKey, String headElem){
		if (headKey == null || headElem == null){
			throw new RuntimeException("Null var or elem: " + headVar + ", " + headElem);
		}
		return new VariableEnv(headKey, headElem, this);
	}

	public boolean isEmpty(){
		return this.headVar == null;
	}

	/**
	 * Returns null if the given key isn't in the env
	 * Otherwise, it returns the string associated with it
	 */
	public String lookup(String key){
		for (Pair<String, String> pair : this){
			if (pair.a.equals(key)){
				return pair.b;
			}
		}
		return null;
	}

	@Override
	public Iterator<Pair<String, String>> iterator() {
		return new VarEnvIter(this);
	}

	private String removeLastDigit(String str){

		Pattern p = Pattern.compile("_[0-9]+$");
		Matcher m = p.matcher(str);
		if(m.find()) {
			return m.replaceAll("");
		}
		throw new RuntimeException("Variable name " + str + " doesn't end in a number");
	}

	public String freshKey(String base){
		//return base + "_" + getNextInt();

		//Find all strings matching the prefix
		HashSet<String> matching = new HashSet<String>();
		for (Pair<String, String> entry : this){
			if (removeLastDigit(entry.b).equals(base)){
				matching.add(entry.b);
			}
		}
		//Our fresh key is our base, plus the lowest number
		//that's greater >= the length of all matching
		//that isn't in the matching list,
		//Usually this should succeed after one try, but we want to be defensive
		int length = matching.size();
		for(int i = 0; i < length + 1; i++){
			String candidate = base + "_" + (length + i);
			if (!matching.contains(candidate)){
				//System.err.println("NAME::: Found name " + candidate + " for env " + this.toString());
				return candidate;
			}
		}
		throw new RuntimeException("Impossible, matching must contain a finine number of strings, can't match more than its length");
	}

	/**
	 * Add all elements of the given container to the front of this list
	 * The order of the input container may not be preserved
	 */
	public VariableEnv appendFront(Iterable<Pair<String, String>> toAppend){
		VariableEnv ret = this;
		for (Pair<String, String> pair : toAppend){
			ret = ret.cons(pair.a, pair.b);
		}
		return ret;
	}

	@Override
	public String toString(){
		StringBuffer ret = new StringBuffer();
		for (Pair<String, String> pair : this )
		{
			ret.append("(" + pair.a + ", " + pair.b + ")");
		}
		return ret.toString();
	}




}
