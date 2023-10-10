package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* DONE:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] buckets = new int[M];
        int N = oomages.size();
        int MINIMUM_BUCKET_SIZE_LIMITATION = N / 50;
        int MAXIMUM_BUCKET_SIZE_LIMITATION = (int) (N / 2.5);

        int bucketNum;
        for (Oomage oomage : oomages) {
            bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum] += 1;
        }

        for (int bucketSize : buckets) {
            if (bucketSize < MINIMUM_BUCKET_SIZE_LIMITATION || bucketSize > MAXIMUM_BUCKET_SIZE_LIMITATION) {
                return false;
            }
        }
        return true;
    }
}
