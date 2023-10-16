package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class ArrayHeapMinPQTest {

    @Test
    public void sanityAddTest() {
        ArrayHeapMinPQ h = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i++) {
            h.add(i, i);
            //make sure put is working via contains
            assertTrue(h.contains(i));
        }
    }

    @Test
    public void sanitySmallestTest() {
        ArrayHeapMinPQ h = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i++) {
            h.add(i, i);
            //make sure put is working via contains
            assertTrue(h.contains(i));
        }
        assertEquals(h.removeSmallest(), 0);
        assertEquals(h.getSmallest(), 1);
    }

    @Test
    public void sanityChangePriority() {
        ArrayHeapMinPQ h = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i++) {
            h.add(i, i);
            //make sure put is working via contains
            assertTrue(h.contains(i));
        }
        h.changePriority(0, 100);
        assertEquals(h.getSmallest(), 1);
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 200000; i += 1) {
            minHeap.add(i, 100000 - i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");

        long start2 = System.currentTimeMillis();
        for (int j = 0; j < 200000; j += 1) {
            minHeap.changePriority(j, j + 1);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");
//        jh61b.junit.TestRunner.runTests(ArrayHeapMinPQTest.class);
    }
}
