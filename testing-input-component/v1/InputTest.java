import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;

public class InputTest {

    @Test
    public void testRead() throws IOException {
        Input unit = new Input();

        byte[] data = "123,456,789,123,456,789,".getBytes();

        InputStream input = new ByteArrayInputStream(data);

        unit.read(input);

        assertEquals("123", unit.tokens.get(0));
        assertEquals("456", unit.tokens.get(1));
        assertEquals("789", unit.tokens.get(2));
        assertEquals("123", unit.tokens.get(3));
        assertEquals("456", unit.tokens.get(4));
        assertEquals("789", unit.tokens.get(5));
    }
}
