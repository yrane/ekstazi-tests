///Some import statements have been removed
import org.junit.Test;
import static org.junit.Assert.*;


//Commenting is ignored? Let's test
public class TriangleTest2 {
@Test
public void testIsoceles() {
    System.out.println("testIsoceles");
    Triangle instance = new Triangle("30", "40", "30");
    String expResult = "Isosceles";
    String result = instance.determineTriangleType();
    assertEquals(expResult, result);
}

@Test
public void testGiantTriangle() {
    System.out.println("testGiantTriangle");
    Triangle instance = new Triangle("3000000", "4000000", "3000000");
    String expResult = "I feel your triangle is too big\n";
    String result = instance.determineTriangleType();
    assertEquals(expResult, result);
}
}
