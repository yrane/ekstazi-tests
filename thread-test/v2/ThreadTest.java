    import org.junit.Test;
    public class ThreadTest {
    public Runnable runnable;
    @Test
    public void testExampleThread() throws Throwable {

        System.out.println("Hello, World");       //1
        runnable = new ThreadMain(5000);        //2
        System.out.println("Goodbye, World");     //3
    }

    // public static void main (String[] args) {
    //     String[] name =
    //         { BadExampleTest.class.getName() };
    //
    //     junit.textui.TestRunner.main(name);
    // }

//     public static Test suite() {
//         return new TestSuite(
//             BadExampleTest.class);
//     }
}
