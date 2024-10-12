import java.util.ArrayList;
import java.util.List;

// @Source Blue Tree Code - YouTube - Doubly Linked List | Insert, Delete, Complexity Analysis
// Link : https://www.youtube.com/watch?v=ZlNKNSz88Nk

// @Source - Neso Academy - Introduction to Doubly Linked List
// Link: https://www.youtube.com/watch?v=e9NG_a6Z0mg&list=PLBlnK6fEyqRg7pacSDMgPn7vDVhz3N1uf
public class LinkedListDeque<T> implements Deque<T> {
    private Node sentinel;
    private int size;
    private class Node {
        private T item;
        private Node prev;
        private Node next;


        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

    }

    public LinkedListDeque() {
        this.sentinel = new Node(null, null, null);
        this.sentinel.next = sentinel;
        this.sentinel.prev = sentinel;
        this.size = 0;
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel.next, sentinel);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        if (isEmpty()) {
            sentinel.prev = newNode;
        }
        size += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel, sentinel.prev);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter tne deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (isEmpty()) {
            return returnList;
        }

        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
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
        }
        Node curr = sentinel.next;
        T storeItem = curr.item;
        curr.next.prev = sentinel;
        sentinel.next = curr.next;
        size--;
        return storeItem;
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
        }
        Node curr = sentinel.prev;
        T storeItem = curr.item;
        curr.prev.next = sentinel;
        sentinel.prev = curr.prev;
        size -= 1;
        return storeItem;

    }

    /**
     * The Deque abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively.Does
     * not alter the deque.
     *
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node curr = sentinel.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively.
     *
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return helper(sentinel.next, index);
    }
    private T helper(Node target, int index) {
        if (index == 0) {
            return target.item;
        }
        return helper(target.next, index - 1);
    }
}
