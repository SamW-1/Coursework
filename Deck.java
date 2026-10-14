import java.util.ArrayDeque;
import java.util.Queue;
public class Deck { 
    private static int totalIDs = 0;
    private Queue<Card> cardQueue;
    private int id;

    public Deck() {
        cardQueue = new ArrayDeque<>();
        id = totalIDs;
        totalIDs++;
    }
    public void addCard(Card card) { cardQueue.add(card); }

    public Card drawCard() { return cardQueue.poll(); }

    public Card peekCard() { return cardQueue.peek(); }

    public int size() { return cardQueue.size(); }

    public boolean isEmpty() { return cardQueue.isEmpty(); }

}