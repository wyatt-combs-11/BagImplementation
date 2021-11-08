import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * You can use this JUnit tester as a starting point for testing your implementations.
 * This JUnit tester is NOT complete.  It contains very basic tests.  You should
 * modify it to fit your needs.  Don't forget to test:
 * - Edge cases
 * - Proper resizing of the array (both growing and shrinking)
 * 
 * You can write more tests if you want.  Just follow the structure below, and 
 * put @Test before each test.
 */


public class JUnitTest_Bags {

	BagInterface<String> b1, b2;
	BagInterface<Integer> b3, b4;
	
	// This code here will be run before each test.  You can use these
	// bags in all your testers.
	// You can change the code below to say LinkedBag() instead of ArrayBag().
	@Before
    public void setUpArrayBags() {
        b1 = new ArrayBag(); 
        //b1 = new LinkedBag();
        
        // Make one commented out
        b2 = new ArrayBag(5); // this constructor only makes sense in ArrayBag
        //b2 = new LinkedBag();
        
        // Integers
        b3 = new ArrayBag();
        b4 = new ArrayBag();
        //b3 = new LinkedBag();
        //b4 = new LinkedBag();
	}
	
	// This next test only makes sense for ArrayBag. It tests to make sure
	// your code is throwing the proper exception.  You can comment out this
	// one test when testing LinkedBag.
	@Test(expected = IllegalArgumentException.class)
	public void intConstructorThrowsProperException() {
		b2 = new ArrayBag(-14);
	}
	
	// All of the tests below should work correctly for ArrayBag and for LinkedBag
	
	@Test
	public void testSize() {
		assertEquals(0, b1.size());
		b1.add("a");
		assertEquals(1, b1.size());
		b1.add("b");
		assertEquals(2, b1.size());
		b1.add("b");
		assertEquals(3, b1.size());
		b1.add("b");
		assertEquals(4, b1.size());
		
		
		assertEquals(0, b3.size());
		b3.add(8);
		assertEquals(1, b3.size());
		
		b1.remove("b");
		assertEquals(3, b1.size());
		b1.remove("b");
		assertEquals(2, b1.size());
		b1.remove("b");
		assertEquals(1, b1.size());
		b1.remove("b");
		assertEquals(1, b1.size());
		
		b1.add("b");
		b1.add("b");
		b1.add("b");
		assertEquals(4, b1.size());
		b1.clear();
		assertEquals(0, b1.size());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(b1.isEmpty());
		assertTrue(b3.isEmpty());
		
		b1.add("a");
		assertFalse(b1.isEmpty());
		
		b3.add(-8);
		assertFalse(b3.isEmpty());
	}

	@Test
	public void testAdd() {
		b1.add("a");
		assertTrue(b1.contains("a"));
		assertFalse(b1.contains("b"));
		assertFalse(b1.isEmpty());
		assertEquals(1, b1.size());
		
		b3.add(3);
		assertTrue(b3.contains(3));
		assertFalse(b3.contains(4));
		assertFalse(b3.isEmpty());
		assertEquals(1, b3.size());
	}

	@Test
	public void testRemove() {
		b1.add("a");
		assertTrue(b1.contains("a"));
		b1.remove();
		assertFalse(b1.contains("a"));
		assertEquals(0, b1.size());
		
		assertFalse(b2.remove("a"));
		b2.add("a");
		assertFalse(b2.isEmpty());
		assertTrue(b2.remove("a"));
		assertTrue(b2.isEmpty());
	}

	@Test
	public void testRemoveString() {
		b1.add("a");
		assertTrue(b1.contains("a"));
		assertFalse(b1.remove("b"));
		b1.remove("a");
		assertFalse(b1.contains("a"));
		assertFalse(b1.remove("a"));
		assertEquals(0, b1.size());
	}

	@Test
	public void testClear() {
		b1.add("a");
		b1.add("b");
		b1.clear();
		assertEquals(0, b1.size());
		
		for(int i = 0; i < 5; i++)
			b3.add(i);
		
		assertEquals(5, b3.size());
		b3.clear();
		assertEquals(0, b3.size());
	}

	// Note: using new String("a") instead of just "a" is helpful because
	// it will help you make sure you used equals() rather than == in your method.
	@Test
	public void testGetFrequencyOf() {
		b1.add("a");
		b1.add("a");
		b1.add("b");
		b1.add("b");
		assertEquals(2, b1.getFrequencyOf(new String("a")));
		
		b1.clear();
		assertEquals(0, b1.getFrequencyOf(new String("a")));
		
		b3.add(1);
		b3.add(2);
		b3.add(-100);
		b3.add(1);
		assertEquals(2, b3.getFrequencyOf(1));
		assertEquals(1, b3.getFrequencyOf(-100));
		assertEquals(0, b3.getFrequencyOf(10000));
	}

	@Test
	public void testContains() {
		b1.add("b");
		assertFalse(b1.contains("a"));
		assertTrue(b1.contains("b"));
		
		b2.add("a");
		b2.add("a");
		assertTrue(b2.contains("a"));
		b2.remove("a");
		assertEquals(1, b2.size());
		assertTrue(b2.contains("a"));
		
		b2.remove();
		assertFalse(b2.contains("b"));
	}

	@Test
	public void testToArray() {
		b1.add("a");
		b1.add("b");
		String[] ar = b1.toArray();
		b3.add(3);
		b3.add(2);
		Integer[] ar2 = b3.toArray();
		
		assertEquals(2, ar.length);
		assertEquals("a", ar[0]);
		assertEquals("b", ar[1]);
		assertEquals(2, ar2.length);
		assertEquals(new Integer(3), ar2[0]);
		assertEquals(new Integer(2), ar2[1]);
	}

	@Test
	public void testRemoveDuplicates() {
		
		String[] data = {"a", "b", "b", "a", "a", "c"};
		String[] result = {"a", "b", "c"};
		Integer[] data2 = {1, 1, 2, 2, 1, 2, 1, 1, 11};
		Integer[] result2 = {1, 2, 11};
		
		for (String s : data) {
			b1.add(s);
		}
		
		for (Integer i : data2) {
			b3.add(i);
		}
		
		b1.removeDuplicates();
		b3.removeDuplicates();
		assertEquals(result.length, b1.size());
		assertEquals(result2.length, b3.size());
		for (String s : result) {
			assertTrue(b1.contains(s));
		}
		for (Integer i : result2) {
			assertTrue(b3.contains(i));
		}
	}

	@Test
	public void testContainsAll() {
		String[] s1 = {"A", "B", "C"};
		String[] s2 = {"A", "A", "B", "A"};
		Integer[] i1 = {1, 1, 2, 2, 1, 2, 1, 1, 11};
		Integer[] i2 = {1, 2, 11};
		
		for (String s : s1) b1.add(s);
		for (String s : s2) b2.add(s);
		for (Integer i : i1) b3.add(i);
		for (Integer i : i2) b4.add(i);
		
		assertTrue(b1.containsAll(b2));
		assertTrue(b1.containsAll(b1));
		assertFalse(b2.containsAll(b1));
		
		assertTrue(b3.containsAll(b4));
		assertTrue(b4.containsAll(b3));
		b4.remove();
		assertTrue(b3.containsAll(b4));
		assertFalse(b4.containsAll(b3));
	}

	@Test
	public void testSameItems() {
		
		String[] s1 = {"B", "A", "B", "C"};
		String[] s2 = {"C", "A", "B","B"};
		String[] s3 = {"C", "A", "B"};
		String[] s4 = {"C", "A", "B","D"};
		Integer[] i1 = {1, 1, 2, 2, 1, 2, 1, 1, 11};
		Integer[] i2 = {1, 2, 1, 2, 2, 1, 1, 1, 11};
		Integer[] i3 = {1, 1, 11};
		Integer[] i4 = {1, 2, 11};
		
		for (String s : s1) b1.add(s);
		for (String s : s2) b2.add(s);
		assertTrue(b1.sameItems(b2));
		
		b1.clear();
		for (String s : s3) b1.add(s);
		assertFalse(b1.sameItems(b2));
		b1.clear();
		for (String s : s4) b1.add(s);
		assertFalse(b1.sameItems(b2));
		
		
		for (Integer i : i1) b3.add(i);
		for (Integer i : i2) b4.add(i);
		assertTrue(b3.sameItems(b4));
		
		b3.clear();
		for (Integer i : i3) b3.add(i);
		assertFalse(b3.sameItems(b4));
		b3.clear();
		for (Integer i : i4) b3.add(i);
		assertFalse(b3.sameItems(b4));
	}
	
	@Test
	public void testResize() {
		// Only for ArrayBag
		
		assertEquals(((ArrayBag) b1).getArrSize(), 10);
		for(int i = 0; i < 10; i++)
			b1.add("a");

		assertEquals(b1.size(), 10);
		assertEquals(((ArrayBag) b1).getArrSize(), 10);
		b1.add("a");
		assertEquals(b1.size(), 11);
		assertEquals(((ArrayBag) b1).getArrSize(), 20);
		b1.remove();
		assertEquals(b1.size(), 10);
		assertEquals(((ArrayBag) b1).getArrSize(), 20);
		b1.remove();
		assertEquals(b1.size(), 9);
		assertEquals(((ArrayBag) b1).getArrSize(), 10);
		
	}
	
	
}
