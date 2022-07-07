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
}
