import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class BitTest {
    @Test
    public void testbitop() {
        UniqueStringBVector tester = new UniqueStringBVector();
        String test_str = "yogesh";
        assertEquals("Unique or Not?", true, tester.match_string(test_str));
    }
}
