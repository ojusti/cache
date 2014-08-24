package ro.j.cache;

import static ro.j.cache.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;


public class LRUTest {
    private LRU<Integer, Integer> policy;
    @Before
    public void init() {
        policy = new LRU<>();
    }
    @Test
    public void makeValueInitializeKeyAndValueFields() {
        CacheValue<Integer, Integer> value = policy.makeValue(1, 2);
        assertThat(value).keyIsEqualTo(1).valueIsEqualTo(2);
    }
    @Test
    public void valuesAreEvictedFifo() {
        CacheValue<Integer, Integer> value1 = policy.makeValue(1, 1);
        CacheValue<Integer, Integer> value2 = policy.makeValue(2, 2);
        
        assertThat(policy.evictAValue()).isSameAs(value1);
        assertThat(policy.evictAValue()).isSameAs(value2);
        assertEmpty();
    }
    
    @Test
    public void whenValueIsUpdated_thenValueIsEvictedLast(){
        CacheValue<Integer, Integer> value1 = policy.makeValue(1, 1);
        CacheValue<Integer, Integer> value2 = policy.makeValue(2, 2);
        
        policy.updateValue(value1);
        
        assertThat(policy.evictAValue()).isSameAs(value2);
        assertThat(policy.evictAValue()).isSameAs(value1);
        assertEmpty();
    }
    private void assertEmpty() {
        assertThat(policy.evictAValue()).isNull();
    }
    
    @Test(expected=NullPointerException.class)
    public void whenUpdateNull_thenNPE() throws NullPointerException {
        policy.updateValue(null);
    }
    
    @Test
    public void whenValueIsRemoved_thenValueIsNotPresentAnymore(){
        CacheValue<Integer, Integer> value1 = policy.makeValue(1, 1);
        CacheValue<Integer, Integer> value2 = policy.makeValue(2, 2);
        CacheValue<Integer, Integer> value3 = policy.makeValue(3, 3);
        
        policy.removeValue(value2);
        
        assertThat(policy.evictAValue()).isSameAs(value1);
        assertThat(policy.evictAValue()).isSameAs(value3);
        assertEmpty();
    }
    
    
}
