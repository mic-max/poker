Feature: Determine winner when Hand to beat and AI Player have the same poker hand 

Scenario Outline: 
	Given Hand to beat has a <poker> of <htbsuit> 
	And   AI Player has a <poker> of <aipsuit>
	When  the hands are compared 
	Then  <winner> wins
	
	Examples: 
		| poker          | htbsuit | aipsuit | winner |
		| Royal Flush    | Spade   | Heart   | htb    |

Scenario Outline: 
	Given Hand to beat has a <poker> with a <htbsuit> - <htbrank>
	And   AI Player has a <poker> with a <aipsuit> - <aiprank>
	When  the hands are compared 
	Then  <winner> wins
	
	Examples: 
		| poker          | htbsuit | htbrank | aipsuit | aiprank | winner |
		| Straight Flush | H       | Q       | C       | 10      | htb    |
		| Straight Flush | C       | K       | D       | K       | aip    | 
		| Four of a Kind | H       | K       | C       | Q       | htb    |
		| Full House     | C D     | J 3     | S H     | K 5     | aip    |
		| Set            | C       | A       | S       | 10      | htb    |
		| Straight       | C       | K       | H       | K       | aip    |
		| Straight       | C       | Q       | H       | J       | htb    |
		| Two Pair       | C D     | 5 9     | H S     | 3 9     | aip    |
		| Two Pair       | C D     | 5 J     | H S     | 3 9     | htb    |
		| Pair           | C       | 9       | H       | 9       | aip    |
		| Pair           | C       | 9       | H       | J       | aip    |
		| High Card      | D       | K       | S       | K       | aip    |
		| High Card      | D       | A       | S       | K       | htb    |

Scenario Outline: 
	Given Hand to beat has a Flush of <htbsuit> - <htbrank>
	And   AI Player has a Flush of <aipsuit> - <aiprank>
	When  the hands are compared 
	Then  <winner> wins
	
	Examples: 
		| htbsuit | aipsuit | htbrank   | aiprank    | winner |
		| H       | C       | 4 6 8 J K | 4 6 8 J K  | htb    |
		| S       | C       | 4 6 8 J K | 5 6 8 J K  | aip    |
		| C       | D       | 5 8 J K A | 2 6 A J K  | htb    |
		| H       | C       | Q K 2 4 6 | Q K 10 9 7 | aip    |
		| D       | C       | A K 2 4 6 | 3 5 10 9 A | aip    |  