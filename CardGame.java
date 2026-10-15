import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class CardGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the number of players:");
        int playerCount = Integer.parseInt(sc.nextLine());

        // Initialise n player objects
        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player();
        }

        System.out.println("Enter the location of a pack to load:");
        String filename = sc.nextLine();

        Pack pack = new Pack();
        try {  
            // Error handling here needs to be completed: So that invalid file input exceptions are properly handled
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String line = input.readLine();

            while (line != null) {
                Card c = new Card(Integer.parseInt(line));
                pack.addCard(c);
                
                line = input.readLine();
            }

            input.close();

        // Distribute cards from the top of the pack in a round robin fashion to the players and decks
        Deck[] decks = pack.distribute(players);

        // Begin player threads, creating an output file for each individual player


        } catch (IOException e) {
            System.out.println("Error: File not found.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid file contents");
        }

        


    }
}