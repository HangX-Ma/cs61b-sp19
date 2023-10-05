package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private enum gridState {
        EMPTY,
        BLOCKED,
    }
    private final gridState[][] grid;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufExcludeBottom; // avoid backslash
    private final int[][] dirs = {
            {0, 1},  // right
            {0, -1}, // left
            {1, 0},  // up
            {-1, 0}  // down
    };
    private final int ceiling = 0;
    private final int flooring = 1;
    private int openedSite;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException(
                    "N-by-N grid needs dimension N to be larger than 0");
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufExcludeBottom = new WeightedQuickUnionUF(N * N + 2);
        grid = new gridState[N][N];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                grid[row][col] = gridState.BLOCKED;
            }
        }
        openedSite = 0;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);
        if (grid[row][col] == gridState.BLOCKED) {
            grid[row][col] = gridState.EMPTY;
        }
        openedSite += 1;
        unionNeighbors(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return grid[row][col] != gridState.BLOCKED;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        return ufExcludeBottom.connected(xyTo1D(row, col), ceiling);
    }

    // check if the input 'row' and 'col' obey the grid size limitation
    private void checkBounds(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new IndexOutOfBoundsException("(" + row + ", " + col + ") is out of gird bound!");
        }
    }

    // number of open sites (constant time cost)
    public int numberOfOpenSites() {
        return openedSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(ceiling, flooring);
    }

    private void unionNeighbors(int row, int col) {
        // The topmost bubble is stuck to ceiling
        int currSiteId = xyTo1D(row, col);
        if (row == 0) {
            uf.union(currSiteId, ceiling);
            ufExcludeBottom.union(currSiteId, ceiling);
        }
        if (row == grid.length - 1) {
            uf.union(currSiteId, flooring);
        }
        for (int[] dir : dirs) {
            int nearRow = row + dir[0];
            int nearCol = col + dir[1];
            // deal with outbound situation
            if (nearRow < 0 || nearRow >= grid.length
                || nearCol < 0 || nearCol >= grid.length
                || grid[nearRow][nearCol] == gridState.BLOCKED)
            {
                continue;
            }
            // We just connect the neighbor to current cell if the neighbor isn't BLOCKED.
            uf.union(currSiteId, xyTo1D(nearRow, nearCol));
            ufExcludeBottom.union(currSiteId, xyTo1D(nearRow, nearCol));
        }
    }

    private int xyTo1D(int row, int col) {
        /* We need to create the virtual ceiling and flooring cell.
           - 'Ceiling' is used to check the percolate state.
           - 'Flooring' is used to prevent the backwash problem. */
        return row * grid.length + col + 2;
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
    }
}
