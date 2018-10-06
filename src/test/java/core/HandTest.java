package core;

import junit.framework.TestCase;

public class HandTest extends TestCase {
	
	private static Hand nothing = new Hand("D2", "C4", "H6", "S8", "CQ"); // high queen

	private static Hand pair = new Hand("SQ", "HQ", "C4", "C7", "D3");
	private static Hand twoPair = new Hand("D2", "S2", "C6", "D6", "HK");
	private static Hand threeOfKind = new Hand("D10", "C10", "H10", "C2", "D5");
	private static Hand straight = new Hand("D3", "C4", "H5", "H6", "H7");
	private static Hand flush = new Hand("DK", "DJ", "D9", "D4", "D6");
	private static Hand fullHouse = new Hand("S4", "C4", "H4", "HJ", "CJ");
	private static Hand fourOfKind = new Hand("S7", "D7", "H7", "C7", "C2");
	private static Hand straightFlush = new Hand("D8", "D9", "D10", "DJ", "DQ");
	private static Hand royalFlush = new Hand("HA", "HK", "HQ", "HJ", "H10");

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
	
	public void testCompareSinglePairs() {
		Hand pairAce = new Hand("SA", "HA", "C5", "C2", "D9");
		Hand pairQueen = new Hand("CQ", "DQ", "D2", "H7", "S3");
		Hand pairTwo = new Hand("S2", "H2", "H4", "CJ", "D6");

		assertTrue(pair.compareTo(pairAce) < 0);
		assertTrue(pair.compareTo(pairQueen) > 0);
		assertTrue(pair.compareTo(pairTwo) > 0);
	}
	
	public void testCompareTwoPairs() {
		Hand pairTwoSix = new Hand("H2", "C2", "S6", "H6", "HA");
		Hand pairThreeFive = new Hand("D3", "S3", "C5", "D5", "HA");
		Hand pairQueenAce = new Hand("DQ", "SQ", "CA", "H2", "HA");
		
		assertTrue(twoPair.compareTo(pairTwoSix) < 0);
		assertTrue(twoPair.compareTo(pairThreeFive) > 0);
		assertTrue(twoPair.compareTo(pairQueenAce) < 0);
	}
	
	public void testCompareThreeOfAKinds() {
		Hand threeFives = new Hand("S5", "C5", "H5", "CJ", "SA");
		Hand threeAces = new Hand("SA", "CA", "DA", "C4", "C3");
		
		assertTrue(threeOfKind.compareTo(threeFives) > 0);
		assertTrue(threeOfKind.compareTo(threeAces) < 0);
	}
	
	public void testCompareStraights() {
		Hand stSix = new Hand("C2", "H3", "H4", "D5", "C6");
		Hand stSeven = new Hand("C3", "D4", "S5", "S6", "S7");
		Hand stQueen = new Hand("CQ", "DJ", "H9", "D10", "C8");
		
		assertTrue(straight.compareTo(stSix) > 0);
		assertTrue(straight.compareTo(stSeven) < 0);
		assertTrue(straight.compareTo(stQueen) < 0);
	}
	
	public void testCompareFlushes() {
		Hand flHeart = new Hand("HK", "HJ", "H9", "H4", "H6");
		Hand flHeart2 = new Hand("HK", "HJ", "H9", "H4", "H5");
		Hand flClub = new Hand("CA", "CJ", "C3", "C9", "C10");
		
		assertTrue(flHeart.compareTo(flush) > 0);
		assertTrue(flHeart2.compareTo(flush) < 0);
		assertTrue(flClub.compareTo(flush) > 0);
	}
	
	public void testCompareFullHouses() {
		Hand fhTwo = new Hand("S2", "C2", "H2", "DA", "CA");
		Hand fhTen = new Hand("S10", "C10", "H10", "D2", "C2");
		
		assertTrue(fullHouse.compareTo(fhTwo) > 0);
		assertTrue(fullHouse.compareTo(fhTen) < 0);
	}
	
	public void testCompareFourOfAKinds() {
		Hand fourSix = new Hand("S6", "C6", "D6", "H6", "SA");
		Hand fourJack = new Hand("SJ", "CJ", "DJ", "HJ", "C10");
		
		assertTrue(fourOfKind.compareTo(fourSix) > 0);
		assertTrue(fourOfKind.compareTo(fourJack) < 0);
	}
	
	public void testCompareStraightFlushes() {
		Hand sfAce = new Hand("CA", "CK", "CQ", "C10", "CJ");
		Hand sfFive = new Hand("HA", "H2", "H3", "H4", "H5");
		Hand sfQueen = new Hand("C8", "C9", "C10", "CJ", "CQ");
		
		assertTrue(sfAce.compareTo(straightFlush) > 0);
		assertTrue(straightFlush.compareTo(sfFive) > 0);
		assertTrue(sfQueen.compareTo(straightFlush) < 0);
	}
	
	public void testCompareRoyalFlushes() {
		Hand rfSpade = new Hand("SA", "SK", "SQ", "SJ", "S10");
		Hand rfClub = new Hand("CA", "CK", "CQ", "CJ", "C10");
		Hand rfDiamond = new Hand("DA", "DK", "DQ", "DJ", "D10");
		
		assertTrue(rfSpade.compareTo(royalFlush) > 0);
		assertTrue(royalFlush.compareTo(rfDiamond) > 0);
		assertTrue(rfDiamond.compareTo(rfClub) > 0);
	}
}
