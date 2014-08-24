package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import ro.j.test.SlowTest;
import ro.j.test.SynchedThreads;

@Category(SlowTest.class)
public class ThreadSafeTest {

    private int n;
    private AtomicInteger counter;
    private MultiLevelCache<Integer, Integer> cache;
    private int maxSize;
    private MultiLevelCache<Integer, Integer> secondLevelCache;

    @Before
    public void init() {
        n = 100;
        maxSize = 100;
    }

    private void createCache() {
        cache = new MultiLevelCache<>();
        cache.maxSize = maxSize;
        secondLevelCache = new MultiLevelCache<>();

        counter = new AtomicInteger();
    }

    @Test
    public void whenKeysAreNotOverlapping_thenCacheIsThreadSafe() throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            createCache();
            SynchedThreads.launch(new FillCache(n)).times(n).await();
            assertThat(cache.localSize()).isEqualTo(maxSize);

        }
    }

    @Test
    public void whenKeysAreOverlapping_thenCacheIsThreadSafe() throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            createCache();
            SynchedThreads.launch(new FillCache(10)).times(n).await();
            assertThat(cache.localSize()).isEqualTo(maxSize);
        }
    }

    @Test
    public void givenCacheOverflows_whenKeysAreNotOverlapping_thenCacheIsThreadSafe() throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < n; i++) {
            createCache();
            cache.overflow = secondLevelCache;
            SynchedThreads.launch(new FillCache(n)).times(n).await();
            assertThat(cache.localSize()).isEqualTo(maxSize);
            int numberOfEntries = numberOfEntries(n);
            assertThat(secondLevelCache.localSize()).isEqualTo(numberOfEntries - maxSize);
            assertThat(unionKeys()).hasSize(numberOfEntries);
        }
    }

    @Test
    public void givenCacheOverflows_whenKeysAreOverlapping_thenCacheIsThreadSafe() throws InterruptedException, BrokenBarrierException {
        int step = 10;
        for (int i = 0; i < n; i++) {
            createCache();
            cache.overflow = secondLevelCache;
            SynchedThreads.launch(new FillCache(step)).times(n).await();
            assertThat(cache.localSize()).isEqualTo(maxSize);
            int numberOfEntries = numberOfEntries(step);
            assertThat(secondLevelCache.localSize()).isGreaterThanOrEqualTo(numberOfEntries - maxSize);
            assertThat(unionKeys()).hasSize(numberOfEntries);
        }
    }

    private Set<Integer> unionKeys() {
        Set<Integer> allKeys = new HashSet<>(cache.localKeySet());
        allKeys.addAll(secondLevelCache.localKeySet());
        return allKeys;
    }

    private int numberOfEntries(int step) {
        return n + step * (counter.get() - 1);
    }

    private class FillCache implements Runnable {
        private final int step;

        FillCache(int step) {
            this.step = step;
        }

        @Override
        public void run() {
            int thread = counter.getAndIncrement();
            int seed = step * thread;
            for (int i = 0; i < n; i++) {
                cache.put(seed + i, thread);
                cache.get(randomKey());
            }

        }

        private int randomKey() {
            return (int) (Math.random() * numberOfEntries(n));
        }
    }

}
