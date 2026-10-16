import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class CardGame {
    public static void main(String[] args) {

        /** 
         * IMPORTANT NOTE:
         * Since players and decks are defined within code using 0-based indexing (as that's how arrays operate in java),
         * whenever outputting information about players or decks or carrying out card logic, the indexes of players and
         * decks need to be incremented by 1 to be consistent with the logical 1 based system the game implements
         * 
         */

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the number of players:");
        int playerCount = Integer.parseInt(sc.nextLine());

        Deck[] deckList = new Deck[playerCount];
        for (int i = 0; i < playerCount; i++) { deckList[i] = new Deck(); }

        // Initialise n player objects
        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player(deckList[i], deckList[(i+1) % playerCount]);
            System.out.println("Left: " + i + " Right: " + (i+1) % playerCount);
        }

        

        System.out.println("Enter the location of a pack to load:");
        String filename = sc.nextLine();

        Pack pack = new Pack();
        try (BufferedReader input = new BufferedReader(new FileReader(filename))) {  
            // Error handling here needs to be completed: So that invalid file input exceptions are properly handled
            String line = input.readLine();

            while (line != null) {
                Card c = new Card(Integer.parseInt(line));
                pack.addCard(c);
                
                line = input.readLine();
            }

            input.close();

        // Distribute cards from the top of the pack in a round robin fashion to the players and decks
        pack.distribute(players, deckList);
        
        // Initialise player text files 
        for (int i = 0; i < playerCount; i++) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("player" + i+1 + ".txt"))) {
                ArrayList<Card> initialHand = players[i].getHand();
                bufferedWriter.write("player " + i+1 + " initial hand " + initialHand.get(0) + " " + initialHand.get(1) + " " + initialHand.get(2) + " " + initialHand.get(3));
                bufferedWriter.newLine();

                bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("Error creating file!");
            }
        }

        // Begin player threads, creating an output file for each individual player
        for (Player player : players) {
            System.out.println("Testing");
        }


        } catch (IOException e) {
            System.out.println("Error: File not found.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid file contents");
        }

        


    }
}