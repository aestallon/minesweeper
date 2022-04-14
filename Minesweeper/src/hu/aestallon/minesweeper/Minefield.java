package hu.aestallon.minesweeper;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Minefield {

    public static final char MINE = 'x';
    public static final char NOT_MINE = ' ';

    private final char[][] CELLS;

    public Minefield(int size, int mineCount) {
        CELLS = createMinefield(size, mineCount);
    }

    /**
     * Creates a minefield of the specified size.
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
        boolean[][] booleanMinefield = new boolean[size][size];
        fillMinefieldWithMines(booleanMinefield, mineCount);
        char[][] chMinefield = convertBooleanArrayToCharArray(booleanMinefield);
        fillMinefieldWithNumbers(chMinefield);
        return chMinefield;
    }

    /**
     * Takes a {@code boolean[][]} array as input, and sets the specified
     * amount of its entries to {@code true}. The position of these entries
     * are acquired in a pseudorandom manner.
     *
     * <p><b>Warning!</b> If there aren't enough {@code false} entries to be
     * changed, the method may run endlessly.
     *
     * @param emptyMineField a {@code boolean[][]} array, preferably all its
     *                       entries should be false.
     * @param mineCount      the number of entries to be changed to true.
     */
    private static void fillMinefieldWithMines(boolean[][] emptyMineField, int mineCount) {
        for (int i = 0; i < mineCount; i++) {
            int minePosX = ThreadLocalRandom.current().nextInt(emptyMineField.length);
            int minePosY = ThreadLocalRandom.current().nextInt(emptyMineField.length);

            if (!emptyMineField[minePosX][minePosY]) emptyMineField[minePosX][minePosY] = true;
            else i--;
        }
    }

    /**
     * Converts a {@code boolean[][]} into a {@code char[][]}. {@code true}
     * entries will be labeled {@link #MINE}, and {@code false} entries
     * {@link #NOT_MINE}.
     *
     * @param booleanArray a {@code boolean[][]} array
     * @return a {@code char[][]} array
     */
    private static char[][] convertBooleanArrayToCharArray(boolean[][] booleanArray) {
        char[][] chArray = new char[booleanArray.length][booleanArray.length];
        for (int i = 0; i < booleanArray.length; i++) {
            for (int j = 0; j < booleanArray.length; j++) {
                if (booleanArray[i][j]) chArray[i][j] = MINE;
                else chArray[i][j] = NOT_MINE;
            }
        }
        return chArray;
    }

    /**
     * Methodically loop through an array and fill out it's missing
     * entries based on the number of {@code MINE} entries neighbouring
     * them.
     *
     * <p><i>Calling this method on an array not containing any
     * {@code MINE} entries will set every entry to {@code '0'}. It is
     * recommended to only use this method on an array already
     * containing said entries.</i>
     *
     * @param minefieldWithMines {@code char[][]} array containing at
     *                           least 0 {@code MINE} entries.
     */
    private static void fillMinefieldWithNumbers(char[][] minefieldWithMines) {
        for (int i = 0; i < minefieldWithMines.length; i++) {
            for (int j = 0; j < minefieldWithMines.length; j++) {
                int neighbourMineCounter = 0;

                // We only do anything if we are not standing on a mine.
                if (minefieldWithMines[i][j] != MINE) {

                    // Check the neighbouring cells.
                    if (i != 0 && minefieldWithMines[i - 1][j] == MINE)
                        neighbourMineCounter++;
                    if (i != (minefieldWithMines.length - 1) && minefieldWithMines[i + 1][j]==MINE)
                        neighbourMineCounter++;
                    if (j != 0 && minefieldWithMines[i][j - 1] == MINE)
                        neighbourMineCounter++;
                    if (j != (minefieldWithMines.length - 1) && minefieldWithMines[i][j + 1] == MINE)
                        neighbourMineCounter++;

                    // Check diagonally neighbouring cells.
                    // Top-left neighbour
                    if (i != 0 && j != 0 && minefieldWithMines[i - 1][j - 1] == MINE)
                        neighbourMineCounter++;
                    // Top-right neighbour
                    if (i != (minefieldWithMines.length - 1) && j != 0 && minefieldWithMines[i + 1][j - 1] == MINE)
                        neighbourMineCounter++;
                    // Bottom-left neighbour
                    if (i != 0 && j != (minefieldWithMines.length - 1) && minefieldWithMines[i - 1][j + 1] == MINE)
                        neighbourMineCounter++;
                    // Bottom-right neighbour
                    if (i != (minefieldWithMines.length - 1) && j != (minefieldWithMines.length - 1) &&
                            minefieldWithMines[i + 1][j + 1] == MINE)
                        neighbourMineCounter++;

                    //Store how many neighbouring mines we found in the cell.
                    minefieldWithMines[i][j] = (char) (neighbourMineCounter + '0');
                }

            }
        }
    }

    /**
     * Returns the string value of a minefield's cell. If the parameters
     * are out of bounds, an empty string is returned.
     *
     * @param x horizontal position of the cell.
     * @param y vertical position of the cell.
     * @return the string representation of the value stored in the cell.
     */
    public char getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= this.CELLS.length || y >= this.CELLS[0].length) {
            return '\0';
        } else {
            return this.CELLS[x][y];
        }
    }

    /** Prints the minefield's solution to standard output. */
    public void print() {
        for (char[] rowOfCells : CELLS) {
            System.out.println(Arrays.toString(rowOfCells));
        }

    }
}
