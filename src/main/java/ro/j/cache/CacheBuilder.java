package ro.j.cache;

public class CacheBuilder<K, T> {

	public static <K, T> CacheBuilder<K, T> newCache() {
		return new CacheBuilder<>();
	}
	
	private MultiLevelCache<K, T> cache = new MultiLevelCache<>();
	
	public CacheBuilder<K, T> overflowTo(CacheBuilder<K, T> anotherCache, OverflowLimitBuilder maxSize) {
		cache.setOverflowTo(anotherCache.build(), maxSize.build());
		return this;
	}
	
	public static OverflowLimitBuilder whenSizeExceeds(int size) {
		return new OverflowLimitBuilder(size);
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

}
