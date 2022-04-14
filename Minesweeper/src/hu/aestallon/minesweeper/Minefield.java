package hu.aestallon.minesweeper;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Minefield {

    public static final String MINE = "x";
    public static final String NOT_MINE = " ";

    private final String[][] CELLS;

    public Minefield(int size, int mineCount) {
        CELLS = createMinefield(size, mineCount);
    }

    /**
     * Creates a minefield of the specified size. Entries denoting a mine contain the <code>MINE</code> constant, and
     * these are spread in a pseudorandom manner. All other entries contain the number of neighbouring mine cells.
     *
     * @param size      <code>int</code> specifying the size of the minefield.
     * @param mineCount <code>int</code> specifying the number of randomly placed mines int the minefield.
     * @return a <code>String[][]</code> containing the "solved" cells of a minefield.
     */
    private static String[][] createMinefield(int size, int mineCount) {
        boolean[][] booleanMinefield = new boolean[size][size];
        fillMinefieldWithMines(booleanMinefield, mineCount);
        String[][] stringMinefield = convertBooleanArrayToStringArray(booleanMinefield);
        fillMinefieldWithNumbers(stringMinefield);
        return stringMinefield;
    }

    /**
     * Takes a <code>boolean[][]</code> array as input, and sets the specified amount of its entries to
     * <code>true</code>. The position of these entries are acquired in a pseudorandom manner.
     * <p></p><strong>Warning!</strong> If there aren't enough <code>false</code> entries to be changed, the method may
     * run endlessly.
     *
     * @param emptyMineField <code>boolean[][]</code> array, preferably all its entries should be <code>false</code>.
     * @param mineCount      the number of entries to be changed to <code>true</code>.
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
     * Converts a <code>boolean[][]</code> into a <code>String[][]</code>. <code>true</code> entries will be labeled
     * <code>MINE</code>, and <code>false</code> entries <code>NOT_MINE</code>.
     *
     * @param booleanArray <code>boolean[][]</code>
     * @return <code>String[][]</code>
     */
    private static String[][] convertBooleanArrayToStringArray(boolean[][] booleanArray) {
        String[][] stringArray = new String[booleanArray.length][booleanArray.length];
        for (int i = 0; i < booleanArray.length; i++) {
            for (int j = 0; j < booleanArray.length; j++) {
                if (booleanArray[i][j]) stringArray[i][j] = MINE;
                else stringArray[i][j] = NOT_MINE;
            }
        }
        return stringArray;
    }

    /**
     * Methodically loop through an array and fill out it's missing entries based on the number of <code>MINE</code>
     * entries neighbouring them.<br>
     * <p></p><i>Calling this method on an array not containing any <code>MINE</code> entries will set every entry
     * to <code>"0"</code>. It is recommended to only use this method on an array already containing said entries.</i>
     *
     *
     * @param minefieldWithMines <code>String[][]</code> array containing at least 0 <code>MINE</code> entries.
     */
    private static void fillMinefieldWithNumbers(String[][] minefieldWithMines) {
        for (int i = 0; i < minefieldWithMines.length; i++) {
            for (int j = 0; j < minefieldWithMines.length; j++) {
                int neighbourMineCounter = 0;

                // We only do anything if we are not standing on a mine.
                if (!minefieldWithMines[i][j].equals(MINE)) {

                    // Check the neighbouring cells.
                    if (i != 0 && minefieldWithMines[i - 1][j].equals(MINE))
                        neighbourMineCounter++;
                    if (i != (minefieldWithMines.length - 1) && minefieldWithMines[i + 1][j].equals(MINE))
                        neighbourMineCounter++;
                    if (j != 0 && minefieldWithMines[i][j - 1].equals(MINE))
                        neighbourMineCounter++;
                    if (j != (minefieldWithMines.length - 1) && minefieldWithMines[i][j + 1].equals(MINE))
                        neighbourMineCounter++;

                    // Check diagonally neighbouring cells.
                    // Top-left neighbour
                    if (i != 0 && j != 0 && minefieldWithMines[i - 1][j - 1].equals(MINE))
                        neighbourMineCounter++;
                    // Top-right neighbour
                    if (i != (minefieldWithMines.length - 1) && j != 0 && minefieldWithMines[i + 1][j - 1].equals(MINE))
                        neighbourMineCounter++;
                    // Bottom-left neighbour
                    if (i != 0 && j != (minefieldWithMines.length - 1) && minefieldWithMines[i - 1][j + 1].equals(MINE))
                        neighbourMineCounter++;
                    // Bottom-right neighbour
                    if (i != (minefieldWithMines.length - 1) && j != (minefieldWithMines.length - 1) &&
                            minefieldWithMines[i + 1][j + 1].equals(MINE))
                        neighbourMineCounter++;

                    //Store how many neighbouring mines we found in the cell.
                    minefieldWithMines[i][j] = Integer.toString(neighbourMineCounter);
                }

            }
        }
    }

    /**
     * Returns the string value of a minefield's cell. If the parameters are out of bounds, an empty string is returned.
     *
     * @param x horizontal position of the cell.
     * @param y vertical position of the cell.
     * @return the string representation of the value stored in the cell.
     */
    public String getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= this.CELLS.length || y >= this.CELLS[0].length) {
            return "";
        } else {
            return this.CELLS[x][y];
        }
    }

    /**
     * Prints the minefield's solution to standard output.
     */
    public void print() {
        for (String[] rowOfCells : CELLS) {
            System.out.println(Arrays.toString(rowOfCells));
        }

    }
}
