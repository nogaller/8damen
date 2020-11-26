package kata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Die L�sung implementiert die Tiefensuche mit Backtracking<br>
 * Es werden von Links nach Rechts Damen zuf�llig an nicht-kollidierende Felder
 * platziert, bis keine L�sung mehr m�glich ist. <br>
 * Bei Sackgasse gehe einen Schritt zur�ck, und probiere andere Optionen durch,
 * bis entweder eine L�sung oder eine Sackgasse gefunden wurde. <br>
 * F�r <b>ungerade N < 9</b> gibt, den Tests nach, keine L�sung
 *
 * @author Anton
 *
 */
public class DamesRecursive {

	/**
	 * Calculate a solution for hard-coded <b>N</b>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		DamesRecursive dames = new DamesRecursive(9);

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
	DamesRecursive(int[] board) {
		this.board = board;
	}

	/**
	 * Default constructor will start an algorithm immediately
	 *
	 * @param n - width of the field
	 */
	public DamesRecursive(int size) {
		if ((size & 1) == 0)
			throw new ExceptionInInitializerError("Only EVEN size allowed");
		board = new int[size];
		boolean solved = next(0);
		if (!solved)
			throw new ExceptionInInitializerError("No solution for " + size);
	}

	public int[] getBoard() {
		return board;
	}

	// FIXME debugging info, show maximal reached depth, until a conflict occurs
	private int maxReached = 10;

	/**
	 * Calculate the next level of depth search<br>
	 * If no options left, return to previous step, and try another path
	 *
	 * @param x
	 * @return
	 */
	boolean next(int x) {
		if (x > maxReached) {
			System.out.println("::::" + maxReached);
			System.out.println(this);
			maxReached = x;
		}

		/* recursion end */
		if (x == board.length)
			return true;

		/* find possible options in current vertical */
		/* Filter in Parallel threads, get all cores busy */
		List<Integer> options = Collections.synchronizedList(new ArrayList<>());
		IntStream.range(0, board.length).parallel().filter(y -> checkConflicts(x, y)).forEach(options::add);

		/* Depth Search for all options, until first one works out */
		Collections.shuffle(options);
		return options.stream().filter(y -> {
			board[x] = y;
			return next(x + 1);
		}).findAny().isPresent();
	}

	/**
	 * Check for conflicts from left
	 *
	 * @param x
	 * @param y
	 * @return TRUE when no conflicts occurred for (X,Y) value with Dames, sitting
	 *         more left
	 */
	boolean checkConflicts(int x, int y) {
		Set<Long> declinations = new HashSet<>(x);
		for (int j = x - 1; j > -1; j--) {
//			0°
			if (y == board[j])
				return false;

			int dx = x - j;
			int dy = y - board[j];
//			45° and 135°
			if (dx == dy || dx == -dy)
				return false;

//			any 3 in one line
			double declination = 10000000d * dy / dx;
			long dec = Math.round(declination);
			if (!declinations.add(dec))
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return Utils.printArray(board);
	}
}
