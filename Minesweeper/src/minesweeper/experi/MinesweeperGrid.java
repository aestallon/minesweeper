package minesweeper.experi;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MinesweeperGrid extends JPanel implements MouseInputListener {
    private Minefield minefield;
    private int size;
    private int mineCount;
    private List<CellButton> cellButtons;


    public MinesweeperGrid(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        minefield = new Minefield(size, mineCount);
        cellButtons = new ArrayList<>();
        this.setLayout(new GridLayout(size, size, 0, 0));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                CellButton cellButton = new CellButton(i, j);
                cellButton.setValue(minefield.getCell(i, j));
                if (cellButton.getValue().equals(Minefield.MINE))
                    cellButton.setMine(true);

                this.add(cellButton);
                cellButtons.add(cellButton);
                cellButton.addMouseListener(this);
            }
        }
        minefield.print();
        this.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        CellButton button = (CellButton) event.getSource();
        if (SwingUtilities.isLeftMouseButton(event) && !button.isSus() && !button.isUncovered()) {
            button.setCellButtonImage(button.getValue());
            if (button.getValue().equals("0")) {
                autoUncoverZero(button);
            }
            button.setUncovered(true);
            if (button.isMine()) {
                for (CellButton cb : cellButtons) {
                    cb.setUncovered(true);
                }
                JOptionPane.showMessageDialog(null, "Sajnos vesztettél :(");
            }
        } else if (SwingUtilities.isRightMouseButton(event) && !button.isUncovered()) {
            if (!button.isSus()) {
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
            if (cb.getxPosition() == x && cb.getyPosition() == y) {
                return cb;
            }
        }
        return null;
    }

    private ArrayList<CellButton> getNeighbours(CellButton button) {
        int x = button.getxPosition();
        int y = button.getyPosition();
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
            if (!cb.isUncovered()) {
                cb.setCellButtonImage(cb.getValue());
                cb.setUncovered(true);
                if (cb.getValue().equals("0")) autoUncoverZero(cb);
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

    public int getMineCount() {
        return mineCount;
    }

    public List<CellButton> getCellButtons() {
        return cellButtons;
    }
}
