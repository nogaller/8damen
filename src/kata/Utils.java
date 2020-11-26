package kata;

import java.util.Arrays;
import java.util.stream.Collectors;

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
		return Arrays.stream(board).mapToObj(Integer::toString).collect(Collectors.joining(" "));
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
