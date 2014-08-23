package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import ro.j.cache.testTools.SlowTest;
import ro.j.cache.testTools.SynchedThreads;

public class MultiLevelCacheOverflowTest {

	private MultiLevelCache<Integer, Integer> cache;
	private int n;
	private AtomicInteger counter;

	@Before
	public void createCache() {
		cache = new MultiLevelCache<>();
		n = 1000;
		counter = new AtomicInteger();
	}

	@Test
	public void givenCacheCannotOverflow_whenLimitIsExceeded_thenAValueIsLost() {
		cache.setOverflowTo(null, new OverflowLimit(1));
		cache.put(1, 1);
		cache.put(2, 2);
		assertThat(cache.localSize()).isEqualTo(1);
	}

	@Test
	@Category(SlowTest.class)
	public void whenKeysAreNotOverlapping_thenCacheIsThreadSafe()
			throws InterruptedException, BrokenBarrierException {
		for(int i = 0; i < n; i++)
		{
			createCache();
			cache.setOverflowTo(null, new OverflowLimit(10));
			SynchedThreads.launch(new FillCache(n)).times(n).await();
			assertThat(cache.localSize()).isEqualTo(10);
		}
	}

	/** when we modify MultiLevelCache implementation as follows, these 'thread safe' tests fails
	 * 1. replace ConcurrentHashMap with LinkedHashMap
	 * 2. or replace AtomicInteger with an int (even volatile) and incrementAndGet with ++
	 */
	@Test
	@Category(SlowTest.class)
	public void whenKeysAreOverlapping_thenCacheIsThreadSafe()
			throws InterruptedException, BrokenBarrierException {
		for(int i = 0; i < n; i++)
		{
			createCache();
			cache.setOverflowTo(null, new OverflowLimit(10));
			SynchedThreads.launch(new FillCache(10)).times(n).await();
			assertThat(cache.localSize()).isEqualTo(10);
		}
	}

	private class FillCache implements Runnable {
		private final int counterMultiplier;

		FillCache(int counterMultiplier) {
			this.counterMultiplier = counterMultiplier;
		}

		@Override
		public void run() {
			int seed = counterMultiplier * counter.getAndIncrement();
			for (int i = 0; i < n; i++) {
				cache.put(seed + i, seed);
			}

		}

	}

}
