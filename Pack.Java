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

        // CURRENTLY WORKING ON DISTRIBUTING CARDS TO PLAYERS
        //  in a round robin order (p1, p2, p3, p4) until each player has 4 cards
        for (int i = 0; i < n * 4; i++;) {
            deckList[i % n]
        }
    }
}