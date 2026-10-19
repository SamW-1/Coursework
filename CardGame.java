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

        /**
         * This block takes the user input for the number of players
         * The try catch within the while loop ensures that the user input is retaken if it is not a valid integer
         */
        System.out.println("Please enter the number of players:");
        boolean valid = false;
        int playerCount = 0;
        while (!valid) {
            try {
                playerCount = Integer.parseInt(sc.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number of players:");
            }
        }
        

        // Decks and players are initialised based off of the number of platers inputted by the user
        Deck[] deckList = new Deck[playerCount];
        for (int i = 0; i < playerCount; i++) { deckList[i] = new Deck(); }

        // Initialise n player objects
        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player(deckList[i], deckList[(i+1) % playerCount]);
        }

        
        // Similarly to the number of players, the input pack is repeatedly asked for until it is valid
        //  (it is both a valid text file location and consists of only lines of integers)
        System.out.println("Enter the location of a pack to load:");
        valid = false;
        Pack pack = null;
        while (!valid) {
            String filename = sc.nextLine();
            pack = new Pack();
            
            try (BufferedReader input = new BufferedReader(new FileReader(filename))) {  
                // Error handling here needs to be completed: So that invalid file input exceptions are properly handled
                String line = input.readLine();

                while (line != null) {
                    Card c = new Card(Integer.parseInt(line));
                    pack.addCard(c);
                    
                    line = input.readLine();
                }

                valid = true;
            } catch (IOException e) {
                System.out.println("Enter a valid location of a pack to load");
            } catch (NumberFormatException e) {
                System.out.println("Enter the location of a valid pack to load");
            }
        }

        
        // Distribute cards from the top of the pack in a round robin fashion to the players and decks
        pack.distribute(players, deckList);
        
        // Create player text files, outputting initial hand card values
        for (int i = 0; i < playerCount; i++) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("player" + (i+1) + ".txt"))) {
                ArrayList<Card> initialHand = players[i].getHand();
                bufferedWriter.write("player " + (i+1) + " initial hand " + initialHand.get(0).getValue() + " " + initialHand.get(1).getValue() + " " + initialHand.get(2).getValue() + " " + initialHand.get(3).getValue());
                bufferedWriter.newLine();

            } catch (IOException e) {
                System.out.println("Error creating file!");
            }
        }

        // Begin player threads, creating an output file for each individual player
        ArrayList<Thread> threads = new ArrayList<>();
        for (Player p : players) {
            Thread thread = new Thread (p, "Player-" + p.getID());
            threads.add(thread);
            thread.start();
        }

        // Create n deck output files, outputting the contents of each deck into its respective text file
        for (int i = 0; i < deckList.length; i++) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("deck" + (i+1) + "_output.txt"))) {
                ArrayList<Card> contents = new ArrayList<>();
                while (!deckList[i].isEmpty()) {
                    contents.add(deckList[i].drawCard());
                }
                bufferedWriter.write("deck" + (i+1) + " contents: ");
                for (Card c : contents) {
                    bufferedWriter.write(c.getValue() + " ");
                }

            } catch (IOException e) {
                System.out.println("Error creating file!");
            }
        }
    }
}