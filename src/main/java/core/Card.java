package core;

public class Card implements Comparable<Card> {

	private Suit suit;
	private Rank rank;
	
	public Card(String suitRank) {
		char suit = suitRank.charAt(0);
		String rank = suitRank.substring(1);

		this.suit = Suit.getEnum(suit);
		this.rank = Rank.getEnum(rank);
	}
	
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public int value() {
		return rank.getValue() * Rank.Ace.getValue() + suit.getValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Card))
			return false;
		
		Card c = (Card) obj;
		return suit == c.suit && rank == c.rank;
	}

	public int compareTo(Card c) {
		int rankCompare = rank.compareTo(c.rank);
		
		if (rankCompare == 0)
			return suit.compareTo(c.suit);
		return rankCompare;
	}
	
	@Override
	public String toString() {
		return "" + suit + rank;
	}
}
