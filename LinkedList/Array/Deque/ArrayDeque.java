import java.util.ArrayList;
import java.util.List;

// @Source Blue Tree Code from Youtube - Circular Queue Implementation - Array
// Link : https://www.youtube.com/watch?v=8sjFA-IX-Ww&t=322s

// @Source StackOverFlow - Herman Arroyo
// Link : https://stackoverflow.com/questions/20137487/queue-array-implementation-resize

// @Source StackExchange - Rocky
// Link: https://codereview.stackexchange.com/questions/129819/queue-resizing-array-implementation

// @Source Geeks for Geeks
// Link : https://www.geeksforgeeks.org/java-program-to-implement-circular-buffer/
public class ArrayDeque<T> implements Deque<T> {
    private T[] aDeque;
    private int _frontIndex;
    private int _lastIndex;
    private int size;
    private int starting = 8;

    public ArrayDeque() {
        aDeque = (T[]) new Object[starting];
        _frontIndex = 0;
        _lastIndex = 0; //considering first = last(circular)
        //size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */

    @Override
    public void addFirst(T x) {
        if (isFull()) {
            upsizing();
        }
        _frontIndex = (_frontIndex - 1 + aDeque.length) % aDeque.length; // updating 0 index
        aDeque[_frontIndex] = x;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (isFull()) {
            upsizing();
        }
        aDeque[_lastIndex] = x;
        _lastIndex = (_lastIndex + 1) % aDeque.length;
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter tne deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (!isEmpty()) {
            int curr = _frontIndex;
            for (int i = 0; i < size; i++) {
                returnList.add(aDeque[curr]);
                curr = (curr + 1) % aDeque.length;
            }
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {

        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {

        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T elementToRemove = aDeque[_frontIndex];
            _frontIndex = (_frontIndex + 1) % aDeque.length;
            size--;
            if (usageCheck()) {
                downsizing();
            }
            return elementToRemove;
        }
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            _lastIndex = ((_lastIndex - 1) + aDeque.length) % aDeque.length;
            T elementToRemove = aDeque[_lastIndex];
            size--;
            if (usageCheck()) {
                downsizing();
            }
            return elementToRemove;
        }
    }

    /**
     * The Deque abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element. Does
     * not alter the deque.
     *
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (!(index < 0 || index >= size)) {
            return aDeque[(_frontIndex + index) % aDeque.length];
        }
        return null;
    }
    public int lengthcheck() {
        return starting * 2;
    }
    public boolean usageCheck() {
        return (size <= aDeque.length * 0.25 && aDeque.length > lengthcheck());
    }
    public boolean isFull() {
        return size == aDeque.length;
    }
    public int resizefactor() {
        return 4;
    }
    public void upsizing() {
        T[] tempArray = (T[]) new Object[aDeque.length * resizefactor()];
        for (int i = 0; i < size; i++) {
            tempArray[i] = aDeque[(_frontIndex + i) % aDeque.length];
        }
        _frontIndex = 0;
        aDeque = tempArray;
        if (size == aDeque.length) {
            _lastIndex = 0;
        } else {
            _lastIndex = size;
        }
    }
    public void downsizing() {
        T[] tempArray = (T[]) new Object[aDeque.length / resizefactor()];
        for (int i = 0; i < size; i++) {
            tempArray[i] = aDeque[(_frontIndex + i) % aDeque.length];
        }
        _frontIndex = 0;
        aDeque = tempArray;
        if (size == aDeque.length) {
            _lastIndex = 0;
        } else {
            _lastIndex = size;
        }
    }
}
