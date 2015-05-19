package victor.training.eepatterns.legacypojo;

public class POJOAsCDIWithFactory {

	private final String id;

	public POJOAsCDIWithFactory(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void doStuff() {
		System.out.println("Do stuff in POJO as CDI @Produced by a factory");
	}
}
