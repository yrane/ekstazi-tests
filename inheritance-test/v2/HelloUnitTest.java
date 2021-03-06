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
 
  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }
 
  @Before
  public void setUp() throws Exception
  {
  }
 
  @After
  public void tearDown() throws Exception
  {
  }
 
  @Test
  public void testAdd2()
  {
    assertEquals(5, helloUnit.add(2, 3));
  }
 
  @Ignore
  @Test
  public void testMoreToBeIgnored()
  {
  }
 
  // this test will succeed as it does raise the expected exception
  @Test(expected = IndexOutOfBoundsException.class)
  public void testException()
  {
    int[] array = new int[] { 1, 2 };
    System.out.println("Last item in array: " + array[2]);
  }
 
  // this test will result in an error
  @Test(expected = RemoteException.class)
  public void testExceptionError()
  {
    int[] array = new int[] { 1, 2 };
    System.out.println("Last item in array: " + array[2]);
  }
}
