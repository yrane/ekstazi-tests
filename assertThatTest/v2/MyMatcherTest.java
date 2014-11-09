import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class MyMatcherTest {

    @Test
    public void testWithMatchers() {
        assertThat(123456789, not(123456789));
    }
}
