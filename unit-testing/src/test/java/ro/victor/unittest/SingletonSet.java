package ro.victor.unittest;

import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SingletonSet {

	Set<String> prodCode() {
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		return set;
	}
	
	@Test
	public void assertOnlyA() {
		//		Set<String> expected = new HashSet<>();
//		expected.add("a");
		assertEquals(singleton("a"), prodCode());
	}
	
}
