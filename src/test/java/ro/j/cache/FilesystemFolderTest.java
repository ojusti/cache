package ro.j.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FilesystemFolderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void roundTrip() throws IOException {
        FilesystemFolder filesystemFolder = new FilesystemFolder(folder.getRoot().getAbsolutePath());
        try (OutputStream output = filesystemFolder.save("file.temp")) {
            output.write(123);
        }

        try (InputStream input = filesystemFolder.load("file.temp")) {
            Assertions.assertThat(input.read()).isEqualTo(123);
        }

    }
}
