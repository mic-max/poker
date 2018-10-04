package core;

public class Card {

	private Suit suit;
	private Rank rank;
	
	public Card(String suitRank) {
		char suit = suitRank.charAt(0);
		String rank = suitRank.substring(1);
		
		this.suit = Suit.getEnum(suit);
		this.rank = Rank.getEnum(rank);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Card))
			return false;
		
		Card c = (Card) obj;
		return suit == c.suit && rank == c.rank;
	}
}
