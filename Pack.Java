import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Pack {
    private ArrayList<Card> cardList;
    private Deck[] deckList;

    public Pack() {
        cardList = new ArrayList<>();
    }

    public void addCard(Card card) {
        cardList.add(card);
    }
    public void printCards() {
        for (int i = 0; i < cardList.size()) {
            System.out.println(cardList.get(i));
        }
    }
    public void distribute(n) {
        deckList = new Deck[n];
        for (int i = 0; i < n; i++) { deckList[i] = new Deck(); }

        for (int i = 0; i < n * 4; i++;) {
            
        }
    }
}