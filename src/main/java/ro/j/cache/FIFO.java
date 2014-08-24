package ro.j.cache;

class FIFO<K, T> implements EvictionPolicy<K, T> {

    protected final DoubleLinkedList<K, T> queue = new DoubleLinkedList<>();
	@Override
	public synchronized CacheValue<K, T> makeValue(K key, T value) {
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
	}

	@Override
	public synchronized void removeValue(CacheValue<K, T> cacheValue) {
	    queue.remove((DoubleLinkedCacheValue<K, T>) cacheValue);
	}

}