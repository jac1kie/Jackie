package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// @Source StackOverFlow - Taryn
// Link: https://stackoverflow.com/questions/28844026/writing-an-equals-method-for-linked-list-object

// @Source StackOverFlow - Yash
// Link : https://stackoverflow.com/questions/47308217/equals-method-comparison-for-two-different-list-implementations
public class ArrayDeque<T> implements Deque<T> {
    private T[] aDeque;
    private int _frontIndex;
    private int _lastIndex;
    private int size;
    private int starting = 8;

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    private class ArrayDequeIterator implements Iterator<T> {

        private int curr = _frontIndex;

        @Override
        public boolean hasNext() {
            return curr != _lastIndex;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T currItem = aDeque[curr];
            curr = (curr + 1) % aDeque.length;
            return currItem;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Deque)) {
            return false;
        }
        Deque<T> otherDeque = (Deque<T>) other;
        if (this.size != otherDeque.size()) {
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            T elem = this.get(i);
            T elemOther = otherDeque.get(i);
            if (elem == null && elemOther == null) {
                continue;
            }
            if (elem == null && elemOther != null || elem != null && elemOther == null) {
                return false;
            }
            if (!elem.equals(elemOther)) {
                return false;
            }

        }
        return true;
    }

    @Override
    public String toString() {
        List<String> lst = new ArrayList<>();
        for (T elem : this) {
            lst.add(elem.toString());
        }
        return lst.toString();
    }

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
            aDeque[_frontIndex] = null;
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
            aDeque[_lastIndex] = null;
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
    @Override
    public T getRecursive(int index) {
        return get(index);
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

