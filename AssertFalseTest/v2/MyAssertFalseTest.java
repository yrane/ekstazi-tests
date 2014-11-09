import org.junit.Test;
import static org.junit.Assert.*;
 
public class MyAssertFalseTest {
 
    public boolean isEvenNumber(int number){
         
        boolean result = false;
        if(number%2 == 0){
            result = true;
        }
        return result;
    }
     
    @Test
    public void evenNumberTest(){
        MyAssertFalseTest asft = new MyAssertFalseTest();
        assertFalse(asft.isEvenNumber(3));
    }
}
