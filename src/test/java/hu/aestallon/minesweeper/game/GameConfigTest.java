package hu.aestallon.minesweeper.game;

import hu.aestallon.minesweeper.scores.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class GameConfigTest {

    private static final int MINIMUM_ALLOWED_SIZE = 2;
    private static final int MINIMUM_ALLOWED_MINE = 1;

    private static int getMaximumAllowedMineCount(int rows, int cols) {
        return rows *  cols - 1;
    }

    private static int calculateExpectedScore(int mineCount, long startTime, long endTime) {
        if (mineCount == 0 || startTime == 0 || endTime == 0) {
            throw new IllegalArgumentException();
        }
        final int coeff  = 10_000_000;
        long elapsedTime = endTime - startTime;

        int dividend = mineCount * coeff;
        int divisor  = (int) elapsedTime;

        return dividend / divisor;
    }

    @Mock
    private Player player = Mockito.mock(Player.class);

    private GameConfig gameConfig;

    @Before
    public void setUp() {
        gameConfig = new GameConfig(player);
    }

    @After
    public void tearDown() {
        gameConfig = null;
    }

    @Test
    public void whenConfigIsCreatedAttributesAreSetToTheMinimumAllowed() {
        assertEquals(MINIMUM_ALLOWED_SIZE, gameConfig.getCols());
        assertEquals(MINIMUM_ALLOWED_SIZE, gameConfig.getRows());
        assertEquals(MINIMUM_ALLOWED_MINE, gameConfig.getMineCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void settingSmallerThanMinimumRowsIsNotAllowed() {
        gameConfig.setRows(MINIMUM_ALLOWED_SIZE - 1);
    }

    @Test
    public void settingRowsToMinimumIsAllGood() {
        gameConfig.setRows(MINIMUM_ALLOWED_SIZE);
        assertEquals(MINIMUM_ALLOWED_SIZE, gameConfig.getRows());
    }

    @Test(expected = IllegalArgumentException.class)
    public void settingSmallerThanMinimumColsIsNotAllowed() {
        gameConfig.setCols(MINIMUM_ALLOWED_SIZE - 1);
    }

    @Test
    public void settingColsToMinimumIsAllGood() {
        gameConfig.setCols(MINIMUM_ALLOWED_SIZE);
        assertEquals(MINIMUM_ALLOWED_SIZE, gameConfig.getCols());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullCannotBeSetAsPlayer() {
        gameConfig.setPlayer(null);
    }

    @Test
    public void settingPlayerWorksSomewhat() {
        // Given
        String expected = "Johnny BigTest";
        gameConfig.setPlayer(player);
        // When
        Mockito.when(player.getName()).thenReturn(expected);
        String actual = gameConfig.getPlayer().getName();
        // Then
        assertEquals(expected, actual);

    }

    @Test
    public void getPlayerGivesBackTheCorrectPlayer() {
        assertEquals(player, gameConfig.getPlayer());
    }

    @Test
    public void settingTheMaximumAllowedMineCountWorksFine() {
        // Given
        int rows = 10;
        int cols = 35;
        int mineCount = getMaximumAllowedMineCount(rows, cols);
        // When
        gameConfig.setRows(rows);
        gameConfig.setCols(cols);
        gameConfig.setMineCount(mineCount);
        // Then
        assertEquals(mineCount, gameConfig.getMineCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void attemptingToSetLessMinesThenAllowedYieldsAnException() {
        gameConfig.setMineCount(MINIMUM_ALLOWED_MINE - 1);
    }

    @Test
    public void attemptingToSetMoreMinesThanAllowedYieldsAnException() {
        // Given
        int rows = 10;
        int cols = 35;
        int mineCount = getMaximumAllowedMineCount(rows, cols);
        // When
        gameConfig.setRows(rows);
        gameConfig.setCols(cols);
        // Then
        assertThrows(
                IllegalArgumentException.class,
                () -> gameConfig.setMineCount(mineCount + 1)
        );
    }

    @Test
    public void newInstanceHasStartAndEndTimesOfZero() {
        assertEquals(0, gameConfig.getStartTime());
        assertEquals(0, gameConfig.getEndTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StartTimeCannotBeSetToZero() {
        gameConfig.setStartTime(0);
    }

    @Test
    public void settingStartTimeToPositiveValue_shouldMakeGetterReturnIt() {
        final long startTime = 1L;
        gameConfig.setStartTime(1);
        assertEquals(startTime, gameConfig.getStartTime());
    }

    @Test
    public void settingEndTimeBeforeStartTimeYieldsException() {
        final long startTime = 100L;
        gameConfig.setStartTime(startTime);
        assertThrows(
                IllegalArgumentException.class,
                () -> gameConfig.setEndTime(startTime - 1)
        );
    }

    @Test
    public void settingEndTimeAfterStartTime_shouldMakeGetterReturnIt() {
        final long startTime = 100L;
        final long endtime = startTime + 1L;
        gameConfig.setStartTime(startTime);
        gameConfig.setEndTime(endtime);
        assertEquals(endtime, gameConfig.getEndTime());
    }

    @Test(expected = IllegalStateException.class)
    public void callingCalculateScoreOnANewInstance_yieldsException() {
        gameConfig.calculateScore();
    }

    @Test
    public void validGameParametersShouldBeEvaluatedToCorrectScore0() {
        final int mineCount = 100;
        final long startTime = 16_5723_0113_000L;
        final long endTime = 16_5723_0173_000L;

        int expected = calculateExpectedScore(mineCount, startTime, endTime);

        // Certainly valid board size:
        gameConfig.setRows(mineCount);
        gameConfig.setCols(mineCount);
        // Setting relevant parameters to the calculation:
        gameConfig.setStartTime(startTime);
        gameConfig.setEndTime(endTime);
        gameConfig.setMineCount(mineCount);
        int actual = gameConfig.calculateScore();

        assertEquals(expected, actual);
    }

    @Test
    public void validGameParametersShouldBeEvaluatedToCorrectScore1() {
        final int mineCount = 25;
        final long startTime = 16_4693_0113_000L;
        final long endTime = 16_4693_0179_000L;

        int expected = calculateExpectedScore(mineCount, startTime, endTime);

        // Certainly valid board size:
        gameConfig.setRows(mineCount);
        gameConfig.setCols(mineCount);
        // Setting relevant parameters to the calculation:
        gameConfig.setStartTime(startTime);
        gameConfig.setEndTime(endTime);
        gameConfig.setMineCount(mineCount);
        int actual = gameConfig.calculateScore();

        assertEquals(expected, actual);
    }

    @Test
    public void validGameParametersShouldBeEvaluatedToCorrectScore2() {
        final int mineCount = 1;
        final long startTime = 10L;
        final long endTime = 900L;

        int expected = calculateExpectedScore(mineCount, startTime, endTime);

        gameConfig.setStartTime(startTime);
        gameConfig.setEndTime(endTime);
        gameConfig.setMineCount(mineCount);
        int actual = gameConfig.calculateScore();

        assertEquals(expected, actual);
    }

}
