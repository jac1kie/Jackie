import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    @Test
    void isEmpty() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        Deque<Integer> lld2 = new LinkedListDeque<>();
        lld1.addFirst(0);
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld2.isEmpty()).isTrue();
    }

    @Test
    void size() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(1);
        lld1.addLast(10);
        lld1.addFirst(3);
        assertThat(lld1.size() == 1).isFalse();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    void removeFirst() {
        Deque<Integer> list = new LinkedListDeque<>();
        assertThat(list.removeFirst()).isNull();

        list.addLast(1);
        assertThat(list.removeFirst()).isEqualTo(1);
        assertThat(list.isEmpty()).isTrue();

        list.addLast(2);
        list.addLast(2);
        list.addLast(3);
        assertThat(list.removeFirst()).isEqualTo(2);
        assertThat(list.removeFirst()).isEqualTo(2);
        assertThat(list.removeFirst()).isEqualTo(3);
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void removeLast() {
        Deque<Integer> list = new LinkedListDeque<>();
        assertThat(list.removeLast()).isNull();

        list.addLast(1);
        assertThat(list.removeLast()).isEqualTo(1);
        assertThat(list.isEmpty()).isTrue();


        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        assertThat(list.removeLast()).isEqualTo(3);
        assertThat(list.removeLast()).isEqualTo(2);
        assertThat(list.removeLast()).isEqualTo(1);
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void get() {
        Deque<Integer> list = new LinkedListDeque<>();
        assertThat(list.get(0)).isNull();
        assertThat(list.get(-1)).isNull();

        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(3);
    }

    @Test
    void getRecursive() {
        Deque<Integer> list = new LinkedListDeque<>();
        assertThat(list.getRecursive(0)).isNull();
        assertThat(list.getRecursive(-1)).isNull();
        list.addLast(-2);
        list.addLast(2);
        list.addLast(3);
        assertThat(list.getRecursive(0)).isEqualTo(-2);
        assertThat(list.getRecursive(1)).isEqualTo(2);
        assertThat(list.getRecursive(2) == 2).isFalse();
    }
    @Test
    public void toListTestEmpty() {
        LinkedListDeque<String> lst = new LinkedListDeque<>();
        assertThat(lst.toList()).isEqualTo(new ArrayList<>());
    }

    // Below, you'll write your own tests for LinkedListDeque.

}