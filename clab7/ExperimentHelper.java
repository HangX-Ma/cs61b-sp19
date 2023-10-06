/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        int depthLevel = (int)Math.floor((Math.log(N) / Math.log(2)) + 1);
        int sum = 0;
        int num = 0;
        for (int level = 0; level < depthLevel; level++) {
            int levelNum = (int)Math.pow(2, level);
            for (int j = 0; j < levelNum; j++) {
                sum += level;
                num += 1;
                if (num >= N) {
                    break;
                }
            }
        }
        return sum;
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     */
    public static double optimalAverageDepth(int N) {
        return (double) optimalIPL(N) / (double) N;
    }

    // randomly delete an item using asymmetric Hibbard deletion and insert a random item
    public static void asymmetricDeletionAndInsertion(BST<Integer> bst) {
        int randomItem;
        // delete a random item
        while (true) {
            randomItem = RandomGenerator.getRandomInt(5000);
            if (!bst.contains(randomItem)) {
                continue;
            }
            bst.deleteTakingSuccessor(randomItem);
            break;
        }

        // insert a random item
        while (true) {
            randomItem = RandomGenerator.getRandomInt(5000);
            if (bst.contains(randomItem)) {
                continue;
            }
            bst.add(randomItem);
            break;
        }
    }


    // randomly delete an item using symmetric deletion and insert a random item
    public static void symmetricDeletionAndInsertion(BST<Integer> bst) {
        int randomItem;
        // delete a random item
        while (true) {
            randomItem = RandomGenerator.getRandomInt(5000);
            if (!bst.contains(randomItem)) {
                continue;
            }
            bst.deleteTakingRandom(randomItem);
            break;
        }

        // insert a random item
        while (true) {
            randomItem = RandomGenerator.getRandomInt(5000);
            if (bst.contains(randomItem)) {
                continue;
            }
            bst.add(randomItem);
            break;
        }
    }
}
