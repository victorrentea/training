package victor.training.java8.stream.menu;

import java.awt.Color;

public class Apple {
	private int weight;
	private Color color;
	
	public Apple() {
	}
	
	public Apple(int weight) {
		this.weight = weight;
	}


	public final int getWeight() {
		return weight;
	}

	public final void setWeight(int weight) {
		this.weight = weight;
	}

	public final Color getColor() {
		return color;
	}

	public final void setColor(Color color) {
		this.color = color;
	}
	
	
}
