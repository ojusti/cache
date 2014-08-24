package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import org.fest.assertions.api.AbstractAssert;

public class CacheValueAssert<K, T> extends AbstractAssert<CacheValueAssert<K, T>, CacheValue<K, T>>{

    protected CacheValueAssert(CacheValue<K, T> actual) {
        super(actual, CacheValueAssert.class);
    }
    
    public CacheValueAssert<K, T> keyIsEqualTo(K expected) {
        assertThat(actual.key).isEqualTo(expected);
        return this;
    }
    
    public CacheValueAssert<K, T> valueIsEqualTo(T expected) {
        assertThat(actual.value).isEqualTo(expected);
        return this;
    }

}
