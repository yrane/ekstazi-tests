import org.junit.Test;
import static org.junit.Assert.*;

public class ConcatenationTest {

    @Test
    public void testConcatenate() {
        Concatenation c = new Concatenation();

        String result = c.concatenate("zero", "two");

        assertEquals("zerotwo", result);

    }
}
