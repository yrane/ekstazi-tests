import org.junit.Test;
import static org.junit.Assert.*;

public class ConcatenationTest {

    @Test
    public void testConcatenate() {
        Concatenation c = new Concatenation();

        String result = c.concatenate("one", "two");

        assertEquals("onetwo", result);

    }
}
