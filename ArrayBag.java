import java.lang.reflect.Array;
import java.util.Random;

/**
 * @author wyattcombs
 *
 * @apiNote
 * 		Project-03
 * 		ArrayBag
 * 		Date: 10/17/2021
 * 
 * Description:
 * 	This is a class for a data structure called an ArrayBag that implements the BagInterface. 
 * It is an array-based structure that contains public methods to alter the ArrayBag and its 
 * contents, check properties of the ArrayBag, and check how two bags compare to each other. 
 * There are also private helper methods to change array size and assist public methods.
 * 
 * DIRECTORY																		LINE #
 * ----------------------------------------------------------------------------------------
 * Properties: 																		
 * 	- All properties																Line 52
 * Constructors:
 *  - ArrayBag()																	Line 58
 *  - Workhorse Constructor()														Line 63
 *  - Copy Constructor(ArrayBag<E>) 												Line 71
 * Public Methods:
 *  - int size()																	Line 84
 *  - int getArrSize()																Line 92
 *  - boolean isEmpty()																Line 101
 *  - boolean add(E newEntry)														Line 111
 *  - E remove()																	Line 124
 *  - boolean remove(E anEntry)														Line 146
 *  - void clear()																	Line 163
 *  - int getFrequencyOf(E anEntry)													Line 174
 *  - boolean contains(E anEntry)													Line 189
 *  - E[] toArray()																	Line 204
 *  - void removeDuplicates()														Line 233
 *  - boolean containsAll(BagInterface aBag)										Line 259
 *  - boolean sameItems(BagInterface aBag)											Line 281
 *  - ArrayBag<E> clone()															Line 302
 * Private Helper Methods:
 *  - void doubleArray()															Line 310
 *  - void halfArray()																Line 320
 *  - int getIndex(E anEntry)														Line 335
 * Testing:
 *  - Class Playground																Line 344
 *
 */


public class ArrayBag<E> implements BagInterface<E> {
	
	//=================================================================== Properties
	private Object[] arr;
	private int size;
	private static final int DEFAULT_CAPACITY = 10;
	
	//=================================================================== Constructors
	public ArrayBag() {
		this(DEFAULT_CAPACITY);
	}
	
	public ArrayBag(E[] items) {
		this();
		for(E item: items)
			add(item);
	}
	
	//-- Workhorse Constructor
	public ArrayBag(int capacity) {
		if(capacity < 5)
			throw new IllegalArgumentException();
		arr = new Object[capacity];
		size = 0;
	}
	
	//-- Copy Constructor
	public ArrayBag(ArrayBag aBag) {
		this(aBag.arr.length);
		for(int i = 0; i < aBag.size; i++)
			add((E) aBag.arr[i]);
	}
	
	//=================================================================== Methods
	
	/**
	 * Gets the current number of entries in this bag.
	 * @return The integer number of entries currently in the bag.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Gets the current size of the resizable array
	 * @return The integer number of the array length
	 */
	public int getArrSize() {
		return arr.length;
	}

	/** 
	 * Sees whether this bag is empty.
	 * @return True if the bag is empty, or false if not.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/** 
	 * Adds a new entry to this bag.
	 * @param newEntry The E to be added as a new entry.
	 * @return True if the addition is successful, or false if not.
	 */
	@Override
	public boolean add(E newEntry) {
		if(size == arr.length)
			doubleArray();
		arr[size++] = newEntry;
		
		return true;
	}

	/** 
	 * Removes one unspecified entry from this bag, if possible.
	 * @return Either the removed entry, if the removal was successful, or null.
	 */
	@Override
	public E remove() {
		if(isEmpty())
			return null;
		
		Random rnd = new Random();
		int index = rnd.nextInt(size) + 1;
		E tmp = (E) arr[index];
		arr[index] = arr[--size];
		arr[size] = null;
		
		if(size < arr.length / 2)
			halfArray();
		
		return tmp;
	}

	/** 
	 * Removes one occurrence of a given entry from this bag, if possible.
	 * @param anEntry The entry to be removed
	 * @return True if the removal was successful, or false if not
	 */
	@Override
	public boolean remove(E anEntry) {
		int index = getIndex(anEntry);
		if(index == -1)
			return false;
		arr[index] = arr[--size];
		arr[size] = null;
		
		if(size < arr.length / 2)
			halfArray();
		
		return true;
	}

	/** 
	 * Removes all entries from this bag. 
	 */
	@Override
	public void clear() {
		arr = new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	/**
	 * Counts the number of times a given entry appears in this bag.
	 * @param anEntry The entry to be counted.
	 * @return The number of times anEntry appears in the bag.
	 */
	@Override
	public int getFrequencyOf(E anEntry) {
		int count = 0;
		for(int i = 0; i < size; i++)
			if(anEntry.equals(arr[i]))
				count++;
		
		return count;
	}

	/**
	 * Tests whether this bag contains a given entry.
	 * @param anEntry The entry to locate.
	 * @return True if the bag contains anEntry, or false if not.
	 */
	@Override
	public boolean contains(E anEntry) {
		for(int i = 0; i < size; i++) {
			if(getIndex(anEntry) != -1)
				return true;
		}
		
		return false;
	}

	/**
	 * Retrieves all entries that are in this bag.
	 * @return A newly allocated array of all the entries in the bag.
	 * Note: If the bag is empty, the returned array is empty.
	 */
	@Override
	public E[] toArray() {
		E[] tmp = (E[]) Array.newInstance(arr[0].getClass(), size);
		for(int i = 0; i < size; i++)
			tmp[i] = (E) arr[i];
		
		return tmp;
	}
	
	/**
	 * Prints out the array stored in the ArrayBag object. Prints it as an array with no
	 * null values printed.
	 * @return A String with the printed array.
	 */
	public String stringArray() {
		if(size == 0)
			return "[]";
		
		String ret = "[";
		for(int i = 0; i < size; i++)
			ret += arr[i] + ", ";
		ret = ret.substring(0, ret.length()-2) + "]";
		
		return ret;
	}

	/**
	 * Remove all duplicate items from a bag.
	 */
	@Override
	public void removeDuplicates() {
		for(int i = 0; i < size; i++)
			for(int j = i + 1; j < size; j++)
				if(arr[i].equals(arr[j])) {
					arr[j] = arr[--size];
					arr[size] = null;
					j--;
				}
		
		if(size < arr.length / 2)
			halfArray();
	}

	/**
	 * Checks to see if this bag contains all the elements of another bag (ignoring duplicates)
	 * For example {"A", "B"} contains all elements in {"A", "A", "A"}.
	 * But {"A", "A", "A"} does not contain all the elements in {"A", "B"}.
	 * As another example, {"A", "A", "A"} contains all the elements in {"A"}
	 * and {"A", "A", "A"} contains all the elements in {"A"}.
	 * One more: {"A", "A", "A"} contains all the elements in {},
	 * and {} does contain all the items in {},
	 * but {} does not contain all the elements in {"A", "A", "A"}.
	 * @param aBag Another object to check this bag against.
	 * @return True if this bag contains all the elements in another Bag.
	 */
	@Override
	public boolean containsAll(BagInterface aBag) {
		if(!(getClass().equals(aBag.getClass())))
			return false;
		
		aBag.removeDuplicates();
		E[] test = (E[]) aBag.toArray();
		
		for(int i = 0; i < test.length; i++)
			if(!(contains(test[i])))
				return false;
		
		return true;
	}

	/**
	 * Checks to see if this bag contains exactly the same elements as another
	 * bag.  Since order does not matter in a bag, two bags may have the same
	 * items, but in a different order, and this method should still return true.
	 * For example {"A", "B", "C"} contains the same items as {"C", "A", "B"} (and vice versa).
	 * For example {"A", "A"} does not contain the same items as {"A", "A", "A"} (and vice versa).
	 * @param aBag Another object to check this bag against.
	 * @return True if this bag contains the same elements as another Bag
	 */
	@Override
	public boolean sameItems(BagInterface aBag) {
		E[] test = (E[]) aBag.toArray();
		if(test.length != size)
			return false;
		
		ArrayBag<E> ret = new ArrayBag<>(test);
		for(int i = 0; i < size; i++) {
			E elem = (E) arr[i];
			if(!ret.remove(elem))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Creates a clone of the ArrayBag object.
	 * @return An ArrayBag object that is a clone.
	 */
	public ArrayBag<E> clone() {
		return new ArrayBag<E>(this);
	}
	
	//=================================================================== Private Helper Methods
	/**
	 * Doubles the array when size reaches the length of the array.
	 */
	private void doubleArray() {
		Object[] tmp = new Object[arr.length * 2];
		for(int i = 0; i < size; i++)
			tmp[i] = arr[i];
		arr = tmp;
	}
	
	/**
	 * Halves the array when size reaches less than half of the length of the array.
	 */
	private void halfArray() {
		if(arr.length < 10)
			return;
		
		Object[] tmp = new Object[arr.length / 2];
		for(int i = 0; i < size; i++)
			tmp[i] = arr[i];
		arr = tmp;
	}
	
	/**
	 * Finds the entry inside of the ArrayBag and gets its index.
	 * @param anEntry The entry whose index is searched for.
	 * @return The index of the entry, -1 if not found.
	 */
	private int getIndex(E anEntry) {
		for(int i = 0; i < size; i++)
			if(anEntry.equals(arr[i]))
				return i;
		
		return -1;
	}
	
	//=================================================================== Class Playground(Tester)
	public static void main(String[] args) {
		ArrayBag<String> test = new ArrayBag<>();
		ArrayBag<Integer> nums = new ArrayBag<>();
		
		test.add("a");
		test.add("b");
		test.add("c");
		System.out.println(test.stringArray());
		
	}
	
}
