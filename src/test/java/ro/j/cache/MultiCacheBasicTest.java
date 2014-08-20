package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MultiCacheBasicTest {

	private Integer key, key2;
	private String value, value2;
	private Cache<Integer, String> cache;

	@Before
	public void initValues() {
		value = "whatever";
		value2 = "another";
		key = 56;
		key2 = 123;
		cache = new MultiCache<>();
	}
	
	@Test
	public void retrievesTheAddedValue() {
		assertCacheRetrievesTheAddedValue(key, value);
		assertCacheRetrievesTheAddedValue(key2, value2);
	
		assertKeyIsMappedToValue(key, value);
		assertKeyIsMappedToValue(key2, value2);
	}
	
	private void assertCacheRetrievesTheAddedValue(Integer key, String value) {
		cache.put(key, value);
		assertKeyIsMappedToValue(key, value);
	}

	private void assertKeyIsMappedToValue(Integer key, String value) {
		assertThat(cache.get(key)).isEqualTo(value);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void doesntAcceptNullKeys() {
		cache.put(null, value);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void doesntAcceptNullValues() {
		cache.put(key, null);
	}
	

}
