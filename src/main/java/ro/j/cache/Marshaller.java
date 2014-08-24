package ro.j.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Marshaller<T> {

    void marshall(T value, OutputStream output) throws IOException;
    
    T unmarshall(InputStream content) throws ClassNotFoundException, IOException;

}
