import org.junit.Test;
import static org.junit.Assert.*;
public class AnonymousTest {
    @Test
    public void testanont() {
        HelloWorldAnonymousClass myApp =
            new HelloWorldAnonymousClass();
        myApp.sayHello();
    }
}
