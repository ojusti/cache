package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import ro.j.SlowTest;
import ro.j.cache.testTools.SynchedThreads;

/** 
 * these 'thread safe' tests fail if we modify MultiLevelCache implementation as follows: 
 * 1. replace ConcurrentHashMap with LinkedHashMap
 * 2. or replace AtomicInteger with an int (even volatile) and incrementAndGet with ++
 */
@Category(SlowTest.class)
public class ThreadSafeTest {
	
	private int n;
	private AtomicInteger counter;
	private MultiLevelCache<Integer, Integer> cache;
	private OverflowLimit limit;
	private MultiLevelCache<Integer, Integer> secondLevelCache;

	@Before
	public void init()
	{
		n = 1000;
		limit = new OverflowLimit(10);
	}
	
	private void createCache() {
		cache = new MultiLevelCache<>();
		secondLevelCache = new MultiLevelCache<>();
		
		counter = new AtomicInteger();
	}
	@Test
	public void whenKeysAreNotOverlapping_thenCacheIsThreadSafe()
			throws InterruptedException, BrokenBarrierException {
		for(int i = 0; i < n; i++)
		{
			createCache();
			cache.setOverflowTo(null, limit);
			SynchedThreads.launch(new FillCache(n)).times(n).await();
			assertThat(cache.localSize()).isEqualTo(limit.get());
		}
	}

	@Test
	public void whenKeysAreOverlapping_thenCacheIsThreadSafe()
			throws InterruptedException, BrokenBarrierException {
		for(int i = 0; i < n; i++)
		{
			createCache();
			cache.setOverflowTo(null, limit);
			SynchedThreads.launch(new FillCache(10)).times(n).await();
			assertThat(cache.localSize()).isEqualTo(limit.get());
		}
	}
	
	@Test
	public void givenCacheOverflows_whenKeysAreNotOverlapping_thenCacheIsThreadSafe()
			throws InterruptedException, BrokenBarrierException {
		for(int i = 0; i < n; i++)
		{
			createCache();
			cache.setOverflowTo(secondLevelCache, limit);
			SynchedThreads.launch(new FillCache(n)).times(n).await();
			assertThat(cache.localSize()).isEqualTo(limit.get());
			assertThat(secondLevelCache.localSize()).isEqualTo(numberOfEntries(n) - limit.get());
		}
	}

	@Test
	public void givenCacheOverflows_whenKeysAreOverlapping_thenCacheIsThreadSafe()
			throws InterruptedException, BrokenBarrierException {
		int step = 10;
		for(int i = 0; i < n; i++)
		{
			createCache();
			cache.setOverflowTo(secondLevelCache, limit);
			SynchedThreads.launch(new FillCache(step)).times(n).await();
			assertThat(cache.localSize()).isEqualTo(limit.get());
			assertThat(secondLevelCache.localSize()).isEqualTo(numberOfEntries(step) - limit.get());
		}
	}

	private int numberOfEntries(int step) {
		return n + step*(counter.get() - 1);
	}

	private class FillCache implements Runnable {
		private final int step;

		FillCache(int step) {
			this.step = step;
		}

		@Override
		public void run() {
			int seed = step * counter.getAndIncrement();
			for (int i = 0; i < n; i++) {
				cache.put(seed + i, seed);
			}

		}
	}

}