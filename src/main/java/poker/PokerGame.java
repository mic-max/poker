package poker;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class PokerGame {
	
	public static Random RANDOM = new Random(201811613);

	private static List<String> loadInputs(String filename) {
		List<String> lines = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
			lines = br.lines().filter(l -> l.trim().length() > 0).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	private static void processLine(String s) {
		List<String> cards = Arrays.asList(s.split("\\s+"));

		Hand ops = new Hand(cards.subList(0, 5));
		Hand aip = new Hand(cards.subList(5, 10));
		List<Card> deck = cards.subList(10, cards.size()).stream().map(Card::new).collect(Collectors.toList());

		System.out.println("Hand to beat: " + ops);
		System.out.println("AIP Hand    : " + aip);
		List<Card> discard = aip.exchange();
		List<Card> pickup = deck.subList(0, discard.size());

		if (discard.size() == 0) {
			System.out.println("AIP Discards: Nothing");
		} else {
			System.out.println("AIP Discards: " + discard + " --> " + pickup);
		}

		aip.swap(discard, pickup);
		boolean aipwin = aip.compareTo(ops) > 0;

		System.out.printf("Winner is   : %-8s '%s'\n", aipwin ? "AIP" : "Opponent", aipwin ? aip.getPh() : ops.getPh());
		System.out.println("--------------------------------");
	}

	public static void main(String[] args) {
		if (args.length != 1)
			throw new IllegalArgumentException();

		List<String> lines = loadInputs(args[0]);

		for (String line : lines) {
			if (!line.startsWith("|"))
				processLine(line);
		}
	}
}
