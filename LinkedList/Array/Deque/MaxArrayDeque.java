package deque;

import java.util.Comparator;
import java.util.Iterator;

//@Source - Link : https://www.geeksforgeeks.org/how-compare-method-works-in-java/
public class MaxArrayDeque<T> extends ArrayDeque<T> {

    // Comparator
    private Comparator<T> comparator;

    // Constructor
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        Iterator<T> i = iterator();
        T maxItem = i.next();

        while (i.hasNext()) {
            T currItem = i.next();
            // @Source - Link : https://www.geeksforgeeks.org/how-compare-method-works-in-java/
            if (c.compare(maxItem, currItem) < 0) {
                maxItem = currItem;
            }
        }
        return maxItem;
    }
    public T max() {
        if (isEmpty()) {
            return null;
        }
        Iterator<T> i = iterator();
        T maxItem = i.next();
        while (i.hasNext()) {
            T currItem = i.next();
            if (comparator.compare(maxItem, currItem) < 0) {
                maxItem = currItem; //Update max
            }
        }
        return maxItem;
    }
}
