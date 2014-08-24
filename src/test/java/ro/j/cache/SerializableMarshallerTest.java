package ro.j.cache;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

public class SerializableMarshallerTest {

    @Test
    public void roundTrip() throws ClassNotFoundException, IOException {
        String value = "a very nice string";
        Marshaller<String> marshaller = new SerializableMarshaller<>();
        try (PipedInputStream pin = new PipedInputStream(); PipedOutputStream pout = new PipedOutputStream(pin)) {
            marshaller.marshall(value, pout);
            Assertions.assertThat(marshaller.unmarshall(pin)).isEqualTo(value);
        }

    }
}
