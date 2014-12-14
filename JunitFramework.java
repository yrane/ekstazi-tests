/****************************************************************************
Program Name: JunitFramework.java
Description:
1. Run the framework by executing command java JunitFramework
2. The framework will create directorories in $HOME/tmp as
$HOME/tmp/<project_folder> for each project.
3. Then it will copy the source files in folder v1 of the project directory
and copty it in the directory created in the previous step.
4. It compiles and runs the copied code. .Ekstazi folder is created for the
project.
5. The framework then repeats the steps 2-4 above for v2 source files.
6. It ocmpares the number of tests run on v2 with the expected number of
tests as mentioned in file <project_folder>/expected file.
7. If the numbers match, Success is reported otherwise the test is reported
to have Failed.
Date Completed: 12.07.2014
****************************************************************************/


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

public class JunitFramework {

  public static void main(String args[]) {
    String[] projectfiles = {
    "annotation-test",
    "anonymous-class-test",
    "anonymous-test-small",
    "ArrayEqualTest",
    "arrayList-test",
    "AssertFalseTest",
    "AssertNotNullTest",
    "assertThatTest",
    "bit-operator-test",
    "change-access-modifier",
    "changed-test-method-body",
    "class-inheritance",
    "commented-code",
    "constructor-test-suite",
    "constructor-test-suite-changed",
    "coverage-testing",
    "constructor-test",
    "employee-business-details",
    "enum-test",
    "find-max",
    "generic-bubble-sort",
    "ignore-tag",
    "infinite-loop",
    "inheritance-test",
    "inheritance-test-2",
    "interface-test",
    "interface-test-class",
    "junit-rules-test",
    "map-test",
    "max-cube-reverse",
    "mean-test",
    "multiply",
    "multiply2",
    "multiply3",
    "multiply3_2testclassfor1class",
    "multiply3_all_separate_classes",
    "multiply3_separate_test_classes",
    // "myList-test",
    "myStack-test",
    "nothing-changed",
    "object-invariant",
    "ordering-test",
    "override-test",
    "parameterized-test",
    "prime-number-2classes",
    "prime-number-checker",
    "simple-inheritance",
    "string-concatenation",
    "switch-test",
    "test-assertions",
    "testing-input-component",
    "testing-output-component",
    "test-interface-itself",
    "test-runner-test",
    "test-subclass",
    "test-suite-test",
    "thread-test",
    "triangle-test-comment-imports-ignored",
    "utility-class-static-only",
    "test-variable-name-change",
    "theory-test",
    "test-method-name-change"
    };

    JunitFramework_single.main(projectfiles);


    }
}
