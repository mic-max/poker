Feature: Can detect what poker hand five playing cards corresponds to. 

Scenario Outline: 
	Given Hand to beat has <cards> cards 
	When  the poker hand is calculated 
	Then  Hand to beat is a <poker> 
	
	Examples: 
	
		| poker          | cards           |
		| Royal Flush    | SA SK SQ SJ S10 |
		| Royal Flush    | S10 SQ SK SJ SA |
		| Royal Flush    | SQ SK S10 SA SJ |
		| Royal Flush    | SK S10 SQ SJ SA |
		| Royal Flush    | SK SA S10 SJ SQ |
		| Royal Flush    | DK DA D10 DJ DQ |
		| Royal Flush    | CA CK CQ CJ C10 |
		| Royal Flush    | HA HK HQ HJ H10 |
		| Straight Flush | CJ C10 C9 C8 C7 |
		| Straight Flush | C10 C8 C7 C9 CJ |
		| Straight Flush | C7 C8 C9 C10 CJ |
		| Straight Flush | CJ C7 C10 C8 C9 |
		| Straight Flush | C10 CJ C7 C9 C8 |
		| Straight Flush | D2 D3 D4 D5 D6  |
		| Straight Flush | H2 H3 H4 H5 HA  |
		| Four of a Kind | C2 H2 S2 D2 HJ  |
		| Four of a Kind | HJ S2 D2 C2 H2  |
		| Four of a Kind | H2 D2 C2 HJ S2  |
		| Four of a Kind | D2 C2 HJ S2 H2  |
		| Four of a Kind | S2 HJ H2 C2 D2  |
		| Four of a Kind | S10 SA HA CA DA |
		| Full House     | H2 H3 C2 C3 D3  |
		| Full House     | H2 C2 H3 C3 D3  |
		| Full House     | C3 D3 H3 C2 H2  |
		| Full House     | C2 D3 H2 C3 H3  |
		| Full House     | D3 C2 C3 H3 H2  |
		| Full House     | HJ DJ SJ SA HA  |
		| Flush          | S2 S6 S7 S9 SK  |
		| Flush          | SK S9 S6 S7 S2  |
		| Flush          | S6 S7 SK S2 S9  |
		| Flush          | S9 SK S2 S6 S7  |
		| Flush          | S7 S2 S9 SK S6  |
		| Flush          | D4 D6 D8 D10 DQ |
		| Straight       | DJ H10 C9 C8 C7 |
		| Straight       | H10 C8 C7 C9 DJ |
		| Straight       | C7 C8 C9 H10 DJ |
		| Straight       | DJ C7 H10 C8 C9 |
		| Straight       | H10 DJ C7 C9 C8 |
		| Straight       | H6 D7 C8 C9 C5  |
		| Set            | H6 H3 C2 C3 D3  |
		| Set            | H6 C2 H3 C3 D3  |
		| Set            | C3 D3 H3 C2 H6  |
		| Set            | C2 D3 H6 C3 H3  |
		| Set            | D3 C2 C3 H3 H6  |
		| Set            | DA CA HA H10 C9 |
		| Two Pair       | H2 H3 C2 SA D3  |
		| Two Pair       | H2 C2 H3 SA D3  |
		| Two Pair       | SA D3 H3 C2 H2  |
		| Two Pair       | C2 D3 H2 SA H3  |
		| Two Pair       | D3 C2 SA H3 H2  |
		| Two Pair       | H7 D7 CK SK CA  |
		| Pair           | DQ HQ C4 C5 C6  |
		| Pair           | C6 C5 HQ C4 DQ  |
		| Pair           | C5 C4 C6 DQ HQ  |
		| Pair           | C4 DQ C5 C6 HQ  |
		| Pair           | HQ C6 DQ C4 C5  |
		| Pair           | D4 C4 C5 H8 DA  |
