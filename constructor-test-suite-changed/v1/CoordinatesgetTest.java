import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CoordinatesgetTest extends TestCase {
  private Coordinates a;
  private Coordinates b;

  public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
  }
  
  public static TestSuite suite() {
      return new TestSuite(CoordinatesgetTest.class);
  }

  protected void setUp() throws Exception {
    a = new Coordinates(9, 10);
    b = new Coordinates(13, 14);
  }

  public void testGetX() throws Exception {
    assertEquals(10, a.getX());
    assertEquals(14, b.getX());
  }

  public void testGetY() throws Exception {
    assertEquals(9, a.getY());
    assertEquals(13, b.getY());
  }
}
