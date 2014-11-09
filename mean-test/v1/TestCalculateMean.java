import static org.junit.Assert.*;
import org.junit.*;

public class TestCalculateMean
{
	int scores[] = {10,20,30};
	@Test
	public void testmean()
	{
		CalculateMean m = new CalculateMean();
		assertEquals("Mean must be 20", 20, m.mean(scores), 0.001);
	}
}
