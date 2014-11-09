// this is your JUnit test
public class MyAbstractClassTest extends MyAbstractClass {

    @Mock
    private MyMock myMock;

    @InjectMocks
    private MyAbstractClass thiz = this;

    private String myName = null;

    @Override
    public String getName() {
        return myName;
    }

    @Test
    public void testSayHello() {
        myName = "Johnny";
        when(myMock.getHello()).thenReturn("Hello");
        String result = sayHello();
        assertEquals("Hello, Johnny", result);
    }
}
