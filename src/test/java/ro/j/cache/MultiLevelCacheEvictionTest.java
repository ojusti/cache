package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class MultiLevelCacheEvictionTest {

	private MultiLevelCache<Integer, Integer> cache;
	private int maxSize;

	@Before
	public void createCache() {
		cache = new MultiLevelCache<>();
		maxSize = 1;
		cache.maxSize = maxSize;
		cache.policy = mock(EvictionPolicy.class);
	}
	@Test
	public void whenSizeExceeded_thenEvictUsingPolicy() {
		CacheValue<Integer, Integer> value1 = addAValue(1, 10);
		when(cache.policy.evictAValue()).thenReturn(value1);
		CacheValue<Integer, Integer> value2 = addAValue(2, 20);

		assertThat(cache.localSize()).isEqualTo(maxSize);
		assertThat(cache.get(2)).isEqualTo(20);
		verify(cache.policy).updateValue(value2);
	}
	
	@Test
	public void givenAKeyIsInCache_whenSameKeyIsAddedAgain_thenOldKeyIsEvicted() {
		CacheValue<Integer, Integer> value1 = addAValue(1, 10);
		addAValue(1, 20);

		assertThat(cache.localSize()).isEqualTo(maxSize);
		assertThat(cache.get(1)).isEqualTo(20);
		verify(cache.policy).removeValue(value1);
	}
	private CacheValue<Integer, Integer> addAValue(int key, int value) {
		CacheValue<Integer, Integer> cacheValue = new CacheValue<>(key, value);
		when(cache.policy.makeValue(key, value)).thenReturn(cacheValue);
		cache.put(key, value);
		return cacheValue;
	}

}
