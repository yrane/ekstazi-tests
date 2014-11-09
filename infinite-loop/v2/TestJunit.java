import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

public class TestJunit {

   String message = "While_test";	
   InfiniteLoop i = new InfiniteLoop(message);
  
   @Test(timeout=1000)
   public void testPrintMessage() {
		
      System.out.println("Inside testPrintMessage()");     
      i.printMessage();  
	
   }
}
