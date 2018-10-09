package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PokerGame {

	private static List<String> loadInputs(String filename) {
		List<String> lines = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
			lines = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	private static void processLine(String s) {
		List<String> cards = Arrays.asList(s.split(" "));

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

		System.out.printf("Winner is   : %-8s '%s'\n", aipwin ? "AIP" : "Opponent", aipwin ? aip.handName : ops.handName);
		System.out.println("--------------------------------");
	}

	public static void main(String[] args) {
		if (args.length != 1)
			throw new IllegalArgumentException();

		List<String> lines = loadInputs(args[0]);

		for (String line : lines) {
			processLine(line);
		}
	}
}
