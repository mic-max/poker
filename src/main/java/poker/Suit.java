package poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public static List<Suit> getRandom(int n) {
		if (n <= 0 || n > values().length)
			return null; // Or throw an illegal argument exception
		
		List<Suit> list = Arrays.asList(values());
		Collections.shuffle(list, PokerGame.RANDOM);
		
		return list.stream().limit(n).collect(Collectors.toList());
	}
	
	public static List<Suit> getVals() {
		return Arrays.asList(values());
	}
}
