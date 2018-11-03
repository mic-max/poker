package poker;

public enum PokerHand {
	RoyalFlush(9), StraightFlush(8), FourOfaKind(7), FullHouse(6),
	Flush(5), Straight(4), Set(3), TwoPair(2), Pair(1), HighCard(0);

	private int value;
	private String name;

	PokerHand(int value) {
		this.value = value * 1_000_000;
		this.name = value == 7 ? "Four of a Kind" : name().replaceAll("(.)([A-Z])", "$1 $2");
	}

	public int value() {
		return value;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public static PokerHand getEnum(String value) {
		for (PokerHand v : values())
			if (v.name.equals(value))
				return v;
		throw new IllegalArgumentException();
	}
}
