import static org.junit.Assert.*;
import org.junit.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JunitTest1 {

   FirstDayAtSchool school = new FirstDayAtSchool();
   String[] bag = {"Books", "Notebooks", "Pens"};

   public static void main(String[] args) {
       junit.textui.TestRunner.run(suite());
   }

   public void testPrepareMyBag() {
      System.out.println("Inside PrepareMyBag()");
      assertArrayEquals(bag, school.prepareMyBag());
   }

   public static TestSuite suite() {
       return new TestSuite(JunitTest1.class);
   }

   public JunitTest1(String name) {
       super(name);
   }
}
