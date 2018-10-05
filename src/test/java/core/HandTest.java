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
	
	public void testTriple() {
		Hand h1 = new Hand("S5", "D5", "H5", "C6", "C9"); // triple 5
		
		assertTrue(h1.hasTriple());
		assertFalse(nothing.hasTriple());
	}
}
