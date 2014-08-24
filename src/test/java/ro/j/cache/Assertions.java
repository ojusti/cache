package ro.j.cache;

public class Assertions extends org.fest.assertions.api.Assertions {
    public static <K, T> CacheValueAssert<K, T> assertThat(CacheValue<K, T> actual) {
        return new CacheValueAssert<>(actual);
    }
}
