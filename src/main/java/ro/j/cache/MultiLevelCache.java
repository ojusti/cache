package ro.j.cache;

import static ro.j.cache.utils.Misc.assertNotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class MultiLevelCache<K, T> implements Cache<K, T> {

	private final Map<K, T> map = new ConcurrentHashMap<>();
	private Cache<K, T> overflow;
	private AtomicInteger approximateSize = new AtomicInteger();
	private boolean overflowPhase;
	private OverflowLimit overflowLimit = OverflowLimitBuilder.noOverflow();
	@Override
	public void put(K key, T value) {
		assertNotNull(key);
		assertNotNull(value);
		T previousValue = map.put(key, value);
		if(previousValue != null) {
			return;
		}
		if(shouldOverflow()) {
			map.remove(key);
		}
	}

	private boolean shouldOverflow() {
		if(!overflowPhase && overflowLimit != null) {
			overflowPhase = overflowLimit.isExceeded(approximateSize.incrementAndGet());
		}
		return overflowPhase;
	}

	@Override
	public T get(K key) {
		assertNotNull(key);
		return map.get(key);
	}

	void setOverflowTo(Cache<K, T> anotherCache, OverflowLimit overflowLimit) {
		this.overflow = anotherCache;
		this.overflowLimit = overflowLimit;
	}
	Cache<K, T> getOverflowCache() {
		return overflow;
	}

	int localSize() {
		return map.size();
	}

}
