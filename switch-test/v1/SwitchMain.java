public class SwitchMain {

   public char switchfun(char grade){
      //char grade = args[0].charAt(0);
    //   char grade = 'C';

      switch(grade)
      {
         case 'A' :
            return 'A';
            // break;
         case 'B' :
         case 'C' :
            System.out.println("Well done");
            return 'C';
            // break;
         default :
            System.out.println("Invalid grade");
      }
      return 'Z';
   }
}
