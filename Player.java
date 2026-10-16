import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Player implements Runnable{
    private static int totalIDs;
    private final int id;
    private ArrayList<Card> hand;
    private Deck leftDeck;
    private Deck rightDeck;
    private Queue<Card> nonDenominationCards;

    public Player(Deck left, Deck right) {
        hand = new ArrayList<Card>();
        nonDenominationCards = new ArrayDeque<>();
        id = totalIDs;
        totalIDs++;
        leftDeck = left;
        rightDeck = right;
    }
    public void addCard(Card c) {
        hand.add(c);
        if (c.getValue() != id+1) { nonDenominationCards.add(c); }
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

    @Override
    public void run() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("player" + id + ".txt", true))) {
            /** 
             * If a player starts with a winning hand, the game is over
             * 
             * Whereas if the hand is not winning, the player thread atomically carries out the necessary actions:
             *      Draw a card from their left
             *      Discard a card to the right (making sure no non-denomination cards are kept in the hand indefinitely)
             */

            if (hand.get(0) == hand.get(1) && hand.get(1) == hand.get(2) && hand.get(2) == hand.get(3) && hand.get(3) == hand.get(4)) {
                // Initiate ending of the game
            } else {
                // Draw from left
                // If the drawn card is not of the same value as the player, it is added to the Queue of cards that can be discarded
                Card drawnCard = leftDeck.drawCard();
                hand.add(drawnCard);
                if (drawnCard.getValue() != id+1) { nonDenominationCards.add(drawnCard); }
                
                // Discard to the right
                for (int i = 0; i < hand.size(); i++) {
                    if (hand.get(i).equals(nonDenominationCards.peek())) 
                    { 
                        rightDeck.addCard(nonDenominationCards.poll()); 
                        hand.remove(i);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
    }
}