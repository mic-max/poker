package core;

public enum PokerHand {
	RoyalFlush(9), StraightFlush(8), FourOfaKind(7), FullHouse(6),
	Flush(5), Straight(4), Set(3), TwoPair(2), Pair(1), HighCard(0);

	private int value;

	PokerHand(int value) {
		this.value = value * 1_000_000;
	}

	public int value() {
		return value;
	}

	@Override
	public String toString() {
		return name().replaceAll("(.)([A-Z])", "$1 $2");
	}
}
