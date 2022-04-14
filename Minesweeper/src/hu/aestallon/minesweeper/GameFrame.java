package hu.aestallon.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    public static final int CELL_SIZE = 40;

    private static final int SMALL = 8;
    private static final int MEDIUM = 10;
    private static final int LARGE = 16;

    private static final int SMALL_MINE_COUNT = 5;
    private static final int MEDIUM_MINE_COUNT = 10;
    private static final int LARGE_MINE_COUNT = 55;

    private GamePanel msGrid; // játékpanel
    private JButton submitButton;
    private JButton newGameSmallButton;
    private JButton newGameMediumButton;
    private JButton newGameLargeButton;

    public GameFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(415, 480);
        this.setLayout(null);

        newGameSmallButton = new JButton("Small");
        newGameSmallButton.setSize(80, 40);
        newGameSmallButton.setLocation(0, 0);
        newGameSmallButton.setBackground(new Color(255, 230, 138));
        newGameSmallButton.addActionListener(this);

        newGameMediumButton = new JButton("Medium");
        newGameMediumButton.setSize(80, 40);
        newGameMediumButton.setLocation(80, 0);
        newGameMediumButton.setBackground(new Color(255, 208, 138));
        newGameMediumButton.addActionListener(this);

        newGameLargeButton = new JButton("Large");
        newGameLargeButton.setSize(80, 40);
        newGameLargeButton.setLocation(160, 0);
        newGameLargeButton.setBackground(new Color(255, 183, 138));
        newGameLargeButton.addActionListener(this);

        submitButton = new JButton("Submit");
        submitButton.setSize(80, 40);
        submitButton.setLocation(240, 0);
        submitButton.setBackground(Color.GREEN);
        submitButton.addActionListener(this);

        msGrid = new GamePanel(10, 10);
        msGrid.setSize(400, 400);
        msGrid.setLocation(0, 41);

        this.add(newGameSmallButton);
        this.add(newGameMediumButton);
        this.add(newGameLargeButton);
        this.add(msGrid);
        this.add(submitButton);
        this.setResizable(false);
        this.setTitle("Home-Cooked Minesweeper");
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameSmallButton) {
            createNewGame(SMALL, SMALL_MINE_COUNT);
        } else if (e.getSource() == newGameMediumButton) {
            createNewGame(MEDIUM, MEDIUM_MINE_COUNT);
        } else if (e.getSource() == newGameLargeButton) {
            createNewGame(LARGE, LARGE_MINE_COUNT);
        } else if (e.getSource() == submitButton) {
            int foundMineCounter = 0;
            for (CellButton cb : msGrid.getCellButtons()) {
                if (cb.isSus() && cb.isMine()) foundMineCounter++;
            }
            if (foundMineCounter == msGrid.getMineCount()) {
                msGrid.getCellButtons().forEach(CellButton::flagUncovered);
                JOptionPane.showMessageDialog(null, "Gratulálunk nyertél!!!!");
            }
        }
    }

    /**
     * Creates a new game of Minesweeper.
     *
     * <p>Removes the panel containing the actual game interface from
     * this instance and initializes it based on the parameters
     * provided, then adds the panel back to the instance. Finally the
     * instance is refreshed to let the user see and interact with the
     * changes.
     *
     * @param gameSize  The size of the game's minefield.
     *                  <i>(Both horizontal and vertical)</i>
     * @param mineCount The number of mines present in the game.
     */
    private void createNewGame(int gameSize, int mineCount) {
        this.remove(msGrid);
        msGrid = new GamePanel(gameSize, mineCount);
        msGrid.setSize(gameSize * CELL_SIZE, gameSize * CELL_SIZE);
        msGrid.setLocation(0, 41);
        this.add(msGrid);
        if (gameSize == SMALL)
            this.setBackground(new Color(255, 208, 138));
        else if (gameSize == LARGE)
            this.setBackground(new Color(255, 183, 138));

            /* Ez egy meglehetősen favágó módszer arra, hogy az új tartalom *
             * megjelenjen, de az `updateComponentTreeUI` felülírja a gomb- *
             * beállításokat, és így az aknakereső gombikonjai bugolni kez- *
             * denek.Ha valakinek van jobb módszere (azon kívül, hogy írni  *
             * kéne egy teljesen custom `look&feel`-t, szóljon.             */
        this.setSize(gameSize * CELL_SIZE + 16, gameSize * CELL_SIZE + 80);
        this.setSize(gameSize * CELL_SIZE + 15, gameSize * CELL_SIZE + 80);
//            SwingUtilities.updateComponentTreeUI(this);
    }
}
