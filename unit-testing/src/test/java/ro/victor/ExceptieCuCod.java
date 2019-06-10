package ro.victor;

public class ExceptieCuCod {
	
	@Rule
	public ExpectedException expectedException = none();

	@Test
	public void test1() {
		expectedException.expectedMessage("a");
		aruncama();
		
	}
	
	
	public void aruncama() {
		if (true) throw new IllegalArgumentException("a");
		if (true) throw new IllegalArgumentException("b");
		if (true) throw new IllegalArgumentException("c");
		if (true) throw new IllegalArgumentException("c");
	}
}
