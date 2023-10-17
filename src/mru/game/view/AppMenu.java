package mru.game.view;

import java.util.Scanner;

import mru.game.model.Player;

public class AppMenu {
    public Scanner input;
    
    public AppMenu() {
        input = new Scanner(System.in);	
    }
    
    public char showMainMenu() {
        System.out.println("Select one of these options:\n");
        System.out.println("\t(P) Play Game");
        System.out.println("\t(S) Search");
        System.out.println("\t(E) Exit\n");
        System.out.print("Enter a choice: ");	
        char option = input.nextLine().toLowerCase().charAt(0);
        return option;
    }
    
    public char showSubMenu() {
        System.out.println("Select one of these options:\n");
        System.out.println("\t(T) Top Player (Most number of wins)");
        System.out.println("\t(N) Looking for a Name");
        System.out.println("\t(B) Back to Main menu \n");
        System.out.print("Enter a choice: ");
        char option = input.nextLine().toLowerCase().charAt(0);
        return option;
    }

    public String promptName() {
        System.out.print("Enter a name here: ");
        String name = input.nextLine().trim();
        return name;
    }
    
    

  //Displays the player information
    public void showPlayer(Player ply) {
 	   System.out.println(" -    PLAYERS INFO -   ");
   	   System.out.println("+==============================+==========================+=================================+");
   	   System.out.println("| NAME                         |# WINS                    |BALANCE                          |");
        System.out.println("+==============================+==========================+=================================+");
        
   	   
   	   System.out.format("| %-26s   | %-9d                |  $%-24d      |                \n  ",   ply.getName(), ply.getNumOfWins(), ply.getBalance());
   	   System.out.println("+------------------------------+--------------------------+---------------------------------+");
   	   System.out.println("Press \"Enter\" to continue...");{
   	   input.nextLine();
   	   }
    }       


    public int promptBalance() {
        System.out.print("Enter your balance here: ");
        
        while (!input.hasNextInt()) {
            System.out.print("Invalid input. Enter your balance here: ");
            input.next();
        }
        
        int balance = input.nextInt();
        input.nextLine();
        return balance;
    }

    public char promptOutcome() {
        System.out.println("Who do you want to bet on?");
        System.out.println("(P) Player Wins");
        System.out.println("(B) Banker Wins");
        System.out.println("(T) Tie Game");
        System.out.print("Enter your choice: ");
        return input.nextLine().toUpperCase().charAt(0);
    }

    public int promptBetAmount() {
        System.out.print("How much do you want to bet this round? ");
        while (!input.hasNextInt()) {
            System.out.print("Invalid input. Enter a valid bet amount: ");
            input.next();
        }
        int amount = input.nextInt();
        input.nextLine();
        return amount;
    }
   public void waitEnter() {
	   System.out.println("Press \"Enter\" to continue...");
	   input.nextLine();
   }
}
