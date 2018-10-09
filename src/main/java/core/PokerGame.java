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
		
		Hand ops = new Hand(cards.get(0), cards.get(1), cards.get(2), cards.get(3), cards.get(4));
		Hand aip = new Hand(cards.get(5), cards.get(6), cards.get(7), cards.get(8), cards.get(9));

		Deck deck = new Deck();
		deck.remove(ops.getCards());
		deck.remove(aip.getCards());
		
		System.out.println("Hand to beat: " + ops);
		System.out.println("AIP Hand    : " + aip);
		List<Card> discard = aip.exchange();
		List<Card> pickup = deck.deal(discard.size());
		
		
		if (discard.size() == 0) {
			System.out.println("AIP Discards: Nothing");
		} else {			
			System.out.println("AIP Discards: " + discard + " --> " + pickup);
		}
		
		aip.swap(discard, pickup);
		boolean aipwin = aip.compareTo(ops) > 0;
		
		System.out.println("Winner is   : " + (aipwin ? "AIP" : "Opponent"));
		System.out.println("--------------------------------");
	}

	public static void main(String[] args) {
		if (args.length != 1) 
			throw new IllegalArgumentException();

		List<String> lines = loadInputs(args[0]);
		
		for (String line: lines) {
			processLine(line);
		}
	}
}