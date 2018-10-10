package core;

import junit.framework.TestCase;

public class HandCompareSameTest extends TestCase {
	
	public void testCompareHighCards() {
		Hand highAce = new Hand("SA", "H10", "C5", "C2", "D9");
		Hand highQueen = new Hand("SQ", "DJ", "D4", "H7", "S3");
		Hand highSeven = new Hand("S2", "C3", "H4", "C7", "D6");

		assertTrue(HandTest.nothing.compareTo(highAce) < 0);
		assertTrue(HandTest.nothing.compareTo(highQueen) < 0);
		assertTrue(HandTest.nothing.compareTo(highSeven) > 0);
	}
	
	public void testCompareSinglePairs() {
		Hand pairAce = new Hand("SA", "HA", "C5", "C2", "D9");
		Hand pairQueen = new Hand("CQ", "DQ", "D2", "H7", "S3");
		Hand pairTwo = new Hand("S2", "H2", "H4", "CJ", "D6");

		assertTrue(HandTest.pair.compareTo(pairAce) < 0);
		assertTrue(HandTest.pair.compareTo(pairQueen) > 0);
		assertTrue(HandTest.pair.compareTo(pairTwo) > 0);
	}
	
	public void testCompareTwoPairs() {
		Hand pairTwoSix = new Hand("H2", "C2", "S6", "H6", "HA");
		Hand pairThreeFive = new Hand("D3", "S3", "C5", "D5", "HA");
		Hand pairQueenAce = new Hand("DQ", "SQ", "CA", "H2", "HA");
		
		assertTrue(HandTest.twoPair.compareTo(pairTwoSix) < 0);
		assertTrue(HandTest.twoPair.compareTo(pairThreeFive) > 0);
		assertTrue(HandTest.twoPair.compareTo(pairQueenAce) < 0);
	}
	
	public void testCompareThreeOfAKinds() {
		Hand threeFives = new Hand("S5", "C5", "H5", "CJ", "SA");
		Hand threeAces = new Hand("SA", "CA", "DA", "C4", "C3");
		
		assertTrue(HandTest.threeOfKind.compareTo(threeFives) > 0);
		assertTrue(HandTest.threeOfKind.compareTo(threeAces) < 0);
	}
	
	public void testCompareStraights() {
		Hand stSix = new Hand("C2", "H3", "H4", "D5", "C6");
		Hand stSeven = new Hand("C3", "D4", "S5", "S6", "S7");
		Hand stQueen = new Hand("CQ", "DJ", "H9", "D10", "C8");
		
		assertTrue(HandTest.straight.compareTo(stSix) > 0);
		assertTrue(HandTest.straight.compareTo(stSeven) < 0);
		assertTrue(HandTest.straight.compareTo(stQueen) < 0);
	}
	
	public void testCompareFlushes() {
		Hand flHeart = new Hand("HK", "HJ", "H9", "H4", "H6");
		Hand flHeart2 = new Hand("SK", "SJ", "S9", "S4", "S5");
		Hand flClub = new Hand("CA", "CJ", "C3", "C9", "C10");
		
		assertTrue(flHeart.compareTo(HandTest.flush) > 0);
		assertTrue(flHeart2.compareTo(HandTest.flush) < 0); // DK", "DJ", "D9", "D4", "D6
		assertTrue(flClub.compareTo(HandTest.flush) > 0);
	}
	
	public void testCompareFullHouses() {
		Hand fhTwo = new Hand("S2", "C2", "H2", "DA", "CA");
		Hand fhTen = new Hand("S10", "C10", "H10", "D2", "C2");
		
		assertTrue(HandTest.fullHouse.compareTo(fhTwo) > 0);
		assertTrue(HandTest.fullHouse.compareTo(fhTen) < 0);
	}
	
	public void testCompareFourOfAKinds() {
		Hand fourSix = new Hand("S6", "C6", "D6", "H6", "SA");
		Hand fourJack = new Hand("SJ", "CJ", "DJ", "HJ", "C10");
		
		assertTrue(HandTest.fourOfKind.compareTo(fourSix) > 0);
		assertTrue(HandTest.fourOfKind.compareTo(fourJack) < 0);
	}
	
	public void testCompareStraightFlushes() {
		Hand sfAce = new Hand("CA", "CK", "CQ", "C10", "CJ");
		Hand sfFive = new Hand("HA", "H2", "H3", "H4", "H5");
		Hand sfQueen = new Hand("C8", "C9", "C10", "CJ", "CQ");
		
		assertTrue(sfAce.compareTo(HandTest.straightFlush) > 0);
		assertTrue(HandTest.straightFlush.compareTo(sfFive) > 0);
		assertTrue(sfQueen.compareTo(HandTest.straightFlush) < 0);
	}
	
	public void testCompareRoyalFlushes() {
		Hand rfSpade = new Hand("SA", "SK", "SQ", "SJ", "S10");
		Hand rfClub = new Hand("CA", "CK", "CQ", "CJ", "C10");
		Hand rfDiamond = new Hand("DA", "DK", "DQ", "DJ", "D10");

		assertTrue(rfSpade.compareTo(HandTest.royalFlush) > 0);
		assertTrue(HandTest.royalFlush.compareTo(rfDiamond) > 0);
		assertTrue(rfDiamond.compareTo(rfClub) > 0);
	}

}
