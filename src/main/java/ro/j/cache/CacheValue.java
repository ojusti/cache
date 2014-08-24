package ro.j.cache;


class CacheValue<K, T> {
	final K key;
	final T value;
	
	CacheValue(K key, T value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s", key, value);
	}
}
