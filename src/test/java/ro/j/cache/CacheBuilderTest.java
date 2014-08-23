package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;
import static ro.j.cache.CacheBuilder.newCache;
import static ro.j.cache.EvictionStrategyBuilder.lru;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CacheBuilderTest {

	private CacheBuilder<String, Integer> newCache;
	@Before
	public void createBuilder()
	{
		newCache = newCache();
	}
	@Test
	public void buildsDefaultCache() {
		Cache<String, Integer> cache = newCache.build();
		assertThat(cache).isInstanceOf(MultiLevelCache.class);
	}
	
	@Test(expected=IllegalStateException.class)
	public void givenACacheWasBuilt_whenBuildIsInvokedAgain_thenItThrowsIllegalStateException() throws IllegalStateException {
		newCache.build();
		
		newCache.build();
	}
	
	@Test
	public void buildsMultiLevelCache() {
		
		CacheBuilder<String, Integer> level2Cache = newCache();
		Cache<String, Integer> cache = newCache.overflowTo(level2Cache).whenSizeExceeds(10).build();
		
		assertThat(((MultiLevelCache<String, Integer>) cache).getOverflowCache()).isNotNull();
	}
	
	@Test @Ignore
	public void buildsCacheWithEvictionStrategy() {
		Cache<String, Integer> cache = newCache.evict(lru()).build();
	}
}
