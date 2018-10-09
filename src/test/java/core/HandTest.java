package core;

import junit.framework.TestCase;

public class HandTest extends TestCase {
	
	static Hand nothing = new Hand("D2", "C4", "H6", "S8", "CQ"); // high queen

	static Hand pair = new Hand("SQ", "HQ", "C4", "C7", "D3");
	static Hand twoPair = new Hand("D2", "S2", "C6", "D6", "HK");
	static Hand threeOfKind = new Hand("D10", "C10", "H10", "C2", "D5");
	static Hand straight = new Hand("D3", "C4", "H5", "H6", "H7");
	static Hand flush = new Hand("DK", "DJ", "D9", "D4", "D6");
	static Hand fullHouse = new Hand("S4", "C4", "H4", "HJ", "CJ");
	static Hand fourOfKind = new Hand("S7", "D7", "H7", "C7", "C2");
	static Hand straightFlush = new Hand("D8", "D9", "D10", "DJ", "DQ");
	static Hand royalFlush = new Hand("HA", "HK", "HQ", "HJ", "H10");	

	public void testEquality() {
		Hand h1 = new Hand("SA", "S2", "S3", "S4", "S5");
		Hand h2 = new Hand("SA", "S2", "S3", "S4", "S5");
		Hand h3 = new Hand("HA", "H2", "H3", "H4", "H5");
		
		assertTrue(h1.equals(h2));
		assertFalse(h1.equals(h3));
	}
	
	public void testSinglePair() {
		Hand h1 = new Hand("SA", "HA", "C2", "C3", "C4"); // pair aces
		
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
		Hand h1 = new Hand("S5", "D5", "H5", "C6", "C9"); // triple 5
		
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
		
		assertTrue(h1.isStraight());
		assertTrue(h2.isStraight());
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
	
	public void testHighCard() {
		Hand h1 = new Hand("SA", "HA", "C2", "C3", "C4"); // pair aces
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
}
