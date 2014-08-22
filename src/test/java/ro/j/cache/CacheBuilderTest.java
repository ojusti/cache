package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;
import static ro.j.cache.CacheBuilder.whenSizeExceeds;

import org.junit.Test;

public class CacheBuilderTest {

	@Test
	public void buildsDefaultCache() {
		Cache<String, Integer> cache = CacheBuilder.<String, Integer> newCache().build();
		assertThat(cache).isInstanceOf(MultiLevelCache.class);
	}
	
	@Test(expected=IllegalStateException.class)
	public void givenACacheWasBuilt_whenBuildIsInvokedAgain_thenItThrowsIllegalStateException() throws IllegalStateException {
		CacheBuilder<String, Integer> builder = CacheBuilder.<String, Integer> newCache();
		builder.build();
		
		builder.build();
	}
	
	@Test
	public void buildsMultiLevelCache() {
		
		CacheBuilder<String, Integer> level2Cache = CacheBuilder.<String, Integer> newCache();
		Cache<String, Integer> cache = CacheBuilder.<String, Integer> newCache().overflowTo(level2Cache, whenSizeExceeds(10)).build();
		
		assertThat(((MultiLevelCache<String, Integer>) cache).getOverflowCache()).isNotNull();
	}
}
