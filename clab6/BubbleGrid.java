public class BubbleGrid {
    private final int[][] grid;
    private final int gridRows;
    private final int gridCols;
    private final int ceiling = 0;
    private final int[][] dirs = new int[][]{
            {0, 1},  // right
            {0, -1}, // left
            {1, 0},  // up
            {-1, 0}  // down
    };
    private final int BUBBLE = 1;
    private final int INVERSE = 2;


    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        gridRows = grid.length;
        gridCols = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        // ceiling is set to 0, so it will occupy one cell
        UnionFind uf = new UnionFind(gridRows * gridCols + 1);

        /* A bubble is stuck if
            - It is in the topmost row of the grid, or
            - It is orthogonally adjacent to a bubble that is stuck. */

        /* We use the inverse method that firstly set those dart hit bubble
           empty. Afterward, we add the bubble to that place */
        int dartX, dartY;
        for (int[] dart : darts) {
            dartX = dart[0];
            dartY = dart[1];
            if (grid[dartX][dartY] == BUBBLE) {
                // update the grid
                grid[dartX][dartY] = INVERSE;
            }
        }

        /* Update the union */
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                if (grid[row][col] == BUBBLE) {
                    unionNeighbors(row, col, uf);
                }
            }
        }

        /* This array store the falling bubbles that influenced by the popped bubble,
           which doesn't contain the popped one! */
        int dartSize = darts.length;
        int [] fallingBubbleNums = new int[dartSize];
        /* Get the bubble number that connects to the ceiling */
        int count = uf.sizeOf(ceiling);
        for (int i = dartSize - 1; i >= 0; i--) {
            dartX = darts[i][0];
            dartY = darts[i][1];
            if (grid[dartX][dartY] == INVERSE) {
                grid[dartX][dartY] = BUBBLE;
                unionNeighbors(dartX, dartY, uf);
            }
            int newCount = uf.sizeOf(ceiling);
            fallingBubbleNums[i] = newCount > count ? newCount - count - 1 : 0;
            count = newCount;
        }

        return fallingBubbleNums;
    }

    private void unionNeighbors(int row, int col, UnionFind uf) {
        // The topmost bubble is stuck to ceiling
        if (row == 0) {
            uf.union(xyTo1D(row, col), ceiling);
        }
        int currCell = xyTo1D(row, col);
        for (int[] dir : dirs) {
            int nearRow = row + dir[0];
            int nearCol = col + dir[1];
            // deal with outbound situation
            if (nearRow < 0 || nearRow >= gridRows || nearCol < 0 || nearCol >= gridCols) {
                continue;
            }
            if (grid[nearRow][nearCol] == BUBBLE) {
                uf.union(currCell, xyTo1D(nearRow, nearCol));
            }
        }
    }

    private int xyTo1D(int row, int col) {
        /* we set each grid id to "row * gridCols + col + 1(ceiling)",
         * if at grid(2,3) with 4x4 grid size, we will get
         * id = 2 * 4 + 3 + 1= 12 */
        return row * gridCols + col + 1;
    }
}
