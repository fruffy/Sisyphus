package datastructures;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.utils.Pair;

/**
 * Environments for mapping variable names to some string value,
 * such as a type or a new variable name
 * Similar to the Gamma often seen in type rules
 *
 */
public class VariableEnv implements Iterable<Pair<String, String>> {
	
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
			Pair<String,String> ret = new Pair<String, String>(this.current.headVar, this.current.headElem);
			this.current = current.tail;
			return ret;
		}
		
	}
	
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
		return new VariableEnv(headVar, headElem, this);
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
	
	public String freshKey(String base){
		//Find all strings matching the prefix
		HashSet<String> matching = new HashSet<String>();
		for (Pair<String, String> entry : this){
			if (entry.a.startsWith(base)){
				matching.add(entry.a);
			}
		}
		//Our fresh key is our base, plus the lowest number
		//that's greater >= the length of all matching
		//that isn't in the matching list,
		//Usually this should succeed after one try, but we want to be defensive
		int length = matching.size();
		for(int i = 0; i < length + 1; i++){
			String candidate = base + (length + i);
			if (!matching.contains(candidate)){
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
	
	
	

}
