package gluser1357.util.tester;

import org.junit.jupiter.api.Test;

import gluser1357.util.something.UtilMain;
import gluser1357.util.something.UtilTest;

public class UtilTester {

	public static void main(String[] args) {
		System.out.println("UtilTester");
		UtilMain a;
		UtilTest b;
	}
	
	@Test
	public void test() {
		UtilMain a;
		System.out.println("UtilTester @Test");
	}
}
