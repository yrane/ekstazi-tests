import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Entry point for all tests in this package.
 *
 * @version $Revision$ $Date$
 *
 * @author Stephen Colebourne
 */
public class JunitTestSuite extends TestCase {

    public JunitTestSuite(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(CoordinatessetTest.suite());
        suite.addTest(CoordinatesgetTest.suite());
        return suite;
}

public static void main(String args[]) {
String[] testCaseName = {
    JunitTestSuite.class.getName()
};
junit.textui.TestRunner.main(testCaseName);
}

}
