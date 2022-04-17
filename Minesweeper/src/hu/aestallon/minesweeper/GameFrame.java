package hu.aestallon.minesweeper;

import javax.swing.*;

public class GameFrame extends JFrame {
    public static final int CELL_SIZE = 40;

    private static final int SMALL = 8;
    private static final int MEDIUM = 10;
    private static final int LARGE = 16;

    private static final int SMALL_MINE_COUNT = 5;
    private static final int MEDIUM_MINE_COUNT = 10;
    private static final int LARGE_MINE_COUNT = 55;

    private GamePanel gamePanel;

    public GameFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(335, 80);
        this.setLayout(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JMenuBar menuBar = new JMenuBar();

        JMenu newGameMenu = new JMenu("New Game");
        menuBar.add(newGameMenu);

        JMenuItem smallGame = new JMenuItem("Small");
        smallGame.addActionListener(e -> createNewGame(SMALL, SMALL_MINE_COUNT));
        newGameMenu.add(smallGame);

        JMenuItem mediumGame= new JMenuItem("Medium");
        mediumGame.addActionListener(e -> createNewGame(MEDIUM, MEDIUM_MINE_COUNT));
        newGameMenu.add(mediumGame);

        JMenuItem largeGame = new JMenuItem("Large");
        mediumGame.addActionListener(e -> createNewGame(LARGE, LARGE_MINE_COUNT));
        newGameMenu.add(largeGame);

        this.setJMenuBar(menuBar);

        this.setResizable(false);
        this.setTitle("Home-Cooked Minesweeper");
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * Creates a new game of Minesweeper.
     *
     * <p>Removes the panel containing the actual game interface from
     * this instance and initializes it based on the parameters
     * provided, then adds the panel back to the instance. Finally, the
     * instance is refreshed to let the user see and interact with the
     * changes.
     *
     * @param gameSize  The size of the game's minefield.
     *                  <i>(Both horizontal and vertical)</i>
     * @param mineCount The number of mines present in the game.
     */
    private void createNewGame(int gameSize, int mineCount) {
        if (gamePanel != null) remove(gamePanel);
        gamePanel = new GamePanel(gameSize, mineCount);
        gamePanel.setSize(gameSize * CELL_SIZE, gameSize * CELL_SIZE);
        gamePanel.setLocation(0, 0);
        this.setSize(gamePanel.getWidth() + 15, gamePanel.getHeight() + 80);
        this.add(gamePanel);
//        SwingUtilities.updateComponentTreeUI(this);
    }
}
