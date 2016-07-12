package victor.training.oo.creational.factorymethod;

public class AvoidOverloadedConstructors {

	private String a,b,c;
	int d;
	
	public AvoidOverloadedConstructors() {
	}

	public AvoidOverloadedConstructors(String b, String c) {
		this.b = b;
		this.c = c;
	}
	
//	public AvoidOverloadedConstructors(String a, String b) {
//		this.a = a;
//		this.b = b;
//	}

	public AvoidOverloadedConstructors(String c, int d) {
		this.c = c;
		this.d = d;
	}
	
	public static AvoidOverloadedConstructors createWithBAndC(String b, String c) {
		AvoidOverloadedConstructors instance = new AvoidOverloadedConstructors();
		instance.b=b;
		instance.c=c;
		return instance;
	}
	
	public static AvoidOverloadedConstructors createWithBAndC(String c, int d) {
		return new AvoidOverloadedConstructors(c, d);
	}
	
	
	
}
