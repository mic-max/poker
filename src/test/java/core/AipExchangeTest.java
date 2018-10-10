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

	public void test1AwayRoyalFlushExchange() {
		Hand rf1Off = new Hand("HA", "HK", "HQ", "C4", "H10");
		List<Card> ex = rf1Off.exchange();
		assertEquals(1, ex.size());
		assertTrue(ex.contains(Deck.C4));

		// Should always try to get the HA instead of the also possible H9 for a straight flush vs. royal flush
		// TODO try with H8, but exchange method won't swap bc its already a flush
		Hand rf1 = new Hand("HK", "HQ", "HJ", "H10", "C8");

		List<Card> ex1 = rf1.exchange();
		assertEquals(1, ex1.size());
		assertTrue(ex1.contains(Deck.C8));
	}

	public void test1AwayFlushExchange() {
		Hand flush1Away = new Hand("D5", "D6", "D10", "C5", "DA");
		List<Card> ex1 = flush1Away.exchange();

		assertEquals(1, ex1.size());
		assertTrue(ex1.contains(Deck.C5));
	}

	public void test1AwayStraightExchange() {
		Hand seq4x = new Hand("D4", "D5", "H6", "C7", "H10");
		List<Card> e4x = seq4x.exchange();
		assertEquals(1, e4x.size());
		assertTrue(e4x.contains(Deck.H10));

		Hand seq1x3 = new Hand("C4", "H9", "HJ", "CQ", "HK");
		List<Card> e1x3 = seq1x3.exchange();
		assertEquals(1, e1x3.size());
		assertTrue(e1x3.contains(Deck.C4));

		Hand seq3x1 = new Hand("C4", "H5", "H6", "C8", "H10");
		List<Card> e3x1 = seq3x1.exchange();
		assertEquals(1, e3x1.size());
		assertTrue(e3x1.contains(Deck.H10) || e3x1.contains(Deck.C4));

		Hand seqx4 = new Hand("C4", "H5", "H6", "C8", "H9");
		List<Card> ex4 = seqx4.exchange();
		assertEquals(1, ex4.size());
		assertTrue(ex4.contains(Deck.H9) || ex4.contains(Deck.C4));

		Hand seqPair = new Hand("C4", "H5", "H6", "C6", "H7");
		List<Card> ePair = seqPair.exchange();
		assertEquals(1, ePair.size());
		assertTrue(ePair.contains(Deck.C6) || ePair.contains(Deck.H6));

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

	public void test2AwayFlushExchange() {
		Hand flush2Away = new Hand("D5", "D6", "D10", "C5", "HA");
		List<Card> ex1 = flush2Away.exchange();

		assertEquals(2, ex1.size());
		assertTrue(ex1.contains(Deck.C5));
		assertTrue(ex1.contains(Deck.HA));
	}

	public void test1AwayFourOfKindExchange() {
		List<Card> ex = HandTest.threeOfKind.exchange();

		assertEquals(2, ex.size());
		assertTrue(ex.contains(Deck.C2));
		assertTrue(ex.contains(Deck.D5));
	}

	public void test3CardSequenceExchange() {
		Hand h2 = new Hand("H2", "D9", "H10", "DJ", "C5");
		List<Card> ex2 = h2.exchange();
		assertEquals(2, ex2.size());
		assertTrue(ex2.contains(Deck.H2));
		assertTrue(ex2.contains(Deck.C5));

		Hand h3 = new Hand("H2", "C3", "D10", "HJ", "CQ");
		List<Card> ex3 = h3.exchange();
		assertEquals(2, ex3.size());
		assertTrue(ex3.contains(Deck.H2));
		assertTrue(ex3.contains(Deck.C3));

		Hand h4 = new Hand("HA", "C2", "D3", "S9", "CA");
		List<Card> ex4 = h4.sequenceOf3().get();
		assertEquals(2, ex4.size());
		assertTrue(ex4.contains(Deck.S9));
		assertTrue(ex4.contains(Deck.CA) || ex4.contains(Deck.HA));

		Hand h5 = new Hand("S2", "C3", "HA", "DK", "CQ"); // toss 2,3 over K,Q
		List<Card> ex5 = h5.exchange();
		assertEquals(2, ex5.size());
		assertTrue(ex5.contains(Deck.S2) || ex5.contains(Deck.DK));
		assertTrue(ex5.contains(Deck.C3) || ex5.contains(Deck.CQ));
	}

	public void testTwoPairExchange() {
		List<Card> ex = HandTest.twoPair.exchange();

		assertEquals(1, ex.size());
		assertTrue(ex.contains(Deck.HK));
	}

	public void testSinglePairExchange() {
		List<Card> ex = HandTest.pair.exchange();

		assertEquals(3, ex.size());
		assertTrue(ex.contains(Deck.C4));
		assertTrue(ex.contains(Deck.C7));
		assertTrue(ex.contains(Deck.D3));
	}

	public void testNothingHandExchange() {
		List<Card> ex = HandTest.nothing.exchange();

		assertEquals(3, ex.size());
		assertTrue(ex.contains(Deck.D2));
		assertTrue(ex.contains(Deck.C4));
		assertTrue(ex.contains(Deck.H6));
	}

	public void testBadHandExchange() {
		List<Card> ex = HandTest.nothing.exchange();

		assertEquals(3, ex.size());
		assertTrue(ex.contains(Deck.D2));
		assertTrue(ex.contains(Deck.C4));
		assertTrue(ex.contains(Deck.H6));
	}
}
