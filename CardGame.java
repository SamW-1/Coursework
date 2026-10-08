import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
public class CardGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the number of players:");
        int playerCount = Integer.parseInt(sc.nextLine());

        System.out.println("Enter the location of a pack to load:");
        String filename = sc.nextLine();

        Pack pack = new Pack();
        try (BufferedReader input = new BufferedReader(new FileReader(filename))) {

            String line = input.readLine();
            while (line != null) {
                Card c = new Card(Integer.parseInt(line));
                pack.addCard(c);
                
                line = input.readLine();
            }

            input.close();
        } catch (IOException e) {
            System.out.println("Whoops");
        }

        pack.printCards();
    }
}