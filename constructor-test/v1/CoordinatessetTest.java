import junit.framework.TestCase;

public class CoordinatessetTest extends TestCase {
  private Coordinates a;
  private Coordinates b;

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
