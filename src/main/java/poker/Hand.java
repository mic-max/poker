package poker;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

public class Hand implements Comparable<Hand> {

	private List<Card> cards;
	private PokerHand  ph;

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
		scoreHand();
	}

	public Hand(PokerHand ph, List<Suit> suits, List<Rank> ranks) {
		if (suits == null)
			suits = Suit.getRandom(2);
		final Suit s1 = suits.get(0);
		if (suits.size() == 1)
			suits = Arrays.asList(s1, Suit.getVals().stream().filter(s -> s != s1).findFirst().get());
		if (ranks == null) {
			if (ph == PokerHand.Straight || ph == PokerHand.StraightFlush)
				ranks = Rank.getRandom(2, Rank.Five);
			else
				ranks = Rank.getRandom(2);
		}
		final Rank r1 = ranks.get(0);
		if (ranks.size() == 1)
			ranks.add(Rank.getVals().stream().filter(r -> r != r1).findFirst().get());

		final Suit s2 = suits.get(1);
		final Rank r2 = ranks.get(1);

		switch (ph) {
		case RoyalFlush: {
			cards = Stream.of(Rank.Ace, Rank.King, Rank.Queen, Rank.Jack, Rank.Ten).map(rnk -> new Card(s1, rnk))
					.collect(Collectors.toList());
			break;
		}
		case StraightFlush: {
			int range = 5;
			if (r1 == Rank.Five)
				range = 4;

			int end = Rank.getVals().indexOf(r1 == Rank.Ace ? r2 : r1) + 1;
			cards = Rank.getVals().subList(end - range, end).stream().map(rnk -> new Card(s1, rnk))
					.collect(Collectors.toList());
			if (range == 4)
				cards.add(new Card(s1, Rank.Ace));
			break;
		}
		case FourOfaKind: {
			cards = Suit.getVals().stream().map(st -> new Card(st, r1)).collect(Collectors.toList());
			cards.add(new Card(s1, r2));
			break;
		}
		case FullHouse: {
			cards = Suit.getRandom(3).stream().map(st -> new Card(st, r1)).collect(Collectors.toList());
			cards.addAll(Suit.getRandom(2).stream().map(st -> new Card(st, r2)).collect(Collectors.toList()));
			break;
		}
		case Flush: {
			List<Rank> shufRank = Rank.getRandom(5); // TODO make sure it can't be a straight flush
			cards = shufRank.stream().map(rnk -> new Card(s1, rnk)).collect(Collectors.toList());
			break;
		}
		case Straight: {
			final Rank rnk;
			if (r1.getValue() < Rank.Six.getValue())
				rnk = Rank.getVals().stream().filter(r -> r.getValue() >= Rank.Six.getValue()).findFirst().get();
			else
				rnk = r1;

			cards = Rank.getVals().stream().filter(r -> r.getValue() < rnk.getValue()).sorted(Comparator.reverseOrder())
					.limit(4).map(r -> new Card(s2, r)).collect(Collectors.toList());

			cards.add(new Card(s1, rnk));
			break;
		}
		case Set: {

			cards = Suit.getVals().stream().limit(3).map(st -> new Card(st, r1)).collect(Collectors.toList());
			cards.addAll(Rank.getVals().stream().filter(rnk -> rnk != r1).limit(2).map(rnk -> new Card(Suit.Spade, rnk))
					.collect(Collectors.toList()));

			break;
		}
		case TwoPair: {
			cards = suits.stream().map(st -> new Card(st, r1)).collect(Collectors.toList());
			cards.addAll(suits.stream().map(st -> new Card(st, r2)).collect(Collectors.toList()));

			cards.add(new Card(
					Suit.getVals().stream().filter(st -> st != s1 && st != s2).collect(Collectors.toList()).get(0),
					Rank.getVals().stream().filter(rnk -> rnk != r1 && rnk != r2).collect(Collectors.toList()).get(0)));
			break;
		}
		case Pair: {
			cards = suits.stream().map(st -> new Card(st, r1)).collect(Collectors.toList());
			cards.addAll(Rank.getVals().stream().filter(rnk -> rnk != r1).limit(3).map(rnk -> new Card(Suit.Spade, rnk))
					.collect(Collectors.toList()));
			break;
		}
		case HighCard: {
			Rank rnk = r1;
			if (r1.getValue() <= Rank.Seven.getValue())
				rnk = Rank.getVals().stream().filter(r -> r.getValue() >= Rank.Eight.getValue()).findFirst().get();
			cards = Arrays.asList(new Card("S2"), new Card("C3"), new Card("D4"), new Card("H6"), new Card(s1, rnk));
			break;
		}
		}

		Collections.sort(cards);
		scoreHand();
	}

	public Hand(PokerHand ph) {
		this(ph, null, null);
	}

	public Hand(PokerHand ph, List<Suit> suits) {
		this(ph, suits, null);
	}

	public List<Card> getCards() {
		return cards;
	}

	public PokerHand getPh() {
		return ph;
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

	private Hand copyHand() {
		List<String> rs = cards.stream().map(c -> c.toString()).collect(Collectors.toList());
		return new Hand(rs);
	}

	private Map<Rank, List<Card>> rMap() {
		return cards.stream().collect(Collectors.groupingBy(Card::getRank));
	}

	private Map<Suit, List<Card>> sMap() {
		return cards.stream().collect(Collectors.groupingBy(Card::getSuit));
	}

	/*   */
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
		return !sMap().values().stream().filter(l -> l.size() == n).collect(Collectors.toSet()).isEmpty();
	}

	public boolean isFlush() {
		return hasSuitN(5);
	}

	private Optional<Suit> isFlushSuitN(int n) {
		return sMap().entrySet().stream().filter(e -> e.getValue().size() == 5 - n).map(Entry::getKey).findFirst();
	}

	private Optional<List<Card>> isAwayFlushN(int n) {
		Optional<Suit> s = isFlushSuitN(n);
		if (!s.isPresent())
			return Optional.empty();

		return Optional.of(cards.stream().filter(c -> c.getSuit() != s.get()).collect(Collectors.toList()));
	}

	public Optional<List<Card>> is1AwayFlush() {
		return isAwayFlushN(1);
	}

	public Optional<List<Card>> is2AwayFlush() {
		return isAwayFlushN(2);
	}

	// Returns a list of cards that are preventing the straight
	public Optional<List<Card>> is1AwayStraight() {
		if (isStraight())
			return Optional.empty();

		for (Card c : cards) {
			for (Rank r : Rank.values()) {
				Hand h = copyHand();
				h.swap(c, new Card(Suit.Spade, r));

				if (h.isStraight())
					return Optional.of(Collections.singletonList(c));
			}
		}

		return Optional.empty();
	}

	private void addCard(Card c) {
		cards.add(c);
	}

	private void removeCard(Card c) {
		cards.remove(c);
	}

	public void swap(Card rem, Card add) {
		swap(Arrays.asList(rem), Arrays.asList(add));
	}

	public void swap(List<Card> remove, List<Card> add) {
		if (remove.size() != add.size())
			throw new IllegalArgumentException();

		for (Card r : remove)
			removeCard(r);

		for (Card a : add)
			addCard(a);

		Collections.sort(cards);
		scoreHand();
	}

	public boolean hasPair() {
		return rMap().values().stream().filter(l -> l.size() == 2).collect(Collectors.toSet()).size() == 1;
	}

	// Returns cards that are not part of the sequence of 3, empty list if there is none
	public Optional<List<Card>> sequenceOf3() {
		Set<Integer> rSet = rMap().keySet().stream().map(Rank::getValue).collect(Collectors.toSet());

		for (int r : rSet) {
			if (r == Rank.Ace.getValue() && rSet.contains(Rank.Two.getValue())
					&& rSet.contains(Rank.Three.getValue())) {
				List<Card> list = Stream
						.concat(rMap().entrySet().stream()
								.filter(e -> e.getKey().getValue() > Rank.Three.getValue() && e.getKey() != Rank.Ace)
								.map(e -> e.getValue().get(0)), rMap().get(Rank.Ace).stream().skip(1))
						.collect(Collectors.toList());
				return Optional.of(list);
			} else if (rSet.contains(r + 1) && rSet.contains(r + 2)) {
				List<Card> list = rMap().entrySet().stream()
						.filter(e -> e.getKey().getValue() < r || e.getKey().getValue() > r + 2)
						.map(e -> e.getValue().get(0)).collect(Collectors.toList());
				return Optional.of(list);
			}
		}

		return Optional.empty();
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
		return rMap().values().stream().filter(l -> l.size() == 2).collect(Collectors.toSet()).size() == 2;
	}

	public boolean isRoyalFlush() {
		return getHighCard().getRank() == Rank.Ace && isStraightFlush();
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

		int extra = 0;

		if (flush && straight) {
			Card hiCard = getHighCard();
			ph = hiCard.getRank() == Rank.Ace ? PokerHand.RoyalFlush : PokerHand.StraightFlush;
			extra = getHighCard().value();
		} else if (hasFourOfKind()) {
			Card top = cards.get(4);
			if (cards.get(0).getRank() == cards.get(3).getRank())
				top = cards.get(0);
			ph = PokerHand.FourOfaKind;
			extra = top.value();
		} else if (isFullHouse()) {
			ph = PokerHand.FullHouse;
			extra = cards.get(2).value();
		} else if (flush) {
			extra = cards.get(0).getSuit().getValue();
			for (int i = 4; i >= 0; i--)
				extra += cards.get(i).getRank().getValue() * Rank.Ace.getValue() * 4 * (i + 2);
			ph = PokerHand.Flush;
		} else if (straight) {
			ph = PokerHand.Straight;
			extra = getHighCard().value();
		} else if (hasSet()) {
			ph = PokerHand.Set;
			extra = cards.get(2).value();
		} else if (hasTwoPairs()) {
			Card top = cards.get(4);
			if (top.getRank() != cards.get(3).getRank())
				top = cards.get(3);
			ph = PokerHand.TwoPair;
			extra = top.value();
		} else if (hasPair()) {
			Card top = cards.get(4);

			for (int i = 3; top.getRank() != cards.get(i).getRank(); i--)
				top = cards.get(i);
			ph = PokerHand.Pair;
			extra = top.value();
		} else {
			ph = PokerHand.HighCard;
			extra = getHighCard().value();
		}

		return ph.value() + extra;
	}

	@Override
	public int compareTo(Hand h) {
		List<Card> common = new ArrayList<>(cards);
		common.retainAll(h.getCards());

		return scoreHand() - h.scoreHand();
	}

	// Returns a list of cards to remove from the players hand
	public List<Card> exchange() {
		Optional<List<Card>> royal1 = is1AwayRoyalFlush();
		Optional<List<Card>> straightFlush1 = is1AwayStraightFlush();
		Optional<List<Card>> fullHouse1 = is1AwayFullHouse();
		Optional<List<Card>> flush1 = is1AwayFlush();
		Optional<List<Card>> straight1 = is1AwayStraight();
		Optional<List<Card>> flush2 = is2AwayFlush();
		Optional<List<Card>> seq3 = sequenceOf3();

		if (scoreHand() >= PokerHand.Straight.value()) {
			return Collections.emptyList();
		} else if (royal1.isPresent()) {
			return royal1.get();
		} else if (straightFlush1.isPresent()) {
			return straightFlush1.get();
		} else if (fullHouse1.isPresent()) {
			return fullHouse1.get();
		} else if (flush1.isPresent()) {
			return flush1.get();
		} else if (straight1.isPresent()) {
			return straight1.get();
		} else if (flush2.isPresent()) {
			return flush2.get();
		} else if (seq3.isPresent()) {
			return seq3.get();
		} else if (hasPair()) {
			return nonPairCards();
		} else {
			return Arrays.asList(cards.get(0), cards.get(1), cards.get(2));
		}
	}

	private Optional<List<Card>> is1AwayFullHouse() {
		Optional<List<Card>> four1 = is1AwayFourOfKind();
		hasTwoPairs();
		if (four1.isPresent()) {
			List<Card> rep = four1.get();
			Collections.sort(rep);
			return Optional.of(Collections.singletonList(rep.get(0)));
		} else if (hasTwoPairs()) {
			return Optional.of(nonPairCards());
		}

		return Optional.empty();
	}

	private List<Card> nonPairCards() {
		return rMap().values().stream().filter(l -> l.size() != 2).flatMap(List::stream).collect(Collectors.toList());
	}

	public Optional<List<Card>> is1AwayFourOfKind() {
		if (!hasSet() || hasFourOfKind())
			return Optional.empty();
		return Optional.of(
				rMap().values().stream().filter(l -> l.size() != 3).flatMap(List::stream).collect(Collectors.toList()));
	}

	public Optional<List<Card>> is1AwayStraightFlush() {
		Optional<List<Card>> straight = is1AwayStraight();
		Optional<List<Card>> flush = is1AwayFlush();

		if (isStraight() && is1AwayFlush().isPresent())
			return flush;
		else if (isFlush() && is1AwayStraight().isPresent())
			return straight;
		else if (straight.isPresent() && flush.isPresent() && straight.get().size() == 1
				&& straight.get().equals(flush.get()))
			return straight;

		return Optional.empty();
	}

	public Optional<List<Card>> is1AwayRoyalFlush() {
		Optional<List<Card>> sf = is1AwayStraightFlush();

		if (sf.isPresent()) {
			Optional<Suit> flush1 = isFlushSuitN(1);
			Optional<Suit> flush = isFlushSuitN(0);
			Card remove = sf.get().get(0);
			Suit suit = flush.isPresent() ? flush.get() : flush1.get();

			for (Rank r : Stream.of(Rank.values()).filter(e -> e.getValue() >= Rank.Ten.getValue())
					.collect(Collectors.toList())) {
				Hand h = copyHand();
				h.swap(remove, new Card(suit, r));

				if (h.isRoyalFlush())
					return Optional.of(Collections.singletonList(remove));
			}
		}

		return Optional.empty();
	}
}
