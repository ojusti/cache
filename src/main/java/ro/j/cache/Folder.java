package ro.j.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Folder {

    OutputStream save(String filename) throws IOException;

    InputStream load(String filename) throws IOException;

}
