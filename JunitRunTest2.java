import java.io.*;
import java.util.*;
// import org.apache.commons.io.FileUtils;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

import javax.tools.Diagnostic;
import javax.swing.JFileChooser;
import javax.tools.DiagnosticListener;

public class JunitRunTest2 {

  public static void main(String args[]) {
    String project_folder = args[0];
    String home_path = System.getProperty("user.home");
    copy_files(project_folder, home_path);
  }

   /** run class from the compiled byte code file by URLClassloader */
    public static void copy_files(String project_folder, String home_path)
    {
        //Steps:
        // Copy project folder v1 to tmp/project folder
        // Compile and run
        // Create log1
        // Copy project folder v2 to tmp/project folder
        // Compile and run
        // Compare expected and tests run in v2 values
        // IF same pass else fail

        //
        String v1 = project_folder + "/v1";
        String v2 = project_folder + "/v2";
        File srcFolder_v1 = new File(v1);
        File srcFolder_v2 = new File(v2);
        String dest = home_path + "/tmp/" + project_folder;
        // String dest_v1 = home_path + "/tmp/" + project_folder + "/v1";
        // String dest_v2 = home_path + "/tmp/" + project_folder + "/v2";
    	File destFolder = new File(dest);
        // File destFolder_v1 = new File(dest_v1);
        // File destFolder_v2 = new File(dest_v2);
        boolean v1_flag = true;

    	//make sure source exists
    	if(!srcFolder_v1.exists() || !srcFolder_v2.exists()){

           System.out.println("Directory does not exist!");
           //just exit
           System.exit(0);

        }else{


            try{// Run for v1
            destFolder.mkdir();
        	copyFolder(srcFolder_v1,destFolder);

            compile_and_run(dest,home_path,v1_flag,project_folder);

            String remove_v1 = "rm -rf " + dest;
            try{
                Process rm_v1 = Runtime.getRuntime().exec(remove_v1);
                rm_v1.waitFor();
            }catch(Exception e){
                 e.printStackTrace();
             //error, just exit
                 System.exit(0);
            }
            // Run for v2

            copyFolder(srcFolder_v2,destFolder);
            v1_flag = false;
            compile_and_run(dest,home_path,v1_flag,project_folder);

           }catch(IOException e){
        	    e.printStackTrace();
        	//error, just exit
                System.exit(0);
           }
        }
    }

    public static void copyFolder(File src, File dest)
    	throws IOException{

    	if(src.isDirectory()){

    		//if directory not exists, create it
    		// if(!dest.exists()){
    		   dest.mkdir();
    		   System.out.println("Directory copied from "
                              + src + "  to " + dest);
    		// }

    		//list all the directory contents
    		String files[] = src.list();

    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
    	}else{
    		//if file, then copy it
    		//Use bytes stream to support all file types
    		InputStream in = new FileInputStream(src);
    	    OutputStream out = new FileOutputStream(dest);

    	    byte[] buffer = new byte[1024];

    	    int length;
    	        //copy the file content in bytes
    	    while ((length = in.read(buffer)) > 0){
    	    	   out.write(buffer, 0, length);
    	    }

    	    in.close();
    	    out.close();
    	    System.out.println("File copied from " + src + " to " + dest);
    	}
    }

    public static void compile_and_run(String dest, String home_path, boolean v_flag, String project_folder) throws IOException
    {
        String javac_path = home_path + "/.m2/repository/junit/junit/4.11/junit-4.11.jar:" + home_path + "/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar ";
        // String java_path = home_path + "/.m2/repository/junit/junit/4.11/junit-4.11.jar:"+ home_path +"/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar org.junit.runner.JUnitCore ";
        // String destpath = dest + "/*.java";
        int[] tests_run = new int[10];
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // Get all .java files from the folder
        String filetype = ".java";
        String destfiles[] = get_files(dest, filetype);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(destfiles));
        List<String> optionList = new ArrayList<String>();
        // set compiler's classpath to be same as the runtime's
        optionList.addAll(Arrays.asList("-classpath",javac_path));

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, optionList, null, compilationUnits);
        boolean success = task.call();
        fileManager.close();
        System.out.println("Compilation Successful?: " + success);

        //Now run the compiled files
        filetype = ".class";
        String classfiles[] = get_files(dest, filetype);


        String junit_path = home_path + "/.m2/repository/junit/junit/4.11/junit-4.11.jar:" + home_path +"/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar";
        try
        {
            for (int i = 0; i < classfiles.length; i++)
            {
                int index = classfiles[i].lastIndexOf("/");
                classfiles[i] = classfiles[i].substring(index + 1);

                classfiles[i] = classfiles[i].substring(0, classfiles[i].lastIndexOf('.'));

                if (classfiles[i].toLowerCase().contains("test"))
                {
                    System.out.println("Currently Executing: " + classfiles[i]);
                    String eks_run = "java -javaagent:" + home_path +"/.m2/repository/org/ekstazi/org.ekstazi.core/4.3.1/org.ekstazi.core-4.3.1.jar=mode=junit -cp .:"
                                + dest + ":" + junit_path + " org.junit.runner.JUnitCore " + classfiles[i];

                    tests_run[i] = runProcess(eks_run,v_flag);
                    eks_run = "";

                }

            }
            if (v_flag == false)
                compare_with_expected(tests_run, project_folder);

        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    public static String[] get_files(String dest, final String filetype)
    {
        // Get all .java files from the folder
        File dir = new File(dest);

        File[] files = dir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(filetype);
            }
        });

        // Getting all java file paths from the folder
        // Have to pass filenames as String array
        String destfiles[] = new String[files.length];

        for (int i = 0;i < destfiles.length;i++)
        {
            destfiles[i] = files[i].toString();
        }
        return destfiles;
    }

    private static int runProcess(String command, boolean v_flag) throws Exception {
        if (v_flag == true)
        {    String remove = "rm -rf .ekstazi";
            Process rm = Runtime.getRuntime().exec(remove);
            rm.waitFor();
        }
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();

        BufferedReader stdInput = new BufferedReader(new
        InputStreamReader(pro.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
        InputStreamReader(pro.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        String no_tests;
        char[] no = new char[20];

        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
            if (s.toLowerCase().contains("ok"))     //When all tests succeed
            {
                if (s.toLowerCase().contains("test") || s.toLowerCase().contains("tests"))
                {
                    if (v_flag == false)
                    {
                        int index = s.lastIndexOf("(");
                        no_tests = s.substring(index + 1);
                        no = no_tests.toCharArray();
                    }
                }
            }
            else if ((s.toLowerCase().contains("failures")))  //When there are failures
            {
                if (s.toLowerCase().contains("test run") || s.toLowerCase().contains("tests run"))
                {
                    if (v_flag == false)
                    {
                        String s1 = ",";
                        int index = s.lastIndexOf(s1);
                        no_tests = s.substring(index - 1);
                        no = no_tests.toCharArray();
                    }
                }
            }

        }

        // read any errors from the attempted command
        if ((s = stdError.readLine()) != null)
        {
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }

        pro.exitValue();
        return Character.getNumericValue(no[0]);
    }

    private static void printLines(String name, InputStream ins) throws Exception {
      String line = null;
      BufferedReader in = new BufferedReader(new InputStreamReader(ins));
      while ((line = in.readLine()) != null) {
          System.out.println(name + " " + line);
      }
  }

  public static void compare_with_expected(int[] tests_run, String project_folder)
  {
        String expected_file = project_folder + "/expected";
        File file = new File(expected_file);
        BufferedReader reader = null;
        int total_tests = 0;
        char[] exp;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            for (int i = 0;i < tests_run.length;i++)
            {
                total_tests = tests_run[i] + total_tests;
            }
            while ((text = reader.readLine()) != null) {
                exp = text.toCharArray();
                System.out.println("Expected Tests: " + exp[0]);
                System.out.println("Actual Tests: " + total_tests);

                if ((Character.getNumericValue(exp[0])) == total_tests)
                    System.out.println("Success!");
                else
                    System.out.println("Fail!");
                break;
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

    }

}
