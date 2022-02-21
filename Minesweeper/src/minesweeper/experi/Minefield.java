package minesweeper.experi;

import java.util.concurrent.ThreadLocalRandom;

public class Minefield {

    public static final String MINE = "x";
    public static final String NOT_MINE = " ";

    String[][] cells;

    public Minefield(int fieldSize, int mineCount) {
        cells = createMinefield(fieldSize, mineCount);
    }

    public static String[][] createMinefield(int fieldSize, int mineCount) {
        boolean[][] booleanMinefield = new boolean[fieldSize][fieldSize];
        fillMinefieldWithMines(booleanMinefield, mineCount);
        String[][] stringMinefield = convertBooleanArrayToStringArray(booleanMinefield);
        fillMinefieldWithNumbers(stringMinefield);
        return stringMinefield;
    }

    private static void fillMinefieldWithMines(boolean[][] emptyMineField, int mineCount) {
        for (int i = 0; i < mineCount; i++) {
            int minePosX = ThreadLocalRandom.current().nextInt(emptyMineField.length);
            int minePosY = ThreadLocalRandom.current().nextInt(emptyMineField.length);

            if (!emptyMineField[minePosX][minePosY]) emptyMineField[minePosX][minePosY] = true;
            else i--;
        }
    }

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

    private static void fillMinefieldWithNumbers(String[][] minefieldWithMines) {
        for (int i = 0; i < minefieldWithMines.length; i++) {
            for (int j = 0; j < minefieldWithMines.length; j++) {
                int neighbourMineCounter = 0;

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

                if (!minefieldWithMines[i][j].equals(MINE))
                    minefieldWithMines[i][j] = Integer.toString(neighbourMineCounter);

            }
        }
    }
}
