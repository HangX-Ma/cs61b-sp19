import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {


    public MyHashMap() {
        this(DEFAULT_BUCKET_SIZE, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = createTable(initialSize);
        maxLoadFactor = loadFactor;
    }

    private static final double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    private static final int DEFAULT_BUCKET_SIZE = 16;

    private final double maxLoadFactor;
    private int size = 0;

    private Collection<Node>[] buckets;

    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    private Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] bucketCollection = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            bucketCollection[i] = createBucket();
        }
        return bucketCollection;
    }

    @Override
    public void clear() {
        buckets = createTable(DEFAULT_BUCKET_SIZE);
        size = 0;
    }

    /* We can assume the 'null' key will never be inserted. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key, buckets);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    private Node getNode(K key) {
        int index = getIndex(key, buckets);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int index = getIndex(key, buckets);
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }
        node = createNode(key, value);
        buckets[index].add(node);
        size += 1;
        if (checkLoadFactor()) {
            resize();
        }
    }

    private void resize() {
        int index;
        Node node;

        Collection<Node>[] newTable = createTable(buckets.length * 2);
        Iterator<Node> hashMapNodeIterator = new hashMapNodeIteractor();

        while (hashMapNodeIterator.hasNext()) {
            node = hashMapNodeIterator.next();
            index = getIndex(node.key, newTable);
            newTable[index].add(node);
        }
        buckets = newTable;
    }

    private boolean checkLoadFactor() {
        return (double) size / buckets.length >= maxLoadFactor;
    }

    private int getIndex(K key, Collection<Node>[] buckets) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        buckets[getIndex(key, buckets)].remove(node);
        size -= 1;
        return node.value;
    }

    @Override
    public V remove(K key, V value) {
        Node node = getNode(key);
        if (node == null || !node.value.equals(value)) {
            return null;
        }
        buckets[getIndex(key, buckets)].remove(node);
        size -= 1;
        return node.value;
    }

    @Override
    public Iterator<K> iterator() {
        return new hashMapKeyIterator();
    }

    private class hashMapKeyIterator implements Iterator<K> {
        private final Iterator<Node> nodeIterator = new hashMapNodeIteractor();

        @Override
        public boolean hasNext() {
            return nodeIterator.hasNext();
        }

        @Override
        public K next() {
            return nodeIterator.next().key;
        }
    }

    private class hashMapNodeIteractor implements Iterator<Node> {
        private final Iterator<Collection<Node>> bucketIterator = Arrays.stream(buckets).iterator();
        private Iterator<Node> collectionIterator;
        private int leftNodeSize = size;

        @Override
        public boolean hasNext() {
            return leftNodeSize > 0;
        }

        @Override
        public Node next() {
            if (collectionIterator == null || !collectionIterator.hasNext()) {
                while (bucketIterator.hasNext()) {
                    Collection<Node> collection = bucketIterator.next();
                    if (collection.isEmpty()) {
                        continue;
                    }
                    collectionIterator = collection.iterator();
                    break;
                }
            }
            leftNodeSize -= 1;
            return collectionIterator.next();
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
