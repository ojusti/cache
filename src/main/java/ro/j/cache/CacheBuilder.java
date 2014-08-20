package ro.j.cache;

public class CacheBuilder<K, T> {

	public static <K, T> CacheBuilder<K, T> newCache() {
		return new CacheBuilder<>();
	}
	
	public Cache<K, T> build()
	{
		return new MultiCache<>();
	}

}
