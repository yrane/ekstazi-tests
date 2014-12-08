import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CoordinatessetTest extends TestCase {
  private Coordinates a;
  private Coordinates b;

  public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
  }

  public static TestSuite suite() {
      return new TestSuite(CoordinatessetTest.class);
  }

  protected void setUp() throws Exception {
    a = new Coordinates(9, 10);
    b = new Coordinates(13, 14);
  }

public void testSetX() throws Exception {
    a.setX(19);
    assertEquals(19, a.getX());

    b.setX(125);
    assertEquals(125, b.getX());
  }

  public void testSetY() throws Exception {
    a.setY(1991);
    assertEquals(1991, a.getY());

    b.setY(100000);
    assertEquals(100000, b.getY());
  }
}
