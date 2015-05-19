package victor.training.jee6.cdi.di.dependencies;

import javax.enterprise.inject.Alternative;

@Alternative
// TODO do it in XML
public class InitPOJO {
	private String name;

	public void init(String name) {
		this.name = name;
	}

	public void doStuff() {
		if (name == null) {
			throw new IllegalStateException("");
		}
		System.out.println("InitPOJO " + name + " does stuff");
	}
	
	@Override
	public String toString() {
return name;	}
}
