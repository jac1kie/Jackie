package deque;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MaxArrayDequeTest {

    @Test
    void max() {
        Comparator<Integer> comparator = Comparator.naturalOrder();
        MaxArrayDeque<Integer> aDeque = new MaxArrayDeque<>(comparator);
        aDeque.addFirst(7);
        aDeque.addLast(3);
        aDeque.addLast(19);

        assertThat(aDeque.max()).isEqualTo(19);
        assertThat(aDeque.max() == 7).isFalse();
    }

    @Test
    void testMax() {
        Comparator<Integer> c = Comparator.naturalOrder();
        Comparator<Integer> other = Comparator.reverseOrder();
        MaxArrayDeque<Integer> aDeque = new MaxArrayDeque<>(c);
        aDeque.addFirst(7);
        aDeque.addLast(0);
        aDeque.addLast(890);

        assertThat(aDeque.max()).isEqualTo(890);
        assertThat(aDeque.max(other)).isEqualTo(0);
    }
}