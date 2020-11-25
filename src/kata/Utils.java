package kata;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility collection
 *
 * @author Anton
 */
public final class Utils {
	/**
	 * No initializaiton from outside
	 */
	private Utils() {
	}

	/**
	 * int array to String method
	 *
	 * @param array of int
	 * @return array contents as {@link String}
	 */
	public static String printArray(int[] board) {
		StringBuilder sb = new StringBuilder().append(board[0]);
		for (int j = 1; j < board.length; j++)
			sb.append(' ').append(board[j]);
		return sb.toString();
	}

	/**
	 * Print out the provided graphically
	 *
	 * @param board - as int array
	 */
	public static void printGfx(int[] board) {
		/* Helper indexes */
		for (int x = 0; x < board.length; x++)
			System.out.print(x + " ");
		System.out.println();

		for (int y = 0; y < board.length; y++) {
			for (int element : board)
				System.out.print(element == y ? "H " : "o ");
			System.out.println();
		}
	}

	/**
	 * @param max
	 * @return random {@link Integer} € [0;N[
	 */
	public static int random(int max) {
		return (int) (Math.random() * max);
	}

}
