package core;

import java.util.ArrayList;
import java.util.List;

public class Deck {

	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>(52);
		for (Rank r : Rank.values()) {
			for (Suit s : Suit.values())
				cards.add(new Card(s, r));
		}
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public int size() {
		return cards.size();
	}
}
