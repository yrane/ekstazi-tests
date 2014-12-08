import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InheritedTest extends HelloUnitTest
{
  int[] adds;
  int sum;

  public InheritedTest(int[] adds, int sum)
  {
    this.adds = adds;
    this.sum = sum;
  }

  @Parameters
  public static Collection<?> regExValues()
  {
    return Arrays.asList(new Object[][] {
            { new int[] { 1, 2 }, 3 },
            { new int[] { 100, 2 }, 102 },
            { new int[] { 12, 3 }, 15 } });
  }

  @Test
  public void testAdd3()
  {
    assertEquals(10, helloUnit.add(6, 4));
  }
}
