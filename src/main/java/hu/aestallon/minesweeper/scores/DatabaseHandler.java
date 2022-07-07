package hu.aestallon.minesweeper.scores;

import hu.aestallon.minesweeper.Main;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    private static final String DB_HEADER = "jdbc:sqlite:";
    private static final String DB_NAME = "/minesweeper.db";

    private static DatabaseHandler dbHandler = null;

    private final String dbUrl;

    public static DatabaseHandler getInstance() {
        if (dbHandler == null) {
            dbHandler = new DatabaseHandler(DB_HEADER, getInstallDirectory(), DB_NAME);
        }
        return dbHandler;
    }

    @SuppressWarnings("unused")
    public static DatabaseHandler getCustomInstance(String dbHeader, String dbDirectory, String dbName) {
        return new DatabaseHandler(dbHeader, dbDirectory, dbName);
    }

    /**
     * Finds the directory where the JAR file containing the application is
     * located.
     *
     * @return the string representation of the path to the directory
     *         containing the application
     */
    private static String getInstallDirectory() {
        String jarPath = Main.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        return new File(jarPath)
                .getParentFile()
                .getPath();
    }

    private DatabaseHandler(String dbHeader, String dbDirectory, String dbName) {
        dbUrl = dbHeader + dbDirectory + dbName;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }


    public void initDatabase() {
        final String sql = """
                CREATE TABLE IF NOT EXISTS scores(
                    id     integer      PRIMARY KEY,
                    player varchar(255) NOT NULL,
                    score  integer      NOT NULL
                );""";
        try (Connection conn = connect()) {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        final String sql = "DELETE FROM scores;";
        try (Connection conn = connect()) {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertScore(String name, int score) {
        final String sql = """
                INSERT INTO scores (player, score)
                VALUES (?,?);""";
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, score);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Integer> getPlayerScoresOrdered(String name) {
        final String sql = """
                SELECT   s.score AS "SCORE"
                FROM     MAIN.scores s
                WHERE    UPPER(s.player) = UPPER(?)
                ORDER BY s.score;""";
        List<Integer> playerScores = new ArrayList<>();
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                playerScores.add(rs.getInt("SCORE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerScores;
    }

    public int getHighestScore() {
        final String sql = """
                SELECT  MAX(s.score) AS "BEST"
                FROM    MAIN.scores s;""";
        Integer highestScore = null;
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) highestScore = rs.getInt("BEST");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (highestScore == null) ? 0 : highestScore;
    }

    public List<Score> getTopTenScores() {
        final String sql = """
                SELECT
                    s.player    AS "NAME",
                    s.score     AS "SCORE"
                FROM
                    MAIN.scores s
                ORDER BY
                    s.score DESC
                LIMIT 10;""";
        List<Score> scores = new ArrayList<>();
        try (Connection conn = connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("NAME");
                Integer score = rs.getInt("SCORE");
                Score s = new Score(name, score);
                scores.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public record Score(
            String name,
            Integer score
    ) {
    }
}
