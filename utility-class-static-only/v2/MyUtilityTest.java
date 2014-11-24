import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.*;

public class MyUtilityTest
{
	@Test
	public void MyUtilityTest() throws IllegalAccessException, InstantiationException {
	    final Class<?> cls = MyUtility.class;
	    final Constructor<?> c = cls.getDeclaredConstructors()[0];
	    c.setAccessible(true);

	    Throwable targetException = null;
	    try {
		c.newInstance((Object[])null);
	    } catch (InvocationTargetException ite) {
		targetException = ite.getTargetException();
		return;
	    }

	    assertNotNull(targetException);
	    assertEquals(targetException.getClass(), InstantiationException.class);
	}
}
