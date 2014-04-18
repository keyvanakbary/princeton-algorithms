import java.util.LinkedList;

public class Board {
    private static final int SPACE = 0;

    private int[][] blocks;

    public Board(int[][] blocks) {
        this.blocks = copy(blocks);
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                copy[row][col] = blocks[row][col];

        return copy;
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int count = 0;
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (blockIsNotInPlace(row, col)) count++;

        return count;
    }

    private boolean blockIsNotInPlace(int row, int col) {
        int block = block(row, col);

        return !isSpace(block) && block != goalFor(row, col);
    }

    private int goalFor(int row, int col) {
        return row*dimension() + col + 1;
    }

    private boolean isSpace(int block) {
        return block == SPACE;
    }

    public int manhattan() {
        int sum = 0;
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                sum += calculateDistances(row, col);

        return sum;
    }

    private int calculateDistances(int row, int col) {
        int block = block(row, col);

        return (isSpace(block)) ? 0 : Math.abs(row - row(block)) + Math.abs(col - col(block));
    }

    private int block(int row, int col) {
        return blocks[row][col];
    }

    private int row (int block) {
        return (block - 1) / dimension();
    }

    private int col (int block) {
        return (block - 1) % dimension();
    }

    public boolean isGoal() {
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (blockIsInPlace(row, col)) return false;

        return true;
    }

    private boolean blockIsInPlace(int row, int col) {
        int block = block(row, col);

        return !isSpace(block) && block != goalFor(row, col);
    }

    public Board twin() {
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length - 1; col++)
                if (!isSpace(block(row, col)) && !isSpace(block(row, col + 1)))
                    return new Board(swap(row, col, row, col + 1));
        throw new RuntimeException();
    }

    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = copy(blocks);
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }

    public boolean equals(Object y) {
        if (y==this) return true;
        if (y==null || !(y instanceof Board) || ((Board)y).blocks.length != blocks.length) return false;
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (((Board) y).blocks[row][col] != block(row, col)) return false;

        return true;
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<Board>();

        int[] location = spaceLocation();
        int spaceRow = location[0];
        int spaceCol = location[1];

        if (spaceRow > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
        if (spaceRow < dimension() - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
        if (spaceCol > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
        if (spaceCol < dimension() - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1)));

        return neighbors;
    }

    private int[] spaceLocation() {
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (isSpace(block(row, col))) {
                    int[] location = new int[2];
                    location[0] = row;
                    location[1] = col;

                    return location;
                }
        throw new RuntimeException();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dimension() + "\n");
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks.length; col++)
                str.append(String.format("%2d ", block(row, col)));
            str.append("\n");
        }

        return str.toString();
    }
}
