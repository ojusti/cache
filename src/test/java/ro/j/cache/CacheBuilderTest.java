package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ro.j.cache.CacheBuilder.newCache;

import org.junit.Before;
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
		
		CacheBuilder<String, Integer> level2CacheBuilder = mock(CacheBuilder.class);
		Cache<String, Integer> level2Cache = mock(Cache.class);
		when(level2CacheBuilder.build()).thenReturn(level2Cache);
		
		Cache<String, Integer> cache = newCache.overflowTo(level2CacheBuilder).build();
		
		assertThat(((MultiLevelCache<String, Integer>) cache).overflow).isSameAs(level2Cache);
	}
	
	@Test
	public void buildsCacheWithMaxSize() {
		
		int maxSize = 5;
		Cache<String, Integer> cache = newCache.withMaxSize(maxSize).build();
		
		assertThat(((MultiLevelCache<String, Integer>) cache).maxSize).isEqualTo(maxSize);
	}
	
	@Test
	public void buildsCacheWithEvictionStrategy() {
		EvictionPolicyBuilder<String, Integer> policyBuilder = mock(EvictionPolicyBuilder.class);
		EvictionPolicy<String, Integer> policy = mock(EvictionPolicy.class);
		when(policyBuilder.build()).thenReturn(policy);
		Cache<String, Integer> cache = newCache.evict(policyBuilder).build();
		
		assertThat(((MultiLevelCache<String, Integer>) cache).policy).isSameAs(policy);
	}
	
	@Test
    public void buildsCacheWithDiskStore() {
	    DiskStoreBuilder<String, Integer> diskStore = DiskStoreBuilder.newDiskStore(mock(Folder.class));
	    Cache<String, Integer> cache = newCache.overflowTo(diskStore).build();
	    
	    assertThat(((MultiLevelCache<String, Integer>) cache).overflow).isSameAs(diskStore.build());
    }
}
