package ro.j.cache;

public class EvictionPolicyBuilder<K, T> implements Builder<EvictionPolicy<K, T>> {

	private EvictionPolicy<K, T> policy;
	
	private EvictionPolicyBuilder(EvictionPolicy<K, T> policy) {
		this.policy = policy;
	}
	
	@Override
	public EvictionPolicy<K, T> build() {
		if(policy == null) {
			throw new IllegalStateException("Policy was already built: build method must be invoked only once"); 
		}
		try {
			return policy;
		}
		finally {
			policy = null;
		}
	}

	
	public static <K, T> EvictionPolicyBuilder<K, T> fifo() {
		return new EvictionPolicyBuilder<>(new FIFO<K, T>());
	}
	public static <K, T> EvictionPolicyBuilder<K, T> lru() {
        return new EvictionPolicyBuilder<>(new LRU<K, T>());
    }
	public static <K, T> EvictionPolicyBuilder<K, T> lfu() {
        return new EvictionPolicyBuilder<>(new LFU<K, T>());
    }
	public static <K, T> EvictionPolicyBuilder<K, T> no() {
		return new EvictionPolicyBuilder<>(new NoEviction<K, T>());
	}
	public static <K, T> EvictionPolicyBuilder<K, T> current() {
		return new EvictionPolicyBuilder<>(new NoGrowth<K, T>());
	}
}
