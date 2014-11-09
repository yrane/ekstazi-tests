//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : StackTest
//
// Description:
//
//   This tests any concrete subclass of the AbstractStack ordered collection
// using the JUnit framework. It automatically performs various tests, logging
// their failures.
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


import org.junit.*;                         //For tags
import static org.junit.Assert.*;           //For assertions
import java.util.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.cmu.cs.pattis.cs151xx.orderedCollections.*;

public class StackTest {
	private AbstractStack s;
	private String n1,n2,n3;

//Magic: note the name StackTest.class
//It appears that this is no longer needed
//	public static junit.framework.Test suite() {
//		return new JUnit4TestAdapter(StackTest.class);
//	}


	private AbstractStack getStack()
	{
	  //Choose what kind of stack to test
	  //This method is called in more than one place below.
	  //I would normally use reflection to generalize this code,
	  //  but it appears that junit's reflection interferes with
	  //  it, so this is the best I can do for now.

	  return new ArrayStack();

	  //or try return new LinkedStack();
	}


	//Set up the stack before each individual test
	@Before public void setUp()
	{
		s  = getStack();
		n1 = "Able";
		n2 = "Baker";
		n3 = "Charlie";
	}

	@Test public void clear()
	{
	  s.add(n1);
	  s.add(n2);
	  s.add(n3);
		assertTrue("before clear, size != 3", s.size() == 3);
	  s.clear();
		assertTrue("after clear, size != 0", s.size() == 0);
	}

  @Test public void addRemove()
  {
	  s.add(n1);
	  s.add(n2);
	  s.add(n3);
	  assertSame("on first String remove", n3,s.remove());
	  assertSame("on second String remove",n2,s.remove());
	  assertSame("on third String remove", n1,s.remove());
		assertTrue("after 3 add/remove, size != 0", s.size() == 0);
	  try {
	    s.remove();
      fail("remove on empty (String) stack failed to throw NoSuchElementException");
    } catch (NoSuchElementException e){}

	  for (int i=0; i<1000; i++)
	  	s.add(new Integer(i));
	  for (int i=999; i>=0; i--)
		  assertEquals("on String remove "+i,new Integer(i),s.remove());
		assertTrue("after 1000 add/remove, size != 0", s.size() == 0);
    try {
		    s.remove();
	      fail("remove on empty (Integer) stack failed to throw NoSuchElementException");
	   } catch (NoSuchElementException e){}
}


  @Test public void addallRemove()
  {
	  AbstractStack s2 = getStack();
	  s2.add(n1);
	  s2.add(n2);
	  s2.add(n3);
	  s.addAll(s2);
	  assertEquals("first remove failed", n1,s.remove());
	  assertEquals("second remove failed",n2,s.remove());
	  assertEquals("third remove failed", n3,s.remove());
 }


  @Test public void equals()
  {
	  AbstractStack s2 = getStack();
	  s2.add(n1);
	  s2.add(n2);
	  s2.add(n3);
	  s.add(n1);
	  s.add(n2);
	  s.add(n3);
	  assertEquals("3 element stacks not equals",            s, s2);
	  assertEquals("3 element stacks not equals (symmetic)", s2,s);
	  s.remove();
	  assertFalse("3/2 element stacks equals",            s.equals(s2));
	  assertFalse("3/2 element stacks equals (symetric)", s2.equals(s));
 }


  @Test public void peek()
  {
	  s.add(n1);
	  assertEquals("first peek failed",n1,s.peek());
	  s.add(n2);
	  assertEquals("second peek failed",n2,s.peek());
	  s.add(n3);
	  assertEquals("third peek failed",n3,s.peek());
	  s.remove();
	  assertEquals("first peek after remove failed",n2,s.peek());
	  s.remove();
	  assertEquals("second peek after remove failed",n1,s.peek());
	  s.remove();
	  try {
	    s.peek();
      fail("peek on empty stack failed to throw NoSuchElementException");
    } catch (NoSuchElementException e){}
  }


  @Test public void size1()
	{
		assertTrue("size after construction != 0",s.size() == 0);
	  s.add(n1);
		assertTrue("size after one add != 1", s.size() == 1);
	  s.add(n2);
		assertTrue("size after two adds != 2", s.size() == 2);
	  s.add(n3);
		assertTrue("size after three adds != 3", s.size() == 3);
	  s.clear();
		assertTrue("size after three adds and a clear != 0", s.size() == 0);
	}


  @Test public void isEmpty()
  {
		assertTrue("not isEmpty after construction", s.isEmpty());
	  s.add(n1);
		assertFalse("isEmpty after add", s.isEmpty());
	  s.remove();
		assertTrue("not isEmpty after add/remove", s.isEmpty());
  }


  @Test public void size2()
	{
	  s.add(n1);
	  s.add(n2);
		s.add(n3);
	  s.remove();
		assertTrue("size after 3 adds and 1 remove != 2", s.size() == 2);
    s.remove();
		assertTrue("size after 3 adds and 2 removes != 1", s.size() == 1);
    s.remove();
		assertTrue("size after 3 adds and 3 removes != 0", s.size() == 0);
	}


  @Test public void iterator()
	{
	  s.add(n1);
	  s.add(n2);
	  s.add(n3);
	  //Here I left off the first String argument
	  Iterator i = s.iterator();
	  assertTrue(i.hasNext());
	  assertEquals(n3,i.next());
	  assertTrue(i.hasNext());
	  assertEquals(n2,i.next());
	  assertTrue(i.hasNext());
	  assertEquals(n1,i.next());
	  assertFalse(i.hasNext());
	  try {
	    i.next();
      fail("next on exhausted iterator failed to throw NoSuchElementException");
    } catch (NoSuchElementException e){}


	}

}
