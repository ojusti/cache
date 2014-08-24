package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DiskStoreTest {

    DiskStore<String, String> store;
    private String key;
    private String value;
    private String filename;
    protected OutputStream writer;

    @Before
    public void create() throws ClassNotFoundException, IOException {
        key = "key";
        filename = "filename";
        value = "value";

        @SuppressWarnings("resource")
        InputStream reader = mock(InputStream.class);
        writer = mock(OutputStream.class);

        store = new DiskStore<>(Mockito.mock(Folder.class));
        store.mapper = mock(Function.class);
        when(store.folder.load(filename)).thenReturn(reader);
        when(store.folder.save(filename)).thenReturn(writer);

        when(store.mapper.apply(key)).thenReturn(filename);
        store.marshaller = Mockito.mock(Marshaller.class);

        when(store.marshaller.unmarshall(reader)).thenReturn(value);
    }

    @Test
    public void whenAMappingIsAdded_thenFilenameIsComputedByMapper_andValueIsMarhalled_andSavedInFolder() throws IOException {
        store.put(key, value);
        verify(store.marshaller).marshall(value, writer);
    }

    @Test
    public void whenAValueIsRequested_thenFilenameIsComputedByMapper_andValueIsUnmarshalledFromFolder() {
        assertThat(store.get(key)).isEqualTo(value);

    }

}
