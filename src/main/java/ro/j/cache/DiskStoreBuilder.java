package ro.j.cache;

import java.util.function.Function;

public class DiskStoreBuilder<K, T> implements Builder<DiskStore<K, T>> {

    public static <K, T> DiskStoreBuilder<K, T> newDiskStore(Folder folder) {
        return new DiskStoreBuilder<>(new DiskStore<K, T>(folder));
    }
    
    private final DiskStore<K, T> diskStore;
    private DiskStoreBuilder(DiskStore<K, T> diskStore) {
        this.diskStore = diskStore;
    }

    public DiskStoreBuilder<K, T> withFilenameMapper(Function<K, String> mapper) {
        diskStore.mapper = mapper;
        return this;
    }

    public DiskStoreBuilder<K, T> withMarshalling(Marshaller<T> marshaller) {
        diskStore.marshaller = marshaller;
        return this;
    }

    @Override
    public DiskStore<K, T> build() {
        return diskStore;
    }

}
