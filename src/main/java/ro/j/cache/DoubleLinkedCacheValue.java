package ro.j.cache;


class DoubleLinkedCacheValue<K, T> extends CacheValue<K, T> {
	DoubleLinkedCacheValue<K, T> next;
	DoubleLinkedCacheValue<K, T> prev;
    private boolean removed;
	DoubleLinkedCacheValue(K key, T value) {
		super(key, value);
	}
	void clean() {
		prev = next = null;
		removed = false;
	}
    void removed() {
        clean();
        removed = true;
    }
	boolean isRemoved() {
	    return removed;
	}
}