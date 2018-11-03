Feature: The AI Player can exchange cards when it is close to a poker hand 

Scenario Outline: 
	Given Hand to beat has a <htb> 
	And   AI Player has <aip> cards 
	When  AI Player exchanges its cards using <deck> 
	And   the hands are compared 
	Then  <winner> wins 
	And   AI Player is a <poker> 
	
	Examples: 
		| htb            | aip             | deck  | poker          | winner |
		| Straight Flush | HA HK HQ HJ D3  | H10   | Royal Flush    | aip    |
		| Pair           | HA HK HQ HJ D3  | C4    | High Card      | htb    |
		| Four of a Kind | H9 HK HQ HJ D3  | H10   | Straight Flush | aip    |
		| Pair           | H9 HK HQ HJ D3  | D5    | High Card      | htb    |
		| Flush          | H9 D9 C9 HJ C3  | DJ    | Full House     | aip    |
		| Straight       | H9 D9 C9 HJ C3  | D3    | Set            | htb    |
		| Flush          | C4 D4 CK HK SA  | DK    | Full House     | aip    |
		| Set            | C4 D4 CK HK SA  | H8    | Two Pair       | htb    |
		| Set            | D2 D4 D5 DQ S10 | DA    | Flush          | aip    |
		| Pair           | D2 D4 D5 DQ S10 | CA    | High Card      | htb    |
		| Set            | D2 D3 H5 C6 S10 | D4    | Straight       | aip    |
		| Pair           | D2 D3 H5 C6 S10 | DK    | High Card      | htb    |
		
Scenario Outline: 
	Given AI Player has <aip> cards 
	When  AI Player exchanges its cards using <deck> 
	Then  AI Player does not have the cards <cards> 
	
	Examples: 
		| aip             | deck      | cards     |
		| D4 H9 C5 H8 HA  | H2 HJ     | C5 D4     |
		| C7 D8 H9 DK C3  | C6 D5     | C3 DK     |
		| CJ DJ H4 C10 SA | C3 C6 DA  | H4 C10 SA |
		| C4 D7 H5 CJ DA  | SK CQ C10 | C4 D7 H5  |