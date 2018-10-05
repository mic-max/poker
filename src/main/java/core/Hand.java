package core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Hand {

	private List<Card> cards;
	private Map<Suit, Integer> suitMap;
	private Map<Rank, Integer> rankMap;
	
	public Hand(String... rankSuit) {
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
}
