package poker;

import java.util.stream.Stream;

public class Card {

	public Value value;
	public Color color;
		
	public Card(Value value, Color color) {
		this.value = value;
		this.color = color;
	}

	@Override
	public String toString() {
		return "Card [value=" + value + ", color=" + color + "]";
	}

	enum Value {
		ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), 
		SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("J"), 
		QUEEN("Q"), KING("K");
		
		String value;
		
		private Value(String value){
			this.value = value;
		}
		
		public static Value fromString(String s){
			return Stream.of(values())
					.filter(v -> v.value.equals(s))
					.findFirst().get();
		}
	}
	
	enum Color {
		ROMB("R"), TREFLA("T"), INIMA("I"), PICA("P");
		
		String value;
		
		Color(String value){
			this.value = value;
		}
		public static Color fromString(String s){
			return Stream.of(values())
					.filter(v -> v.value.equals(s))
					.findFirst().get();
		}
		
	}

	public final Value getValue() {
		return value;
	}

	public final void setValue(Value value) {
		this.value = value;
	}

	public final Color getColor() {
		return color;
	}

	public final void setColor(Color color) {
		this.color = color;
	}
	
	
}
