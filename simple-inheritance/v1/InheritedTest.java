import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

// @RunWith(Parameterized.class)
public class InheritedTest extends HelloUnitTest
{
  int[] adds;
  int sum;

  // public InheritedTest(int[] adds, int sum)
  // {
  //   this.adds = adds;
  //   this.sum = sum;
  // }

  @Test
  public void testAdd2()
  {
    helloUnit = new HelloUnit();
    assertEquals(5, helloUnit.add(2, 3));
  }

  // @Parameters
  // public static Collection<?> regExValues()
  // {
  //   return Arrays.asList(new Object[][] {
  //           { new int[] { 1, 2 }, 3 },
  //           { new int[] { 100, 2 }, 102 },
  //           { new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE }, Integer.MAX_VALUE } });
  // }

  // @Test
  // public void testAdd3()
  // {
  //   assertEquals(10, helloUnit.add(6, 4));
  // }
}
