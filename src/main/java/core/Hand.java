package core;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

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
	
	public Hand(String... rankSuit) {
		this(Arrays.asList(rankSuit));
	}

	public Hand(List<String> rankSuit) {
		if (rankSuit.size() != 5)
			throw new IllegalArgumentException();
		if (new HashSet<String>(rankSuit).size() != 5)
			throw new IllegalArgumentException();

		cards = rankSuit.stream().map(rs -> new Card(rs)).collect(Collectors.toList());
		Collections.sort(cards);
	}

	public Hand copyHand() {
		List<String> rs  = cards.stream().map(c -> c.toString()).collect(Collectors.toList());
		return new Hand(rs);
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
	
	private Map<Rank, List<Card>> rMap() {
		return cards.stream()
				.collect(Collectors.groupingBy(Card::getRank));
	}
	
	private Map<Suit, List<Card>> sMap() {
		return cards.stream()
				.collect(Collectors.groupingBy(Card::getSuit));
	}

	private boolean hasRankN(int n) {
		return !rMap().values().stream().filter(l -> l.size() == n).collect(Collectors.toSet()).isEmpty();
	}

	public boolean hasFourOfKind() {
		return hasRankN(4);
	}
	
	public boolean hasSet() {
		return hasRankN(3);
	}
	
	private boolean hasSuitN(int n) {
		return !sMap().values().stream()
			.filter(l -> l.size() == n)
			.collect(Collectors.toSet()).isEmpty();
	}
	
	public boolean isFlush() {
		return hasSuitN(5);
	}
	
	public Optional<Suit> isFlushSuitN(int n) {
		return sMap().entrySet().stream()
			.filter(e -> e.getValue().size() == 5 - n)
			.map(Entry::getKey).findFirst();
	}
	
	// TODO consider switching to Optional<List<Card>>
	public List<Card> isAwayFlushN(int n) {
		Optional<Suit> s = isFlushSuitN(n);
		if (!s.isPresent())
			return Collections.emptyList();
		
		return cards.stream()
			.filter(c -> c.getSuit() != s.get())
			.collect(Collectors.toList());
	}

	// Returns a list of cards that are preventing the straight
	public List<Card> is1AwayStraight() {
		if (isStraight())
			return Collections.emptyList();
		

		for (Card c : getCards()) {
			for (Rank r : Rank.values()) {
				Hand h = copyHand();
				h.swap(c, new Card(Suit.Spade, r));

				if (h.isStraight())
					return Collections.singletonList(c);
			}
		}

		return Collections.emptyList();
	}

	private void addCard(Card c) {
		cards.add(c);
	}

	private void removeCard(Card c) {
		cards.remove(c);
	}

	// take an array of indices, sort in descinding order so index of cards to
	// rmeove doesnt change
	private void swap(Card rem, Card add) {
		removeCard(rem);
		addCard(add);

		Collections.sort(cards);
	}

	public void swap(List<Card> remove, List<Card> add) {
		if (remove.size() != add.size())
			throw new IllegalArgumentException();

		for (Card r : remove)
			removeCard(r);

		for (Card a : add)
			addCard(a);

		Collections.sort(cards);
	}

	// make these return optionals? that if true have the cards associated with the
	// hand?
	// could return all pairs, then test can check for 0, 1 or 2
	public boolean hasPair() {
		return rMap().values().stream().filter(l -> l.size()  == 2).collect(Collectors.toSet()).size() == 1;
	}

	public boolean isStraight() {
		Optional<List<Card>> u = rMap().values().stream().filter(l -> l.size() > 1).findAny();
		if (u.isPresent())
			return false;
		
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
		return hasRankN(2) && hasRankN(3);
	}

	public boolean hasTwoPairs() {
		return rMap().values().stream().filter(l -> l.size()  == 2).collect(Collectors.toSet()).size() == 2;
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
		} else if (hasSet()) {
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
		if (scoreHand() >= STRAIGHT) {
			return Collections.emptyList();
//		} else if (hasSet() || hasTwoPairs()) {
//			return is1AwayFullHouse();
		} else if (!isAwayFlushN(1).isEmpty()) {
			return isAwayFlushN(1);
		} else if (!is1AwayStraight().isEmpty()) {
			return is1AwayStraight();
		} else if (!isAwayFlushN(2).isEmpty()) {
			return isAwayFlushN(2);
		} else if (hasSet()) {
			return is1AwayFourOfKind();
		} else if (hasTwoPairs() || hasPair()) {
			return nonPairCards();
		} else {
			return Arrays.asList(cards.get(0), cards.get(1), cards.get(2));
		}
	}

//	private List<Card> is1AwayFullHouse() {
//		if (hasTwoPairs()) {
//			return rMap().values().stream().filter(l -> l.size() != 2).flatMap(List::stream).collect(Collectors.toList());
//		} else { // set (3 of a kind)
//			// TODO refactor, make a judgement on which of the 2 cards to remove ?
//			return Arrays.asList(rMap().values().stream().filter(l -> l.size() != 3).flatMap(List::stream).collect(Collectors.toList()).get(0));
//		}
//	}

	private List<Card> nonPairCards() {
		return rMap().values().stream()
			.filter(l -> l.size() != 2)
			.flatMap(List::stream)
			.collect(Collectors.toList());
	}

	private List<Card> is1AwayFourOfKind() {
		return rMap().values().stream()
			.filter(l -> l.size() != 3)
			.flatMap(List::stream)
			.collect(Collectors.toList());
	}

	public List<Card> is1AwayStraightFlush() {
		List<Card> straight = is1AwayStraight();
		List<Card> flush = isAwayFlushN(1);
		
		if (!straight.isEmpty() && !flush.isEmpty() && straight.size() == 1 && straight.equals(flush))
			return straight;
	
		return Collections.emptyList();
	}

	public List<Card> is1AwayRoyalFlush() {
		return is1AwayStraightFlush();
	}
}
