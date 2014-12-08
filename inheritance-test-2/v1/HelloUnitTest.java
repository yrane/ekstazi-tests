import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class HelloUnitTest
{
  public static HelloUnit helloUnit;
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
    helloUnit = new HelloUnit();
  }

  @Test
  public void testAdd()
  {
    assertEquals(5, helloUnit.add(2, 3));
  }

}
