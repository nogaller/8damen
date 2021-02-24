package kata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void printArray() throws Exception {
		String out = Utils.printArray(new int[] { 1, 2, 3, 4 });
		assertEquals("1 2 3 4", out);
	}

	@Test
	public void random() throws Exception {
		boolean[] out = new boolean[10];
		for (int i = 0; i < 0xFF; i++) {
			int rnd = Utils.random(10);
			/* a runtime check for the borders [0,10) is performed implicitly */
			out[rnd] = true;
		}

		/* all values 0-9 should be chosen at least once */
		for (boolean element : out)
			assertTrue(element);
	}
}
