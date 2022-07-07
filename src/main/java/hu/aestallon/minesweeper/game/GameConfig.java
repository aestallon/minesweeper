package hu.aestallon.minesweeper.game;

import hu.aestallon.minesweeper.scores.Player;

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

    private Player player;
    private int rows;
    private int cols;
    private int mineCount;

    public GameConfig(Player player) {
        this.setPlayer(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null!");
        }
        this.player = player;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows < 2) throw new IllegalArgumentException("Number of rows must be at least 2!");
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        if (cols < 2) throw new IllegalArgumentException("Number of columns must be at least 2!");
        this.cols = cols;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        if (mineCount < 1) throw new IllegalArgumentException("There must be at least 1 mine!");
        if (mineCount > rows * cols - 1) throw new IllegalArgumentException("Too many mines!");
        this.mineCount = mineCount;
    }
}
