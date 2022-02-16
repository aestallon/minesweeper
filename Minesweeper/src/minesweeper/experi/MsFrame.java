package minesweeper.experi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MsFrame extends JFrame implements ActionListener{
    static final int SMALL = 8;
    static final int MEDIUM = 10;
    static final int LARGE = 16;

    static final int SMALL_MINE_COUNT = 5;
    static final int MEDIUM_MINE_COUNT = 10;
    static final int LARGE_MINE_COUNT = 55;

    public static final int CELL_SIZE = 40;

    MinesweeperGrid msGrid;
    JButton submitButton;
    JButton newGameSmallButton;
    JButton newGameMediumButton;
    JButton newGameLargeButton;

    public MsFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(415, 480);
        this.setLayout(null);

        newGameSmallButton = new JButton("Small");
        newGameSmallButton.setSize(80, 40);
        newGameSmallButton.setLocation(0,0);
        newGameSmallButton.setBackground(new Color(255, 230, 138));
        newGameSmallButton.addActionListener(this);

        newGameMediumButton = new JButton("Medium");
        newGameMediumButton.setSize(80, 40);
        newGameMediumButton.setLocation(80,0);
        newGameMediumButton.setBackground(new Color(255, 208, 138));
        newGameMediumButton.addActionListener(this);

        newGameLargeButton = new JButton("Large");
        newGameLargeButton.setSize(80, 40);
        newGameLargeButton.setLocation(160,0);
        newGameLargeButton.setBackground(new Color(255, 183, 138));
        newGameLargeButton.addActionListener(this);

        submitButton = new JButton("Submit");
        submitButton.setSize(80, 40);
        submitButton.setLocation(240,0);
        submitButton.setBackground(Color.GREEN);
        submitButton.addActionListener(this);

        msGrid = new MinesweeperGrid(10, 10);
        msGrid.setSize(400,400);
        msGrid.setLocation(0, 41);

        this.add(newGameSmallButton);
        this.add(newGameMediumButton);
        this.add(newGameLargeButton);
        this.add(msGrid);
        this.add(submitButton);
        this.setTitle("Home-Cooked Minesweeper");
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
            for (CellButton cb : msGrid.cellButtons) {
                if (cb.isSus && cb.isMine) foundMineCounter++;
            }
            if (foundMineCounter == msGrid.mineCount) {
                for (CellButton cb : msGrid.cellButtons) {
                    cb.setUncovered(true);
                }
                JOptionPane.showMessageDialog(null, "Gratulálunk nyertél!!!!");
            }
        }
    }

    private void createNewGame(int gameSize, int mineCount) {
        this.remove(msGrid);
        for (CellButton cb : msGrid.cellButtons) {
            cb.setIcon(null);
        }
        msGrid = new MinesweeperGrid(gameSize, mineCount);
        msGrid.setSize(gameSize*CELL_SIZE,gameSize*CELL_SIZE);
        msGrid.setLocation(0, 41);
        this.add(msGrid);
        if (gameSize == SMALL)
            this.setBackground(new Color(255, 208, 138));
        else if (gameSize == LARGE)
            this.setBackground(new Color(255, 183, 138));

            /*  Ez egy meglehetősen favágó módszer arra, hogy az új tartalom
                megjelenjen, de az `updateComponentTreeUI` felülírja a gomb-
                beállításokat, és így az aknakereső gombikonjai bugolni kez-
                denek.Ha valakinek van jobb módszere (azon kívül, hogy írni
                kéne egy teljesen custom `look&feel`-t, szóljon.
             */
        this.setSize(gameSize*CELL_SIZE + 16,gameSize*CELL_SIZE + 80);
        this.setSize(gameSize*CELL_SIZE + 15,gameSize*CELL_SIZE + 80);
//            SwingUtilities.updateComponentTreeUI(this);
    }
}
