package hu.aestallon.minesweeper;

import javax.swing.*;
import java.awt.*;

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

        JButton newGameSmallButton = new JButton("Small");
        newGameSmallButton.setSize(80, 40);
        newGameSmallButton.setLocation(0, 0);
        newGameSmallButton.setBackground(new Color(255, 230, 138));
        newGameSmallButton.addActionListener(e -> createNewGame(SMALL, SMALL_MINE_COUNT));

        JButton newGameMediumButton = new JButton("Medium");
        newGameMediumButton.setSize(80, 40);
        newGameMediumButton.setLocation(80, 0);
        newGameMediumButton.setBackground(new Color(255, 208, 138));
        newGameMediumButton.addActionListener(e -> createNewGame(MEDIUM, MEDIUM_MINE_COUNT));

        JButton newGameLargeButton = new JButton("Large");
        newGameLargeButton.setSize(80, 40);
        newGameLargeButton.setLocation(160, 0);
        newGameLargeButton.setBackground(new Color(255, 183, 138));
        newGameLargeButton.addActionListener(e -> createNewGame(LARGE, LARGE_MINE_COUNT));

        JButton submitButton = new JButton("Submit");
        submitButton.setSize(80, 40);
        submitButton.setLocation(240, 0);
        submitButton.setBackground(Color.GREEN);
        submitButton.addActionListener(e -> {
            long foundMineCounter = gamePanel.getCellButtons().stream()
                    .filter(cb -> cb.isSus() && cb.isMine())
                    .count();

            if (foundMineCounter == gamePanel.getMineCount()) {
                gamePanel.getCellButtons().forEach(CellButton::setPassive);
                JOptionPane.showMessageDialog(null, "Gratulálunk nyertél!!!!");
            }
        });

        this.add(newGameSmallButton);
        this.add(newGameMediumButton);
        this.add(newGameLargeButton);
        this.add(submitButton);

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
        gamePanel.setLocation(0, 41);
        this.add(gamePanel);

        /* Ez egy meglehetősen favágó módszer arra, hogy az új tartalom *
         * megjelenjen, de az `updateComponentTreeUI` felülírja a gomb- *
         * beállításokat, és így az aknakereső gombikonjai bugolni kez- *
         * denek.Ha valakinek van jobb módszere (azon kívül, hogy írni  *
         * kéne egy teljesen custom `look&feel`-t, szóljon.             */

        // SwingUtilities.updateComponentTreeUI(this);
        this.setSize(gameSize * CELL_SIZE + 16, gameSize * CELL_SIZE + 80);
        this.setSize(gameSize * CELL_SIZE + 15, gameSize * CELL_SIZE + 80);
    }
}
