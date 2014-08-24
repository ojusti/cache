package ro.j.cache;


public interface EvictionPolicy<K, T> {

	CacheValue<K, T> makeValue(K key, T value);
	void updateValue(CacheValue<K, T> cacheValue);
	void removeValue(CacheValue<K, T> value);
	CacheValue<K, T> evictAValue();
}
