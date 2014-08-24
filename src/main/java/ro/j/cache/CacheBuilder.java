package ro.j.cache;

public class CacheBuilder<K, T> implements Builder<Cache<K, T>> {

	public static <K, T> CacheBuilder<K, T> newCache() {
		return new CacheBuilder<>();
	}
	
	private MultiLevelCache<K, T> cache = new MultiLevelCache<>();
	
	public CacheBuilder<K, T> overflowTo(Builder<Cache<K, T>> anotherCache) {
		cache.overflow = anotherCache.build();
		return this;
	}
	
	public CacheBuilder<K, T> evict(Builder<EvictionPolicy<K, T>> policy) {
		cache.policy = policy.build();
		return this;
	}
	public CacheBuilder<K, T> whenSizeExceeds(int maxSize) {
		return withMaxSize(maxSize);
	}
	public CacheBuilder<K, T> withMaxSize(int maxSize) {
		cache.maxSize = maxSize;
		return this;
	}
	@Override
	public Cache<K, T> build() {
		if(cache == null) {
			throw new IllegalStateException("Cache was already built: build method must be invoked only once");
		}
		try {
			return cache;
		}
		finally {
			cache = null;
		}
	}

}
