package hu.aestallon.minesweeper;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    /** This is the height and width of the cells in the game, given in pixels. */
    public static final int CELL_SIZE = 30;

    private static final int SMALL = 8;
    private static final int MEDIUM = 10;
    private static final int LARGE = 16;

    private static final int SMALL_MINE_COUNT = 5;
    private static final int MEDIUM_MINE_COUNT = 10;
    private static final int LARGE_MINE_COUNT = 55;

    private GamePanel gamePanel;

    public GameFrame() {
        /* <--- EXIT, SIZE & LAYOUT ---> */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(335, 80);
        this.setLayout(null);
        /* <- EXIT, SIZE & LAYOUT end -> */

        /* <-------- MENU BAR --------> */
        JMenuBar menuBar = new JMenuBar();

        // Menu Bar - New Game Menu
        JMenu newGameMenu = new JMenu("New Game");
        menuBar.add(newGameMenu);

        JMenuItem smallGame = new JMenuItem("Small");
        smallGame.addActionListener(e -> createNewGame(SMALL, SMALL, SMALL_MINE_COUNT));
        newGameMenu.add(smallGame);

        JMenuItem mediumGame = new JMenuItem("Medium");
        mediumGame.addActionListener(e -> createNewGame(MEDIUM, MEDIUM, MEDIUM_MINE_COUNT));
        newGameMenu.add(mediumGame);

        JMenuItem largeGame = new JMenuItem("Large");
        largeGame.addActionListener(e -> createNewGame(LARGE, LARGE, LARGE_MINE_COUNT));
        newGameMenu.add(largeGame);

        newGameMenu.add(new JToolBar.Separator());

        JMenuItem customGame = new JMenuItem("Custom...");
        customGame.addActionListener(e ->
                new CustomGameDialogueFrame(this)
        );
        newGameMenu.add(customGame);

        this.setJMenuBar(menuBar);
        /* <------ MENU BAR end ------> */

        /* <---------- MISC ----------> */
        this.setResizable(false);
        this.setTitle("Home-Cooked Minesweeper");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        /* <-------- MISC end --------> */

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
     * @param gameRows  The {@code int} number of rows in the game's
     *                  board
     * @param gameCols  The {@code int} number of columns in the game's
     *                  board
     * @param mineCount The number of mines present in the game.
     */
    private void createNewGame(int gameRows, int gameCols, int mineCount) {
        if (gamePanel != null) remove(gamePanel);
        gamePanel = new GamePanel(gameRows, gameCols, mineCount);
        gamePanel.setSize(gameCols * CELL_SIZE, gameRows * CELL_SIZE);
        gamePanel.setLocation(0, 0);
        this.setSize(gamePanel.getWidth() + 15, gamePanel.getHeight() + 80);
        this.add(gamePanel);
        SwingUtilities.updateComponentTreeUI(this);
    }


    private static class CustomGameDialogueFrame extends JFrame {

        private CustomGameDialogueFrame(GameFrame gameFrame) {
            this.setUndecorated(true);
            this.setSize(220, 200);

            JLabel rowLabel = new JLabel("Number of rows:");
            this.add(rowLabel);
            JTextField rowTextField = new JTextField(5);
            this.add(rowTextField);

            JLabel colLabel = new JLabel("Number of columns:");
            this.add(colLabel);
            JTextField colTextField = new JTextField(5);
            this.add(colTextField);

            JLabel mineLabel = new JLabel("Number of mines:");
            this.add(mineLabel);
            JTextField mineTextField = new JTextField(5);
            this.add(mineTextField);

            JButton okButton = new JButton("OK");
            okButton.addActionListener(e ->
                    parseUserInputs(
                            gameFrame,
                            rowTextField,
                            colTextField,
                            mineTextField
                    )
            );
            this.add(okButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> this.dispose());
            this.add(cancelButton);

            this.setLayout(new GridLayout(4, 2, 5, 5));
            this.setLocationRelativeTo(this.getParent());
            this.setVisible(true);
        }

        private void parseUserInputs(
                GameFrame gameFrame,
                JTextField rowTextField,
                JTextField colTextField,
                JTextField mineTextField) {

            try {
                int rows = Integer.parseInt(rowTextField.getText());
                int cols = Integer.parseInt(colTextField.getText());
                int mineCount = Integer.parseInt(mineTextField.getText());
                if (rows < 1 || cols < 1) {
                    throw new IllegalArgumentException("Number of rows and columns have to be at least 1!");
                }
                if (mineCount > rows * cols) {
                    throw new IllegalArgumentException("Number of mines is too large!");
                }
                gameFrame.createNewGame(rows, cols, mineCount);
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please provide numbers!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }
}
