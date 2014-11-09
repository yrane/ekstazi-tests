import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
public class HelloInterfaceTest {

    @Test
    public void testDefaultBehaviourEndsNormally() {
        HelloInterface hi = new HelloInterfaceImpl();    
        hi.sayHello();
    }

    @Test
    public void testCheckHelloWorld() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream target = new PrintStream(out);
        HelloInterface hi = new HelloInterfaceImpl(target);    
        hi.sayHello();
        String result = out.toString();
        assertEquals("Hello World", result);
    }
}
