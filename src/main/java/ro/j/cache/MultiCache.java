package ro.j.cache;

import static ro.j.cache.utils.Misc.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

class MultiCache<K, T> implements Cache<K, T> {

	private Map<K, T> map = new LinkedHashMap<>();

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
