package victor.training.jee6.cdi.di.dependencies;


public class CtrPOJO {

	private final String keyParameter;

	public CtrPOJO(String keyParameter) {
		this.keyParameter = keyParameter;
	}

	public void doStuff() {
		System.out.println("CtrPOJO does stuff with " + keyParameter);
	}
}
