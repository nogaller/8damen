package kata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DamesRecursiveTest {

	@Test
	public void nextBorder() throws Exception {
		DamesRecursive dames = new DamesRecursive(new int[] { 0 });
		assertTrue(dames.next(1));
	}

	@Test
	public void next3() throws Exception {
		DamesRecursive dames = new DamesRecursive(new int[] { 1, 2, 3 });
		assertTrue(dames.next(2));
		assertEquals(0, dames.getBoard()[2]);

		assertTrue(dames.checkConflicts(2, 0));
		assertFalse(dames.checkConflicts(2, 1));
		assertFalse(dames.checkConflicts(2, 2));
	}

	@Test
	public void checkConflicts_diagonal() throws Exception {
		DamesRecursive dames = new DamesRecursive(new int[] { 1, 2, 3, 0 });
		assertFalse(dames.checkConflicts(3, 0));
		assertTrue(dames.checkConflicts(3, 5));
	}

	@Test
	public void checkConflicts_3inRow() throws Exception {
		DamesRecursive dames = new DamesRecursive(new int[] { 1, 0, 2, 0, 8 });
		assertFalse(dames.checkConflicts(4, 3));

		dames = new DamesRecursive(new int[] { 5, 0, 4, 0, 8 });
		assertFalse(dames.checkConflicts(4, 3));
	}

	@Test
	public void checkConflicts_someFields() throws Exception {
		int[] board = { 6, 1, 3, 7, 9, 2, 5, 10, 4, 11, 8 };// 11
		DamesRecursive dames = new DamesRecursive(board);
		for (int x = 10; x < board.length; x++)
			assertTrue(dames.checkConflicts(x, board[x]));
		board = new int[] { 1, 4, 2, 5, 3 };
		dames = new DamesRecursive(board);
		assertTrue(dames.checkConflicts(3, board[3]));
		assertFalse(dames.checkConflicts(4, board[4]));
	}

	@Test(expected = ExceptionInInitializerError.class)
	public void exception_on_odd_number() throws Exception {
		new DamesRecursive(2);
	}

	@Test(expected = ExceptionInInitializerError.class)
	public void exception_NoSolution() throws Exception {
		new DamesRecursive(3);
	}

	@Test
	public void constructor_singlePath() throws Exception {
		int[] board = { 2, 7, 3, 6, 8, 1, 4, 0, 0 };
		DamesRecursive dames = new DamesRecursive(board);
		assertEquals(0, dames.getBoard()[8]);
		boolean ok = dames.next(8);
		assertTrue(ok);
		assertEquals(5, dames.getBoard()[8]);
	}

	/** Solution should be randomly set */
	@Test(timeout = 1000)
	public void randomSolutions() throws Exception {
		long t0 = System.currentTimeMillis();
		int[] board0 = new DamesRecursive(9).getBoard();
		int[] board1 = new DamesRecursive(9).getBoard();
		assertNotEquals(board0[0], board1[0]);
		assertNotEquals(board0[8], board1[8]);
		System.out.println("2 runs in " + (System.currentTimeMillis() - t0) + "ms");
	}
}
