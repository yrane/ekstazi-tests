import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class JunitRunTest {

  @Test
  public void testPrint() {
    System.out.println("Test written by Yogesh");
    // runProcess("javac Main.java");
    // runProcess("java Main");
    // assertEquals("10 x 5 must be 50", 50, tester.multiply(10, 5));
  }
  private static void printLines(String name, InputStream ins) throws Exception {
    String line = null;
    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
    while ((line = in.readLine()) != null) {
        System.out.println(name + " " + line);
    }
  }

  private static int runProcess(String command) throws Exception {
    Process pro = Runtime.getRuntime().exec(command);
    printLines(command + " stdout:", pro.getInputStream());
    printLines(command + " stderr:", pro.getErrorStream());
    pro.waitFor();
    System.out.println(command + " exitValue() " + pro.exitValue());
    return pro.exitValue();
  }
  //
  @Test
  public void compile_code() {
    String file_name = "TestT.java";
    String class_name = "TestT";
    String junit_path = home_path + "/.m2/repository/junit/junit/4.11/junit-4.11.jar:" + home_path +"/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"
    String home_path = System.getProperty("user.home");
    String command = "javac -cp " + junit_path + file_name;
    // String run_command = "java -cp .:" + home_path + "/.m2/repository/junit/junit/4.11/junit-4.11.jar:"+ home_path +"/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar org.junit.runner.JUnitCore " + class_name;
    String eks_run = "java -javaagent:" + home_path +"/.m2/repository/org/ekstazi/org.ekstazi.core/4.1.0/org.ekstazi.core-4.1.0.jar=mode=junit -cp .:" + junit_path + " org.junit.runner.JUnitCore" + class_name;
    try {
    //   int del_class = runProcess("rm -rf *.class");
      int result = runProcess("javac Main.java");
      runProcess("java Main");
      int result2 = runProcess(command);
      if (result2 == 0)
      {
        runProcess(eks_run);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
