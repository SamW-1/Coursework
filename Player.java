import java.util.ArrayList;

public class Player {
    private static int totalIDs;
    private int id;
    private ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
        id = totalIDs;
        totalIDs++;
    }
    public void addCard(Card c) {
        hand.add(c);
    }
    public void discard() {
        // Implement logic to decide and discard the correct card
    }
}