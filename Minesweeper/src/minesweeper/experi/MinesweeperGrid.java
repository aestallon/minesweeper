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
        CellButton button = (CellButton)event.getSource();
        if (SwingUtilities.isLeftMouseButton(event) && !button.isSus && !button.isUncovered) {
            switch (button.value) {
                case "0" -> {
                    button.setCellButtonImage("0");
                    autoUncoverZero(button);
                }
                case "1" -> button.setCellButtonImage("1");
                case "2" -> button.setCellButtonImage("2");
                case "3" -> button.setCellButtonImage("3");
                case "4" -> button.setCellButtonImage("4");
                case "5" -> button.setCellButtonImage("5");
                case "6" -> button.setCellButtonImage("6");
                case "7" -> button.setCellButtonImage("7");
                case "8" -> button.setCellButtonImage("8");
                default -> button.setCellButtonImage("x");
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
            }
            else {
                button.setSus(false);
                button.setCellButtonImage("defaultImage");
            }
        }
    }

    private CellButton getCellButton(int x, int y) {
        for (CellButton b : cellButtons) {
            if (b.xPosition == x && b.yPosition == y) {
                return b;
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
        for (CellButton b : buttonNeighbours) {
            if (!b.isUncovered) {
                b.setCellButtonImage(b.value);
                button.setUncovered(true);
                if (b.value.equals("0")) autoUncoverZero(b);
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
