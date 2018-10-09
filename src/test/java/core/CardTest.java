package core;

import junit.framework.TestCase;

public class CardTest extends TestCase {

	public void testEquality() {
		Card c1 = new Card("C3");
		Card c2 = new Card("C3");
		Card c3 = new Card("SA");

		assertTrue(c1.equals(c2));
		assertFalse(c1.equals(c3));
	}

	public void testComparison() {
		Card c1 = new Card("H8");
		Card c2 = new Card("S7");
		Card c3 = new Card("D7");
		Card c4 = new Card("D7");

		assertTrue(c1.compareTo(c2) > 0);
		assertTrue(c3.compareTo(c2) < 0); // same rank
		assertTrue(c1.compareTo(c3) > 0);
		assertEquals(0, c3.compareTo(c4)); // same card
	}
}
