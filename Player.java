import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Player implements Runnable{
    private static int totalIDs;
    private final int id;
    private final ArrayList<Card> hand;
    private final Deck leftDeck;
    private final Deck rightDeck;
    private final Queue<Card> nonDenominationCards;

    // Volatile makes sure the threads can see whether gameOver or winningPlayer have changed
    // Using volatile, the variable becomes visible to all readers (other threads) immediately after a write operation

    // As well as this, AtomicBoolean is used to ensure that gameOver is checked and set in the same atomic action
    //  when a player has won the game to remove the possibility of another thread winning in the time between getting 
    //  and setting sequentially
    public static volatile AtomicBoolean gameOver = new AtomicBoolean(false);
    public static volatile Player winningPlayer;
    private Boolean pWon = false;

    public Player(Deck left, Deck right) {
        hand = new ArrayList<>();
        nonDenominationCards = new ArrayDeque<>();
        id = totalIDs;
        totalIDs++;
        leftDeck = left;
        rightDeck = right;
    }
    public int getID() { return id; }
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
    public ArrayList<Card> getHand() { return hand; } 
    
    @Override
    public void run() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("player" + (id+1) + ".txt", true))) {
            /** 
             * If a player starts with a winning hand, the game is over
             * 
             * Whereas if the hand is not winning, the player thread atomically carries out the necessary actions:
             *      Draw a card from their left
             *      Discard a card to the right (making sure no non-denomination cards are kept in the hand indefinitely)
             * 
             * Implement using a while loop which carries out player actions unless the player has a winning hand (or the thread is stopped otherwise)
             */
            
            pWon = hand.get(0).getValue() == hand.get(1).getValue() && hand.get(1).getValue() == hand.get(2).getValue() && hand.get(2).getValue() == hand.get(3).getValue();
            while (!gameOver.get() && !pWon) {
                // Draw from left
                // If the drawn card is not of the same value as the player, it is added to the Queue of cards that can be discarded
                Card drawnCard = leftDeck.drawCard();

                // This block ensures that when a player (thread) attempts to draw a card from the deck to its left, 
                // If the deck is empty (because other threads have withdrawn cards faster) then onSpinWait signals to the CPU
                //  that the current thread is waiting but is also doing work (checking whether the game is over) so that the thread gets less processor time
                if (drawnCard == null) {
                    Thread.onSpinWait();
                    continue;
                }
                hand.add(drawnCard);
                if (drawnCard.getValue() != id+1) { nonDenominationCards.add(drawnCard); }
                bufferedWriter.write("player " + (id+1) + " draws a " + drawnCard.getValue() + " from deck " + (leftDeck.getID()+1));
                bufferedWriter.newLine();
                

                // Discard to the right
                for (int i = 0; i < hand.size(); i++) {
                    if (hand.get(i).equals(nonDenominationCards.peek())) { 
                        rightDeck.addCard(nonDenominationCards.poll()); 
                        Card r = hand.remove(i);
                        bufferedWriter.write("player " + (id+1) + " discards a " + r.getValue() + " to deck " + (rightDeck.getID()+1));
                        bufferedWriter.newLine();

                        break;
                    }
                }
            

                // Output current hand 
                bufferedWriter.write("player " + (id+1) + " current hand is " + hand.get(0).getValue() + " " + hand.get(1).getValue() + " " + hand.get(2).getValue() + " " + hand.get(3).getValue());
                bufferedWriter.newLine();

                pWon = hand.get(0).getValue() == hand.get(1).getValue() && hand.get(1).getValue() == hand.get(2).getValue() && hand.get(2).getValue() == hand.get(3).getValue();
            }

            /**
             * Case where current player wins
             * Break out of current loop and inform other threads that the game is over
             * 
             * Otherwise, gameOver has been set by another thread (another thread has won) so 
             *  the current thread has lost, carrying out the required processes for that
             */
            if (pWon && gameOver.compareAndSet(false, true)) {

                winningPlayer = this;

                bufferedWriter.write("player " + (id+1) + " wins");
                bufferedWriter.newLine();
                bufferedWriter.write("player " + (id+1) + " exits");
                bufferedWriter.newLine();
                bufferedWriter.write("player " + (id+1) + " final hand: " + hand.get(0).getValue() + " " + hand.get(1).getValue() + " " + hand.get(2).getValue() + " " + hand.get(3).getValue());
                bufferedWriter.newLine();

                System.out.println("player" + (id+1) + " wins");
            } else {

                bufferedWriter.write("player " + (winningPlayer.getID()+1) + " informed player " + (id+1) + " that player " + (winningPlayer.getID()+1) + " has won");
                bufferedWriter.newLine();
                bufferedWriter.write("player " + (id+1) + " exits");
                bufferedWriter.newLine();
                bufferedWriter.write("player " + (id+1) + " hand: " + hand.get(0).getValue() + " " + hand.get(1).getValue() + " " + hand.get(2).getValue() + " " + hand.get(3).getValue());
                bufferedWriter.newLine();
            }  
            
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
    }
}