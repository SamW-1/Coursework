import java.util.ArrayDeque;
import java.util.Queue;
public class Deck { 
    private static int totalIDs = 0;
    private Queue<Card> cardQueue;
    private final int id;

    public Deck() {
        cardQueue = new ArrayDeque<>();
        id = totalIDs;
        totalIDs++;
    }
    public int getID() { return id; }

    public synchronized void addCard(Card card) { cardQueue.add(card); }

    public Card drawCard() { return cardQueue.poll(); }

    public Card peekCard() { return cardQueue.peek(); }

    public int size() { return cardQueue.size(); }

    public boolean isEmpty() { return cardQueue.isEmpty(); }

}