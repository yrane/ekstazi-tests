import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SwitchTest {

  @Test
  public void testSwitch() {
    SwitchMain tester = new SwitchMain();
    assertEquals('C', tester.switchfun('B'));
  }
}
