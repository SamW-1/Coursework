import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Card {
    private final int value;
    
    public Card(int value) {
        this.value = value;
    }
    public int getValue() { return value; }

    public static void main(String[] args) {
        int id = 1;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("player" + id + ".txt"))) {
            bufferedWriter.write("this is a test");
            bufferedWriter.newLine();
            bufferedWriter.write("Is this another line??");
            bufferedWriter.append("ooh maybe this works instead");

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error: File not found!");
        }
    }
}