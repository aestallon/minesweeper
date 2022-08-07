package hu.aestallon.minesweeper.scores;

import org.junit.*;

import java.io.File;
import java.sql.*;

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
        final String deleteTableContentsSql = "DELETE * FROM MAIN.scores;";
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

//    @Test
//    public void
}
