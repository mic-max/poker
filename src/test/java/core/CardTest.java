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
}
