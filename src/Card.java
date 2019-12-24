public class Card {

    private String suit;
    private int rank;

    public Card(String suit, int rank) {
        this.suit = suit;
        this.rank =  rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public String toString() {
        String result = "";
        result += Integer.toString(getRank()) + " of " + getSuit();
        return result;
    }

}
