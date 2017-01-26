import java.util.*;

/**
 * Created by Daniel A. Rodriguez on 11/27/16.
 *
 * NOTES:
 * - Suits will be disregarded since War does not rely on them.
 * - This program is strictly for two players.
 * - No user interaction. The program runs through a simulation of the game.
 *
 * CARD CODESET:
 * 11 = Jack
 * 12 = Queen
 * 13 = King
 * 14 = Ace
 **/

public class Main {

    public static void main(String args[]) {

        //Declare player objects
        Player playerOne = new Player();
        Player playerTwo = new Player();

        //Creates the deck of cards with four of each type (52 cards total)
        Integer[] initializedDeck = initializeDeck();

        //Shuffles the deck. The array is converted into an ArrayList.
        List<Integer> shuffledDeck = shuffleDeck(initializedDeck);

        //The shuffled deck is split in half for each player. Returned as a Stack.
        playerOne.setDeck(splitDeck(shuffledDeck, 0, shuffledDeck.size()/2));
        playerTwo.setDeck(splitDeck(shuffledDeck, shuffledDeck.size()/2, shuffledDeck.size()));

        //Initiate game.
        beginGame(playerOne, playerTwo);

    }

    public static Integer[] initializeDeck() {

        //Declare array of Bicycle deck and initialize card value.
        Integer[] bicycleDeck = new Integer[52];
        int card = 2;

        //Fill array with 52 cards of 13 different values (four for each value).
        for (int i = 0; i < bicycleDeck.length; i++) {
            if (i != 0 && i % 4 == 0) { card++; } //Every 4 cards, increment the card value.
            bicycleDeck[i] = card;
        }

        return bicycleDeck;

    }

    public static List<Integer> shuffleDeck(Integer[] bicycleDeck) {

        //Convert Integer array into an ArrayList.
        List<Integer> deckList = new ArrayList<Integer>(Arrays.asList(bicycleDeck));

        //Shuffle ArrayList
        Collections.shuffle(deckList);

        return deckList;

    }

    public static Stack splitDeck(List<Integer> deckList, int startIndex, int endIndex) {

        //Splits the ArrayList into a sublist with the specified parameters (cuts the deck in half).
        List<Integer> splitDeck = deckList.subList(startIndex, endIndex);

        //Adds the ArrayList values into a Stack.
        Stack<Integer> stack = new Stack<>();
        stack.addAll(splitDeck);

        return stack;

    }

    public static void beginGame(Player one, Player two) {

        //Initialize the top card values and the player pile objects.
        int cardOne;
        int cardTwo;
        one.setPile(new Stack<Integer>());
        two.setPile(new Stack<Integer>());

        //Loops while at least one of the players has a card in either the deck or the pile.
        while (!(one.getDeck().isEmpty() && one.getPile().isEmpty()) && !(two.getDeck().isEmpty() && two.getPile().isEmpty())) {

            //Store the generic Objects from the top of the Stack (deck) into casted Integer variables.
            cardOne = (Integer) one.getTopCard();
            cardTwo = (Integer) two.getTopCard();

            //Compare players' cards.
            if (cardOne > cardTwo) {
                //CASE: Player one wins. Adds values of both players' top cards to own pile,
                //      then removes the values from both respective decks.
                System.out.println(cardOne + "\t>\t" + cardTwo + "\t--->\t" + "PLAYER ONE: Deck\t" + one.getDeck().size() + "\tPile\t" + one.getPile().size() + "\t|\t" + "PLAYER TWO: Deck\t" + two.getDeck().size() + "\tPile\t" + two.getPile().size());
                two.pushToPile(cardOne);
                two.pushToPile(cardTwo);
                one.popDeck();
                two.popDeck();
            } else if (cardOne < cardTwo) {
                //CASE: Player two wins. Adds values of both players' top cards to own pile,
                //      then removes the values from both respective decks.
                System.out.println(cardOne + "\t<\t" + cardTwo + "\t--->\t" + "PLAYER ONE: Deck\t" + one.getDeck().size() + "\tPile\t" + one.getPile().size() + "\t|\t" + "PLAYER TWO: Deck\t" + two.getDeck().size() + "\tPile\t" + two.getPile().size());
                one.pushToPile(cardOne);
                one.pushToPile(cardTwo);
                one.popDeck();
                two.popDeck();
            } else if (cardOne == cardTwo) {
                //CASE: A draw. Both players must initiate War (see "startWar" method for details).
                //      If either of the players do not have at least one extra cards in their decks, the pile is added then shuffled.
                System.out.println(one.getTopCard() + "\t==\t" + two.getTopCard() + "\t--->\t" + "PLAYER ONE: Deck\t" + one.getDeck().size() + "\tPile\t" + one.getPile().size() + "\t|\t" + "PLAYER TWO: Deck\t" + two.getDeck().size() + "\tPile\t" + two.getPile().size());
                if (one.getDeck().size() <= 2 && !one.getPile().isEmpty()) {
                    addPileToDeckForWar(one);
                    System.out.println("ADDED PILE\t--->\tPLAYER ONE: Deck\t" + one.getDeck().size() + "\tPile\t" + one.getPile().size());
                }
                if (two.getDeck().size() <= 2 && !two.getPile().isEmpty()) {
                    addPileToDeckForWar(two);
                    System.out.println("ADDED PILE\t--->\tPLAYER TWO: Deck\t" + two.getDeck().size() + "\tPile\t" + two.getPile().size());
                }
                if (one.getDeck().size() > 2 && two.getDeck().size() > 2) {
                    System.out.println("\tWAR!\t--->\tPLAYER ONE: Deck\t" + one.getDeck().size() + "\tPile\t" + one.getPile().size() + "\t|\t" + "PLAYER TWO: Deck\t" + two.getDeck().size() + "\tPile\t" + two.getPile().size());
                    startWar(one, two);
                } else if (one.getDeck().size() <= 2) {
                    //Not enough cards for a War. Default to game over for player one.
                    System.out.println("PLAYER ONE RAN OUT OF CARDS. ENDING GAME.");
                    two.addPile(one.getDeck());
                    one.setDeck(new Stack<Integer>());
                    one.setPile(new Stack<Integer>());
                } else if (two.getDeck().size() <= 2) {
                    //Not enough cards for a War. Default to game over for player two.
                    System.out.println("PLAYER TWO RAN OUT OF CARDS. ENDING GAME.");
                    one.addPile(two.getDeck());
                    two.setDeck(new Stack<Integer>());
                    two.setPile(new Stack<Integer>());
                }
            }

        //If either players' deck is empty, then add the cards from the pile to it and create a new pile object. The deck is shuffled.
        if (one.getDeck().isEmpty() && !one.getPile().isEmpty()) { addPileToDeck(one); }
        if (two.getDeck().isEmpty() && !two.getPile().isEmpty()) { addPileToDeck(two); }

    }

        //Print out game stats.
        System.out.println();
        System.out.println("-- GAME OVER --");
        if (one.getDeck().isEmpty()) { System.out.println("PLAYER TWO WINS!"); }
        else if (two.getDeck().isEmpty()) { System.out.println("PLAYER ONE WINS!"); }
        else { System.out.println("THE GAME ENDS IN A DRAW!"); }
        System.out.println();
        System.out.println("-- FINAL STATS --");
        System.out.println("PLAYER ONE:");
        System.out.println(" - Cards Left in Deck = " + one.getDeck().size());
        System.out.println(" - Cards in Pile = " + one.getPile().size());
        System.out.println("PLAYER TWO:");
        System.out.println(" - Cards Left in Deck = " + two.getDeck().size());
        System.out.println(" - Cards in Pile = " + two.getPile().size());

    }

    public static void startWar(Player one, Player two) {

        //Create new stacks specifically for comparing War cards.
        Stack warOne = new Stack<Integer>();
        Stack warTwo = new Stack<Integer>();

        //Store matched cards used to initiate War into the War stack, then remove the values from their respective decks.
        warOne.add(one.getTopCard());
        warTwo.add(two.getTopCard());
        one.popDeck();
        two.popDeck();

        //Boolean used to control War while loop.
        boolean warWon = false;

        //Initialize variable for storing top-facing War cards.
        int topWarCardOne;
        int topWarCardTwo;

        //Begin War loop.
        while (!warWon) {

            //If player one has enough cards (at least two), proceed normally. Otherwise pull just one card.
            if (one.getDeck().size() >= 2) {
                warOne.push(one.getTopCard());
                one.popDeck();
                warOne.push(one.getTopCard());
                one.popDeck();
            } else {
                warOne.push(one.getTopCard());
                one.popDeck();
            }

            //If player two has enough cards (at least two), proceed normally. Otherwise pull just one card.
            if (two.getDeck().size() >= 2) {
                warTwo.push(two.getTopCard());
                two.popDeck();
                warTwo.push(two.getTopCard());
                two.popDeck();
            } else {
                warTwo.push(two.getTopCard());
                two.popDeck();
            }

            //Assign top-facing card values from each respective war stack to variables for comparisons.
            topWarCardOne = (Integer) warOne.peek();
            topWarCardTwo = (Integer) warTwo.peek();

            //Determines which player wins the War. The winner takes both War piles.
            if (topWarCardOne > topWarCardTwo) {
                one.addPile(warOne);
                one.addPile(warTwo);
                warWon = true;
            } else if (topWarCardOne < topWarCardTwo) {
                two.addPile(warOne);
                two.addPile(warTwo);
                warWon = true;
            } else if (topWarCardOne == topWarCardTwo && one.getDeck().isEmpty()) {
                two.addPile(warOne);
                two.addPile(warTwo);
                warWon = true;
            } else if (topWarCardOne == topWarCardTwo && two.getDeck().isEmpty()) {
                one.addPile(warOne);
                one.addPile(warTwo);
                warWon = true;
            }

        }

    }

    public static void addPileToDeck(Player player) {
        player.setDeck(player.getPile());
        Collections.shuffle(player.getDeck());
        player.setPile(new Stack<Integer>());
    }

    public static void addPileToDeckForWar(Player player) {
        //Store last card to variable, shuffle the pile, add the last card to the pile,
        //then add the pile to the remaining deck.
        int lastCard = (Integer) player.getTopCard();
        player.popDeck();
        Collections.shuffle(player.getPile());
        if (!player.getDeck().isEmpty()) { player.addPile(player.getDeck()); }
        player.pushToPile(lastCard);
        player.setDeck(player.getPile());
        player.setPile(new Stack<Integer>());
    }

}
