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
    public void printCards() {
        for (int i = 0; i < hand.size(); i++) {
            System.out.print(hand.get(i).getValue());
            System.out.print(' ');
        }
        System.out.println();
    }
}