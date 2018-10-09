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
	
	public void test1AwayStraight() {
		Hand seq4x = new Hand("D4", "D5", "H6", "C7", "H10");
		List<Card> e4x = seq4x.exchange();
		System.out.println(e4x);
		assertEquals(1, e4x.size());
		assertTrue(e4x.contains(Deck.H10));
		
		Hand seq1x3 = new Hand("C4", "H9", "HJ", "CQ", "HK");
		List<Card> e1x3 = seq1x3.exchange();
		assertEquals(1, e1x3.size());
		assertTrue(e1x3.contains(Deck.C4));
		
		Hand seq3x1 = new Hand("C4", "H5", "H6", "C8", "H10");
		List<Card> e3x1 = seq3x1.exchange();
		assertEquals(1, e3x1.size());
		assertTrue(e3x1.contains(Deck.H10)); // or c4
		
		Hand seqx4 = new Hand("C4", "H5", "H6", "C8", "H9");
		List<Card> ex4 = seqx4.exchange();
		assertEquals(1, ex4.size());
		assertTrue(ex4.contains(Deck.H9)); // or c4
		
		Hand seqPair = new Hand("C4", "H5", "H6", "C6", "H7");
		List<Card> ePair = seqPair.exchange();
		assertEquals(1, ePair.size());
		assertTrue(ePair.contains(Deck.C6)); // or h6
		
		Hand seqLowA = new Hand("CA", "H2", "H3", "C4", "H10");
		List<Card> eLowA = seqLowA.exchange();
		assertEquals(1, eLowA.size());
		assertTrue(eLowA.contains(Deck.H10));
		
		Hand seqHighA = new Hand("CA", "HK", "H6", "CJ", "H10");
		List<Card> eHighA = seqHighA.exchange();
		assertEquals(1, eHighA.size());
		assertTrue(eHighA.contains(Deck.H6));
		
		Hand seqLow = new Hand("C2", "H3", "H4", "C5", "H10");
		List<Card> eLow = seqLow.exchange();
		assertEquals(1, eLow.size());
		assertTrue(eLow.contains(Deck.H10));
		
		Hand seqHigh = new Hand("CK", "HQ", "HJ", "C6", "H10");
		List<Card> eHigh = seqHigh.exchange();
		assertEquals(1, eHigh.size());
		assertTrue(eHigh.contains(Deck.C6));
		
		Hand seq4 = new Hand("C5", "H6", "HA", "C8", "H9");
		List<Card> e4 = seq4.exchange();		
		assertEquals(1, e4.size());
		assertTrue(e4.contains(Deck.HA));
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
