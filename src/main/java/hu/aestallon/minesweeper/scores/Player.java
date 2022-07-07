package hu.aestallon.minesweeper.scores;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Player {

    private final String name;
    private List<Integer> scores;
    private final DatabaseHandler databaseHandler;

    private int personalBest;
    private long startTime;

    public Player(String name, DatabaseHandler databaseHandler) {
        this.name = Objects.requireNonNull(name);
        this.databaseHandler = Objects.requireNonNull(databaseHandler);

        scores = databaseHandler.getPlayerScoresOrdered(name);
        if (scores.isEmpty()) {
            personalBest = 0;
        } else {
            Collections.sort(scores);
            personalBest = scores.get(scores.size() - 1);
        }
    }

    public int getPersonalBest() {
        return personalBest;
    }

    public ScoreCategory saveScore(int score) {
        ScoreCategory category;
        if (score > databaseHandler.getHighestScore()) {
            personalBest = score;
            category = ScoreCategory.HIGH_SCORE;
        } else if (score > personalBest){
            personalBest = score;
            category = ScoreCategory.PERSONAL_BEST;
        } else {
            category = ScoreCategory.REGULAR;
        }
        scores.add(score);
        databaseHandler.insertScore(this.name, score);
        return category;
    }

    public void startGame() {
        this.startTime = System.currentTimeMillis();
    }

    public ScoreCategory endGame(int mineCount) {
        long endTime = System.currentTimeMillis();
        long gameTime = endTime - startTime;
        int score = calculateScore(gameTime, mineCount);
        return this.saveScore(score);
    }

    private int calculateScore(long gameTime, int mineCount) {
        return (int) (mineCount * 10_000_000 / gameTime);
    }
}
