package ro.j.cache;

import static ro.j.cache.utils.Misc.assertNotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class MultiLevelCache<K, T> implements Cache<K, T> {

    private final Map<K, CacheValue<K, T>> map = new ConcurrentHashMap<>();
    Cache<K, T> overflow;
    EvictionPolicy<K, T> policy = EvictionPolicyBuilder.<K, T> lru().build();

    @Override
    public void put(K key, T value) {
        assertNotNull(key);

        CacheValue<K, T> evictedValue = putInMap(key, value);
        if (evictedValue != null && overflow != null) {
            overflow.put(evictedValue.key, evictedValue.value);
        }
    }

    private synchronized CacheValue<K, T> putInMap(K key, T value) {
        CacheValue<K, T> currentValue = policy.makeValue(key, value);
        CacheValue<K, T> previousValue = map.put(key, currentValue);

        if (previousValue != null) {
            policy.removeValue(previousValue);
            return null;
        }
        if (!isOverLimit()) {
            return null;
        }

        CacheValue<K, T> evictedValue = policy.evictAValue();
        if (evictedValue != null) {
            map.remove(evictedValue.key);
        }
        return evictedValue;
    }

    int maxSize = Integer.MAX_VALUE;

    private boolean isOverLimit() {
        return map.size() > maxSize;
    }

    @Override
    public synchronized T get(K key) {
        assertNotNull(key);
        CacheValue<K, T> cacheValue = map.get(key);
        if (cacheValue == null) {
            if (overflow != null) {
                return overflow.get(key);
            }
            return null;
        }
        policy.updateValue(cacheValue);
        return cacheValue.value;
    }

    int localSize() {
        return map.size();
    }

    Set<K> localKeySet() {
        return map.keySet();
    }

}
