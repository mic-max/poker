package core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Hand implements Comparable<Hand> {

	private static final int STRAIGHT_FLUSH = 8000000;
	private static final int FOUR_OF_A_KIND = 7000000;
	private static final int FULL_HOUSE     = 6000000;
	private static final int FLUSH          = 5000000;
	private static final int STRAIGHT       = 4000000;
	private static final int SET            = 3000000;
	private static final int TWO_PAIRS      = 2000000;
	private static final int ONE_PAIR       = 1000000;

	private List<Card> cards;
	private Map<Suit, Integer> suitMap;
	private Map<Rank, Integer> rankMap;

	public Hand(String... rankSuit) {
		if (rankSuit.length != 5)
			throw new IllegalArgumentException();
		// check that all values in rankSuit are unique, else throw exception
		cards = new ArrayList<>(5);
		suitMap = new HashMap<>();
		rankMap = new HashMap<>();

		for (String rs : rankSuit) {
			Card c = new Card(rs);
			cards.add(c);

			Suit s = c.getSuit();
			Rank r = c.getRank();

			suitMap.put(s, suitMap.containsKey(s) ? suitMap.get(s) + 1 : 1);
			rankMap.put(r, rankMap.containsKey(r) ? rankMap.get(r) + 1 : 1);
		}

		Collections.sort(cards);
	}

	public List<Card> getCards() {
		return cards;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Hand))
			return false;

		Hand h = (Hand) obj;
		List<Card> hcards = h.getCards();
		return cards.containsAll(hcards) && hcards.containsAll(cards) && cards.size() == hcards.size();
	}

	@Override
	public String toString() {
		return cards.stream().map(Card::toString).collect(Collectors.joining(" "));
	}

	public boolean hasTriple() {
		return rankMap.containsValue(3);
	}

	public boolean hasFourOfKind() {
		return rankMap.containsValue(4);
	}

	public boolean isFlush() {
		return suitMap.containsValue(5);
	}

	// off should only realistically be 1-3d
	public Optional<Suit> isNAwayFlush(int off) {
		return suitMap.entrySet().stream().filter(entry -> entry.getValue() == (5 - off)).map(Entry::getKey).findAny();
	}

	// Returns a list of cards that are preventing the straight
	public Optional<List<Card>> is1AwayStraight() {
		Hand h = new Hand(toString().split(" "));
		// check if its already a flush ?

		for (int i = 0; i < cards.size(); i++) {
			for (Rank r : Rank.values()) {
				Card old = h.swap(i, new Card(Suit.Spade, r));

				if (isFlush())
					return Optional.of(Collections.singletonList(old));
			}
		}

		return Optional.empty();
	}

	private void addCard(Card c) {
		cards.add(c);

		Suit s = c.getSuit();
		Rank r = c.getRank();
		suitMap.put(s, suitMap.containsKey(s) ? suitMap.get(s) + 1 : 1);
		rankMap.put(r, rankMap.containsKey(r) ? rankMap.get(r) + 1 : 1);
	}

	private Card removeCard(int index) {
		Card ret = cards.remove(index);

		// if value == 1, remove from map

		rankMap.put(ret.getRank(), rankMap.get(ret.getRank()) - 1);
		suitMap.put(ret.getSuit(), suitMap.get(ret.getSuit()) - 1);

		return ret;
	}

	// take an array of indices, sort in descinding order so index of cards to
	// rmeove doesnt change
	private Card swap(int i, Card card) {
		Card ret = removeCard(i);
		addCard(card);

		Collections.sort(cards);

		return ret;
	}

	public void swap(List<Card> remove, List<Card> add) {
//		if (remove.size() != add.size())
//			throw new IllegalArgumentException();

		for (Card r : remove)
			removeCard(cards.indexOf(r));

		for (Card a : add)
			addCard(a);

		Collections.sort(cards);
	}

	// make these return optionals? that if true have the cards associated with the
	// hand?
	// could return all pairs, then test can check for 0, 1 or 2
	public boolean hasPair() {
		return Collections.frequency(rankMap.values(), 2) == 1;
	}

	public boolean isStraight() {
		boolean hasAce = cards.get(4).getRank() == Rank.Ace;
		Rank topCardRank = cards.get(hasAce ? 3 : 4).getRank();
		Rank lowCardRank = cards.get(0).getRank();

		if (hasAce)
			return lowCardRank == Rank.Two && topCardRank == Rank.Five // ace low
					|| lowCardRank == Rank.Ten && topCardRank == Rank.King; // ace high

		// no ace
		return lowCardRank.getValue() + 4 == topCardRank.getValue();
	}

	public boolean isFullHouse() {
		return rankMap.containsValue(2) && rankMap.containsValue(3);
	}

	public boolean hasTwoPairs() {
		return Collections.frequency(rankMap.values(), 2) == 2;
	}

	public boolean isRoyalFlush() {
		return isStraightFlush() && getHighCard().getRank() == Rank.Ace;
	}

	public boolean isStraightFlush() {
		return isStraight() && isFlush();
	}

	public Card getHighCard() {
		Card top2 = cards.get(3);
		if (isStraight() && top2.getRank() == Rank.Five)
			return top2;

		return cards.get(4);
	}

	private int scoreHand() {
		boolean flush = isFlush();
		boolean straight = isStraight();

		if (flush && straight) {
			return STRAIGHT_FLUSH + getHighCard().value();
		} else if (hasFourOfKind()) {
			Card top = cards.get(4);
			if (cards.get(0).getRank() == cards.get(3).getRank())
				top = cards.get(0);
			return FOUR_OF_A_KIND + top.value();
		} else if (isFullHouse()) {
			return FULL_HOUSE + cards.get(2).value();
		} else if (flush) {
			int score = cards.get(0).getSuit().getValue();
			// TODO refactor this
			for (int i = 4; i >= 0; i--)
				score += cards.get(i).getRank().getValue() * Rank.Ace.getValue() * (i + 2);
			return FLUSH + score;
		} else if (straight) {
			return STRAIGHT + getHighCard().value();
		} else if (hasTriple()) {
			return SET + cards.get(2).value();
		} else if (hasTwoPairs()) {
			Card top = cards.get(4);
			if (top.getRank() != cards.get(3).getRank())
				top = cards.get(3);
			return TWO_PAIRS + top.value();
		} else if (hasPair()) {
			Card top = cards.get(4);

			for (int i = 3; top.getRank() != cards.get(i).getRank(); i--)
				top = cards.get(i);
			return ONE_PAIR + top.value();
		}

		return getHighCard().value();
	}

	@Override
	public int compareTo(Hand h) {
		List<Card> common = new ArrayList<>(cards);
		common.retainAll(h.getCards());

		if (common.size() != 0)
			throw new IllegalStateException("Cannot compare hands with the same card: " + common);

		return scoreHand() - h.scoreHand();
	}

	// Returns a list of cards to remove from the players hand
	public List<Card> exchange() {
		int k = scoreHand();

		if (k >= STRAIGHT) {
			return Collections.emptyList();
//		} else if (is1AwayRoyalFlush().isPresent()) {
//			return Collections.emptyList(); // todo
//		} else if (is1AwayStraightFlush().isPresent()) {
//			return Collections.emptyList(); // todo
//		} else if (is1AwayFullHouse().isPresent()) {
//			return Collections.emptyList(); // todo
		} else if (isNAwayFlush(1).isPresent()) {
			Suit s = isNAwayFlush(1).get();
			return cards.stream().filter(c -> c.getSuit() != s).collect(Collectors.toList());
		} else if (is1AwayStraight().isPresent()) {
			return is1AwayStraight().get();
		} else if (isNAwayFlush(2).isPresent()) {
			Suit s = isNAwayFlush(2).get();
			return cards.stream().filter(c -> c.getSuit() != s).collect(Collectors.toList());
		} else if (hasTriple()) {
			Rank r = cards.get(2).getRank();
			return cards.stream().filter(c -> c.getRank() != r).collect(Collectors.toList());
		} else if (is2AwayStraight().isPresent()) {
			return Collections.emptyList(); // todo
		} else if (hasPair()) {
			return Collections.emptyList(); // todo
		} else {
			return Arrays.asList(cards.get(3), cards.get(4)); // keep top 2 cards
		}
	}

	public Optional<Suit> is2AwayStraight() {
		return Optional.empty();
	}

	public Optional<Card> is1AwayFullHouse() {
		// get the 2 pair cards, filter those out return other card
		return Optional.empty();
	}

	public Optional<Card> is1AwayStraightFlush() {
		boolean flush = isFlush();
		boolean straight = isStraight();

		if (flush) {

		} else if (straight) {

		}

		return Optional.empty();
	}

	public Optional<Card> is1AwayRoyalFlush() {
		// 1 away from a straight and that card is the wrong suit too
		// have a flush, 1 away from straight
		// have a straight, one suit is incorrect
		return Optional.empty();
	}
}
