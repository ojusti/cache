package ro.j.cache;

public class NoEviction<K, T> implements EvictionPolicy<K, T> {
	@Override
	public CacheValue<K, T> makeValue(K key, T value) {
		return new CacheValue<>(key, value);
	}

	@Override
	public CacheValue<K, T> evictAValue() {
		return null;
	}

	@Override
	public void updateValue(CacheValue<K, T> cacheValue) {
	}

	@Override
	public void removeValue(CacheValue<K, T> value) {
	}

}
