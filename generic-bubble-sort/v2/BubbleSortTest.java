import junit.framework.TestCase;

public class BubbleSortTest extends TestCase {
        private String[] string;
        private Integer[] integer;
	BubbleSort b;

        protected void setUp() throws Exception {
		b = new BubbleSort();		

                string = new String[]{
                                "Pranjal",
                                "Avichal",
                                "Gaurav",
                                "Ankur",
                };

                integer = new Integer[] {
                                0, 64, 26, 90, 45, 12, 1, 96, 150, 10,
                };
        }

        public void testBubble() throws Exception {
                b.bubble(string);

                assertEquals("Ankur", string[0]);
                assertEquals("Avichal", string[1]);
                assertEquals("Gaurav", string[2]);
                assertEquals("Pranjal", string[3]);

                b.bubble(integer);

                assertEquals((Integer) 0, integer[0]);
                assertEquals((Integer) 1, integer[1]);
                assertEquals((Integer) 10, integer[2]);
                assertEquals((Integer) 12, integer[3]);
                assertEquals((Integer) 26, integer[4]);
                assertEquals((Integer) 45, integer[5]);
                assertEquals((Integer) 64, integer[6]);
                assertEquals((Integer) 90, integer[7]);
                assertEquals((Integer) 96, integer[8]);
                assertEquals((Integer) 150, integer[9]);
        }
}
