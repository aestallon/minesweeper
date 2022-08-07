package hu.aestallon.minesweeper.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MinefieldTest {

    private static final int testRowCount = 200;
    private static final int testColCount = 300;
    // Percentage of mines are similar to the
    // 'Large' standard difficulty setting (22%):
    private static final int testMineCount = 110;

    private Minefield minefield;

    @Before
    public void setUp() {
        minefield = new Minefield(testRowCount, testColCount, testMineCount);
    }

    @After
    public void tearDown() {
        minefield = null;
    }

    @Test
    public void mineFieldContainsAsManyMinesAsTheInjectedIntoTheConstructor() {
        int actualMineCount = 0;
        for (int i = 0; i < testRowCount; i++) {
            for (int j = 0; j < testColCount; j++) {
                if (minefield.getCell(i, j) == Minefield.MINE) {
                    actualMineCount++;
                }
            }
        }
        assertEquals(testMineCount, actualMineCount);
    }

    @Test
    public void mineFieldHasAsManyRowsAsItShould() {
        minefield.getCell(testRowCount - 1, 0);
        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> minefield.getCell(testRowCount, 0)
        );
    }

    @Test
    public void mineFieldHasAsManyColsAsItShould() {
        minefield.getCell(0, testColCount - 1);
        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> minefield.getCell(0, testColCount)
        );
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void attemptingToAccessNegativeRowNumber_yieldsException() {
        minefield.getCell(-1, 0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void attemptingToAccessNegativeColNumber_yieldsException() {
        minefield.getCell(0, -1);
    }

    @Test
    public void numberedCellsShouldHaveNumberEqualToTheirMineNeighboursCount() {
        for (int i = 0; i < testRowCount; i++) {
            for (int j = 0; j < testColCount; j++) {
                if (isDigit(minefield.getCell(i, j))) {
                    int cellDigit = toDigit(minefield.getCell(i, j));
                    int actualMineNeighbourCount = countOfMineNeighbours(minefield, i, j);
                    assertEquals(cellDigit, actualMineNeighbourCount);
                }
            }
        }
    }

    private static boolean isDigit(char ch) {
        // ch1 - ch2 returns the integer associated with the
        // characters' Unicode table value:
        return (ch - '0') >= 0 && (ch - '0') < 10;
    }

    private static int toDigit(char ch) {
        return ch - '0';
    }

    private static int countOfMineNeighbours(Minefield mf, int x, int y) {
        int mineNeighbourCount = 0;
        int[] steps = {-1, 0, 1};
        for (int deltaX : steps) {
            for (int deltaY : steps) {
                int xPos = x + deltaX;
                int yPos = y + deltaY;
                if (!(xPos == x && yPos == y) &&
                        xPos >= 0 &&
                        xPos < testRowCount &&
                        yPos >= 0 &&
                        yPos < testColCount) {
                    if (mf.getCell(xPos, yPos) == Minefield.MINE) {
                        mineNeighbourCount++;
                    }
                }
            }
        }
        return mineNeighbourCount;
    }
}
