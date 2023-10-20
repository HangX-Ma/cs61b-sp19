import edu.princeton.cs.algs4.Queue;

import org.junit.Assert;
import org.junit.Test;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tqs = new Queue<>();
        tqs.enqueue("Joe");
        tqs.enqueue("Omar");
        tqs.enqueue("Itai");
        tqs.enqueue("Hang");
        tqs.enqueue("X");
        tqs.enqueue("Ma");
        Queue<String> res = QuickSort.quickSort(tqs);
        Assert.assertTrue(isSorted(res));
    }

    @Test
    public void testMergeSort() {
        Queue<String> tms = new Queue<>();
        tms.enqueue("Joe");
        tms.enqueue("Omar");
        tms.enqueue("Itai");
        tms.enqueue("Hang");
        tms.enqueue("X");
        tms.enqueue("Ma");
        Queue<String> res = MergeSort.mergeSort(tms);
        Assert.assertTrue(isSorted(res));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
