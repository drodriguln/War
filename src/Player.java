import java.util.Stack;

/**
 * Created by Daniel on 11/27/16.
 */
public class Player {

    private Stack deck;
    private Stack pile;

    public Stack getDeck() {
        return deck;
    }

    public void setDeck(Stack deck) {
        this.deck = deck;
    }

    public Stack getPile() {
        return pile;
    }

    public void setPile(Stack pile) {
        this.pile = pile;
    }

    public void pushToPile(Integer value) {
        pile.push(value);
    }

    public void popDeck() {
        deck.pop();
    }

    public Object getTopCard() {
        return deck.peek();
    }

    public void addPile(Stack<Integer> stack) {
        pile.addAll(stack);
    }

}
