public class InfiniteLoop {

   private String message;

   public InfiniteLoop(String message){
      this.message = message; 
   }

   public void printMessage(){
      System.out.println(message);
      while(true);

   }   
}
