public class Percolation {
    private WeightedQuickUnionUF union;
    private WeightedQuickUnionUF backwash;
    private boolean[] openSites;
    private final int SIZE, TOP_INDEX, BOTTOM_INDEX;

    public Percolation(int size) {
        int numNodes = size*size + 2;
        union = new WeightedQuickUnionUF(numNodes);
        backwash = new WeightedQuickUnionUF(numNodes);
        openSites = new boolean[size*size];
        SIZE = size;
        TOP_INDEX = size * size;
        BOTTOM_INDEX = size * size + 1;
    }

    public void open(int row, int col) {
        assertInRange(row, col);
        openSite(row, col);
        connectToVirtualTopNode(row, col);
        connectToAdjacents(row, col);
        connectToVirtualBottomNode(row, col);
    }

    private void assertInRange(int row, int col) {
        if (row < 1 || row > SIZE || col < 1 || col > SIZE) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void openSite(int row, int col) {
        openSites[toIndex(row, col)] = true;
    }

    private void connectToVirtualTopNode(int row, int col) {
        if (row == 1) {
            union(TOP_INDEX, toIndex(row, col));
        }
    }

    private void union(int row, int col) {
        union.union(row, col);
        backwash.union(row, col);
    }

    private int toIndex(int row, int col) {
        return (row - 1) * SIZE + (col - 1);
    }

    private void connectToAdjacents(int row, int col) {
        connectTopNode(row, col);
        connectBottomNode(row, col);
        connectLeftNode(row, col);
        connectRightNode(row, col);
    }

    private void connectTopNode(int row, int col) {
        if (row > 1 && isOpen(row - 1, col)) {
            union(toIndex(row - 1, col), toIndex(row, col));
        }
    }

    private void connectBottomNode(int row, int col) {
        if (row < SIZE && isOpen(row + 1, col)) {
            union(toIndex(row + 1, col), toIndex(row, col));
        }
    }

    private void connectLeftNode(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            union(toIndex(row, col - 1), toIndex(row, col));
        }
    }

    private void connectRightNode(int row, int col) {
        if (col < SIZE && isOpen(row, col + 1)) {
            union(toIndex(row, col + 1), toIndex(row, col));
        }
    }

    private void connectToVirtualBottomNode(int row, int col) {
        if (row == SIZE) {
            backwash.union(BOTTOM_INDEX, toIndex(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        assertInRange(row, col);
        return openSites[toIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        assertInRange(row, col);
        return union.connected(toIndex(row, col), TOP_INDEX);
    }

    public boolean percolates() {
        return backwash.connected(BOTTOM_INDEX, TOP_INDEX);
    }
}