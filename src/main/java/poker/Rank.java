package poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
			if (v.rank.equals(value))
				return v;
		throw new IllegalArgumentException();
	}
	
	public static List<Rank> getRandom(int n) {
		if (n <= 0 || n > values().length)
			return null; // Or throw an illegal argument exception
		
		List<Rank> list = Arrays.asList(values());
		Collections.shuffle(list);
		
		return list.stream().limit(n).collect(Collectors.toList());
	}
	
	public static List<Rank> getVals() {
		return Arrays.asList(values());
	}

	public static List<Rank> getRandom(int n, Rank minRank) {
		if (n <= 0 || n > values().length)
			return null; // Or throw an illegal argument exception
		
		List<Rank> list = getVals().stream().filter(r -> r.getValue() >= minRank.getValue()).collect(Collectors.toList());
		Collections.shuffle(list, PokerGame.RANDOM);
		
		return list.stream().limit(n).collect(Collectors.toList());
	}
}
