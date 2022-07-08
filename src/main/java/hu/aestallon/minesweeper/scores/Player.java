package hu.aestallon.minesweeper.scores;

import java.util.List;
import java.util.Objects;

public class Player {

    private final String name;
    private final List<Integer> scores;
    private final DatabaseHandler databaseHandler;

    private int personalBest;

    public Player(String name, DatabaseHandler databaseHandler) {
        this.name = Objects.requireNonNull(name);
        this.databaseHandler = Objects.requireNonNull(databaseHandler);

        scores = databaseHandler.getPlayerScoresOrdered(name);
        if (scores.isEmpty()) {
            personalBest = 0;
        } else {
            personalBest = scores.get(scores.size() - 1);
        }
    }

    public String getName() {
        return name;
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
}
