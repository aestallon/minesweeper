package hu.aestallon.minesweeper;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Minefield {

    /** The {@code char} representing a mine in a {@code char[][]} array. */
    public static final char MINE = 'x';

    /** The {@code int} representing a mine in an {@code int[][]} array. */
    private static final int INT_MINE = -1;

    private final char[][] cells;

    /**
     * Constructs a fully solved, pseudorandom Minesweeper
     * board with the provided size and mine count.
     *
     * @param size      the {@code int} width and height of
     *                  the board
     * @param mineCount the {@code int} number of mines to
     *                  be placed in the board.
     */
    public Minefield(int size, int mineCount) {
        cells = createMinefield(size, mineCount);
    }

    /**
     * Creates a minesweeper board of the specified size.
     *
     * <p>Entries denoting a mine contain the {@code MINE} constant, and
     * these are spread in a pseudorandom manner. All other entries contain
     * the number of neighbouring mine cells.
     *
     * @param size      {@code int} specifying the size of the minefield.
     * @param mineCount {@code int} specifying the number of randomly placed
     *                  mines int the minefield.
     * @return a {@code char[][]} array containing the "solved" cells of a
     *         minefield.
     */
    private static char[][] createMinefield(int size, int mineCount) {
        int[][] intMinefield = new int[size][size];
        fillWithMines(intMinefield, mineCount);
        fillWithNumbers(intMinefield);
        return convertToCharArray(intMinefield);

    }

    /**
     * Takes an {@code int[][]} array as input, and sets the specified
     * amount of its entries to {@link #INT_MINE}. The position of these
     * entries are acquired in a pseudorandom manner.
     *
     * <p><b>Warning!</b> If the {@code minecount} is greater than the
     * number of elements in the {@code minefield} array, the method
     * will run endlessly!
     *
     * @param minefield a {@code int[][]} array, preferably all its
     *                  entries should be 0.
     * @param mineCount the number of entries to be changed to
     *                  {@link #INT_MINE}.
     */
    private static void fillWithMines(int[][] minefield, int mineCount) {
        for (int i = 0; i < mineCount; i++) {
            int x = ThreadLocalRandom.current().nextInt(minefield.length);
            int y = ThreadLocalRandom.current().nextInt(minefield.length);

            if (minefield[x][y] != INT_MINE) minefield[x][y] = INT_MINE;
            else i--;
        }
    }

    /**
     * Transforms an incomplete {@code int[][] minefield} into a
     * solved one.
     *
     * <p>An {@code int[][]} array is taken as input, which is
     * partially complete: entries symbolising mines should be set
     * already to {@link #INT_MINE}, the rest should be {@code 0}.
     *
     * <p>All non-mine entries are set to represent the number of
     * Moore-neighbouring mines the given entry has.
     *
     * @param minefield an {@code int[][]} array with some entries
     *                  already set to {@link #INT_MINE}.
     */
    private static void fillWithNumbers(int[][] minefield) {
        for (int i = 0; i < minefield.length; i++) {
            for (int j = 0; j < minefield.length; j++) {
                if (minefield[i][j] == INT_MINE) {
                    incrementNotMineNeighbours(minefield, i, j);
                }
            }
        }
    }

    /**
     * @param arr an {@code int[][]} array to be modified
     * @param r   the {@code int} row of a given entry
     * @param c   the {@code int} coloumn of a given entry
     */
    private static void incrementNotMineNeighbours(int[][] arr, int r, int c) {
        final int[] dirs = {-1, 0, 1};
        for (int dirR : dirs) {
            for (int dirC : dirs) {
                if ((r + dirR >= 0 && r + dirR < arr.length) &&
                        (c + dirC >= 0 && c + dirC < arr.length) &&
                        (arr[r + dirR][c + dirC] != INT_MINE) &&
                        !(dirR == 0 && dirC == 0)
                ) {
                    arr[r + dirR][c + dirC]++;
                }
            }
        }
    }

    /**
     * Converts an {@code int[][]} minefield into a {@code char[][]}
     * version. Digits are preserved as characters, {@link #INT_MINE}
     * entries are converted to {@link #MINE}.
     *
     * @param minefield an {@code int[][]} array representing a
     *                  minesweeper board.
     * @return a {@code char[][]} array doing the same.
     */
    private static char[][] convertToCharArray(int[][] minefield) {
        char[][] result = new char[minefield.length][minefield[0].length];
        for (int i = 0; i < minefield.length; i++) {
            for (int j = 0; j < minefield[0].length; j++) {
                if (minefield[i][j] == INT_MINE) result[i][j] = MINE;
                else result[i][j] = (char) (minefield[i][j] + '0');
            }
        }
        return result;
    }

    /**
     * Returns the {@code char} value of a minefield's cell. If the
     * parameters are out of bounds, the {@code null character} is
     * returned.
     *
     * @param x horizontal position of the cell.
     * @param y vertical position of the cell.
     * @return the {@code char} representation of the value stored
     *         in the cell.
     */
    public char getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= this.cells.length || y >= this.cells[0].length) {
            return '\0';
        } else {
            return this.cells[x][y];
        }
    }

    /** Prints the minefield's solution to standard output. */
    public void print() {
        for (char[] rowOfCells : cells) {
            System.out.println(Arrays.toString(rowOfCells));
        }

    }
}
