// Class: Blackjack
// Purpose: Main class used to play the card game Blackjack
// @author Austin Wang
// @version December 2020

import static java.lang.System.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Blackjack
{
    /* instance variables here */    
    // deck of cards used for this game
    private Deck TheCardDeck;
    // arrays to hold the dealer's and player's hands
    private Card[] dealerCards;
    private Card[] playerCards;
    // keep track of win points for player and dealer
    private double playerPts;
    private double dealerPts;
    // number of rounds that have been played
    private int rounds;
    // scanner used for keyboard input
    Scanner input = new Scanner(System.in);
    private int numberofPlayerCards;
    private int numberofDealerCards;
    // constructor
    public Blackjack()
    {
        TheCardDeck = new Deck();
        playerPts = 0;
        dealerPts = 0;
        rounds = 0;
        numberofPlayerCards = 0;
        numberofDealerCards = 0;      
    }

    // "main" method in the class, used to play one complete round of Blackjack
    public void playRound()
    {        
        dealerCards = new Card[2];
        playerCards = new Card[2];
        //draws 2 cards for player
        for(int i = 0; i < 2; i++){
            playerCards[i] =  TheCardDeck.dealCard();
        }
        //draws 2 cards for dealer
        for(int i = 0; i < 2; i++){
            dealerCards[i] =  TheCardDeck.dealCard();
        }
        System.out.println("Dealer: "+ dealerCards[0]);
        //calculates sum of player's two cards
        System.out.println("Player: " +"[" +playerCards[0]+", " + playerCards[1] + "] : "+ getHandValue(playerCards));
        checkWinner();
        //tracks points of player vs. dealer
        System.out.println("\nScore is: P=" + playerPts + ", D="+dealerPts);
    }
    
    // look at the current hands and determine a winner
    public void checkWinner()
    {
        //determines winner
        if (getHandValue(playerCards) == 21 && getHandValue(dealerCards) == 21) {
            System.out.println("\n^^^ Draw! ^^^");          
        } else if(getHandValue(playerCards) == 21) {
            System.out.println("\n!!! Player gets a blackjack and 1.5 points, nice! !!!");
            playerPts += 1.5;
        } else if(getHandValue(dealerCards) == 21) {
            System.out.println("\n:( Dealer gets a blackjack, tough break! :(");
            dealerPts += 1.0;
        } else {
            boolean stand = false;
            int increase = 1;
            //asks player to hit or stand
            while(stand == false && getHandValue(playerCards) < 21)
            {
                System.out.print("Player! What would you like to do? (H)it or (S)tand? ");
                String scan = input.nextLine();
                if(scan.equals("s") || scan.equals("S")) {
                    stand = true;
                } else if(scan.equals("h") || scan.equals("H")) {                    
                    //adds another card to player's hand, first creates a 2nd array playerTwo
                    Card[] playerTwo = new Card[2 + increase];
                    //copies playerCard array to 2nd array
                    for(int z = 0; z < playerCards.length; z++)
                    {
                        playerTwo[z] = playerCards[z];
                    }
                    //draws new card for player and adds to 2nd array playerTwo
                    playerTwo[playerTwo.length-1] = TheCardDeck.dealCard();
                    //sets playerCards into playerTwo
                    playerCards = playerTwo;
                    //prints new card, which is located in the playerCards array
                    System.out.println("\n*** " + playerCards[playerCards.length - 1] + " ***");
                    System.out.println();
                    //prints out player's new card set and calculates the total sum for player
                    System.out.print("Player: [");
                    for(int p = 0; p < playerCards.length - 1; p++)
                    {
                        System.out.print(playerCards[p] + " , ");
                    }
                    System.out.println(playerCards[playerCards.length - 1] + "] : " + getHandValue(playerCards));
                    increase++;
                } else {
                    System.out.println("Please type (h) or (s)");
                    scan = input.nextLine();
                }
            }

            if(getHandValue(playerCards) > 21) {
                System.out.println("\n### Player busted! Dealer wins! ###");
                dealerPts += 1.0;
            } else {            
                //adds one card for dealer
                int increasedealer = 1;
                //if less than a total of 17, the dealer draws a new card
                while(getHandValue(dealerCards) < 17){
                    //creates 2nd array called dealerTwo
                    Card[] dealerTwo = new Card[2 + increasedealer];
                    //copies dealerCards into dealerTwo
                    for(int i = 0; i < dealerCards.length; i++){
                        dealerTwo[i] = dealerCards[i];
                    }
                    //draws new card for dealer
                    dealerTwo[dealerTwo.length - 1] = TheCardDeck.dealCard();
                    //prints new card, which is in the dealerTwo array
                    System.out.println("*** " + dealerTwo[dealerTwo.length - 1] + " ***");
                    //sets dealerCards as dealerTwo
                    dealerCards = dealerTwo;
                    increasedealer++;
                }
                //shows dealer's cards and calculates total sum for dealer
                System.out.print("Dealer: [");
                for(int d = 0; d < dealerCards.length - 1; d++)
                {
                    System.out.print(dealerCards[d] + " , ");
                }
                System.out.println(dealerCards[dealerCards.length-1] + "] : " + getHandValue(dealerCards));
                //checks if player or dealer wins
                if(getHandValue(dealerCards) > 21) {
                    System.out.println("\n### Dealer Busted! Player wins! ###");
                    playerPts += 1.0;
                } else if (getHandValue(dealerCards) > getHandValue(playerCards)) {
                    System.out.println("\n### Dealer wins! ###");
                    dealerPts += 1.0;
                } else if (getHandValue(dealerCards) == getHandValue(playerCards)) {
                    System.out.println("\n^^^ Draw! ^^^");
                } else {
                    System.out.println("\n$$$ Player wins! $$$");
                    playerPts += 1.0;
                }
            }
        }
    }
    
    //shuffles card deck
    public void shuffleCard(){
        TheCardDeck.shuffle();
    }
    
    // calculate the value of a hand (handle aces correctly!)
    public int getHandValue(Card[] cards)
    {
        int val = 0; //value of Ace
        int length = cards.length;
        int[] arrayVal = new int[length];
        boolean aceEleven = false;
        //this adds up all of the values in the dealer's hands
        for(int i = 0; i < length; i++){
            arrayVal[i] = cards[i].getValue();
            val += cards[i].getValue();
        }
        //ace is 11 if dealer's hands contains a card value of 11
        for(int value: arrayVal){
            if(val == 11){
                aceEleven = true;
            }      
        }
        //ace is one, however, if dealer's sum total exceeds 21. This is so the dealer would not get busted, and ace would be a one instead of a eleven
        if(val > 21 && aceEleven == true){
            while(val > 21 && aceEleven == true){
                aceEleven = false;
                for(int i = 0; i < length; i++){
                    if(arrayVal[i] == 11){
                        arrayVal[i] = 1;
                        val -= 10;
                        aceEleven = true;
                        break;
                    }
                }
            }
        }
        return val;
    }
}