package core;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;

public class Hand {

	private List<Card> cards;
	
	public Hand(String... rankSuit) {
		cards = new ArrayList<>();
		for (String rs : rankSuit)
			cards.add(new Card(rs));
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
}
