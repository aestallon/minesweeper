package hu.aestallon.minesweeper.scores;

import org.junit.*;

import java.io.File;
import java.sql.*;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseHandlerTest {

    private static final String testDatabaseHeader = "jdbc:sqlite:";
    private static final String testDirectory      = System.getProperty("user.dir");
    private static final String testDatabaseName   = "/testMinesweeper.db";
    private static final String testDataBaseUrl    =
            testDatabaseHeader + testDirectory + testDatabaseName;

    private static DatabaseHandler databaseHandler;

    @BeforeClass
    public static void beforeClass() {
        // Initialize the instance to point at the right URL
        databaseHandler = DatabaseHandler.getCustomInstance(
                testDatabaseHeader,
                testDirectory,
                testDatabaseName
        );
        try (Connection conn = getConnection()) {
            @SuppressWarnings("unused")
            DatabaseMetaData metaData = conn.getMetaData();
        } catch (SQLException e) {
            throw new AssertionError("Connection with test db failed!");
        }
    }

    @Before
    public void setUp() throws Exception {
        // Before tests make sure the table exists in the database (this is to
        // simulate initialization logic performed by the singleton upon first
        // instance acquisition):
        final String createTableSql = """
                CREATE TABLE IF NOT EXISTS scores(
                    id      integer         PRIMARY KEY,
                    player  varchar(255)    NOT NULL,
                    score   integer         NOT NULL
                );""";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(createTableSql);
        }
    }

    @After
    public void tearDown() throws Exception {
        // After each test, clear the table in the database:
        final String deleteTableContentsSql = "DELETE FROM scores;";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(deleteTableContentsSql);
        }
    }

    @AfterClass
    public static void afterClass() {
        databaseHandler = null;
        File testDatabase = new File(testDirectory + testDatabaseName);
        if (testDatabase.exists()) {
            boolean successfulDeletion = testDatabase.delete();
            if (!successfulDeletion) {
                throw new AssertionError("Deleting test db failed!");
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(testDataBaseUrl);
    }

    private static int countRecords() {
        final String sql = "SELECT COUNT(*) FROM MAIN.scores;";
        try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
            else throw new AssertionError("Count should always succeed!");
        } catch (SQLException throwables) {
            throw new AssertionError("Database init error!");
        }
    }

    @Test
    public void newDatabaseIsEmpty() {
        assertEquals(0, countRecords());
    }

    @Test
    public void afterInsertingAScoreIntoEmptyDb_recordCountIs1() {
        databaseHandler.insertScore("test", 1);
        assertEquals(1, countRecords());
    }

    @Test
    public void databaseWithTwoPlayersRetrievesHighestPersonalScoresForEachCorrectly() {
        // Given
        final String player1 = "player1", player2 = "player2";
        final int player1Score = 5, player2Score = 6;
        // When
        databaseHandler.insertScore(player1, player1Score);
        databaseHandler.insertScore(player2, player2Score);
        //Then
        assertEquals(player1Score, databaseHandler.getPlayerScoresOrdered(player1).get(0).intValue());
        assertEquals(player2Score, databaseHandler.getPlayerScoresOrdered(player2).get(0).intValue());
    }

    @Test
    public void afterInserting5ScoresForAPlayer_queryingThemReturnsThemInAscendingOrder() {
        final String playername = "test";
        List<Integer> scores = List.of(15, 4, 67, 43, 95);
        scores.forEach(s -> databaseHandler.insertScore(playername, s));

        List<Integer> expectedScores= scores.stream().sorted(Comparator.naturalOrder()).toList();
        List<Integer> retrievedScores = databaseHandler.getPlayerScoresOrdered(playername);
        for (int i = 0; i < expectedScores.size(); i++) {
            assertEquals(expectedScores.get(i), retrievedScores.get(i));
        }
    }

    @Test
    public void emptyDbHasHighestScoreOfZero() {
        assertEquals(0, databaseHandler.getHighestScore());
    }

    @Test
    public void nonEmptyDbCorrectlyFindsTheHighestScore() {
        final String playername = "test";
        List<Integer> scores = List.of(15, 4, 67, 43, 95);
        scores.forEach(s -> databaseHandler.insertScore(playername, s));

        assertEquals(95, databaseHandler.getHighestScore());
    }

    @Test
    public void whenDbHas5ScoresTotal_TheTopTenOnlyContainsFiveEntries() {
        final String playername = "test";
        List<Integer> scores = List.of(15, 4, 67, 43, 95);
        scores.forEach(s -> databaseHandler.insertScore(playername, s));
        assertEquals(5, databaseHandler.getTopTenScores().size());
    }

    @Test
    public void whenDbHas1ScoresTotal_TheTopTenOnlyContains10Entries() {
        final String playername = "test";
        List<Integer> scores = List.of(
                15, 4, 67, 43, 95,
                32, 6, 85, 19, 44,
                1
        );
        scores.forEach(s -> databaseHandler.insertScore(playername, s));
        assertEquals(10, databaseHandler.getTopTenScores().size());
    }

    @Test
    public void insertingTheExactSameResultTwice_correctlyGetsSavedTwice() {
        final String playername = "test";
        final int repeatedResult = 20;
        databaseHandler.insertScore(playername, repeatedResult);
        databaseHandler.insertScore(playername, repeatedResult);

        assertEquals(2, databaseHandler.getPlayerScoresOrdered(playername).size());
    }
}
