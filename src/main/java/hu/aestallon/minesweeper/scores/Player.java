package hu.aestallon.minesweeper.scores;

import java.util.Objects;

public class Player {

    private final String name;
    private final DatabaseHandler databaseHandler;

    private int personalBest;

    public Player(String name, DatabaseHandler databaseHandler) {
        this.name = Objects.requireNonNull(name);
        this.databaseHandler = Objects.requireNonNull(databaseHandler);

        personalBest = databaseHandler.getPlayerScoresOrdered(name).stream()
                .mapToInt(Integer::intValue)
                .max().orElse(0);
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
        databaseHandler.insertScore(this.name, score);
        return category;
    }
}
