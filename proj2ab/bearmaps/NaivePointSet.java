package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private final List<Point> points;

    NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point nearestPoint = null;
        double minDist = Double.MAX_VALUE;
        for (Point p : points) {
            double dist = Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2);
            if (dist < minDist) {
                minDist = dist;
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println("nearest point x=" + ret.getX() + ", y=" + ret.getY());
    }

}
