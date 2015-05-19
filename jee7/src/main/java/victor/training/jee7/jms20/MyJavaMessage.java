package victor.training.jee7.jms20;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MyJavaMessage implements Serializable {
	public String field;

	public MyJavaMessage(String field) {
		this.field = field;
	}

	public MyJavaMessage() {
	}
	
	@Override
	public String toString() {
		return "java message : "+field;
	}
	
}
