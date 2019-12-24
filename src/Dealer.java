public class Dealer extends Player {

    public Dealer() {
        strategy = new DealerStrategy(false);
    }

}
