// Class: BlackjackRunner
// Purpose: Runner class used to initiate and play blackjack
// @author Austin Wang
// @version December 2020

import static java.lang.System.*;
import java.util.Scanner;

public class BlackjackRunner
{
    public static void main(String[] args)
    {
        boolean Play = true;
        // create a new scanner object
        int counts = 0;
        Scanner input = new Scanner(System.in);
        String continuePlaying;
        // create a new instance of the game class
        Blackjack cardgame = new Blackjack();
        System.out.println("Welcome to BlackJack! (this is only for fun, not betting !!)");   
        // write a loop to keep playing until player wants to stop
        do{
            cardgame.playRound();
            counts++;
            //tells dealer when to shuffle
            if(counts%5==0) {
                System.out.println("\n@@@@@@ Wait a sec, shuffling deck @@@@@@");
                cardgame.shuffleCard();
            }
            //asks player whether or not to continue playing
            System.out.print("Wow, that was fun! You want to play again? ");
            continuePlaying = input.nextLine();
            //checks input of player, no or yes to continue playing
            //if player equals something other than n or y, the code asks again until n or y is entered
            while(!continuePlaying.equals("y") && !continuePlaying.equals("n")) {
                System.out.print("Please enter n or y ");
                continuePlaying = input.nextLine();
                if(continuePlaying.equals("n") || continuePlaying.equals("y")) {
                break;
                }  
            }
            if(continuePlaying.equals("n")) {
                break;
            }           
        } while(continuePlaying.equals("y"));
       System.out.println("That was fun, come back if you want more practices!");
    }
}