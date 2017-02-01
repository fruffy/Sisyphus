package datastructures;

import java.util.Iterator;

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
	
	private String headVar;
	private String headElem;
	private VariableEnv tail;
	
	private VariableEnv(String headVar, String headElem, VariableEnv tail){
		this.headVar = headVar;
		this.headElem = headElem;
		this.tail = tail;
	}
	
	public static VariableEnv empty(){
		return new VariableEnv(null, null, null);
	}
	
	public VariableEnv cons(String headVar, String tailVar){
		return new VariableEnv(headVar, tailVar, this);
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
	
	
	

}
