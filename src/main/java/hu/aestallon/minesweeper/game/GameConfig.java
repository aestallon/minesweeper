package hu.aestallon.minesweeper.game;

import hu.aestallon.minesweeper.scores.Player;

/**
 * Stores configuration information used for setting up and evaluating a single
 * session of Minesweeper.
 *
 * <p>Instances store a given gameboard attributes (number of rows, columns and
 * mines hidden) and the {@link Player} associated with it.
 *
 * @author Szabolcs Bazil Papp
 * @version 1.0
 * @see Player
 * @see GamePanel
 * @since 2022-07-07
 */
public final class GameConfig {

    /** The height and width of a standard 'small' sized game given in cells. */
    public static final int SMALL = 8;
    /** The height and width of a standard 'medium' sized game given in cells. */
    public static final int MEDIUM = 10;
    /** The height and width of a standard 'large' sized game given in cells. */
    public static final int LARGE = 16;

    /** The number of mines in a standard 'small' sized game. */
    public static final int SMALL_MINE_COUNT = 5;
    /** The number of mines in a standard 'medium' sized game. */
    public static final int MEDIUM_MINE_COUNT = 10;
    /** The number of mines in a standard 'small' sized game. */
    public static final int LARGE_MINE_COUNT = 55;

    /** The smallest allowed width or height given in number of cells. */
    private static final int MINIMUM_SIZE = 2;
    /** The smallest allowed number of mines in a given game. */
    private static final int MIN_MINE_COUNT = 1;

    private Player player;
    private int rows;
    private int cols;
    private int mineCount;
    private long startTime;
    private long endTime;

    /**
     * Creates a new instance with the minimum allowed sizes and
     * the specified Player.
     *
     * <p>The player and other fields may be set later with the
     * appropriate mutators.
     *
     * @param player a {@link Player}, not null
     */
    public GameConfig(Player player) {
        this.setPlayer(player);
        // Default settings to yield a 'minimal' valid instance:
        this.rows = MINIMUM_SIZE;
        this.cols = MINIMUM_SIZE;
        this.mineCount = MIN_MINE_COUNT;
    }

    /**
     * Returns the player associated with this instance.
     *
     * @return the player stored in this instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Associates the specified player with this instance.
     *
     * @param player a {@link Player}, not null
     */
    public void setPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null!");
        }
        this.player = player;
    }

    /**
     * Returns the number of rows stored in this instance.
     *
     * @return the {@code int} number of rows currently saved in this
     *         instance
     */
    public int getRows() {
        return rows;
    }

    /**
     * Saves the specified row count in this instance.
     *
     * @param rows the number of rows, at least of {@link GameConfig#MINIMUM_SIZE}
     */
    public void setRows(int rows) {
        if (rows < MINIMUM_SIZE) {
            throw new IllegalArgumentException("Number of rows must be at least 2!");
        }
        this.rows = rows;
    }

    /**
     * Returns the number of columns stored in this instance.
     *
     * @return the {@code int} number of columns currently saved in this
     *         instance
     */
    public int getCols() {
        return cols;
    }

    /**
     * Saves the specified column count in this instance.
     *
     * @param cols the number of rows, at least of {@link GameConfig#MINIMUM_SIZE}
     */
    public void setCols(int cols) {
        if (cols < MINIMUM_SIZE) {
            throw new IllegalArgumentException("Number of columns must be at least 2!");
        }
        this.cols = cols;
    }

    /**
     * Returns the number of mines stored in this instance.
     *
     * @return the {@code int} number of mines currently saved in this instance
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * Saves the specified mine count to this instance.
     *
     * @param mineCount the {@code int} number of mines, at least
     *                  {@link GameConfig#MIN_MINE_COUNT} and at most
     *                  {@code rows * cols - 1}
     */
    public void setMineCount(int mineCount) {
        if (mineCount < MIN_MINE_COUNT) {
            throw new IllegalArgumentException("There must be at least 1 mine!");
        }
        if (mineCount > rows * cols - 1) {
            throw new IllegalArgumentException("Too many mines!");
        }
        this.mineCount = mineCount;
    }

    /**
     * Returns the timestamp of the game's start.
     *
     * @return the game's start time, in epoch-time (milliseconds)
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the provided epoch time-stamp as the game's starting millisecond.
     *
     * @param startTime the {@code long} timestamp of the game's start time,
     *                  in millisecond epoch-time
     */
    public void setStartTime(long startTime) {
        if (startTime <= 0) {
            throw new IllegalArgumentException("Invalid start time!");
        }
        this.startTime = startTime;
    }

    /**
     * Returns the timestamp of the game's end.
     *
     * @return the game's start time, in epoch-time (milliseconds)
     */
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        if (endTime < startTime) {
            throw new IllegalArgumentException("Invalid end time!");
        }
        this.endTime = endTime;
    }

    public int calculateScore() {
        if (mineCount == 0 || endTime == 0 || startTime == 0) {
            throw new IllegalStateException("Cannot calculate score with empty fields!");
        }
        return (int) (mineCount * 10_000_000 / (endTime - startTime));
    }
}
