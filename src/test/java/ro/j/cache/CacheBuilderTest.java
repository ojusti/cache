package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class CacheBuilderTest {

	@Test
	public void buildsDefaultCache() {
		Cache<String, Integer> cache = CacheBuilder.<String, Integer> newCache().build();
		assertThat(cache).isInstanceOf(MultiCache.class);
	}
}
