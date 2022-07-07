package hu.aestallon.minesweeper;

import hu.aestallon.minesweeper.game.GameConfig;
import hu.aestallon.minesweeper.game.GamePanel;
import hu.aestallon.minesweeper.scores.DatabaseHandler;
import hu.aestallon.minesweeper.scores.Player;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    /** This is the height and width of the cells in the game, given in pixels. */
    public static final int CELL_SIZE = 30;

    private static final DatabaseHandler db = DatabaseHandler.getInstance();

    private final GameConfig gameConfig;

    private GamePanel gamePanel;
    private JButton newGameButton;

    public GameFrame() {
        // Default game configuration setup:
        Player defaultPlayer = new Player("guest", db);
        gameConfig = new GameConfig(defaultPlayer);

        // Frame initialization:
        initAppearance();
        initMenuBar();
        this.setVisible(true);
    }

    private void initAppearance() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(335, 80);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Home-Cooked Minesweeper");
        this.setLocationRelativeTo(null);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Settings Menu
        JMenu settingsMenu = new JMenu("Settings");
        ButtonGroup settingsGroup = new ButtonGroup();

        JMenuItem smallGame = new JRadioButtonMenuItem("Small");
        smallGame.addActionListener(e -> {
            gameConfig.setCols(GameConfig.SMALL);
            gameConfig.setRows(GameConfig.SMALL);
            gameConfig.setMineCount(GameConfig.SMALL_MINE_COUNT);
            newGameButton.setEnabled(true);
        });
        settingsGroup.add(smallGame);
        settingsMenu.add(smallGame);

        JMenuItem mediumGame = new JRadioButtonMenuItem("Medium");
        mediumGame.addActionListener(e -> {
            gameConfig.setCols(GameConfig.MEDIUM);
            gameConfig.setRows(GameConfig.MEDIUM);
            gameConfig.setMineCount(GameConfig.MEDIUM_MINE_COUNT);
            newGameButton.setEnabled(true);
        });
        settingsGroup.add(mediumGame);
        settingsMenu.add(mediumGame);

        JMenuItem largeGame = new JRadioButtonMenuItem("Large");
        largeGame.addActionListener(e -> {
            gameConfig.setCols(GameConfig.LARGE);
            gameConfig.setRows(GameConfig.LARGE);
            gameConfig.setMineCount(GameConfig.LARGE_MINE_COUNT);
            newGameButton.setEnabled(true);
        });
        settingsGroup.add(largeGame);
        settingsMenu.add(largeGame);

        JMenuItem customGame = new JRadioButtonMenuItem("Custom...");
        customGame.addActionListener(e -> new CustomGameDialog());
        settingsGroup.add(customGame);
        settingsMenu.add(customGame);

        menuBar.add(settingsMenu);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");

        JMenuItem howToPlay = new JMenuItem("How to Play");
        howToPlay.addActionListener(e -> new InfoFrame(InfoFrame.ContentType.HOW_TO_PLAY));
        helpMenu.add(howToPlay);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> new InfoFrame(InfoFrame.ContentType.ABOUT));
        helpMenu.add(about);

        menuBar.add(helpMenu);

        // Scores Menu
        JMenu scoreMenu = new JMenu("Scores");

        JMenuItem highScores = new JMenuItem("High Scores");
        highScores.addActionListener(e -> new HighScoreFrame());
        scoreMenu.add(highScores);

        JMenuItem changeName = new JMenuItem("Change name...");
        changeName.addActionListener(e -> new PlayerChangeDialog());
        scoreMenu.add(changeName);

        menuBar.add(scoreMenu);

        // New Game Button
        newGameButton = new JButton("New Game");
        newGameButton.setFocusable(false);
        newGameButton.setEnabled(false);            // any valid game setup enables this
        newGameButton.addActionListener(e -> createNewGame());
        menuBar.add(Box.createHorizontalGlue());    // force the button to be on the right side.

        menuBar.add(newGameButton);

        this.setJMenuBar(menuBar);
    }

    /**
     * Creates a new game of Minesweeper.
     *
     * <p>Removes the panel containing the actual game interface from
     * this instance and initializes it based on the parameters
     * provided, then adds the panel back to the instance. Finally, the
     * instance is refreshed to let the user see and interact with the
     * changes.
     */
    private void createNewGame() {
        if (gamePanel != null) remove(gamePanel);
        gamePanel = new GamePanel(gameConfig);
        gamePanel.setSize(
                gameConfig.getCols() * CELL_SIZE,
                gameConfig.getRows() * CELL_SIZE
        );
        gamePanel.setLocation(0, 0);
        this.setSize(gamePanel.getWidth() + 15, gamePanel.getHeight() + 65);
        this.add(gamePanel);
        SwingUtilities.updateComponentTreeUI(this);
    }

    private class CustomGameDialog extends JFrame {

        private CustomGameDialog() {
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

        private void parseUserInputs(JTextField rowTextField,
                                     JTextField colTextField,
                                     JTextField mineTextField) {
            try {
                int rows = Integer.parseInt(rowTextField.getText());
                int cols = Integer.parseInt(colTextField.getText());
                int mineCount = Integer.parseInt(mineTextField.getText());

                GameFrame.this.gameConfig.setRows(rows);
                GameFrame.this.gameConfig.setCols(cols);
                GameFrame.this.gameConfig.setMineCount(mineCount);

                this.dispose();
                GameFrame.this.newGameButton.setEnabled(true);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please provide numbers!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private class InfoFrame extends JFrame {

        private static final String howToPlayText = """
                <html>
                <h1>How to Play</font></h1>
                                
                <h2>Start a game</h2>
                                
                <p>Click on the "Settings" menu and select the desired difficulty.<br>
                If you want to play with a custom board, select "Custom..." and<br>
                specify the board to your liking.<br>
                                
                <p>Once a difficulty is selected, click on the "New Game" button<br>
                to start a new game.
                                
                <h2>Playing the game<h2>
                                
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

        private InfoFrame(ContentType contentType) {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JLabel info = new JLabel(contentType.text);
            this.add(info);

            this.pack();
            this.setLocationRelativeTo(GameFrame.this);
            this.setVisible(true);
        }
    }

    private class HighScoreFrame extends JFrame {
        private HighScoreFrame() {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setTitle("High Scores");
            this.setLayout(new GridLayout(11, 2));

            JLabel playerHeader = new JLabel("Player");
            this.add(playerHeader);

            JLabel scoreHeader = new JLabel("Score");
            this.add(scoreHeader);

            db.getTopTenScores().forEach(score -> {
                JLabel player = new JLabel(score.name());
                this.add(player);
                JLabel highScore = new JLabel(String.valueOf(score.score()));
                this.add(highScore);
            });

            this.pack();
            this.setLocationRelativeTo(GameFrame.this);
            this.setVisible(true);
        }
    }

    private class PlayerChangeDialog extends JFrame {
        private PlayerChangeDialog() {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setTitle("High Scores");
            this.setLayout(new GridLayout(2, 2));

            this.add(new JLabel("Enter your name:"));
            JTextField textField = new JTextField(20);
            this.add(textField);

            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                String input = textField.getText();
                if (input.isEmpty() || input.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Name cannot be blank!");
                } else {
                    GameFrame.this.gameConfig
                            .setPlayer(new Player(input, db));
                    this.dispose();
                }
            });
            this.add(okButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> this.dispose());
            this.add(cancelButton);

            this.pack();
            this.setLocationRelativeTo(GameFrame.this);
            this.setVisible(true);
        }
    }
}
