import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Cards {

    private DeckQueue<Card> cards;
    private int numDecks;

    public Cards(int numDecks) {
        assert (numDecks > 0);
        this.numDecks = numDecks;
        cards = initiateCards(numDecks);
    }

    private DeckQueue<Card> initiateCards(int decks) {
        DeckQueue<Card> result = new DeckQueue<>();

        String[] suits = { "hearts", "spades", "diamonds", "clubs" };
        int[] ranks = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10 };
        for (int i = 0; i < decks; i++) {
            for (int j = 0; j < suits.length; j++) {
                for (int k = 0; k < ranks.length; k++) {
                    result.add(new Card(suits[j], ranks[k]));
                }
            }
        }
        result.shuffle();
        return result;
    }

    public Card getCard() {
        if (cards.size() < (52 * numDecks) / 2) {
            cards.clear();
            cards = initiateCards(numDecks);
        }
        return cards.pop();
    }
}
