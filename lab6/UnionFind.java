public class UnionFind {
    private final int[] parent; // use negative number to represent the size of the set

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        int size = parent.length;
        if (vertex < 0 || vertex >= size) {
            throw new IllegalArgumentException(
                    "vertex index " + vertex + " should between [0," + size + "]"
            );
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return Math.abs(parent(find(v1)));
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) { return; }

        int size1 = Math.abs(parent[root1]);
        int size2 = Math.abs(parent[root2]);
        if (size1 <= size2) {
            parent[root1] = root2;
            parent[root2] -= size1;
        } else {
            parent[root2] = root1;
            parent[root1] -= size2;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        // find the root
        int vertexOrigin = vertex;
        while(parent[vertex] > 0) {
            vertex = parent[vertex];
        }
        int root = vertex;

        // do path compression
        vertex = vertexOrigin;
        while (parent[vertex] > 0) {
            int parentVertex = parent[vertex];
            parent[vertex] = root;
            vertex = parentVertex;
        }
        return root;
    }

    public void printParents() {
        int size = parent.length;
        for (int i = 0; i < size; i++) {
            System.out.print(parent(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        UnionFind unionFind = new UnionFind(5);
        unionFind.printParents();

        unionFind.union(0, 1);
        unionFind.union(3, 4);
        unionFind.printParents();

        unionFind.union(2, 3);
        unionFind.printParents();

        unionFind.union(2, 3);
        unionFind.printParents();

        unionFind.union(0, 3);
        unionFind.printParents();

        if (unionFind.connected(1, 2)) {
            System.out.println("vertex1 and vertex2 is connected!");
        }
    }

}
