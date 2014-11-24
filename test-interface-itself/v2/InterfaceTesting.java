import java.util.*;
import org.junit.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;


@RunWith(Parameterized.class)
public class InterfaceTesting {
    public MyInterface myInterface;

    public InterfaceTesting(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    @Test
    public final void testMyMethod_True() {
        assertTrue(myInterface.myMethod(true));
    }

    @Test
    public final void testMyMethod_False() {
        assertFalse(myInterface.myMethod(false));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(new Object[]{new MyClass1()}, new Object[]{new MyClass2()});
        
    }
}
