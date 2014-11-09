import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
 
public class MyAssertNotNullTest {
 
    public String getPropValue(final Integer key){
        HashMap<Integer, String> h1 = new HashMap<Integer, String>();
        h1.put(1, "First");
		h1.put(5, "Fifth");
		h1.put(2, "Second");
        return h1.get(key);
    }
     
    @Test
    public void test_1(){
        MyAssertNotNullTest msnt = new MyAssertNotNullTest();
        assertNotNull(msnt.getPropValue(2));
    }
    @Test
    public void test_2(){
        MyAssertNotNullTest msnt = new MyAssertNotNullTest();
        assertNotNull(msnt.getPropValue(5));
    }
}
