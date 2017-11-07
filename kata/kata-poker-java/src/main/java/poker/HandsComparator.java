package poker;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;

import poker.Card.Value;

public class HandsComparator {

	enum Possibilities {
		NOTHING, ONE_PAIR, TWO_PAIR, THREE_BUC, FULL
	}
	public static Possibilities evaluateHand(List<Card> hand) {
		if (hasFull(hand)) return Possibilities.FULL;
		if (has1Pair(hand)) return Possibilities.ONE_PAIR;
		if (has2Pair(hand)) return Possibilities.TWO_PAIR;
		if (has3Buc(hand)) return Possibilities.THREE_BUC;
		return Possibilities.NOTHING;
	}
	
	public static int compareHands(List<Card> hand1, List<Card> hand2) {
		Possibilities v1 = evaluateHand(hand1);
		Possibilities v2 = evaluateHand(hand2);
		
		return (int) Math.signum(v2.ordinal() - v1.ordinal());
	}
	
	
	
	private static boolean has1Pair(List<Card> hand) {
		return 1 == cardsByValue(hand).values().stream()
			.filter(list -> list.size() == 2)
			.count();
	}
	private static boolean has2Pair(List<Card> hand) {
		return 2 == cardsByValue(hand).values().stream()
				.filter(list -> list.size() == 2)
				.count();
	}
	private static boolean has3Buc(List<Card> hand) {
		return 1 == cardsByValue(hand).values().stream()
			.filter(list -> list.size() == 3)
			.count();
	}
	private static boolean hasFull(List<Card> hand) {
		return has3Buc(hand) && has1Pair(hand);
	}
	
	private static Map<Value, List<Card>> cardsByValue(List<Card> hand) {
		return hand.stream().collect(groupingBy(Card::getValue));
	}
	

}
