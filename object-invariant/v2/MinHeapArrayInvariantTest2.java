import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class MinHeapArrayInvariantTest2 {
    private static final long SEED = -42;
    private static final long VALUES_TO_TEST = 1000;
    
    private Random random;
    private HeapArray<Integer> heap;
    
    @Before
    public void setUp() {
        random = new Random(SEED);
        heap = new HeapArray<Integer>();
    }
        
    @Test
    public void testPop() {
        fillWithRandomValues(VALUES_TO_TEST);
        while (heap.size() > 0) {
            heap.pop();
            assertTrue(invariantHolds());
        }
    }
    
    @Test
    public void testClear() {
        fillWithRandomValues(VALUES_TO_TEST);
        heap.clear();
        assertTrue(invariantHolds());
    }

    private boolean invariantHolds() {
        Integer top = heap.peek();
        if (top == null) {
            return true;
        }
        
        Integer[] contents = new Integer[heap.size()];
        Integer min = Collections.min(Arrays.asList(heap.toArray(contents)));
        if (min > top) {
            System.out.println("Whoops!");
        }
        return min <= top;
    }

    private void fillWithRandomValues(long numValues) {
        for (int i = 0; i < numValues; i++){
            heap.add(random.nextInt());
        }
    }
}
