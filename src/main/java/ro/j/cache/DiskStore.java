package ro.j.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;

public class DiskStore<K, T> implements Cache<K, T> {

    final Folder folder;
    Function<K, String> mapper = new Function<K, String>() {
        @Override
        public String apply(K t) {
            return "" + t.hashCode();
        }
    };
    Marshaller<T> marshaller = new SerializableMarshaller<>();

    DiskStore(Folder folder) {
        this.folder = folder;
    }

    @Override
    public void put(K key, T value) {
        try (OutputStream stream = folder.save(mapper.apply(key))) {
            marshaller.marshall(value, stream);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public T get(K key) {
        try (InputStream input = folder.load(mapper.apply(key))) {
            return marshaller.unmarshall(input);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
