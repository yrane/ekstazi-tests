import java.io.*;
public class HelloInterfaceImpl implements HelloInterface {

    private PrintStream target;

    public HelloInterfaceImpl() {
        this(System.out);
    }

    public HelloInterfaceImpl(PrintStream ps) {
       this.target = ps;
    }

    @Override
    public void sayHello() {
        target.print("Hello World!");
    }
}
