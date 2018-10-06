package core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Hand implements Comparable<Hand> {
	
	private static final int STRAIGHT_FLUSH = 8000000;
	private static final int FOUR_OF_A_KIND = 7000000;
	private static final int FULL_HOUSE     = 6000000;
	private static final int FLUSH          = 5000000;
	private static final int STRAIGHT       = 4000000;
	private static final int SET            = 3000000;
	private static final int TWO_PAIRS      = 2000000;
	private static final int ONE_PAIR       = 1000000;

	private List<Card> cards;
	private Map<Suit, Integer> suitMap;
	private Map<Rank, Integer> rankMap;
	
	public Hand(String... rankSuit) {
		if (rankSuit.length != 5)
			throw new IllegalArgumentException();
		// check that all values in rankSuit are unique, else throw exception
		cards = new ArrayList<>(5);
		suitMap = new HashMap<>();
		rankMap = new HashMap<>();

		for (String rs : rankSuit) {
			Card c = new Card(rs);
			cards.add(c);
			
			Suit s = c.getSuit();
			Rank r = c.getRank();
			
			suitMap.put(s, suitMap.containsKey(s) ? suitMap.get(s) + 1 : 1);
			rankMap.put(r, rankMap.containsKey(r) ? rankMap.get(r) + 1 : 1);
		}

		Collections.sort(cards);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Hand))
			return false;
 
		Hand h = (Hand) obj;
		List<Card> hcards = h.getCards();
		return cards.containsAll(hcards)
			&& hcards.containsAll(cards)
			&& cards.size() == hcards.size();
	}
	
	@Override
	public String toString() {
		return cards.stream().map(n -> n.toString()).collect(Collectors.joining(" "));
	}

	public boolean hasTriple() {
		return rankMap.containsValue(3);
	}
	
	public boolean hasFourOfKind() {
		return rankMap.containsValue(4);
	}
	
	public boolean isFlush() {
		return suitMap.containsValue(5);
	}

	// make these return optionals? that if true have the cards associated with the hand?
	// could return all pairs, then test can check for 0, 1 or 2
	public boolean hasPair() {
		return Collections.frequency(rankMap.values(), 2) == 1;
	}

	public boolean isStraight() {
		boolean hasAce = cards.get(4).getRank() == Rank.Ace;
		int topCardValue = cards.get(hasAce ? 3 : 4).getRank().getValue();
		int lowCardValue = cards.get(0).getRank().getValue();
		
		if (hasAce)
			return lowCardValue == 2 && topCardValue == 5 // ace low
					|| lowCardValue == 10 && topCardValue == 13; // ace high
		
		// no ace
		return lowCardValue + 4 == topCardValue;
	}

	public boolean isFullHouse() {
		return rankMap.containsValue(2) && rankMap.containsValue(3);
	}

	public boolean hasTwoPairs() {
		return Collections.frequency(rankMap.values(), 2) == 2;
	}

	public boolean isRoyalFlush() {
		return isStraightFlush() && getHighCard().getRank() == Rank.Ace;
	}

	public boolean isStraightFlush() {
		return isStraight() && isFlush();
	}

	public Card getHighCard() {
		return cards.get(4);
	}
	
	private int scoreHand() {
		boolean flush = isFlush();
		boolean straight = isStraight();
		
		if (flush && straight) {
			return STRAIGHT_FLUSH + getHighCard().value();
			// TODO multiply rank by # of ranks + add suits number value
		} else if (hasFourOfKind()) {
			return FOUR_OF_A_KIND; // + the cards that formed the four of kind
		} else if (isFullHouse()) {
			// card2 is always part of the 3 pair when sorted
			return FULL_HOUSE + cards.get(2).value();
		} else if (flush) {
			return FLUSH + getHighCard().value();
		} else if (straight) {
			return STRAIGHT + getHighCard().value();
		} else if (hasTriple()) {
			return SET + cards.get(2).value();
		} else if (hasTwoPairs()) {
			return TWO_PAIRS; // TODO hipair*14^2 + lowPair*14 + otherCard
		} else if (hasPair()) {
			return ONE_PAIR; // pair*14 + hicard (thats not part of the pair)
		}
		
		return getHighCard().value();
	}

	@Override
	public int compareTo(Hand h) {
		// throw error if hands contain any of the same cards
		return scoreHand() - h.scoreHand();
	}
}
