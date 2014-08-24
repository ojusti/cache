package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DoubleLinkedListTest {
    private DoubleLinkedList<Integer, Integer> queue;

    @Before
    public void init() {
        queue = new DoubleLinkedList<>();
    }

    @Test
    public void givenEmptyList_thenFirstAddedNodeIsLast() {
        DoubleLinkedCacheValue<Integer, Integer> node = new DoubleLinkedCacheValue<>(1, 1);
        queue.addFirst(node);
        assertThat(queue.removeLast()).isSameAs(node);
        assertQueueIsEmpty();
    }

    @Test
    public void testSuccessivesAddFirst() {
        DoubleLinkedCacheValue<Integer, Integer>[] nodes = addFirst(10);

        for (int i = 0; i < nodes.length; i++) {
            assertThat(queue.removeLast()).isSameAs(nodes[i]);
        }

        assertQueueIsEmpty();
    }

    private DoubleLinkedCacheValue<Integer, Integer>[] addFirst(int n) {
        DoubleLinkedCacheValue<Integer, Integer>[] nodes = new DoubleLinkedCacheValue[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new DoubleLinkedCacheValue<>(i, i);
            queue.addFirst(nodes[i]);
        }
        return nodes;
    }

    @Test
    public void givenOneElementList_whenRemove_thenListIsEmpty() {
        DoubleLinkedCacheValue<Integer, Integer> node = new DoubleLinkedCacheValue<>(1, 1);
        queue.addFirst(node);
        queue.remove(node);
        assertQueueIsEmpty();
    }

    private void assertQueueIsEmpty() {
        assertThat(queue.removeLast()).isNull();
    }

    @Test
    public void testSuccessiveRemoves() {
        DoubleLinkedCacheValue<Integer, Integer>[] nodes = addFirst(10);
        queue.remove(nodes[5]);
        assertThat(nodes[4].prev).isSameAs(nodes[6]);
        assertThat(nodes[6].next).isSameAs(nodes[4]);

        for (int i = nodes.length - 1; i > 6; i--) {
            queue.remove(nodes[i]);
            assertThat(nodes[i - 1].prev).isNull();
        }

        for (int i = 0; i < 4; i++) {
            queue.remove(nodes[i]);
            assertThat(nodes[i + 1].next).isNull();
        }

        queue.remove(nodes[4]);
        queue.remove(nodes[6]);

        assertQueueIsEmpty();
    }
    
    @Test
    public void whenNodeIsRemovedTwice_thenSecondIsNop() {
        DoubleLinkedCacheValue<Integer, Integer>[] nodes = addFirst(10);
        queue.remove(nodes[5]);
        queue.remove(nodes[5]);
        assertThat(queue.removeLast()).isNotNull();
    }
}
