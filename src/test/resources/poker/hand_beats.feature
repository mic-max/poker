Feature: Can decide a winner from two poker hands 

Scenario Outline: 
	Given Hand to beat has a <htb> 
	And   AI Player has a <aip> 
	When  the hands are compared 
	Then  <winner> wins 
	
	Examples: 
		| htb            | aip            | winner |
		| Royal Flush    | Straight Flush | htb    |
		| Royal Flush    | Four of a Kind | htb    |
		| Royal Flush    | Full House     | htb    |
		| Royal Flush    | Flush          | htb    |
		| Royal Flush    | Straight       | htb    |
		| Royal Flush    | Set            | htb    |
		| Royal Flush    | Two Pair       | htb    |
		| Royal Flush    | Pair           | htb    |
		| Royal Flush    | High Card      | htb    |
		| Straight Flush | Royal Flush    | aip    |
		| Straight Flush | Four of a Kind | htb    |
		| Straight Flush | Full House     | htb    |
		| Straight Flush | Flush          | htb    |
		| Straight Flush | Straight       | htb    |
		| Straight Flush | Set            | htb    |
		| Straight Flush | Two Pair       | htb    |
		| Straight Flush | Pair           | htb    |
		| Straight Flush | High Card      | htb    |
		| Four of a Kind | Royal Flush    | aip    |
		| Four of a Kind | Straight Flush | aip    |
		| Four of a Kind | Full House     | htb    |
		| Four of a Kind | Flush          | htb    |
		| Four of a Kind | Straight       | htb    |
		| Four of a Kind | Set            | htb    |
		| Four of a Kind | Two Pair       | htb    |
		| Four of a Kind | Pair           | htb    |
		| Four of a Kind | High Card      | htb    |
		| Full House     | Royal Flush    | aip    |
		| Full House     | Straight Flush | aip    |
		| Full House     | Four of a Kind | aip    |
		| Full House     | Flush          | htb    |
		| Full House     | Straight       | htb    |
		| Full House     | Set            | htb    |
		| Full House     | Two Pair       | htb    |
		| Full House     | Pair           | htb    |
		| Full House     | High Card      | htb    |
		| Flush          | Royal Flush    | aip    |
		| Flush          | Straight Flush | aip    |
		| Flush          | Four of a Kind | aip    |
		| Flush          | Full House     | aip    |
		| Flush          | Straight       | htb    |
		| Flush          | Set            | htb    |
		| Flush          | Two Pair       | htb    |
		| Flush          | Pair           | htb    |
		| Flush          | High Card      | htb    |
		| Straight       | Royal Flush    | aip    |
		| Straight       | Straight Flush | aip    |
		| Straight       | Four of a Kind | aip    |
		| Straight       | Full House     | aip    |
		| Straight       | Flush          | aip    |
		| Straight       | Set            | htb    |
		| Straight       | Two Pair       | htb    |
		| Straight       | Pair           | htb    |
		| Straight       | High Card      | htb    |
		| Set            | Royal Flush    | aip    |
		| Set            | Straight Flush | aip    |
		| Set            | Four of a Kind | aip    |
		| Set            | Full House     | aip    |
		| Set            | Flush          | aip    |
		| Set            | Straight       | aip    |
		| Set            | Two Pair       | htb    |
		| Set            | Pair           | htb    |
		| Set            | High Card      | htb    |
		| Two Pair       | Royal Flush    | aip    |
		| Two Pair       | Straight Flush | aip    |
		| Two Pair       | Four of a Kind | aip    |
		| Two Pair       | Full House     | aip    |
		| Two Pair       | Flush          | aip    |
		| Two Pair       | Straight       | aip    |
		| Two Pair       | Set            | aip    |
		| Two Pair       | Pair           | htb    |
		| Two Pair       | High Card      | htb    |
		| Pair           | Royal Flush    | aip    |
		| Pair           | Straight Flush | aip    |
		| Pair           | Four of a Kind | aip    |
		| Pair           | Full House     | aip    |
		| Pair           | Flush          | aip    |
		| Pair           | Straight       | aip    |
		| Pair           | Set            | aip    |
		| Pair           | Two Pair       | aip    |
		| Pair           | High Card      | htb    |
		| High Card      | Royal Flush    | aip    |
		| High Card      | Straight Flush | aip    |
		| High Card      | Four of a Kind | aip    |
		| High Card      | Full House     | aip    |
		| High Card      | Flush          | aip    |
		| High Card      | Straight       | aip    |
		| High Card      | Set            | aip    |
		| High Card      | Two Pair       | aip    |
		| High Card      | Pair           | aip    |
		