package ro.j.cache;

public class NoGrowth<K, T> extends NoEviction<K, T> {
    private final ThreadLocal<CacheValue<K, T>> lastValue = new ThreadLocal<>();

    @Override
    public CacheValue<K, T> makeValue(K key, T value) {
        CacheValue<K, T> cacheValue = super.makeValue(key, value);
        lastValue.set(cacheValue);
        return cacheValue;
    }

    @Override
    public CacheValue<K, T> evictAValue() {
        return lastValue.get();
    }

}
