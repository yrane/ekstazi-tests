/* File name : MammalInt.java */
import org.junit.*;

public class MammalIntTest implements Animal{
   @Test
   public void eat(){
      System.out.println("Mammal eats");
   }
   @Test
   public void travel(){
      System.out.println("Mammal travels");
   }
   @Test
   public void noOfLegs(){
      System.out.println("2");
   }

   public static void main(String args[]){
      MammalIntTest m = new MammalIntTest();
      m.eat();
      m.travel();
   }
}
