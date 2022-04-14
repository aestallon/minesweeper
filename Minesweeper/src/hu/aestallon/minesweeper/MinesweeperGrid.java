package hu.aestallon.minesweeper;

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
            button.setUncovered();
            if (button.getValue().equals("0")) {
                autoUncoverZero(button);
            }
            if (button.isMine()) {
                cellButtons.forEach(CellButton::flagUncovered);
                JOptionPane.showMessageDialog(null, "Sajnos vesztettél :(");
            }
        } else if (SwingUtilities.isRightMouseButton(event) && !button.isUncovered()) {
            button.setSus(!button.isSus());
        }
    }

    /**
     * Returns a <code>cellButton</code> from this instance's <code>cellbuttons</code> array-list.
     *
     * @param x horizontal position of the cellButton
     * @param y vertical position of the cellButton
     * @return a cellButton, if it can be found based on the above parameters, else <code>null</code>.
     */
    private CellButton getCellButton(int x, int y) {
        for (CellButton cb : cellButtons) {
            if (cb.getxPosition() == x && cb.getyPosition() == y) {
                return cb;
            }
        }
        return null;
    }

    /**
     * Provides every cellButton neighbouring the one provided as parameter.
     *
     * <p>The search is performed on the <code>cellButtons</code> array-list of this instance.</p>
     *
     * @param button the cellButton in question.
     * @return an ArrayList containing all buttons neighbouring the above cellButton.
     *         <i>Diagonal neighbours included.</i>
     */
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

    /**
     * Uncovers all cellButtons neighbouring the button provided as
     * parameter. If any of these newly uncovered buttons have a
     * value of "0", the method is recursively called on them.
     *
     * <p>This method can be used to automatically uncover chunks
     * of "0" valued cellButtons of the instance, and the neighbouring
     * trivial safe cells as well. Non-trivial cells won't be uncovered.
     *
     * @param button the cellButton where the uncovering should start.
     *               <b>It should be called only on buttons with value
     *               {@code "0"}!</b>
     */
    private void autoUncoverZero(CellButton button) {
        getNeighbours(button).stream()
                .filter(cellButton -> !cellButton.isUncovered())
                .forEach(cellButton -> {
                    cellButton.setUncovered();
                    if (cellButton.getValue().equals("0")) autoUncoverZero(cellButton);
                });
//        ArrayList<CellButton> buttonNeighbours = getNeighbours(button);
//        for (CellButton cb : buttonNeighbours) {
//            if (!cb.isUncovered()) {
//                cb.setUncovered();
//                if (cb.getValue().equals("0")) autoUncoverZero(cb);
//            }
//        }
    }

    public int getMineCount() {
        return mineCount;
    }

    /**
     * @return the {@code List&lt;CellButton&gt;} attribute of this instance.
     */
    public List<CellButton> getCellButtons() {
        return cellButtons;
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
