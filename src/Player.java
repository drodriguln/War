import java.util.Queue;

/**
 * Created by Daniel A. Rodriguez on 11/27/16.
 */

public class Player {

    private Queue deck;
    private Queue pile;

    public Queue getDeck() {
        return deck;
    }

    public void setDeck(Queue deck) {
        this.deck = deck;
    }

    public Queue getPile() {
        return pile;
    }

    public void setPile(Queue pile) {
        this.pile = pile;
    }

    public void addToPile(Integer value) {
        pile.add(value);
    }

    public void removeCard() {
        deck.remove();
    }

    public Object getTopCard() {
        return deck.peek();
    }

    public void addPile(Queue<Integer> queue) {
        pile.addAll(queue);
    }

}
