package ro.j.cache;

import java.util.concurrent.atomic.AtomicInteger;

/** Least Frequently Used */ 
class LFU<K, T> extends FIFO<K, T> {

    private class DoubleLinkedCacheValueWithHits extends DoubleLinkedCacheValue<K, T> {
        
        AtomicInteger hits = new AtomicInteger();
        DoubleLinkedCacheValueWithHits(K key, T value) {
            super(key, value);
        }
        public void incrementHits() {
            int actualHits = hits.incrementAndGet();
            for(DoubleLinkedCacheValue<K, T> it = prev; it != null; it = it.prev)
            {
                DoubleLinkedCacheValueWithHits node = (DoubleLinkedCacheValueWithHits) it;
                int nodeHits = node.hits.get();
                if(nodeHits >= actualHits) {
                    queue.modeAfter(this, node);
                    return;
                }
            }
            queue.moveFirst(this);
        }        
    }
    @Override
    public synchronized CacheValue<K, T> makeValue(K key, T value) {
        DoubleLinkedCacheValue<K, T> cacheValue = new DoubleLinkedCacheValueWithHits(key, value);
        queue.addFirst(cacheValue);
        return cacheValue;
    }
	@Override
	public synchronized void updateValue(CacheValue<K, T> cacheValue) {
	    
	    ((DoubleLinkedCacheValueWithHits) cacheValue).incrementHits();
	}
	
	

}