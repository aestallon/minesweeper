package hu.aestallon.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
    private int gameRows;
    private int gameCols;
    private int mineCount;

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

        // Settings Menu
        JMenu settingsMenu = new JMenu("Settings");
        ButtonGroup settingsGroup = new ButtonGroup();

        JMenuItem smallGame = new JRadioButtonMenuItem("Small");
        smallGame.addActionListener(e -> setGameParameters(SMALL, SMALL, SMALL_MINE_COUNT));
        settingsGroup.add(smallGame);
        settingsMenu.add(smallGame);

        JMenuItem mediumGame = new JRadioButtonMenuItem("Medium");
        mediumGame.addActionListener(e -> setGameParameters(MEDIUM, MEDIUM, MEDIUM_MINE_COUNT));
        settingsGroup.add(mediumGame);
        settingsMenu.add(mediumGame);

        JMenuItem largeGame = new JRadioButtonMenuItem("Large");
        largeGame.addActionListener(e -> setGameParameters(LARGE, LARGE, LARGE_MINE_COUNT));
        settingsGroup.add(largeGame);
        settingsMenu.add(largeGame);

        JMenuItem customGame = new JRadioButtonMenuItem("Custom...");
        customGame.addActionListener(e ->
                new CustomGameDialogueFrame(this)
        );
        settingsGroup.add(customGame);
        settingsMenu.add(customGame);

        menuBar.add(settingsMenu);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");

        JMenuItem howToPlay = new JMenuItem("How to Play");
        howToPlay.addActionListener(e -> new InfoFrame(this, InfoFrame.ContentType.HOW_TO_PLAY));
        helpMenu.add(howToPlay);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> new InfoFrame(this, InfoFrame.ContentType.ABOUT));
        helpMenu.add(about);

        menuBar.add(helpMenu);

        // New Game Button
        JButton newGameButton = new JButton("New Game");
        newGameButton.setFocusable(false);
        newGameButton.addActionListener(e -> {
            if (gameRows != 0) createNewGame(gameRows, gameCols, mineCount);
        });
        menuBar.add(Box.createHorizontalGlue());    // force the button to be on the right side.

        menuBar.add(newGameButton);

        this.setJMenuBar(menuBar);
        /* <------ MENU BAR end ------> */

        /* <---------- MISC ----------> */
        this.setResizable(false);
        this.setTitle("Home-Cooked Minesweeper");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        /* <-------- MISC end --------> */

    }

    private void setGameParameters(int gameRows, int gameCols, int mineCount) {
        this.gameRows = gameRows;
        this.gameCols = gameCols;
        this.mineCount = mineCount;
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
        this.setSize(gamePanel.getWidth() + 15, gamePanel.getHeight() + 65);
        this.add(gamePanel);
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Ώριστε, Τζαύαντοκ στα ελληνικά!
     */
    private static class CustomGameDialogueFrame extends JFrame {

        private CustomGameDialogueFrame(GameFrame gameFrame) {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
                gameFrame.setGameParameters(rows, cols, mineCount);
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please provide numbers!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private static class InfoFrame extends JFrame {

        private static final String howToPlayText = """
                <html>
                <b><font size=+2>How to Play</font></b>
                
                <p><font size=+1>Start a game</font>
                
                <p>Click on the "Settings" menu and select the desired difficulty.<br>
                If you want to play with a custom board, select "Custom..." and<br>
                specify the board to your liking.<br>
                
                <p>Once a difficulty is selected, click on the "New Game" button<br>
                to start a new game.
                
                <p><font size=+1>Playing the game</font>
                
                <p>Left-clicking any cell will reveal what lies hidden under it.<br>
                If you left-click on any cell which contained a mine, you lose!<br>
                Cells that instead contain a number tell you how many neighbouring<br>
                cells are mines. Use this information to carefully avoid any mines.<br>
                Once you revealed every safe cell, you win the game!
                
                <p>Right-clicking any cell will mark it as "suspected". Suspected<br>
                mines cannot be left-clicked, so you can prevent yourself clicking<br>
                on a mine by mistake! Right-clicking a suspected mine again will<br>
                remove this restriction.
                </html>""";

        private static final String aboutText = """
                <html>
                <i>Lorem ipsum once again<i>
                </html>""";

        private enum ContentType {
            HOW_TO_PLAY(howToPlayText),
            ABOUT(aboutText);

            final String text;
            ContentType(String text) {
                this.text = text;
            }
        }

        private InfoFrame(GameFrame gameFrame, ContentType contentType) {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JLabel info = new JLabel(contentType.text);
            this.add(info);

            this.pack();
            this.setLocationRelativeTo(gameFrame);
            this.setVisible(true);
        }
    }
}
