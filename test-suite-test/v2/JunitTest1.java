import static org.junit.Assert.*;
import org.junit.Test;

public class JunitTest1 {

   FirstDayAtSchool school = new FirstDayAtSchool();
   String[] bag = {"Books", "Notebooks", "Pens"};

   public void testPrepareMyBag() {
      System.out.println("Inside PrepareMyBag()");
      assertArrayEquals(bag, school.prepareMyBag());
   }
}
