package steps;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.*;

import cucumber.api.java.en.*;
import poker.*;

public class StepDefinitions {

	Hand htb, aip, winner;

	public void setPlayer(String player, PokerHand ph) {
		switch (player) {
		case "Hand to beat":
			this.htb = new Hand(ph);
			break;
		case "AI Player":
			this.aip = new Hand(ph);
			break;
		default:
			System.out.println("Unknown player!");
			break;
		}
	}

	@Given("^([\\w ]+) has a Royal Flush$")
	public void player_has_a_Royal_Flush(String player) throws Throwable {
		setPlayer(player, PokerHand.RoyalFlush);
	}

	@Given("^([\\w ]+) has a Straight Flush$")
	public void player_has_a_Straight_Flush(String player) throws Throwable {
		setPlayer(player, PokerHand.StraightFlush);
	}

	@Given("^([\\w ]+) has a Four of a Kind$")
	public void ai_Player_has_a_Four_of_a_Kind(String player) throws Throwable {
		setPlayer(player, PokerHand.FourOfaKind);
	}

	@Given("^([\\w ]+) has a Full House$")
	public void ai_Player_has_a_Full_House(String player) throws Throwable {
		setPlayer(player, PokerHand.FullHouse);
	}

	@Given("^([\\w ]+) has a Flush$")
	public void ai_Player_has_a_Flush(String player) throws Throwable {
		setPlayer(player, PokerHand.Flush);
	}

	@Given("^([\\w ]+) has a Straight$")
	public void ai_Player_has_a_Straight(String player) throws Throwable {
		setPlayer(player, PokerHand.Straight);
	}

	@Given("^([\\w ]+) has a Set$")
	public void ai_Player_has_a_Set(String player) throws Throwable {
		setPlayer(player, PokerHand.Set);
	}

	@Given("^([\\w ]+) has a Two Pair$")
	public void ai_Player_has_a_Two_Pair(String player) throws Throwable {
		setPlayer(player, PokerHand.TwoPair);
	}

	@Given("^([\\w ]+) has a Pair$")
	public void ai_Player_has_a_Pair(String player) throws Throwable {
		setPlayer(player, PokerHand.Pair);
	}

	@Given("^([\\w ]+) has a High Card$")
	public void ai_Player_has_a_High_Card(String player) throws Throwable {
		setPlayer(player, PokerHand.HighCard);
	}

	@When("^the hands are compared$")
	public void the_hands_are_compared() throws Throwable {
		int value = htb.compareTo(aip);
		this.winner = value == 0 ? null : (value > 0 ? htb : aip);
	}

	@Then("^htb wins$")
	public void htb_wins() throws Throwable {
		assertEquals(this.htb, this.winner);
	}

	@Then("^aip wins$")
	public void aip_wins() throws Throwable {
		assertEquals(this.aip, this.winner);
	}

	@Given("^AI Player has ([\\w ]+) cards$")
	public void aip_has_close_cards(String cards) throws Throwable {
		aip = new Hand(cards.split(" "));
	}

	@When("^AI Player exchanges its cards using ([\\w ]+)$")
	public void aip_exchanges_using(String deck) throws Throwable {
		List<Card> discard = aip.exchange();
		List<Card> pickup = Arrays.stream(deck.split(" ")).map(s -> new Card(s)).collect(Collectors.toList());
		aip.swap(discard, pickup);
	}

	@Given("^Hand to beat has ([\\w ]+) cards$")
	public void htb_has_cards(String cards) {
		htb = new Hand(cards.split(" "));
	}

	@When("^the poker hand is calculated$")
	public void the_poker_hand_is_calculated() throws Throwable {
		// Do nothing
	}

	@Then("^AI Player does not have the cards ([\\w ]+)$")
	public void aip_does_not_have_the_cards(String cards) throws Throwable {
		List<Card> not = Arrays.stream(cards.split(" ")).map(s -> new Card(s)).collect(Collectors.toList());
		assertTrue(Collections.disjoint(aip.getCards(), not));
	}

	@Given("^([\\w ]+) has a Royal Flush of (\\w+)$")
	public void player_has_a_Royal_Flush_of_suit(String player, String suit) throws Throwable {
		if (player.equals("AI Player")) {
			aip = new Hand(PokerHand.RoyalFlush, Arrays.asList(Suit.getEnum(suit.charAt(0))));
		} else {
			htb = new Hand(PokerHand.RoyalFlush, Arrays.asList(Suit.getEnum(suit.charAt(0))));
		}
	}
	
	private List<Suit> getSuits(String suit) {
		return Arrays.stream(suit.split(" ")).map(s -> Suit.getEnum(s.charAt(0))).collect(Collectors.toList());
	}
	
	private List<Rank> getRanks(String rank) {
		return Arrays.stream(rank.split(" ")).map(s -> Rank.getEnum(s)).collect(Collectors.toList());
	}

	@Given("^([\\w ]+) has a ([\\w ]+) with a ([\\w ]+) - ([\\w ]+)$")
	public void player_has_a_poker_hand_with(String player, String pokerHand, String suit, String rank) throws Throwable {
		List<Suit> suits = getSuits(suit);
		List<Rank> ranks = getRanks(rank);
		PokerHand ph = PokerHand.getEnum(pokerHand);
		
		if (player.equals("AI Player")) {
			aip = new Hand(ph, suits, ranks);
		} else {
			htb = new Hand(ph, suits, ranks);
		}
	}
	
	@Given("^([\\w ]+) has a Flush of (\\w+) - ([\\w ]+)$")
	public void player_has_a_Flush_of_suit_and_ranks(String player, String suit, String rank) throws Throwable {
		List<Suit> suits = getSuits(suit);
		List<Rank> ranks = getRanks(rank);
		final Suit s1 = suits.get(0);
		
		if (player.equals("AI Player")) {
			aip = new Hand(ranks.stream().map(r -> ("" + s1 + r)).collect(Collectors.toList()));
		} else {
			htb = new Hand(ranks.stream().map(r -> ("" + s1 + r)).collect(Collectors.toList()));
		}
	}
	
	@Then("^Hand to beat is a ([\\w ]+)$")
	public void htb_is_a_poker(String pokerHand) throws Throwable {
		assertTrue(htb.getPh() == PokerHand.getEnum(pokerHand));
	}
	
	@Then("^AI Player is a ([\\w ]+)$")
	public void aip_is_a_poker(String pokerHand) throws Throwable {
		assertTrue(aip.getPh() == PokerHand.getEnum(pokerHand));
	}
}
