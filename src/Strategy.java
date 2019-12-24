import javafx.util.Pair;

public abstract class Strategy {

    public abstract Action makeDecision(Card dealerUpCard, Pair<Integer, Boolean> handTotal);

}
