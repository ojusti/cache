package ro.j.cache;

public class CacheBuilder<K, T> {

	public static <K, T> CacheBuilder<K, T> newCache() {
		return new CacheBuilder<>();
	}
	
	private MultiLevelCache<K, T> cache = new MultiLevelCache<>();
	
	public CacheBuilder<K, T> overflowTo(CacheBuilder<K, T> anotherCache) {
		cache.setOverflowTo(anotherCache.build());
		return this;
	}
	
	public CacheBuilder<K, T> whenSizeExceeds(int maxSize) {
		cache.setMaxSize(maxSize);
		return this;
	}

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

	public CacheBuilder<K, T> evict(EvictionStrategy strategy) {
		// TODO Auto-generated method stub
		return null;
	}

}
