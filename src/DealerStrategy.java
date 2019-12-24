import javafx.util.Pair;

public class DealerStrategy extends Strategy {

    private boolean hitSoft17;

    public DealerStrategy(boolean hitSoftSeventeen) {
        hitSoft17 = hitSoftSeventeen;
    }

    public Action makeDecision(Card dealerUpCard, Pair<Integer, Boolean> handTotal) {
        if(handTotal.getKey() < 17) {
            return Action.HIT;
        } else if (hitSoft17 && handTotal.getKey() == 17 && handTotal.getValue()){
            return Action.HIT;
        } else {
            return Action.STAY;
        }
    }

}
