package core;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class DeckTest extends TestCase {
	
	public void testDuplicates() {
		Deck deck = new Deck();
		
		Set<Card> cards = new HashSet<Card>(deck.getCards());
		
		assertEquals(deck.size(), cards.size(), 52);
	}
}