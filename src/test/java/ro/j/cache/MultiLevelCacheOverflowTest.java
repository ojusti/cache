package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MultiLevelCacheOverflowTest {

	private MultiLevelCache<Integer, Integer> cache;

	@Before
	public void createCache() {
		cache = new MultiLevelCache<>();
	}

	@Test
	public void givenCacheCannotOverflow_whenLimitIsExceeded_thenAValueIsLost() {
		cache.setOverflowTo(null, new OverflowLimit(1));
		cache.put(1, 1);
		cache.put(2, 2);
		assertThat(cache.localSize()).isEqualTo(1);
	}

	@Test
	public void whenSizeIsExceeded_thenCacheOverflows() {
		MultiLevelCache<Integer, Integer> secondLevelCache = new MultiLevelCache<>();
		cache.setOverflowTo(secondLevelCache, new OverflowLimit(1));
		
		cache.put(1, 1);
		cache.put(2, 2);
		
		assertThat(cache.localSize()).isEqualTo(1);
		assertThat(secondLevelCache.localSize()).isEqualTo(1);
		
		assertThat(cache.keySet()).isNotEqualTo(secondLevelCache.keySet());
		
	}

}
