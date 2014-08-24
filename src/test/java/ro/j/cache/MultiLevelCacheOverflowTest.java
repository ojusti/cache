package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MultiLevelCacheOverflowTest {

	private MultiLevelCache<Integer, Integer> cache;
	private int maxSize;

	@Before
	public void createCache() {
		cache = new MultiLevelCache<>();
		maxSize = 1;
		cache.maxSize = maxSize;
		
	}

	@Test
	public void givenCacheCannotOverflow_whenLimitIsExceeded_thenAValueIsLost() {
		cache.put(1, 1);
		cache.put(2, 2);
		assertThat(cache.localSize()).isEqualTo(maxSize);
	}

	@Test
	public void whenSizeIsExceeded_thenCacheOverflows() {
		MultiLevelCache<Integer, Integer> secondLevelCache = new MultiLevelCache<>();
		cache.overflow = secondLevelCache;
		
		cache.put(1, 1);
		cache.put(2, 2);
		
		assertThat(cache.localSize()).isEqualTo(maxSize);
		assertThat(secondLevelCache.localSize()).isEqualTo(2 - maxSize);
		
		assertThat(cache.localKeySet()).isNotEqualTo(secondLevelCache.localKeySet());
	}
	
	@Test
    public void whenKeysAreAddedTwice_thenTheyAreInBothCaches() {
        MultiLevelCache<Integer, Integer> secondLevelCache = new MultiLevelCache<>();
        cache.overflow = secondLevelCache;
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(1, 5);
        
        assertThat(cache.localSize()).isEqualTo(maxSize);
        assertThat(secondLevelCache.localSize()).isEqualTo(2);
        
        assertThat(cache.get(1)).isEqualTo(5);
        assertThat(secondLevelCache.get(1)).isEqualTo(1);
        
        cache.put(3, 3);
        
        assertThat(cache.get(1)).isEqualTo(5);
        assertThat(secondLevelCache.get(1)).isEqualTo(5);

    }

}
