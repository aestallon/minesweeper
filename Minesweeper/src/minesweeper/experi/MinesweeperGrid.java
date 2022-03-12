package minesweeper.experi;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MinesweeperGrid extends JPanel implements MouseInputListener {
    Minefield minefield;
    CellButton cellButton;
    int size;
    int mineCount;
    List<CellButton> cellButtons;


    public MinesweeperGrid(int gameSize, int numberOfMines) {
        size = gameSize;
        mineCount = numberOfMines;
        minefield = new Minefield(size, mineCount);
        cellButtons = new ArrayList<>();
        this.setLayout(new GridLayout(size, size, 0, 0));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cellButton = new CellButton(i, j);

                cellButton.isSus = false;
                cellButton.value = minefield.cells[i][j];
                cellButton.isUncovered = false;
                if (cellButton.value.equals(Minefield.MINE))
                    cellButton.setMine(true);
                cellButton.setCellButtonImage("defaultImage");

                this.add(cellButton);
                cellButtons.add(cellButton);
                cellButton.addMouseListener(this);
                System.out.print(minefield.cells[i][j] + "\t");   //ParasztDEBUG
            }
            System.out.println();
            System.out.println();                               //ParasztDEBUG
        }
        this.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        CellButton button = (CellButton) event.getSource();
        if (SwingUtilities.isLeftMouseButton(event) && !button.isSus && !button.isUncovered) {
            if (button.value.equals("0")) {
                button.setCellButtonImage("0");
                autoUncoverZero(button);
            }
            else {
                button.setCellButtonImage(button.value);
            }
            button.setUncovered(true);
            if (button.isMine) {
                for (CellButton cb : cellButtons) {
                    cb.setUncovered(true);
                }
                JOptionPane.showMessageDialog(null, "Sajnos vesztettél :(");
            }
        } else if (SwingUtilities.isRightMouseButton(event) && !button.isUncovered) {
            if (!button.isSus) {
                button.setSus(true);
                button.setCellButtonImage("suspected");
            } else {
                button.setSus(false);
                button.setCellButtonImage("defaultImage");
            }
        }
    }

    private CellButton getCellButton(int x, int y) {
        for (CellButton cb : cellButtons) {
            if (cb.xPosition == x && cb.yPosition == y) {
                return cb;
            }
        }
        return null;
    }

    private ArrayList<CellButton> getNeighbours(CellButton button) {
        int x = button.xPosition;
        int y = button.yPosition;
        ArrayList<CellButton> neighbouringButtons = new ArrayList<>();
        if (x > 0 && y > 0) neighbouringButtons.add(getCellButton(x - 1, y - 1));
        if (x > 0) neighbouringButtons.add(getCellButton(x - 1, y));
        if (x > 0 && y < size - 1) neighbouringButtons.add(getCellButton(x - 1, y + 1));
        if (y < size - 1) neighbouringButtons.add(getCellButton(x, y + 1));
        if (x < size - 1 && y < size - 1) neighbouringButtons.add(getCellButton(x + 1, y + 1));
        if (x < size - 1) neighbouringButtons.add(getCellButton(x + 1, y));
        if (x < size - 1 && y > 0) neighbouringButtons.add(getCellButton(x + 1, y - 1));
        if (y > 0) neighbouringButtons.add(getCellButton(x, y - 1));

        return neighbouringButtons;
    }

    private void autoUncoverZero(CellButton button) {
        ArrayList<CellButton> buttonNeighbours = getNeighbours(button);
        for (CellButton cb : buttonNeighbours) {
            if (!cb.isUncovered) {
                cb.setCellButtonImage(cb.value);
                cb.setUncovered(true);
                if (cb.value.equals("0")) autoUncoverZero(cb);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
