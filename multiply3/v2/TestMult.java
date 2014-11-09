import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMult {

  @Test(expected = IllegalArgumentException.class)
  public void testExceptionIsThrown() {
    MyClass tester = new MyClass();
    tester.check(1000, 5);
  }

  @Test
  public void testMultiply() {
    MyClass tester = new MyClass();
    assertEquals("10 x 5 must be 50", 50, tester.multiply(10, 5));
    assertEquals("10 x 5 must be 50", 50, tester.multiply(20, 5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException_again() {
    NewClass tester = new NewClass();
    tester.check_again(1000, 5);
  }
}
