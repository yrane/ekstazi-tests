import java.io.*;
import java.util.*;
// import org.apache.commons.io.FileUtils;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
        // Create log2
        // Compare expected and log2 values
        // IF same pass else fail

        //

        File srcFolder = new File(project_folder);
        String dest = home_path + "/tmp/" + project_folder;
    	File destFolder = new File(dest);

    	//make sure source exists
    	if(!srcFolder.exists()){

           System.out.println("Directory does not exist!");
           //just exit
           System.exit(0);

        }else{

           try{

        	copyFolder(srcFolder,destFolder);

            compile_and_run(dest,home_path);

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

    public static void compile_and_run(String dest, String home_path) throws IOException
    {
        String javac_path = home_path + "/.m2/repository/junit/junit/4.11/junit-4.11.jar:" + home_path + "/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar ";
        // String java_path = home_path + "/.m2/repository/junit/junit/4.11/junit-4.11.jar:"+ home_path +"/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar org.junit.runner.JUnitCore ";
        // String destpath = dest + "/*.java";

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

// for (String classFile : classFiles){

        //System.out.println("Found: " + classFile + "\n");
    //    }

        // We have all the class files. Now execute these one by one
        // DID NOT WORK = "'.class' expected error"
        // JUnitCore junit = new JUnitCore();
        // Result result = junit.run(dest);
        // classfiles[1] = classfiles[1].substring(0, classfiles[1].lastIndexOf('.'));
        // System.out.println(classfiles[1]);
        // Class<?> namedClass = Class.forName(classfiles[1]);
        // Class namedClass = Class.forName("[Lclassfiles;");
        // System.out.println(namedClass);
        // String runthis = classfiles[1];
        // System.out.println(runthis);




        // String cd = "pushd " + dest;
        // try{
        //     runProcess(cd);
        // } catch (Exception e) {
        //   e.printStackTrace();
        // }
        // System.out.println(cd);
        // File f = new File(dest);
        // JFileChooser chooser = new JFileChooser();

        // boolean res = setCurrentDirectory(dest);
        // System.out.println("Directory set?: " + res);
    //     String set_path = "cd " + dest;
    //     try{
    //         runProcess(set_path);
    //     }catch(Exception e) {
    //       e.printStackTrace();
    //   }
        // chooser.setCurrentDirectory(f);

        String junit_path = home_path + "/.m2/repository/junit/junit/4.11/junit-4.11.jar:" + home_path +"/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar";
        try
        {
            for (int i = 0; i < classfiles.length; i++)
            {
                // classfiles[i] = classfiles[i].substring(0, classfiles[i].lastIndexOf('/'));
                int index = classfiles[i].lastIndexOf("/");
                classfiles[i] = classfiles[i].substring(index + 1);

                classfiles[i] = classfiles[i].substring(0, classfiles[i].lastIndexOf('.'));

                if (classfiles[i].toLowerCase().contains("test"))
                {
                    System.out.println("Currently Executing: " + classfiles[i]);
                    String eks_run = "java -javaagent:" + home_path +"/.m2/repository/org/ekstazi/org.ekstazi.core/4.1.0/org.ekstazi.core-4.1.0.jar=mode=junit -cp .:"
                                + dest + ":" + junit_path + " org.junit.runner.JUnitCore " + classfiles[i];

                // System.out.println(eks_run);
                    runProcess(eks_run);
                    eks_run = "";
                }
            }
        } catch (Exception e) {
          e.printStackTrace();
        }

        // String cd2 = "popd";
        // try{
        //     runProcess(cd2);
        // } catch (Exception e) {
        //   e.printStackTrace();
        // }
                // org.junit.runner.JUnitCore.main(new String[] {classfiles[0]});
        // org.junit.runner.JUnitCore.run(classfiles);
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
            // System.out.println(destfiles[i]);
        }
        return destfiles;
    }

    private static int runProcess(String command) throws Exception {
        String remove = "rm -rf .ekstazi";
        Process rm = Runtime.getRuntime().exec(remove);
        rm.waitFor();
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();
        //  System.out.println(command);
        //  System.out.println("exitValue() => " + pro.exitValue());
        BufferedReader stdInput = new BufferedReader(new
        InputStreamReader(pro.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
        InputStreamReader(pro.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
        System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        return pro.exitValue();
    }

    private static void printLines(String name, InputStream ins) throws Exception {
      String line = null;
      BufferedReader in = new BufferedReader(new InputStreamReader(ins));
      while ((line = in.readLine()) != null) {
          System.out.println(name + " " + line);
      }
  }


    // public static boolean setCurrentDirectory(String directory_name)
    // {
    //     boolean result = false;  // Boolean indicating whether directory was set
    //     File    directory;       // Desired current working directory
    //
    //     directory = new File(directory_name).getAbsoluteFile();
    //     if (directory.exists() || directory.mkdirs())
    //     {
    //         result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
    //     }
    //
    //     return result;
    // }
}
