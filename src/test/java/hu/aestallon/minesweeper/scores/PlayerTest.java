package hu.aestallon.minesweeper.scores;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private static final int ESTABLISHED_PLAYER_TOP = 5_000;
    // Holds the mocked previous scores returned from the database
    // for an already established player.
    private static final List<Integer> PREV_SCORES = new ArrayList<>(
            List.of(
                    -150, 30, 50, 169, 2_500, 3_420, 3_969,
                    4_420, 4_785, ESTABLISHED_PLAYER_TOP
            )
    );

    private static final String newPlayerName         = "NewPlayer";
    private static final String establishedPlayerName = "EstablishedPlayer";

    @Mock
    private final DatabaseHandler dbHandler = Mockito.mock(DatabaseHandler.class);

    private Player newPlayer;
    private Player establishedPlayer;

    @Before
    public void setUp() {
        // Completely new player to be tested:
        Mockito.when(dbHandler.getPlayerScoresOrdered(newPlayerName))
                .thenReturn(new ArrayList<>());
        newPlayer = new Player(newPlayerName, dbHandler);

        // An already established player (with entries in the database)
        // to be tested:
        Mockito.when(dbHandler.getPlayerScoresOrdered(establishedPlayerName))
                .thenReturn(PREV_SCORES);
        establishedPlayer = new Player(establishedPlayerName, dbHandler);
    }

    @After
    public void tearDown() {
        newPlayer = null;
        establishedPlayer = null;
    }

    private void simulateHighestEverScore(int score) {
        Mockito.when(dbHandler.getHighestScore()).thenReturn(score);
    }

    @Test
    public void creatingBrandNewPlayerHasBestScoreOfZero() {
        assertEquals(0, newPlayer.getPersonalBest());
    }

    @Test
    public void whenCreatingAPlayerWithPriorHighScores_PersonalBestIsTheHighestOfFormerScores() {
        assertEquals(ESTABLISHED_PLAYER_TOP, establishedPlayer.getPersonalBest());
    }

    @Test(expected = NullPointerException.class)
    public void attemptingToCreateAPlayerWithoutANameYieldsAnException() {
        Player p = new Player(null, dbHandler);
    }

    @Test(expected = NullPointerException.class)
    public void attemptingToCreateAPlayerWithoutInjectingAReferenceToTheDatabaseYieldsAnException() {
        Player p = new Player("a name", null);
    }

    @Test
    public void newPlayerAchievingNegativeScoreYieldsRegularScore() {
        simulateHighestEverScore(Integer.MAX_VALUE);
        assertEquals(
                ScoreCategory.REGULAR,
                newPlayer.saveScore(-1)
        );
    }

    @Test
    public void newPlayerAchievingZeroScoreYieldsRegularScore() {
        simulateHighestEverScore(Integer.MAX_VALUE);
        assertEquals(ScoreCategory.REGULAR, newPlayer.saveScore(-1));
    }

    @Test
    public void newPlayerAchievingPositiveScoreYieldsPersonalBest() {
        simulateHighestEverScore(Integer.MAX_VALUE);
        assertEquals(ScoreCategory.PERSONAL_BEST, newPlayer.saveScore(1));
    }

    @Test
    public void newPlayerToppingPreviousGamewideHighestScoreIsNewHighestScore() {
        int formerHighest = 10_000;
        int newHighest = formerHighest + 1;
        simulateHighestEverScore(formerHighest);
        assertEquals(ScoreCategory.HIGH_SCORE, newPlayer.saveScore(newHighest));
    }

    @Test
    public void gettersShouldGiveBackTheCorrectName() {
        assertEquals(newPlayerName, newPlayer.getName());
        assertEquals(establishedPlayerName, establishedPlayer.getName());
    }


}