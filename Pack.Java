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
        for (int i = 0; i < cardList.size(); i++) {
            System.out.println(cardList.get(i).getValue());
        }
    }
    public void distribute(Player[] ps, Deck[] deckList) {
        int n = ps.length;
        

        // CURRENTLY WORKING ON DISTRIBUTING CARDS TO PLAYERS
        //  in a round robin order (p1, p2, p3, p4) until each player has 4 cards
        for (int i = 0; i < n * 4; i++) {
            ps[i % n].addCard(cardList.remove(0));
        }

        // After the players' hands have been filled, fill the decks
        int i = 0;
        while (!cardList.isEmpty()) {
            deckList[i % n].addCard(cardList.remove(0));
            i++;
        }
    }
    // public static void main(String[] args) {
    //     Pack demo = new Pack();
    //     for (int i = 0; i < 40; i++) {
    //         demo.addCard(new Card(i));
    //     }

    //     Player[] pl = new Player[4];
    //     pl[0] = new Player();
    //     pl[1] = new Player();
    //     pl[2] = new Player();
    //     pl[3] = new Player();

    //     demo.distribute(pl);
        
    //     for (int i = 0; i < 4; i++) {
    //         pl[i].printCards();
    //     }
    // }
}