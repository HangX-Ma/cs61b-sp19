import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private List<Bear> solvedBears = new ArrayList<>();
    private List<Bed> solvedBeds = new ArrayList<>();

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        quickSort(bears, beds);
    }

    @SuppressWarnings("rawtypes")
    private <Item extends Comparable> List<Item> catenate(List<Item> l1, List<Item> l2) {
        List<Item> catenated = new ArrayList<>();
        catenated.addAll(l1);
        catenated.addAll(l2);
        return catenated;
    }

    @SuppressWarnings("rawtypes")
    private <Item extends Comparable> Item getRandomItem(List<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    private void partitionBears(
            List<Bear> unsorted, Bed pivot,
            List<Bear> less, List<Bear> equal, List<Bear> greater) {
        // We use scan method
        for (Bear Bear : unsorted) {
            if (Bear.compareTo(pivot) < 0) {
                less.add(Bear);
            } else if (Bear.compareTo(pivot) > 0) {
                greater.add(Bear);
            } else {
                equal.add(Bear);
            }
        }
    }

    private void partitionBeds(
            List<Bed> unsorted, Bear pivot,
            List<Bed> less, List<Bed> equal, List<Bed> greater) {
        // We use scan method
        for (Bed Bed : unsorted) {
            if (Bed.compareTo(pivot) < 0) {
                less.add(Bed);
            } else if (Bed.compareTo(pivot) > 0) {
                greater.add(Bed);
            } else {
                equal.add(Bed);
            }
        }
    }

    private void quickSort(List<Bear> bears, List<Bed> beds) {
        List<Bear> lessBear = new ArrayList<>();
        List<Bear> equalBear = new ArrayList<>();
        List<Bear> greaterBear = new ArrayList<>();
        List<Bed> lessBed = new ArrayList<>();
        List<Bed> equalBed = new ArrayList<>();
        List<Bed> greaterBed = new ArrayList<>();

        Bed pivotBed = getRandomItem(beds);
        Bear pivotBear = getRandomItem(bears);

        partitionBears(bears, pivotBed, lessBear, equalBear, greaterBear);
        for (Bear bear : bears) {
            if (pivotBed.compareTo(bear) == 0) {
                pivotBear = bear;
            }
        }
        partitionBeds(beds, pivotBear, lessBed, equalBed, greaterBed);

        // Just one type comparison is enough!
        if (lessBear.size() > 1) {
            quickSort(lessBear, lessBed);
        } else {
            solvedBears = catenate(equalBear, solvedBears);
            solvedBeds = catenate(equalBed, solvedBeds);
        }

        if (greaterBear.size() > 1) {
            quickSort(greaterBear, greaterBed);
        } else {
            solvedBears = catenate(solvedBears, equalBear);
            solvedBeds = catenate(solvedBeds, equalBed);
        }
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return solvedBeds;
    }
}
