package ro.j.cache;

import static ro.j.cache.Assertions.assertThat;

import org.junit.Test;

public class LFUTest {
    @Test
    public void whenNodeIsUpdated_thenItProgress() {
        LFU<Integer, Integer> policy = new LFU<>();
        CacheValue<Integer, Integer> node1 = policy.makeValue(1, 1);
        CacheValue<Integer, Integer> node2 = policy.makeValue(2, 2);
        CacheValue<Integer, Integer> node3 = policy.makeValue(3, 3);
        CacheValue<Integer, Integer> node4 = policy.makeValue(4, 4);

        policy.updateValue(node1);
        policy.updateValue(node3);

        assertThat(policy.evictAValue()).isSameAs(node2);
        assertThat(policy.evictAValue()).isSameAs(node4);
        assertThat(policy.evictAValue()).isSameAs(node3);
        assertThat(policy.evictAValue()).isSameAs(node1);
        assertThat(policy.evictAValue()).isNull();

    }
}
