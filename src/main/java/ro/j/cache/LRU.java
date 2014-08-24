package ro.j.cache;

/** Least Recently Used (put == makeValue or get == updateValue) eviction policy */ 
class LRU<K, T> extends FIFO<K, T> {

	@Override
	public synchronized void updateValue(CacheValue<K, T> cacheValue) {
	    queue.moveFirst((DoubleLinkedCacheValue<K, T>) cacheValue);
	}

}