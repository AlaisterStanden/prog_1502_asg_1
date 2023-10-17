package mru.game.controller;

import mru.game.model.Player;
import mru.game.view.AppMenu;

public class PuntoBancoGame {
	private CardDeck deck;
	private Player player;
	private AppMenu appMenu;

	public PuntoBancoGame(Player player, AppMenu appMenu, CardDeck deck, int betAmount) {
		this.deck = new CardDeck();
		this.player = player;
		this.appMenu = appMenu;
		this.betAmount = betAmount;
	}

	private int calculateHandValue(Card... cards) {
		int value = 0;
		for (Card card : cards) {
			int rank = card.getRank();
			if (rank >= 10)
				value += 0; // Face cards and 10s are worth 0
			else
				value += rank;
		}
		return value % 10;
	}

	private Card drawCard() {
		return deck.getDeck().remove(deck.getDeck().size() - 1);
		/*
		 * Card drawnCard = deck.getDeck().remove(deck.getDeck().size() - 1);
		 * System.out.println("[DEBUG] Card drawn: " + drawnCard +
		 * ". Cards remaining in deck: " + deck.getDeck().size()); return drawnCard;
		 */

	}

	private boolean shouldBankerDrawThirdCard(Card playerThirdCard, int bankerScore) {
		int rank = playerThirdCard.getRank();
		switch (rank) {
		case 2:
		case 3:
			return bankerScore >= 0 && bankerScore <= 4;
		case 4:
		case 5:
			return bankerScore >= 0 && bankerScore <= 5;
		case 6:
		case 7:
			return bankerScore >= 0 && bankerScore <= 6;
		case 8:
			return bankerScore >= 0 && bankerScore <= 2;
		case 9:
		case 10:
		case 11:
		case 12:
		case 1:
			return bankerScore >= 0 && bankerScore <= 3;
		default:
			return false;
		}
	}

	public char playRound() {

		// debug test
		// System.out.println("DEBUG: Entering playRound() method");

		if (deck.getDeck().size() < 6) {
			deck = new CardDeck(); // Reset deck if it has less than 6 cards
		}

		// Step 1: Deal 2 cards each for player and banker
		Card playerCard1 = drawCard();
		Card playerCard2 = drawCard();
		Card bankerCard1 = drawCard();
		Card bankerCard2 = drawCard();

		int playerScore = calculateHandValue(playerCard1, playerCard2);
		int bankerScore = calculateHandValue(bankerCard1, bankerCard2);

		// System.out.println("Player's cards: " + playerCard1 + " and " + playerCard2 +
		// ". Score: " + playerScore);
		// System.out.println("Banker's cards: " + bankerCard1 + " and " + bankerCard2 +
		// ". Score: " + bankerScore);

		// Step 2: Check for natural win (8 or 9)
		if (playerScore >= 8 || bankerScore >= 8) {
			decideWinner(playerScore, bankerScore);

		}

		// Step 3: Player draws third card if score is 0-5
		Card playerCard3 = null;
		if (playerScore <= 5) {
			playerCard3 = drawCard();
			playerScore = calculateHandValue(playerCard1, playerCard2, playerCard3);
			//System.out.println("Player draws: " + playerCard3 + ". New Score: " + playerScore);
		}

		// Step 4: Banker draws third card based on certain conditions
		Card bankerCard3 = null;
		if (playerCard3 != null) {
			if (shouldBankerDrawThirdCard(playerCard3, bankerScore)) {
				bankerCard3 = drawCard();
				bankerScore = calculateHandValue(bankerCard1, bankerCard2, bankerCard3);
				//System.out.println("Banker draws: " + bankerCard3 + ". New Score: " + bankerScore);
			}
		} else if (bankerScore <= 5) {
			bankerCard3 = drawCard();
			bankerScore = calculateHandValue(bankerCard1, bankerCard2, bankerCard3);
			//System.out.println("Banker draws: " + bankerCard3 + ". New Score: " + bankerScore);
		}

		// ... [Keep your existing code for drawing cards and calculating scores]

		// Print the results in the desired format
		System.out.println("          – PUNTO BANCO –          ");
		System.out.println("+==============================================+");
		System.out.println(String.format("|%-25s|%20s|", "PLAYER", "BANKER"));
		System.out.println("|-------------------------|--------------------|");
		System.out.println(String.format("|%-25s|%20s|", playerCard1, bankerCard1));
		System.out.println("|-------------------------|--------------------|");
		System.out.println(String.format("|%-25s|%20s|", playerCard2, bankerCard2));
		System.out.println("|-------------------------|--------------------|");

		if (playerCard3 != null) {
			System.out.println(String.format("|%-25s|%-9s|", playerCard3, ""));
			// } else {
			// System.out.println(String.format("|%-25s|%-9s|", "", ""));
		}

		if (bankerCard3 != null) {
			System.out.println(String.format("|%-25s|%20s|", "", bankerCard3));
			System.out.println("|-------------------------|--------------------|");
			// } else {
			// System.out.println(String.format("|%-25s|%20s|", "", ""));
		}

		System.out.println(String.format("|PLAYER POINTS: %-10d|Banker POINTS: %-5d|", playerScore, bankerScore));
		System.out.println("+==============================================+");
		System.out.println();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		char result = decideWinner(playerScore, bankerScore);
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println();
		System.out.println();
		//System.out.print("Do you want to play again(Y/N)? ");
		return result;

	}

	// Step 5: Decide winner
	// return decideWinner(playerScore, bankerScore);

	private int betAmount;

	private char decideWinner(int playerScore, int bankerScore) {
		if (playerScore > bankerScore) {
			System.out.println("	PLAYER WON " + betAmount + "$");
			return 'W';
		} else if (bankerScore > playerScore) {
			System.out.println("	PLAYER LOST " + betAmount + "$");
			return 'L';
		} else {
			System.out.println("	It's a tie");
			return 'T';
		}
	}
}
