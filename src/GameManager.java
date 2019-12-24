import java.util.Date;

public class GameManager {

    private Dealer dealer;
    private Player player;
    private Cards cards;

    public GameManager(int numDecks, Dealer dealer, Player player) {
        this.cards = new Cards(numDecks);
        this.dealer = dealer;
        this.player = player;
    }

    public Card dealPlayers() {
        log("Dealing:");
        player.addCardToHand(cards.getCard());
        dealer.addCardToHand(cards.getCard());
        player.addCardToHand(cards.getCard());
        Card dealerUpCard = cards.getCard();
        dealer.addCardToHand(dealerUpCard);
        log("\tPLAYER:");
        player.printTotal();
        player.printHand();
        log("\n\tDEALER:");
        dealer.printTotal();
        dealer.printHand();
        return dealerUpCard;
    }

    public Action getPlayerDecision(Card upcard) {
        log("Asking Player Decision");
        Action decision = player.makeDecision(upcard);
        log("\tPlayer wants to " + decision);
        return decision;
    }

    public Action getDealerDecision() {
        log("Asking Dealer Decision");
        Action decision = dealer.makeDecision(null);
        log("\tDealer wants to " + decision);
        return decision;
    }

    public Card hitDealer() {
        log("Hitting Dealer");
        Card card = cards.getCard();
        log("\tDealer got " + card);
        dealer.addCardToHand(card);
        return card;
    }

    public Card hitPlayer() {
        log("Hitting Player");
        Card card = cards.getCard();
        log("\tPlayer got " + card);
        player.addCardToHand(card);
        return card;
    }

    public HandResult playHand() {
        boolean done = false;
        HandResult result = HandResult.IN_PROGRESS;
        Card upCard = dealPlayers();
        if (player.hasBlackjack()) {
            if (dealer.hasBlackjack()) {
                result = HandResult.BLACKJACK_PUSH;
            } else {
                result = HandResult.BLACKJACK_WIN;
            }
            done = true;
        } else if (dealer.hasBlackjack()) {
            result = HandResult.LOSE;
            done = true;
        }

        boolean doubled = false;
        boolean playerDone = false;
        while (!done && !playerDone) {
            Action playerAction = getPlayerDecision(upCard);
            if (playerAction == Action.STAY) {
               playerDone = true;
            } else if (playerAction == Action.HIT) {
                hitPlayer();
                if(player.isBust()) {
                    result = HandResult.LOSE;
                    playerDone = true;
                    done = true;
                }
            } else if (playerAction == Action.DOUBLE) {
                hitPlayer();
                doubled = true;
                playerDone = true;
                if(player.isBust()) {
                    result = HandResult.DOUBLE_LOSE;
                    done = true;
                }
            }

            // TODO: add double, split
        }

        boolean dealerDone = false;
        while (!done && !dealerDone) {
            Action dealerAction = getDealerDecision();
            if (dealerAction == Action.STAY) {
                dealerDone = true;
            } else {
                hitDealer();
                if (dealer.isBust()) {
                   result = (doubled) ? HandResult.DOUBLE_WIN : HandResult.WIN;
                   dealerDone = true;
                   done = true;
                }
            }
        }

        if (!done) {
            int playerTotal = player.getHandTotal().getKey();
            int dealerTotal = dealer.getHandTotal().getKey();
            log("\nPlayer ended with " + playerTotal + "\nDealer ended with " + dealerTotal);
            if (playerTotal > dealerTotal) {
               result = (doubled) ? HandResult.DOUBLE_WIN : HandResult.WIN;
            } else if (playerTotal == dealerTotal) {
                result = HandResult.PUSH;
            } else {
                result = (doubled) ? HandResult.DOUBLE_LOSE : HandResult.LOSE;
            }
        }
        log("Result of game: " + result);
        return result;
    }

    public void simulateHands(int num) {
        int longestStreak = 0;
        int currentStreak = 0;
        int longestLosingStreak = 0;
        int currentLosingStreak = 0;
        int totalWins = 0;
        int totalPushes = 0;
        int totalBlackjacks = 0;
        int doubleWins = 0;
        int doubleLosses = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i ++) {
            HandResult result = playHand();
            if (result == HandResult.WIN) {
                currentStreak++;
                currentLosingStreak = 0;
                totalWins++;
                player.updateBettingUnits(1);
            } else if (result == HandResult.BLACKJACK_WIN) {
                currentStreak++;
                currentLosingStreak = 0;
                totalWins++;
                totalBlackjacks++;
                player.updateBettingUnits(1.5f);
            } else if (result == HandResult.DOUBLE_WIN) {
                currentStreak++;
                currentLosingStreak = 0;
                totalWins++;
                doubleWins++;
                player.updateBettingUnits(2);
            } else if (result == HandResult.BLACKJACK_PUSH) {
                totalPushes++;
                totalBlackjacks++;
            } else if (result == HandResult.PUSH) {
                totalPushes++;
            } else {
                currentStreak = 0;
                currentLosingStreak++;
                if (result == HandResult.DOUBLE_LOSE) {
                    doubleLosses++;
                    player.updateBettingUnits(-2);
                } else {
                    player.updateBettingUnits(-1);
                }
            }

            if (currentStreak > longestStreak) {
                longestStreak = currentStreak;
            }
            if (currentLosingStreak > longestLosingStreak) {
                longestLosingStreak = currentLosingStreak;
            }

            resetPlayers();
        }
        long end = System.currentTimeMillis();
        System.out.println(num + " hands simulated in " + (end - start) + " milliseconds.");
        System.out.println("Win Percentage: " + (((float)totalWins / num) * 100) + "%");
        System.out.println("Blackjack Percentage: " + (((float)totalBlackjacks / num) * 100) + "%");
        System.out.println("Push Percentage: " + (((float)totalPushes / num) * 100) + "%");
        if (doubleLosses + doubleWins != 0) {
            System.out.println("Win to Loss Ratio on Double: " + (((float) doubleWins / (doubleLosses + doubleWins)) * 100) + "%");
        }
        System.out.println("Longest Win Streak: " + longestStreak);
        System.out.println("Longest Losing Streak: " + longestLosingStreak);
        System.out.println("Net Betting Units at End: " + player.getBettingUnits());
    }

    public void resetPlayers() {
        player.clearHand();
        dealer.clearHand();
    }

    private void log(String string) {
        //System.out.println(string);
    }

    public static void main(String[] args) {
        GameManager gm = new GameManager(4, new Dealer(), new Player());
        gm.simulateHands(20);
    }

}
