package hu.aestallon.minesweeper.scores;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

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
                .thenReturn(Collections.emptyList());
        newPlayer = new Player(newPlayerName, dbHandler);

        List<Integer> prevScores = new ArrayList<>(
                List.of(
                        2_500, 3_000, 4_269, 1_500, 90,
                        325, 5_000, 1_100, 36, -150
                )
        );

        // An already established player (with entries in the database)
        // to be tested:
        Mockito.when(dbHandler.getPlayerScoresOrdered(establishedPlayerName))
                .thenReturn(prevScores);
        establishedPlayer = new Player(establishedPlayerName, dbHandler);
    }

    @Test
    public void creatingBrandNewPlayerHasBestScoreOfZero() {
        assertEquals(0, newPlayer.getPersonalBest());
    }

    @Test
    public void whenCreatingAPlayerWithPriorHighScores_PersonalBestIsTheHighestOfFormerScores() {
        assertEquals(5_000, establishedPlayer.getPersonalBest());
    }


}