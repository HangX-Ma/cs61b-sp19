package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    public void sanityInsertTest() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2, 3));
        points.add(new Point(4, 2));
        points.add(new Point(4, 5));
        points.add(new Point(3, 3));
        points.add(new Point(1, 5));
        points.add(new Point(4, 4));

        KDTree kdTree = new KDTree(points);
        Point nearestPoint = kdTree.nearest(0, 7);
        assertEquals(nearestPoint, new Point(1, 5));
    }

    @Test
    public void sanityRandomTest() {
        double x, y;
        Random rand = new Random();

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i += 1) {
            x = rand.nextDouble(1000);
            y = rand.nextDouble(1000);
            points.add(new Point(x, y));
        }
        KDTree kdTree = new KDTree(points);
        NaivePointSet naivePointSet = new NaivePointSet(points);

        assertEquals(naivePointSet.nearest(10, 10), kdTree.nearest(10, 10));
    }
}