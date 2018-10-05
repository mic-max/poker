package core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Hand {

	private List<Card> cards;
	private Map<Suit, Integer> suitMap;
	private Map<Rank, Integer> rankMap;
	
	public Hand(String... rankSuit) {
		if (rankSuit.length != 5)
			throw new IllegalArgumentException();
		cards = new ArrayList<>(5);
		suitMap = new HashMap<>(5);
		rankMap = new HashMap<>(5);

		for (String rs : rankSuit) {
			Card c = new Card(rs);
			cards.add(c);
			
			Suit s = c.getSuit();
			Rank r = c.getRank();
			
			suitMap.put(s, suitMap.containsKey(s) ? suitMap.get(s) + 1 : 1);
			rankMap.put(r, rankMap.containsKey(r) ? rankMap.get(r) + 1 : 1);
		}
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
		return rankMap.containsValue(2);
	}

	public boolean isStraight() {
		Collections.sort(cards);
		
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
		return rankMap.values().containsAll(Arrays.asList(2, 2));
	}
}