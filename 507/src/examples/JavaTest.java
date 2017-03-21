package examples;

public class JavaTest {
	static class X {
		public static void bar() {
			int i = 0;
			i++;
		}
	}

	public void test() {
	    JavaTest.X.bar();
	}
}