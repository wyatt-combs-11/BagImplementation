import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Random;

/**
 * @author wyattcombs
 *
 * @apiNote
 * 		Project-03
 * 		LinkedBag
 * 		Date: 10/17/2021
 * 
 * Description:
 * 	This is a class for a data structure called a LinkedBag that implements the BagInterface. 
 * It is a LinkedList-based structure that contains public methods to alter the LinkedBag and 
 * its contents, check properties of the LinkedBag, and check how two bags compare to each other. 
 * There are also private helper methods to assist public methods.
 *
 */


public class LinkedBag<E> implements BagInterface<E>, Iterable<E> {
	
	//==================================================================== Internal Node Class
	
	private class Node {
		//==================================================== Properties
		E data;
		Node next;
		
		//==================================================== Constructors
		Node(E data) {
			this(data, null);
		}
		
		//-- Workhorse Constructor
		Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
		
		//-- Copy Constructor (only copies data)
		Node(Node n) {
			this(n.data);
		}
		
	}
	//==================================================================== End Node Class
	
	
	
	//=================================================================== Properties
	private Node head;
	private Node tail;
	private int size;
	
	//=================================================================== Constructors
	//-- Workhorse Constructor
	public LinkedBag() {
		head = tail = null;
		size = 0;
	}
	
	public LinkedBag(E[] items) {
		this();
		for(E item: items)
			add(item);
	}
	
	//-- Copy Constructor
	public LinkedBag(LinkedBag<E> aBag) {
		this();
		Node chk = aBag.head;
		add(chk.data);
		
		while(chk.next != null) {
			add(chk.next.data);
			chk = chk.next;
		}
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
		if(isEmpty())
			head = tail = new Node(newEntry);
		else {
			tail.next = new Node(newEntry);
			tail = tail.next;
		}
		size++;
		
		return true;
	}

	/** 
	 * Removes one unspecified entry from this bag, if possible.
	 * @return Either the removed entry, if the removal was successful, or null.
	 * */
	@Override
	public E remove() {
		if(isEmpty())
			return null;
		
		E tmp;
		int i = new Random().nextInt(size);
		
		if(size == 1) {
			tmp = head.data;
			clear();
		} else if (i == 0) {
			tmp = head.data;
			head = head.next;
			size--;
		} else {
			Node chk = findNode(i - 1);
			tmp = chk.next.data;
			chk.next = chk.next.next;
			size--;
		}
		
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
		
		if(index == 0) 
			head = head.next;
		else {
			Node chk = findNode(index - 1);
			chk.next = chk.next.next;
		}
		size--;
		
		return true;
	}

	/**
	 * Removes all entries from this bag.
	 */
	@Override
	public void clear() {
		head = tail = null;
		size = 0;
	}

	/**
	 * Counts the number of times a given entry appears in this bag. Note: Calls to 
	 * private method getFrequencyOf() to compute recursively.
	 * @param anEntry The entry to be counted.
	 * @return The number of times anEntry appears in the bag. 
	 */
	@Override
	public int getFrequencyOf(E anEntry) {
		return getFrequencyOf(head, anEntry, 0);
	}
	
	/**
	 * Counts the number of times a given entry appears in this bag.
	 * @param chk The node currently being looked at.
	 * @param anEntry The entry to be counted.
	 * @param num The current number of times anEntry has been found.
	 * @return The number of times anEntry appears in the bag. 
	 */
	private int getFrequencyOf(Node chk, E anEntry, int num) {
		if(chk == null)
			return num;
		return getFrequencyOf(chk.next, anEntry, (anEntry.equals(chk.data)) ? ++num : num);
	}

	/**
	 * Tests whether this bag contains a given entry.
	 * @param anEntry The entry to locate.
	 * @return True if the bag contains anEntry, or false if not.
	 */
	@Override
	public boolean contains(E anEntry) {
		return (getIndex(anEntry) == -1) ? false : true;
	}

	/**
	 * Retrieves all entries that are in this bag.
	 * @return A newly allocated array of all the entries in the bag.
	 * Note: If the bag is empty, the returned array is empty.
	 */
	@Override
	public E[] toArray() {
		E[] tmp = (E[]) Array.newInstance(head.data.getClass(), size);
		Node chk = head;
		for(int i = 0; i < size; i++) {
			tmp[i] = chk.data;
			chk = chk.next;
		}
		
		return tmp;
	}

	/**
	 * Remove all duplicate items from a bag.
	 */
	@Override
	public void removeDuplicates() {
		Node chk = head;
		Node tmp;
		
		while(chk != null && chk.next != null) {
			tmp = chk;
			while(tmp != null && tmp.next != null) {
				if(tmp.next.data.equals(chk.data)) {
					tmp.next = tmp.next.next;
					size--;
				} else
					tmp = tmp.next;
			}
			chk = chk.next;
		}
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
		
		LinkedBag<E> ret = new LinkedBag<>(test);
		
		Node chk = head;
		for(int i = 0; i < size; i++) {
			ret.remove(chk.data);
			chk = chk.next;
		}
		
		return ret.isEmpty();
	}
	
	/**
	 * Creates a new clone of the LinkedBag object.
	 * @return A LinkedBag that is a clone of the current LinkedBag.
	 */
	public LinkedBag<E> clone() {
		return new LinkedBag<E>(this);
	}
	
	/**
	 * Creates an iterator for the LinkedBag.
	 * @return An iterator used to iterate through the LinkedBag object.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private Node prev, curr = head;
			
			@Override
			public boolean hasNext()	{	return curr != null;	}

			@Override
			public E next() 			{
				prev = curr;
				curr = curr.next;
				
				return prev.data;
			}
			
		};
	}
	
	//=================================================================== Private Helper Methods
	/**
	 * Finds the entry inside of the LinkedBag and gets its index.
	 * @param anEntry The entry whose index is searched for.
	 * @return The index of the entry, -1 if not found.
	 */
	private int getIndex(E anEntry) {
		if(isEmpty())
			return -1;
		
		return getIndex(anEntry, head, 0);
	}
	
	/**
	 * Finds the entry inside of the LinkedBag and gets its index.
	 * @param anEntry The entry whose index is searched for.
	 * @param tmp A node that follows the LinkedBag.
	 * @param index An integer that keeps track of the index the tmp node is pointing to.
	 * @return The index of the entry, -1 if not found.
	 */
	private int getIndex(E anEntry, Node tmp, int index) {
		if(tmp == null)
			return -1;
		if(anEntry.equals(tmp.data))
			return index;
		
		return getIndex(anEntry, tmp.next, ++index);
	}
	
	/**
	 * Finds the node that an index is referencing from various methods.
	 * @param index An integer that represents a node's "index" in the LinkedBag.
	 * @return The node in the specified index.
	 */
	private Node findNode(int index) {
		Node chk = head;
		for(int i = 0; i < index; i++)
			chk = chk.next;
		
		return chk;
	}
	
	//=================================================================== Class Playground(Tester)
	public static void main(String[] args) {
		LinkedBag<String> test = new LinkedBag<>();
//		LinkedBag<Integer> nums = new LinkedBag<>();
		
		test.add("a");
		test.add("b");
		test.add("t");
		test.add("t");
		test.removeDuplicates();
		for(String s : test)
			System.out.println(s);
		
	}
	
}
