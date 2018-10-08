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
	
	static Hand ops, aip;
	
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
		
		ops = new Hand(cards.subList(0, 5).toArray(new String[5]));
		aip = new Hand(cards.subList(5, 10).toArray(new String[5]));
		
		aip.exchange();
	}

	public static void main(String[] args) {
		if (args.length != 1) 
			throw new IllegalArgumentException();

		List<String> lines = loadInputs(args[0]);
		
		for (String line: lines) {
			processLine(line);
		}
		
		String discard = "todo";
		String pickup  = "todo";
		boolean aipwin = false;
		
		System.out.println("Hand to beat: " + ops);
		System.out.println("AIP Hand    : " + aip);
		System.out.println("AIP Discards: " + discard + " --> " + pickup);
		System.out.println("Winner is   : " + (aipwin ? "AIP" : "opponent"));
	}
}
