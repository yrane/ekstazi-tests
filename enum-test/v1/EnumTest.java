import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;

public class EnumTest {

    @Test
    public void should() {
        assertThat(EnumClass.DOUBLE.applyMultiplier(2.0), not(5.0));
        assertThat(EnumClass.TRIPLE.applyMultiplier(1.0), is(3.0));
    }
}
