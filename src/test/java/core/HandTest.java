package core;

import java.util.*;
import junit.framework.TestCase;

public class HandTest extends TestCase {

	static Hand nothing = new Hand("D2", "C4", "H6", "S8", "CQ");

	static Hand pair          = new Hand("SQ", "HQ", "C4", "C7", "D3");
	static Hand twoPair       = new Hand("D2", "S2", "C6", "D6", "HK");
	static Hand threeOfKind   = new Hand("D10", "C10", "H10", "C2", "D5");
	static Hand straight      = new Hand("D3", "C4", "H5", "H6", "H7");
	static Hand flush         = new Hand("DK", "DJ", "D9", "D4", "D6");
	static Hand fullHouse     = new Hand("S4", "C4", "H4", "HJ", "CJ");
	static Hand fourOfKind    = new Hand("S7", "D7", "H7", "C7", "C2");
	static Hand straightFlush = new Hand("D8", "D9", "D10", "DJ", "DQ");
	static Hand royalFlush    = new Hand("HA", "HK", "HQ", "HJ", "H10");

	public void testEquality() {
		Hand h1 = new Hand("SA", "S2", "S3", "S4", "S5");
		Hand h2 = new Hand("SA", "S2", "S3", "S4", "S5");
		Hand h3 = new Hand("HA", "H2", "H3", "H4", "H5");
		Object obj = new Object();

		assertTrue(h1.equals(h2));
		assertFalse(h1.equals(h3));
		assertFalse(h1.equals(obj));
	}

	public void testSinglePair() {
		Hand h1 = new Hand("SA", "HA", "C2", "C3", "C4");

		assertTrue(h1.hasPair());
		assertFalse(nothing.hasPair());
	}

	public void testTwoPairs() {
		Hand h1 = new Hand("SA", "HA", "CK", "HK", "C2");
		Hand h2 = new Hand("D5", "S5", "C5", "H5", "SA");

		assertTrue(h1.hasTwoPairs());
		assertFalse(h2.hasTwoPairs());
		assertFalse(nothing.hasTwoPairs());
	}

	public void testSet() {
		Hand h1 = new Hand("S5", "D5", "H5", "C6", "C9");

		assertTrue(h1.hasSet());
		assertFalse(nothing.hasSet());
	}

	public void testFourOfKind() {
		Hand h1 = new Hand("D5", "S5", "C5", "H5", "SA");

		assertTrue(h1.hasFourOfKind());
		assertFalse(nothing.hasFourOfKind());
	}

	public void testFullHouse() {
		Hand h1 = new Hand("D5", "S5", "C5", "H4", "S4");
		Hand h2 = new Hand("S10", "D10", "H10", "HA", "D6");

		assertTrue(h1.isFullHouse());
		assertFalse(h2.isFullHouse());
		assertFalse(nothing.isFullHouse());

	}

	public void testFlush() {
		Hand h1 = new Hand("S5", "S7", "SJ", "SQ", "SK");

		assertTrue(h1.isFlush());
		assertFalse(nothing.isFlush());
	}

	public void testStraight() {
		Hand h1 = new Hand("SA", "C2", "H3", "S4", "D5");
		Hand h2 = new Hand("HA", "HK", "CQ", "S10", "DJ");
		Hand h3 = new Hand("S3", "H5", "C6", "H6", "H7");

		assertTrue(h1.isStraight());
		assertTrue(h2.isStraight());
		assertFalse(h3.isStraight());
		assertFalse(nothing.isStraight());
	}

	public void testRoyalFlush() {
		Hand h1 = new Hand("SA", "SK", "SQ", "SJ", "S10");

		assertTrue(h1.isRoyalFlush());
		assertFalse(nothing.isRoyalFlush());
	}

	public void testStraightFlush() {
		Hand h1 = new Hand("S8", "S9", "S10", "SJ", "SQ");

		assertTrue(h1.isStraightFlush());
		assertFalse(nothing.isStraightFlush());
	}

	public void testSwapCards() {
		Hand h = new Hand("C2", "H7", "SQ", "SJ", "H10");
		List<Card> d = Arrays.asList(Deck.C2, Deck.H7);
		List<Card> a = Arrays.asList(Deck.SA, Deck.SK);

		h.swap(d, a);

		List<Card> cards = h.getCards();
		assertEquals(5, cards.size());
		assertTrue(cards.containsAll(Arrays.asList(Deck.SA, Deck.SK, Deck.SQ, Deck.SJ, Deck.H10)));
		assertFalse(cards.contains(Deck.C2));
		assertFalse(cards.contains(Deck.H7));
	}

	public void testSwapCardsDiffSize() {
		boolean thrown = false;
		Hand h = new Hand("C2", "H7", "SQ", "SJ", "H10");
		List<Card> d = Arrays.asList(Deck.C2, Deck.H7);
		List<Card> a = Arrays.asList(Deck.SA);

		try {
			h.swap(d, a);
		} catch (IllegalArgumentException e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

	public void testCompareHandsWithSameCard() {
		boolean thrown = false;
		Hand h1 = new Hand("S2", "C4", "CK", "S5", "D8");
		Hand h2 = new Hand("S3", "C9", "CQ", "S5", "D10");
		try {
			h1.compareTo(h2);
		} catch (IllegalStateException e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

	public void testHandMustBeFiveCards() {
		boolean thrown = false;
		try {
			new Hand("C4", "H2", "D9");
		} catch (IllegalArgumentException e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

	public void testHandMustBeUnique() {
		boolean thrown = false;
		try {
			new Hand("C4", "H2", "D9", "DA", "DA");
		} catch (IllegalArgumentException e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

	public void testHighCard() {
		Hand h1 = new Hand("SA", "HA", "C2", "C3", "C4");
		Hand h2 = new Hand("SA", "C2", "H3", "S4", "D5");

		assertEquals(new Card("CQ"), nothing.getHighCard());
		assertEquals(new Card("SA"), h1.getHighCard());
		assertEquals(new Card("D5"), h2.getHighCard());
	}

	public void testComparingDifferentHands() {
		assertTrue(pair.compareTo(twoPair) < 0);
		assertTrue(twoPair.compareTo(threeOfKind) < 0);
		assertTrue(threeOfKind.compareTo(straight) < 0);
		assertTrue(straight.compareTo(flush) < 0);
		assertTrue(flush.compareTo(fullHouse) < 0);
		assertTrue(fullHouse.compareTo(fourOfKind) < 0);
		assertTrue(fourOfKind.compareTo(straightFlush) < 0);
		assertTrue(straightFlush.compareTo(royalFlush) < 0);
		assertTrue(royalFlush.compareTo(nothing) > 0);
	}
	
	public void testIndependentOfExchange() {
		Hand h = new Hand("S4", "S5", "S6", "S7", "S8");	
		assertEquals("Straight Flush", h.handName);

		h.swap(Deck.S4, Deck.C8);
		assertEquals("Pair", h.handName);
	}

}
