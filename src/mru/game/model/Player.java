package mru.game.model;

public class Player {

    private String name;
    private int balance;
    private int numOfWins;
    private int bet;
    private char outcome;  // 'P' for Player, 'B' for Banker, 'T' for Tie

    /**
     * This class represents each player record in the Database.
     * It is basically a model class for each record in the txt file.
     */
    public Player(String name, int balance, int numOfWins) {
        this.name = name;
        this.balance = balance;
        this.numOfWins = numOfWins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

    public void setOutcome(char outcome) {
        this.outcome = outcome;
    }

    public char getOutcome() {
        return outcome;
    }

    @Override
    public String toString() {
        return "Name: " + name + " balance: " + balance + " Number of Wins: " + numOfWins;
    }

    public String format() {
        return name + "," + balance + "," + numOfWins;
    }
}
