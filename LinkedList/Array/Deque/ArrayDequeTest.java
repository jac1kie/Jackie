import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    @Test
    public void testAddingAfterRemoveToEmpty() {
        ArrayDeque<String> array = new ArrayDeque<>();

        array.addFirst("item1");
        array.addLast("item2");

        assertThat(array.removeFirst()).isEqualTo("item1");
        assertThat(array.removeLast()).isEqualTo("item2");

        assertThat(array.isEmpty()).isTrue();

        array.addFirst("item3");
        assertThat(array.get(0)).isEqualTo("item3");

        array.removeFirst();
        assertThat(array.isEmpty()).isTrue();

        array.addLast("item4");
        assertThat(array.isEmpty()).isFalse();
        assertThat(array.get(0)).isEqualTo("item4");
    }


    @Test
    void testaddFirst() {
        ArrayDeque<String> array1 = new ArrayDeque<>();
        array1.addFirst("A");
        array1.addFirst("B");
        array1.addFirst("C");
        array1.addFirst("D");
        array1.addFirst("E");
        assertThat(array1.toList()).containsExactly("E", "D", "C", "B", "A").inOrder();


    }
    @Test
    public void addFirstAndAddLastTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);
        lld1.addFirst(10);
        lld1.addLast(4);
        assertThat(lld1.toList()).containsExactly(10, -2, -1, 0, 1, 2, 4).inOrder();
    }
    @Test
    void testaddLast() {
        ArrayDeque<String> lld1 = new ArrayDeque<>();

        lld1.addLast("first");
        assertThat(lld1.get(0)).isEqualTo("first");
        lld1.addLast("middle");
        assertThat(lld1.get(1)).isEqualTo("middle");
        lld1.addLast("third");
        assertThat(lld1.get(2)).isEqualTo("third");
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    void toListEmpty() {
        ArrayDeque<String> array1 = new ArrayDeque<>();

        assertThat(array1.isEmpty()).isTrue();
        assertThat(array1.toList().isEmpty()).isTrue();

    }

    @Test
    void isEmpty() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        Deque<Integer> lld2 = new ArrayDeque<>();
        lld1.addFirst(0);
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld2.isEmpty()).isTrue();
    }

    @Test
    void size() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(1);
        lld1.addLast(10);
        lld1.addFirst(3);
        assertThat(lld1.size() == 1).isFalse();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    void removeFirst() {
        ArrayDeque<String> array1 = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            array1.addLast("num" + i);
        }
        assertThat(array1.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            assertThat(array1.removeFirst()).isEqualTo("num" + i);
        }
    }

    @Test
    public void testRemoveEmpty() {
        ArrayDeque<String> array1 = new ArrayDeque<>();
        assertThat(array1.removeFirst()).isNull();
        assertThat(array1.removeLast()).isNull();
    }

    @Test
    void removeLast() {
        ArrayDeque<String> array1 = new ArrayDeque<>();

        array1.addLast("first");
        array1.addLast("middle");
        array1.addLast("last");

        assertThat(array1.removeLast()).isEqualTo("last");
        assertThat(array1.removeLast()).isEqualTo("middle");
        assertThat(array1.removeLast()).isEqualTo("first");

        for (int i = 0; i < 9; i++) {
            array1.addLast("num" + i);
        }
        assertThat(array1.removeLast()).isEqualTo("num8");

    }

    @Test
    void get() {
        ArrayDeque<String> array1 = new ArrayDeque<>();
        assertThat((array1.get(-1))).isNull();
        assertThat((array1.get(8))).isNull();
        array1.addFirst("A");
        array1.addLast("B");
        array1.addFirst("C");
        array1.addLast("D");
        array1.addFirst("E");

        assertThat(array1.get(0)).isEqualTo("E");
        assertThat(array1.get(1)).isEqualTo("C");
        assertThat(array1.get(2)).isEqualTo("A");
        assertThat(array1.get(3)).isEqualTo("B");
        assertThat(array1.get(4)).isEqualTo("D");
        assertThat((array1.get(5))).isNull();
    }

    @Test
    void sizingUp() {
        ArrayDeque<String> array1 = new ArrayDeque<>();
        for (int i = 0; i < 50; i++) {
            array1.addLast("num" + i);
        }
        assertThat(array1.size()).isEqualTo(50);
        assertThat(array1.removeFirst()).isEqualTo("num0");
    }

    @Test
    void downsizing() {
        ArrayDeque<String> array1 = new ArrayDeque<>();
        for (int i = 0; i < 256; i++) {
            array1.addLast("num" + i);
        }
        for (int i = 0; i < 61; i++) {
            array1.removeFirst();
        }

        assertThat(array1.size()).isEqualTo(195);
        assertThat(array1.removeFirst()).isEqualTo("num61");
    }

    @Test
    void size_after_remove_from_empty() {
        ArrayDeque<String> array1 = new ArrayDeque<>();
        assertThat(array1.removeFirst()).isNull();
        assertThat(array1.size()).isEqualTo(0);
    }

    @Test
    void size_after_remove_to_empty() {
        ArrayDeque<String> array1 = new ArrayDeque<>();
        array1.addFirst("1");
        array1.addLast("2");
        array1.removeFirst();
        array1.removeFirst();
        assertThat(array1.size()).isEqualTo(0);
    }

    @Test
    void GradeScope(){
        ArrayDeque<Integer> ArrayDeque = new ArrayDeque<>();
        ArrayDeque.addLast(0);
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(2);
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(4);
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(6);
        ArrayDeque.addLast(7);
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(9);
        ArrayDeque.addLast(10);
        ArrayDeque.get(2);
        ArrayDeque.addLast(12);
        ArrayDeque.get(3);
        ArrayDeque.addLast(14);
        ArrayDeque.addLast(15);
        ArrayDeque.addFirst(16);
        ArrayDeque.addLast(17);
        ArrayDeque.removeLast();
        ArrayDeque.addLast(19);
        ArrayDeque.addFirst(20);
        ArrayDeque.removeLast();
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(23);
    }

}






