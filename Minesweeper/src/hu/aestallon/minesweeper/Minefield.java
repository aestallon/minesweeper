package hu.aestallon.minesweeper;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides objects representing the values of a Minesweeper board.
 *
 * <p>The {@link #Minefield(int, int, int)} constructor can be called to
 * generate a pseudorandom board. Individual cell values can be
 * accessed with the {@link #getCell(int, int)} method. These values
 * are returned as {@code char}s, and can be interpreted as follows:
 *
 * <ul>
 * <li>Cells with a value denoting a digit {@code '0'...'8'} are not
 * mines, the digit represents the number of Moore-neighbouring
 * cells that are mines.
 * <li>Cells with value {@link #MINE} represent a mine.
 * </ul>
 *
 * <p>The {@link #print()} method is provided for debugging purposes.
 */
public class Minefield {

    /** The value representing a mine in a minesweeper board. */
    public static final char MINE = 'x';

    private final char[][] cells;

    /**
     * Constructs a fully solved, pseudorandom Minesweeper
     * board with the provided size and mine count.
     *
     * @param rows      the {@code int} number of rows in the board
     * @param cols      the {@code int} number of columns in the board
     * @param mineCount the {@code int} number of mines to
     *                  be placed in the board.
     */
    public Minefield(int rows, int cols, int mineCount) {
        cells = createMinefield(rows, cols, mineCount);
    }

    /**
     * Creates a minesweeper board of the specified size.
     *
     * <p>Entries denoting a mine contain the {@code MINE} constant, and
     * these are spread in a pseudorandom manner. All other entries contain
     * the number of neighbouring mine cells.
     *
     * @param rows      {@code int} number of rows of the board
     * @param cols      {@code int} number of columns of the board
     * @param mineCount {@code int} specifying the number of randomly placed
     *                  mines in the board.
     * @return a {@code char[][]} array containing the "solved" cells of a
     *         minefield.
     */
    private static char[][] createMinefield(int rows, int cols, int mineCount) {
        int[][] intMinefield = new int[rows][cols];
        fillWithMines(intMinefield, mineCount);
        fillWithNumbers(intMinefield);
        return convertToCharArray(intMinefield);

    }

    /**
     * Takes an {@code int[][]} array as input, and sets the specified
     * amount of its entries to {@link #MINE}. The position of these
     * entries are acquired in a pseudorandom manner.
     *
     * <p><b>Warning!</b> If the {@code mine-count} is greater than the
     * number of elements in the {@code minefield} array, the method
     * will run endlessly!
     *
     * @param minefield a {@code int[][]} array, preferably all its
     *                  entries should be 0.
     * @param mineCount the number of entries to be changed to
     *                  {@link #MINE}.
     */
    private static void fillWithMines(int[][] minefield, int mineCount) {
        for (int i = 0; i < mineCount; i++) {
            int x = ThreadLocalRandom.current().nextInt(minefield.length);
            int y = ThreadLocalRandom.current().nextInt(minefield[0].length);

            if (minefield[x][y] != MINE) minefield[x][y] = MINE;
            else i--;
        }
    }

    /**
     * Transforms an incomplete {@code int[][] minefield} into a
     * solved one.
     *
     * <p>An {@code int[][]} array is taken as input, which is
     * partially complete: entries symbolising mines should be set
     * already to {@link #MINE}, the rest should be {@code 0}.
     *
     * <p>All non-mine entries are set to represent the number of
     * Moore-neighbouring mines the given entry has.
     *
     * @param minefield an {@code int[][]} array with some entries
     *                  already set to {@link #MINE}.
     */
    private static void fillWithNumbers(int[][] minefield) {
        for (int i = 0; i < minefield.length; i++) {
            for (int j = 0; j < minefield[0].length; j++) {
                if (minefield[i][j] == MINE) {
                    incrementNotMineNeighbours(minefield, i, j);
                }
            }
        }
    }

    /**
     * @param arr an {@code int[][]} array to be modified
     * @param r   the {@code int} row of a given entry
     * @param c   the {@code int} column of a given entry
     */
    private static void incrementNotMineNeighbours(int[][] arr, int r, int c) {
        final int[] dirs = {-1, 0, 1};
        for (int dirR : dirs) {
            for (int dirC : dirs) {
                if ((r + dirR >= 0 && r + dirR < arr.length) &&
                        (c + dirC >= 0 && c + dirC < arr[0].length) &&
                        (arr[r + dirR][c + dirC] != MINE) &&
                        !(dirR == 0 && dirC == 0)
                ) {
                    arr[r + dirR][c + dirC]++;
                }
            }
        }
    }

    /**
     * Converts an {@code int[][]} minefield into a {@code char[][]}
     * version. Digits are preserved as characters, {@link #MINE}
     * entries are outright copied.
     *
     * @param minefield an {@code int[][]} array representing a
     *                  minesweeper board.
     * @return a {@code char[][]} array doing the same.
     */
    private static char[][] convertToCharArray(int[][] minefield) {
        char[][] result = new char[minefield.length][minefield[0].length];
        for (int i = 0; i < minefield.length; i++) {
            for (int j = 0; j < minefield[0].length; j++) {
                if (minefield[i][j] == MINE) result[i][j] = MINE;
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
        if (x < 0 || y < 0 || x >= cells.length || y >= cells[0].length) {
            return '\0';
        } else {
            return cells[x][y];
        }
    }

    /** Prints the minefield's solution to standard output. */
    public void print() {
        for (char[] rowOfCells : cells) {
            System.out.println(Arrays.toString(rowOfCells));
        }
    }

}
