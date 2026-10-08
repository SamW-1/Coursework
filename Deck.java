import java.util.ArrayList;
public class Deck { 
    private static int totalIDs = 0;
    private ArrayList<Card> cardList;
    private int id;

    public Deck() {
        cardList = new ArrayList<>();
        id = totalIDs;
        totalIDs++;
    }
    public void addCard(Card card) { cardList.add(card); }
}