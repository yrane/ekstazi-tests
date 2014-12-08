    import org.junit.Test;
    public class ThreadTest {
    public Runnable runnable;
    @Test
    public void testExampleThread() throws Throwable {

        System.out.println("Hello, World!");       //1
        runnable = new ThreadMain(5000);        //2
        System.out.println("Goodbye, World");     //3
    }

}
