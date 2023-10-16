package bearmaps;

import java.util.Collections;
import java.util.List;

public class KDTree {
    private final boolean HORIZONTAL = false;
    private Node root;

    public KDTree(List<Point> points) {
        Collections.shuffle(points); // shuffle the points to make them random
        for (Point point : points) {
            root = insert(point, root, HORIZONTAL);
        }
    }

    private Node insert(Point point, Node node, boolean spiltDirection) {
        if (node == null) {
            return new Node(point, spiltDirection);
        }

        // If node's point is equals to inserted point, just return the node itself.
        if (point.equals(node.getPoint())) {
            return node;
        }

        double cmp = comparePoint(point, node.getPoint(), spiltDirection);
        if (cmp < 0) {
            node.leftChild = insert(point, node.getLeftChild(), !spiltDirection);
        } else {
            node.rightChild = insert(point, node.getRightChild(), !spiltDirection);
        }
        return node;
    }

    private double comparePoint(Point p1, Point p2, boolean spiltDirection) {
        if (spiltDirection == HORIZONTAL) {
            return Double.compare(p1.getX(), p2.getX());
        } else {
            return Double.compare(p1.getY(), p2.getY());
        }
    }


    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearest(root, goal, root.getPoint());
    }

    private Point nearest(Node node, Point goal, Point best) {
        if (node == null) {
            return best;
        }
        double nodeDist = Point.distance(node.getPoint(), goal);
        double bestDist = Point.distance(best, goal);

        if (Double.compare(nodeDist, bestDist) < 0) {
            best = node.getPoint();
            bestDist = nodeDist;
        }
        Node goodSide;
        Node badSide;

        if (comparePoint(goal, node.getPoint(), node.getSpiltDirection()) < 0) {
            goodSide = node.leftChild;
            badSide = node.rightChild;
        } else {
            goodSide = node.rightChild;
            badSide = node.leftChild;
        }
        best = nearest(goodSide, goal, best);
        if (isBadSideWorthChecking(badSide, goal, bestDist)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    private boolean isBadSideWorthChecking(Node badNode, Point goal, double bestDist) {
        double dist;
        Point intersectionPoint;

        // No bad node exists
        if (badNode == null) {
            return false;
        }

        if (badNode.spiltDirection == HORIZONTAL) {
            intersectionPoint = new Point(badNode.getPoint().getX(), goal.getY());
        } else {
            intersectionPoint = new Point(goal.getX(), badNode.getPoint().getY());
        }
        dist = Point.distance(intersectionPoint, goal);

        return bestDist > dist;
    }

    private static class Node {
        Point point;
        boolean spiltDirection; // This indicates the action direction
        Node leftChild;
        Node rightChild;

        public Node(Point point, boolean spiltDirection) {
            this.point = point;
            this.spiltDirection = spiltDirection;
            leftChild = null;
            rightChild = null;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public boolean getSpiltDirection() {
            return spiltDirection;
        }

        public Point getPoint() {
            return point;
        }
    }
}
