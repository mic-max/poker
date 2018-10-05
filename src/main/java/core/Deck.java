package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<>(52);
		for (Rank r : Rank.values()) {
			for (Suit s : Suit.values())
				cards.add(new Card(s, r));
		}
		Collections.shuffle(cards);
	}
	
	public Card dealCard() {
		return cards.remove(cards.size() - 1);
	}
	
	public Card returnCard(Card card) {
		Card newCard = dealCard();
		cards.add(card);
		return newCard;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public int size() {
		return cards.size();
	}
}
