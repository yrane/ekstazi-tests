import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


//Commenting is ignored? Let's test
//Comment Line1
//Comment Line2
public class TriangleTest {
    // Create a new Triangle
    public TriangleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("\nSETUP CLASS RUNNING -- Nothing to do though");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("\nTEARDOWN CLASS RUNNING -- Nothing to do though");
    }

    @Before
    public void setUp() {
        System.out.println("\nSETUP IS RUNNING -- Nothing to do though");
    }

    @After
    public void tearDown() {
        System.out.println("TEARDOWN IS RUNNING -- Nothing to do though");
    }

    @Test
    public void testScalene() {
        System.out.println("testScalene");

        Triangle instance = new Triangle("30", "40", "50");
        String expResult = "Scalene";
        String result = instance.determineTriangleType();
        assertEquals(expResult, result);
    }

    @Test
    public void testEquilateral() {
        System.out.println("testEquilateral");
        Triangle instance = new Triangle("1", "1", "1");
        String expResult = "Equilateral"; //Comment added
        String result = instance.determineTriangleType();
        assertEquals(expResult, result);
    }
}
