package kata;

import static kata.Utils.random;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Heuristischer Algorithmus<br>
 * Die Lösung geht nach Zufalsprinzip vor<br>
 * Es werden Links-nach-rechts Damen zufällig an mit schnellstem Test platziert
 * <br>
 * Filtere dann alle Damen in Konflikt <br>
 * Versuche sie zu tauschen, damit Konflikte weniger werden <br>
 * Bei Glück konvergiert die Lösung ziemlich schnell!
 *
 * @author Anton
 *
 */
public class DamesRandom {

	/**
	 * Calculate a solution for hard-coded <b>N</b>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		DamesRandom dames = new DamesRandom(13);
		dames.fixDiagonalConflicts();

		time = System.currentTimeMillis() - time;
		System.out.printf("Calculated in %sms\n", time);
		System.out.println(dames);
		System.out.println();
		Utils.printGfx(dames.board);
	}

	private int[] board;

	/**
	 * Constructor mainly for tests
	 *
	 * @param board - int array
	 */
	DamesRandom(int[] board) {
		this.board = board;
	}

	/**
	 * Default constructor will create a randomly filled board with the fastest
	 * horizontal (and vertical) check only
	 *
	 * @param size - width of the field
	 */
	public DamesRandom(int size) {
		if ((size & 1) == 0)
			throw new ExceptionInInitializerError("Only EVEN size allowed");
		if (size < 9)
			throw new ExceptionInInitializerError("No solution for N<9");

		board = new int[size];

		for (int i1 = 0; i1 < size; i1++) {
			board[i1] = random(size);
			if (!checkH(i1))
				i1--;
		}
	}

	public int[] getBoard() {
		return board;
	}

	/**
	 * fast check for horizontal conflicts, <br>
	 * to the left from a given cell
	 *
	 * @param х
	 * @return
	 */
	/**
	 * @param x
	 * @return
	 */
	private boolean checkH(int x) {
		final int y = board[x];
		for (int j = 0; j < x; j++)
			if (board[j] == y)
				return false;
		return true;
	}

	/**
	 * now find fields in conflict, and randomly swap them
	 */
	private void fixDiagonalConflicts() {
		Set<Integer> conflicts = findDiagonalConflicts();
		while (!conflicts.isEmpty())
			/* for many conflicts - swap any 2 of them */
			if (conflicts.size() > 4)
				conflicts = swapRandom(conflicts);
			/*
			 * for a low amount of conflicts, swap any one of them, with random another one
			 */
			else if (conflicts.size() > 0)
				conflicts = swapOne(conflicts);
	}

	/**
	 * find conflicting fields
	 *
	 * @return distinct set of fields
	 */
	Set<Integer> findDiagonalConflicts() {
		/* Parallel threads, use all cores */
		Set<Integer> conflicts = Collections.synchronizedSet(new HashSet<>());
		IntStream.range(0, board.length).parallel().forEach(x -> findDiagonalConflicts(conflicts, x));
		return conflicts;
	}

	private void findDiagonalConflicts(Set<Integer> conflicts, int x) {
		Set<Long> declinations = new HashSet<>(board.length - x);
		for (int xr = x + 1; xr < board.length; xr++) {
			int dx = xr - x;
			int dy = board[xr] - board[x];
//				45° and 135°
			if (dx == dy || dx == -dy) {
				conflicts.add(x);
				conflicts.add(xr);
			}

//				any 3 in one line
			double declination = 1000000. * dy / dx;
			long dec = Math.round(declination);
			if (!declinations.add(dec)) {
				conflicts.add(x);
				conflicts.add(xr);
			}
		}
	}

	// FIXME debugging information, that algorithm is in convergence
	private int prvConflicts = 1000;

	/**
	 * Take randomly 2 conflicting values, and swap them.<br>
	 * If the result is worse - reverse the swap, try another swap.
	 */
	private Set<Integer> swapRandom(Set<Integer> conflicts) {
		if (conflicts.size() < prvConflicts) {
			prvConflicts = conflicts.size();
			System.out.println("Conflicting: " + prvConflicts);
		}

		final int size = conflicts.size();
		int i = (Integer) conflicts.toArray()[random(size)];
		int j = (Integer) conflicts.toArray()[random(size)];
		if (i == j)
			swapRandom(conflicts);
		else
			swap(i, j);

		/* Check that result is not worse, than before */
		Set<Integer> conflicts2 = findDiagonalConflicts();
		if (conflicts2.size() > size) {
			swap(j, i);
			return conflicts;
		} else
			return conflicts2;
	}

	/**
	 * randomly take 1 conflicting value, and swap it with some one from
	 * outside.<br>
	 * If the result is worse - reverse the swap, try another swap.
	 */
	private Set<Integer> swapOne(Set<Integer> conflicts) {
		int size = conflicts.size();
		int i = (Integer) conflicts.toArray()[random(size)];
		int j = random(board.length);
		swap(i, j);

		Set<Integer> conflicts2 = findDiagonalConflicts();
		if (conflicts2.size() > size) {
			swap(j, i);
			return conflicts;
		} else
			return conflicts2;
	}

	private void swap(int i, int j) {
		int tmp = board[i];
		board[i] = board[j];
		board[j] = tmp;
	}

	@Override
	public String toString() {
		return Utils.printArray(board);
	}
}
