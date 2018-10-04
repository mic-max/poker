package core;

public enum Rank {
	Two("2"), Three("3"), Four("4"), Five("5"), Six("6"), Seven("7"), Eight("8"),
	Nine("9"), Ten("10"), Jack("J"), Queen("Q"), King("K"), Ace("A");
	
	private String rank;
	
	Rank(String rank) {
		this.rank = rank;
	}
	
	public static Rank getEnum(String value) {
		for (Rank v : values())
			if (v.rank.equals(value)) return v;
		throw new IllegalArgumentException();
	}
}
