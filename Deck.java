import java.util.PriorityQueue;
import java.util.Queue;
public class Deck { 
    private static int totalIDs = 0;
    private Queue<Card> cardQueue;
    private int id;

    public Deck() {
        cardQueue = new PriorityQueue<>();
        id = totalIDs;
        totalIDs++;
    }
    public void addCard(Card card) { cardQueue.add(card); }
}