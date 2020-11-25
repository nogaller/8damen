package kata;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class DamesRandomTest {

	@Test
	public void findDiagonalConflicts_diagonal() throws Exception {
		int[] board = { 5, 0, 1, 2, 4 };
		DamesRandom dames = new DamesRandom(board);
		Set<Integer> conflicts = dames.findDiagonalConflicts();
		assertEquals(4, conflicts.size());
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, toArray(conflicts));
	}

	@Test
	public void findDiagonalConflicts_3inLine() throws Exception {
		// values y= 7,6,5 are in one line
		int[] board = { 4, 2, 7, 3, 6, 0, 5, 1 };
		DamesRandom dames = new DamesRandom(board);
		Set<Integer> conflicts = dames.findDiagonalConflicts();
		assertEquals(2, conflicts.size());
		assertArrayEquals(new int[] { 2, 6 }, toArray(conflicts));
	}

	/**
	 * transform
	 *
	 * @param collection of Integers
	 * @return to array id INT
	 */
	public static int[] toArray(Collection<Integer> collection) {
		int size = collection.size();
		int[] arr = new int[size];
		Iterator<Integer> iter = collection.iterator();
		for (int i = 0; i < size; i++)
			arr[i] = iter.next();
		return arr;
	}

}
