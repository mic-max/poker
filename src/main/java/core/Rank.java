package core;

import java.util.stream.IntStream;

public enum Rank {
	Two("2"), Three("3"), Four("4"), Five("5"), Six("6"), Seven("7"), Eight("8"),
	Nine("9"), Ten("10"), Jack("J"), Queen("Q"), King("K"), Ace("A");
	
	private String rank;
	private int value;
	
	Rank(String rank) {
		this.rank = rank;
		
		int res;
		try {
			res = Integer.parseInt(rank);
		} catch (NumberFormatException e) {
			switch (rank) {
				case "J": res = 11; break;
				case "Q": res = 12; break;
				case "K": res = 13; break;
				case "A": res = 14; break;
				default: throw new IllegalArgumentException();
			}
		}
		this.value = res;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return this.rank;
	}
	
	public static Rank getEnum(String value) {
		for (Rank v : values())
			if (v.rank.equals(value)) return v;
		throw new IllegalArgumentException();
	}
}
