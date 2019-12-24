import javafx.util.Pair;

public class BasicStrategy extends Strategy {

    // could add two dimensional arrays of Actions for hard, soft, and splits
    // and return index of table[playerTotal][dealerTotal] but i think it's
    // easier to just make checks like this

    public Action makeDecision(Card upCard, Pair<Integer, Boolean> handTotal) {
        int total = handTotal.getKey();
        boolean isSoft = handTotal.getValue();

        if (!isSoft) {
            // hard total
            if (total <= 11) {
                return Action.HIT;
            } else if (total == 12) {
                if (upCard.getRank() > 3 && upCard.getRank() < 7) {
                    return Action.STAY;
                } else {
                    return Action.HIT;
                }
            } else if (total < 17) {
                if (upCard.getRank() < 7) {
                    return Action.STAY;
                } else {
                    return Action.HIT;
                }
            } else {
                return Action.STAY;
            }
        } else {
            // soft total
            if (total <= 17) {
                return Action.HIT;
            } else if (total == 18) {
                if (upCard.getRank() > 8) {
                    return Action.HIT;
                } else {
                    return Action.STAY;
                }
            } else {
                return Action.STAY;
            }
        }
    }

}
