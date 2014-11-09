import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;


public class OutputTest {

    @Test
    public void testWrite() throws IOException {
        Output unit = new Output();

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        unit.tokens.add("one");
        unit.tokens.add("two");
        unit.tokens.add("three");

        unit.write(output);

        String string = new String(output.toByteArray());
        assertEquals("one,two,three", string);
    }
}
