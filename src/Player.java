import javafx.util.Pair;

import java.util.ArrayList;

public class Player {

    protected Strategy strategy;
    protected ArrayList<Card> hand;

    public Player() {
        this.strategy = new BasicStrategy();
        hand = new ArrayList<Card>();
    }

    public Player(Strategy strategy) {
        this.strategy = strategy;
        hand = new ArrayList<Card>();
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public Pair<Integer, Boolean> getHandTotal() {
        int total = 0;
        boolean hasAce = false;
        boolean isSoft = false;
        for (Card card : hand) {
            total += card.getRank();
            if (card.getRank() == 1) {
                hasAce = true;
            }
        }
        if (total < 12 && hasAce) {
            total += 10;
            isSoft = true;
        }
        return new Pair<Integer, Boolean>(total, isSoft);
    }

    public boolean hasBlackjack() {
        Pair<Integer, Boolean> total = getHandTotal();
        return total.getKey() == 21;
    }

    public boolean isBust() {
        Pair<Integer, Boolean> total = getHandTotal();
        return total.getKey() > 21;
    }

    public Action makeDecision(Card dealerUpCard) {
        return strategy.makeDecision(dealerUpCard, getHandTotal());
    }

    public void printTotal() {
        //System.out.println("\t\t" + getHandTotal());
    }

    public void printHand() {
        //System.out.println("\t\t" + hand);
    }

    public void clearHand() {
        hand.clear();
    }
}
