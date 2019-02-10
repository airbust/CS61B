package hw3.hash;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        int min = oomages.size() / 50;
        int max = (int) (oomages.size() / 2.5);

        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (hashMap.containsKey(bucketNum)) {
                hashMap.put(bucketNum, hashMap.get(bucketNum) + 1);
            } else {
                hashMap.put(bucketNum, 0);
            }
        }

        for (int k : hashMap.keySet()) {
            int v = hashMap.get(k);
            if (v <= min || v >= max) {
                return false;
            }
        }

        return true;
    }
}
