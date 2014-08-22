package ro.j.cache;

import static ro.j.cache.utils.Misc.assertNotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class MultiLevelCache<K, T> implements Cache<K, T> {

	private Map<K, T> map = new ConcurrentHashMap<>();

	@Override
	public void put(K key, T value) {
		assertNotNull(key);
		assertNotNull(value);
		map.put(key, value);
	}

	@Override
	public T get(K key) {
		assertNotNull(key);
		return map.get(key);
	}

}
