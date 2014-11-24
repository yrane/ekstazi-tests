import junit.framework.TestCase;

public class CoordinatesgetTest extends TestCase {
  private Coordinates a;
  private Coordinates b;

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
