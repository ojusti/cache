package ro.j.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializableMarshaller<T> implements Marshaller<T> {

    @Override
    public void marshall(T value, OutputStream output) throws IOException {
        new ObjectOutputStream(output).writeObject(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T unmarshall(InputStream content) throws ClassNotFoundException, IOException {
        return (T) new ObjectInputStream(content).readObject();
    }

}
