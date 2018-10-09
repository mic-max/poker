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
		assertTrue(ex1.contains(new Card("C5")));
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
		assertTrue(ex.contains(new Card("C2")));
		assertTrue(ex.contains(new Card("D5")));
	}
	
	public void testNothingHandExchange() {
		List<Card> ex = HandTest.nothing.exchange();

		assertEquals(3, ex.size());
		assertTrue(ex.contains(new Card("D2")));
		assertTrue(ex.contains(new Card("C4")));
		assertTrue(ex.contains(new Card("H6")));
	}
}
