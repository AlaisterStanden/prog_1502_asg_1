package mru.game.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import mru.game.model.Player;
import mru.game.view.AppMenu;




public class GameManager {

    private final String FILE_PATH = "res/CasinoInfo.txt";
    ArrayList<Player> players;
    AppMenu appMen;
    CardDeck myDeck;  // CardDeck is now a member variable, to preserve state across game rounds

    public GameManager() throws Exception {
        players = new ArrayList<>();
        appMen = new AppMenu();
        myDeck = new CardDeck();  // Initialize card deck when GameManager is created
        loadData();
        launchApplication();
    }

    private void launchApplication() throws IOException {
        boolean flag = true;
        char option;

        while(flag) {
            option = appMen.showMainMenu();

            switch (option) {
            case 'p':
                playGame();
                break;
            case 's':
                Search();
                break;
            case 'e':
                Exit();
                flag = false;
            }
        }
    }

    private void playGame() {
    	String name = appMen.promptName();
    	Player p = lookByName(name);
    	if (p == null) {
    		// New Player
    		System.out.println("***********************************************************************************************************");
    		System.out.println("*** Welcome " + name.toUpperCase() + " --- Your balance initial balance is: 100 " + " $ ***" );
    		System.out.println("***********************************************************************************************************");
    		int startingBalance = 100;
    		players.add(new Player(name, startingBalance, 0));
    		p = lookByName(name);
    	} else {
    		
    		// Returning Player
    		 System.out.println("***************************************************************************************");
    		 System.out.println("*** Welcome back " + name.toUpperCase() + " --- Your balance is: " + p.getBalance() + " $ ***" );
    		 System.out.println("***************************************************************************************");
    	}
    	if (p.getBalance() <= 0) {
    		 System.out.println("Your balance is 0. You are unable to play. Returning to main menu. ");
    		 return; //Return to the main menu
        }

        boolean continuePlaying = true;
        while (continuePlaying) {
            char outcome = appMen.promptOutcome();
            int betAmount;

            do {
                betAmount = appMen.promptBetAmount();
                if (betAmount > p.getBalance()) {
                    System.out.println("You cannot bet more than your current balance of $" + p.getBalance() + ". Please enter a valid amount.");
                }
            } while (betAmount > p.getBalance());

            
            PuntoBancoGame puntoBancoGame = new PuntoBancoGame(p, appMen, myDeck, betAmount);
           
            char gameOutcome = puntoBancoGame.playRound();

        
            
            if (gameOutcome == 'W') {
                //System.out.println("Congratulations! You guessed right!");
                p.setBalance(p.getBalance() + betAmount); // Add to balance.
            } 
            else if(gameOutcome == 'L') {
                //System.out.println("Oops! Better luck next time.");
                p.setBalance(p.getBalance() - betAmount); // Subtract from balance.
            }

            System.out.println("Your current balance: $" + p.getBalance());
            
            if (p.getBalance() <= 0) {
                System.out.println("Sorry, you don't have enough balance to continue playing.");
                return;  // Return to the main menu
            }

            System.out.println("Would you like to play again? (Y/N)");
            char choice = appMen.input.nextLine().toUpperCase().charAt(0);
            if (choice == 'N') {
                continuePlaying = false;
            }
            
        }
    
        //check and print cards
   
        Card currentCard = myDeck.getDeck().remove(0);
        
        currentCard = myDeck.getDeck().remove(0);
        
        
        
    }
 private void Search() {
        char option = appMen.showSubMenu();

        switch (option) {
        case 't':
            FindTopPlayer();
            break;
        case 'n':
            String name = appMen.promptName();
            Player ply = lookByName(name);  // fixed: passed name to method
            if (ply != null) {
            	appMen.showPlayer(ply);
            } else {
            	System.out.println("Player does not exist :(");
            }
            break;
        case 'b':
            break;
        }
    }

//Creates a new player and adds the player to the player list
	public Player makePlayer(String name) {
		Player newPlayer = new Player(name, 100,0);
		players.add(newPlayer);
		return newPlayer;
	}
	
	//Searches for player and displays wins and balance
	private Player lookByName(String name) {
	
		for (Player p: players) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	private void FindTopPlayer() {
	
	 int highestWins = Integer.MIN_VALUE;
	 // First, find the highest number of wins from the list
	 for (Player p : players) {
	 if (p.getNumOfWins() > highestWins) {
	 highestWins = p.getNumOfWins();
	 }
	 }
	 // Display all the players with the highest number of wins
	 System.out.println(" - TOP PLAYERS - ");
	 System.out.println("+==============================+==========================+");
	 System.out.println("| NAME |# WINS |");
	 System.out.println("+==============================+==========================+");
	
	 for (Player p : players) {
	 if (p.getNumOfWins() == highestWins) {
System.out.format("| %-16s | %9d |\n", p.getName(), p.getNumOfWins());
	 System.out.println("+------------------------------+--------------------------+");
	
	 }
	
	
	 }
	 appMen.waitEnter();
	
	
	
//Logic for finding the top player ( highest balance)
}
	private void Exit() throws IOException {
		System.out.println("Exiting and saving data...");
		File info = new File(FILE_PATH);
		if (info.exists()) {
			PrintWriter pw = new PrintWriter(info);
			for (Player p : players) {
				pw.println(p.format());
			}
			pw.close();
		}

	}
	
	private void loadData() throws Exception {
		File info = new File(FILE_PATH);
		if(info.exists()) {
			Scanner fileReader = new Scanner(info);
			while (fileReader.hasNextLine()) {
				String currentLine = fileReader.nextLine();
				String[] splittedLine = currentLine.split(",");
				Player p = new Player(splittedLine[0], 
				Integer.parseInt(splittedLine[1]), Integer.parseInt(splittedLine[2]));
				players.add(p);
			}
			fileReader.close();
		}
	}
}

