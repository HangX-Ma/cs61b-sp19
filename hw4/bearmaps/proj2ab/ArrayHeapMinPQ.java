package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private final ArrayList<PriorityNode> nodes;
    private final HashMap<T, Integer> indexes;

    public ArrayHeapMinPQ() {
        nodes = new ArrayList<>();
        indexes = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        nodes.add(new PriorityNode(item, priority));
        indexes.put(item, size() - 1);
        ascend(size() - 1);
    }

    private void ascend(int idx) {
        int parentIdx = getParentIndex(idx);
        PriorityNode parent = nodes.get(parentIdx);
        PriorityNode current = nodes.get(idx);
        if (parentIdx > 0 && parent.compareTo(current) > 0) {
            swap(parentIdx, idx);
            ascend(parentIdx);
        }
    }

    private void descend(int idx) {
        int minimum = idx;
        int childLeftIdx = getLeftChildIndex(idx);
        int childRightIdx = getRightChildIndex(idx);
        if (childLeftIdx <= size() - 1 && nodes.get(minimum).compareTo(nodes.get(childLeftIdx)) > 0) {
            minimum = childLeftIdx;
        }
        if (childRightIdx <= size() - 1 && nodes.get(minimum).compareTo(nodes.get(childRightIdx)) > 0) {
            minimum = childRightIdx;
        }
        if (minimum != idx) {
            swap(minimum, idx);
            descend(minimum);
        }
    }

    private void swap(int a, int b) {
        PriorityNode nodeA = nodes.get(a);
        PriorityNode nodeB = nodes.get(b);
        nodes.set(b, nodeA);
        nodes.set(a, nodeB);
        indexes.put(nodeA.getItem(), b);
        indexes.put(nodeB.getItem(), a);
    }

    private int getParentIndex(int k) {
        return (k + 1) / 2;
    }

    private int getLeftChildIndex(int k) {
        return k * 2 + 1;
    }

    private int getRightChildIndex(int k) {
        return k * 2 + 2;
    }

    @Override
    public boolean contains(T item) {
        return indexes.get(item) != null;
    }

    @Override
    public T getSmallest() {
        PriorityNode root = nodes.get(0);
        if (root == null) {
            throw new NoSuchElementException();
        }
        return root.getItem();
    }

    @Override
    public T removeSmallest() {
        PriorityNode root = nodes.get(0);
        if (root == null) {
            throw new NoSuchElementException();
        }
        swap(0, size() - 1);
        nodes.remove(size() - 1);
        indexes.remove(root.getItem());
        descend(0);
        return root.getItem();
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        Integer index = indexes.get(item);
        if (index == null) {
            throw new NoSuchElementException();
        }
        double oldPriority = nodes.get(index).getPriority();
        nodes.get(index).setPriority(priority);
        if (oldPriority < priority) {
            descend(index);
        } else {
            ascend(index);
        }
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private final T item;
        private double priority;

        PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null || o.getClass() != this.getClass() || o.hashCode() != this.hashCode()) {
                return false;
            }
            return ((PriorityNode) o).getItem().equals(getItem());
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
