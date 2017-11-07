package poker;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import poker.Card.Color;
import poker.Card.Value;

public class HandsComparatorTest {

//	Pica Romb Inima Trefla
	@Test
	public void onePairWinsOverNothingP1P2() {
		int rez = HandsComparator.compareHands(makeHand("2P-2R-10P-8R-6I"),
				makeHand("2P-5R-10P-8R-6I"));
		assertEquals(-1, rez);
	}
	
	private List<Card> makeHand(String handStr) {
		return Stream.of(handStr.split("-")).map(s->makeCard(s)).collect(toList());
	}

	private Card makeCard(String s) {
		String valueStr = s.substring(0, s.length() - 1);
		String colorStr = s.substring(s.length() - 1);
		return new Card(Value.fromString(valueStr), 
					Color.fromString(colorStr));
	}
	@Test
	public void onePairWinsOverNothingP2P1() {
		int rez = HandsComparator.compareHands(
				makeHand("2P-5R-10P-8R-6I"),
				makeHand("2P-2R-10P-8R-6I"));
		assertEquals(1, rez);
	}
	@Test
	public void onePairEqual() {
		int rez = HandsComparator.compareHands(
				makeHand("2P-2R-10P-8R-6I"),
				makeHand("2P-2R-10P-8R-6I"));
		assertEquals(0, rez);
	}
	@Test
	public void nothingEqualsNothing() {
		int rez = HandsComparator.compareHands(
				makeHand("2P-3R-10P-8R-6I"),
				makeHand("2P-3R-10P-8R-6I"));
		assertEquals(0, rez);
	}
	@Test
	public void twoPairsGre8ThenNothing() {
		int rez = HandsComparator.compareHands(
				makeHand("2P-2R-6P-6R-7I"),
				makeHand("2P-3R-10P-8R-6I"));
		assertEquals(-1, rez);
	}
	@Test
	public void twoPairsGreaterThan1Pair() {
		int rez = HandsComparator.compareHands(
				makeHand("2P-2R-3T-3R-7I"),
				makeHand("2P-2R-10P-8R-6I"));
		assertEquals(-1, rez);
	}
}
