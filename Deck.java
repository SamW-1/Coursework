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
    public synchronized int getID() { return id; }

    public synchronized void addCard(Card card) { cardQueue.add(card); }

    public synchronized Card drawCard() { return cardQueue.poll(); }

    public synchronized Card peekCard() { return cardQueue.peek(); }

    public synchronized int size() { return cardQueue.size(); }

    public synchronized boolean isEmpty() { return cardQueue.isEmpty(); }

}