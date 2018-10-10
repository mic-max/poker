package core;

import junit.framework.TestCase;

public class HandOneAwayTest extends TestCase {

	public void test1AwayFlush() {
		Hand s4h = new Hand("S3", "S5", "S8", "SK", "HA");
		Hand d5 = new Hand("D7", "D8", "D9", "D10", "DJ");
		Hand h3 = new Hand("H4", "H2", "HJ", "C8", "CA");

		assertTrue(s4h.is1AwayFlush().isPresent());
		assertFalse(d5.is1AwayFlush().isPresent());
		assertFalse(h3.is1AwayFlush().isPresent());
	}

	public void test2AwayFlush() {
		Hand s4h = new Hand("S3", "S5", "S8", "SK", "HA");
		Hand d5 = new Hand("D7", "D8", "D9", "D10", "DJ");
		Hand h3 = new Hand("H4", "H2", "HJ", "C8", "CA");

		assertFalse(s4h.is2AwayFlush().isPresent());
		assertFalse(d5.is2AwayFlush().isPresent());
		assertTrue(h3.is2AwayFlush().isPresent());
	}

	public void test1AwayStraight() {
		Hand s25 = new Hand("S2", "C3", "D4", "H5", "S7");
		Hand s10k = new Hand("C10", "HJ", "DQ", "SK", "C2");
		Hand s8q = new Hand("S8", "C9", "D10", "CJ", "HQ");
		Hand s1 = new Hand("S3", "C3", "D4", "H5", "S6");
		
		assertTrue(s25.is1AwayStraight().isPresent());
		assertTrue(s10k.is1AwayStraight().isPresent());
		assertFalse(s8q.is1AwayStraight().isPresent());
		assertTrue(s1.is1AwayStraight().isPresent());
	}

	public void test1AwayStraightFlush() {
		Hand h1 = new Hand("C2", "C3", "C4", "C5", "D9");
		Hand h2 = new Hand("C2", "C3", "H4", "C5", "D9");
		Hand h3 = new Hand("C2", "C3", "H4", "C5", "C6");

		assertTrue(h1.is1AwayStraightFlush().isPresent());
		assertFalse(h2.is1AwayStraightFlush().isPresent());
		assertTrue(h3.is1AwayStraightFlush().isPresent());
	}

	public void test1AwayRoyalFlush() {
		Hand h1 = new Hand("C2", "C3", "C4", "C5", "D9");
		Hand h2 = new Hand("C2", "C3", "C4", "C5", "CA");
		Hand h3 = new Hand("CA", "CK", "CQ", "CJ", "H6");
		Hand h4 = new Hand("S10", "SK", "SQ", "SJ", "S2");

		assertFalse(h1.is1AwayRoyalFlush().isPresent());
		assertFalse(h2.is1AwayRoyalFlush().isPresent());
		assertTrue(h3.is1AwayRoyalFlush().isPresent());
		assertTrue(h4.is1AwayRoyalFlush().isPresent());
	}

	public void test1AwayFourOfKind() {

	}

	public void testSequenceOf3() {

	}
}
