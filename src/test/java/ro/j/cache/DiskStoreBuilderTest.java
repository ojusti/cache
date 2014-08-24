package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.function.Function;

import org.junit.Test;
import org.mockito.Mockito;


public class DiskStoreBuilderTest {
    @Test
    public void buildsADiskStore() {
        Folder folder = Mockito.mock(Folder.class);
        Function<Integer, String> mapper = Mockito.mock(Function.class);
        Marshaller<Integer> marshaller = Mockito.mock(Marshaller.class);
        
        DiskStoreBuilder<Integer, Integer> builder = DiskStoreBuilder.<Integer, Integer>newDiskStore(folder)
                .withFilenameMapper(mapper)
                .withMarshalling(marshaller);
        DiskStore<Integer, Integer> diskStore = builder.build();
        assertThat(diskStore.folder).isSameAs(folder);
        assertThat(diskStore.mapper).isSameAs(mapper);
    }
}
