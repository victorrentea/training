package victor.training.spring.remoting;

import java.io.Serializable;

public class Pair implements Serializable {
	private int a;
	private int b;

	public Pair() {
	}

	public Pair(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public final int getA() {
		return a;
	}

	public final void setA(int a) {
		this.a = a;
	}

	public final int getB() {
		return b;
	}

	public final void setB(int b) {
		this.b = b;
	}

}
