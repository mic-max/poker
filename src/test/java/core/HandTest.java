package core;

import junit.framework.TestCase;

public class HandTest extends TestCase {
	
	private static Hand nothing = new Hand("D2", "C4", "H6", "S8", "CQ"); // high queen

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
	
	public void testTriple() {
		Hand h1 = new Hand("S5", "D5", "H5", "C6", "C9"); // triple 5
		
		assertTrue(h1.hasTriple());
		assertFalse(nothing.hasTriple());
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
	
}
