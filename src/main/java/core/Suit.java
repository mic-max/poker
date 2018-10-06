package core;

public enum Suit {
	Club('C'), Diamond('D'), Heart('H'), Spade('S');
	
	private char suit;
	private int value;
	
	Suit(char suit) {
		this.suit = suit;
		int res;

		switch (suit) {
			case 'C': res = 1; break;
			case 'D': res = 2; break;
			case 'H': res = 3; break;
			case 'S': res = 4; break;
			default: throw new IllegalArgumentException();
		}

		this.value = res;
	}

	public static Suit getEnum(char value) {
		for (Suit v : values())
			if (v.suit == value) return v;
		throw new IllegalArgumentException();
	}
	
	@Override
	public String toString() {
		return "" + this.suit;
	}

	public int getValue() {
		return value;
	}
}
