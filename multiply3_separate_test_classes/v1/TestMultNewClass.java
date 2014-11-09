import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMultNewClass {
  
  @Test(expected = IllegalArgumentException.class)
  public void testException_again() {
    NewClass tester = new NewClass();
    tester.check_again(1000, 5);
  }
} 
