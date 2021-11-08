
/**
 * An interface that describes the operations of a bag of objects.
 * @author Frank M. Carrano, Manar Mohamed, Mike Stahr
 * This code is from Chapter 1 of
 * Data Structures and Abstractions with Java 4/e by Carrano
 */
public interface BagInterface<E> {

	/** Gets the current number of entries in this bag.
	 * @return The integer number of entries currently in the bag.
	 */
	public int size();


	/** Sees whether this bag is empty.
	 * @return True if the bag is empty, or false if not.
	 */
	public boolean isEmpty();

	/** Adds a new entry to this bag.
	 * @param newEntry The E to be added as a new entry.
	 * @return True if the addition is successful, or false if not.
	 */
	public boolean add(E newEntry);

	/** Removes one unspecified entry from this bag, if possible.
	 * @return Either the removed entry, if the removal was successful, or null.
	 * */
	public E remove();

	/** Removes one occurrence of a given entry from this bag, if possible.
	 * @param anEntry The entry to be removed
	 * @return True if the removal was successful, or false if not
	 */
	public boolean remove(E anEntry);

	/** Removes all entries from this bag. */
	public void clear();

	/**
	 * Counts the number of times a given entry appears in this bag.
	 * @param anEntry The entry to be counted.
	 * @return The number of times anEntry appears in the bag. */
	public int getFrequencyOf(E anEntry);

	/**
	 * Tests whether this bag contains a given entry.
	 * @param anEntry The entry to locate.
	 * @return True if the bag contains anEntry, or false if not.
	 * */
	public boolean contains(E anEntry);

	/**
	 * Retrieves all entries that are in this bag.
	 * @return A newly allocated array of all the entries in the bag.
	 * Note: If the bag is empty, the returned array is empty.
	 * */
	public E[] toArray();


	/**
	 * Remove all duplicate items from a bag
	 */
	public void removeDuplicates();


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
	public boolean containsAll(BagInterface aBag);

	/**
	 * Checks to see if this bag contains exactly the same elements as another
	 * bag.  Since order does not matter in a bag, two bags may have the same
	 * items, but in a different order, and this method should still return true.
	 * For example {"A", "B", "C"} contains the same items as {"C", "A", "B"} (and vice versa).
	 * For example {"A", "A"} does not contain the same items as {"A", "A", "A"} (and vice versa).
	 * @param aBag Another object to check this bag against.
	 * @return True if this bag contains the same elements as another Bag
	 */
	public boolean sameItems(BagInterface aBag);
}
