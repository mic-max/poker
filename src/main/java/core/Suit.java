package core;

public enum Suit {
	Club('C'), Diamond('D'), Heart('H'), Spade('S');
	
	private char suit;
	
	Suit(char suit) {
		this.suit = suit;
	}

	public static Suit getEnum(char value) {
		for (Suit v : values())
			if (v.suit == value) return v;
		throw new IllegalArgumentException();
	}
}
