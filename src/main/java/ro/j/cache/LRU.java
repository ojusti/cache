package ro.j.cache;

/** Least Recently Used (put == makeValue or get == updateValue) eviction policy */ 
class LRU<K, T> implements EvictionPolicy<K, T> {

    private final DoubleLinkedList<K, T> queue = new DoubleLinkedList<>();
	@Override
	public CacheValue<K, T> makeValue(K key, T value) {
		DoubleLinkedCacheValue<K, T> cacheValue = new DoubleLinkedCacheValue<>(key, value);
		queue.addFirst(cacheValue);
		return cacheValue;
	}
	
	@Override
	public synchronized CacheValue<K, T> evictAValue() {
	    return queue.removeLast();
	}

	@Override
	public synchronized void updateValue(CacheValue<K, T> cacheValue) {
	    queue.moveFirst((DoubleLinkedCacheValue<K, T>) cacheValue);
	}

	@Override
	public synchronized void removeValue(CacheValue<K, T> cacheValue) {
	    queue.remove((DoubleLinkedCacheValue<K, T>) cacheValue);
	}

}