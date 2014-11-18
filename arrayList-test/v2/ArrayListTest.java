import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArrayListTest {

	List<Integer> testArray;

	/**
	 * This method is invoked before each test is run to set up the test array
	 * with a known set of values.
	 */
	@Before
	// Informs JUnit that this method should be run before each test
	public void setUp() {
		testArray = new ArrayList<Integer>(Arrays.asList(3, 1, 4, 1, 5));
	}

	/**
	 * Adds a value to the array and verifies the add was successful.
	 */
	@Test
	public void testAdd() {
		testArray.add(9);

		List<Integer> expected = 
			new ArrayList<Integer>(Arrays.asList(3, 1, 4, 1, 5, 9));

		assertEquals(testArray, expected);
	}

	/**
	 * Removes a value from the array and verifies the remove was successful.
	 */
	@Test
	public void testRemoveObject() {
		testArray.remove(new Integer(5));

		List<Integer> expected = 
			new ArrayList<Integer>(Arrays.asList(3, 1, 4, 1));

		assertEquals(testArray, expected);
	}

	/**
	 * Tests the indexOf method and verifies the expected return value.
	 */
	@Test
	public void testIndexOf() {
		assertEquals(testArray.indexOf(4), 2);
	}

}
