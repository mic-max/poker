package core;

import java.util.List;

import junit.framework.TestCase;

public class AipExchangeTest extends TestCase {

	public void testNoExchange() {
		List<Card> stExchange = HandTest.straight.exchange();
		List<Card> flExchange = HandTest.flush.exchange();
		List<Card> fhExchange = HandTest.fullHouse.exchange();
		List<Card> f4Exchange = HandTest.fourOfKind.exchange();
		List<Card> sfExchange = HandTest.straightFlush.exchange();
		List<Card> rfExchange = HandTest.royalFlush.exchange();
		
		assertTrue(stExchange.isEmpty());
		assertTrue(flExchange.isEmpty());
		assertTrue(fhExchange.isEmpty());
		assertTrue(f4Exchange.isEmpty());
		assertTrue(sfExchange.isEmpty());
		assertTrue(rfExchange.isEmpty());
	}
	
	public void test1AwayFlush() {
		Hand flush1Away = new Hand("D5", "D6", "D10", "C5", "DA");
		List<Card> ex1 = flush1Away.exchange();

		assertEquals(1, ex1.size());
		assertTrue(ex1.contains(Deck.C5));
	}
	
	public void test2AwayFlush() {
		Hand flush2Away = new Hand("D5", "D6", "D10", "C5", "HA");
		List<Card> ex1 = flush2Away.exchange();
		
		assertEquals(2, ex1.size());
		assertTrue(ex1.contains(Deck.C5));
		assertTrue(ex1.contains(Deck.HA));
	}
	
	public void test1AwayFourOfKind() {
		List<Card> ex = HandTest.threeOfKind.exchange();

		assertEquals(2, ex.size());
		assertTrue(ex.contains(Deck.C2));
		assertTrue(ex.contains(Deck.D5));
	}
	
	public void testNothingHandExchange() {
		List<Card> ex = HandTest.nothing.exchange();

		assertEquals(3, ex.size());
		assertTrue(ex.contains(Deck.D2));
		assertTrue(ex.contains(Deck.C4));
		assertTrue(ex.contains(Deck.H6));
	}
	
//	public void test1AwayFullHouse() {
//		List<Card> ex2 = HandTest.twoPair.exchange();
//		List<Card> ex3 = HandTest.threeOfKind.exchange();
//		// TODO test for what card gets exchanged when already has a set, lowest card or doesn't matter?
//
//		assertEquals(1, ex2.size());
//		assertTrue(ex2.contains(Deck.HK));
//		
//		assertEquals(1, ex3.size());
//		assertTrue(ex3.contains(Deck.C2) || ex3.contains(Deck.D5));
//	}
}
