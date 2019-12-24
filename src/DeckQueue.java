import java.util.ArrayList;
import java.util.Collections;

public class DeckQueue<T> extends ArrayList<T> {

    public T pop() {
        T t = get(0);
        remove(0);
        return t;
    }

    public void shuffle() {
        Collections.shuffle(this);
    }
}
