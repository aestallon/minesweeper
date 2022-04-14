package hu.aestallon.minesweeper;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements MouseInputListener {
    private final int size;
    private final int mineCount;
    private final List<CellButton> cellButtons;


    public GamePanel(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        Minefield minefield = new Minefield(size, mineCount);
        cellButtons = new ArrayList<>();
        this.setLayout(new GridLayout(size, size, 0, 0));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                CellButton cellButton = new CellButton(i, j, minefield.getCell(i, j));
                this.add(cellButton);
                cellButtons.add(cellButton);
                cellButton.addMouseListener(this);
            }
        }
        minefield.print();      // DEBUGGING
        this.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        CellButton button = (CellButton) event.getSource();
        if (SwingUtilities.isLeftMouseButton(event) && !button.isSus() && button.isInteractive()) {
            button.setUncovered();
            if (button.getValue() == '0') {
                autoUncoverZero(button);
            }
            if (button.isMine()) {
                cellButtons.forEach(CellButton::setPassive);
                JOptionPane.showMessageDialog(null, "Sajnos vesztettél :(");
            }
        } else if (SwingUtilities.isRightMouseButton(event) && button.isInteractive()) {
            button.setSus(!button.isSus());
        }
    }

    /**
     * Returns a {@code CellButton} from this instance's {@link #cellButtons}
     * based on the grid positions provided.
     *
     * @param x {@code int} horizontal position of the cellButton
     * @param y {@code int} vertical position of the cellButton
     * @return a {@code CellButton}, if it can be found , else {@code null}.
     */
    private CellButton getCellButton(int x, int y) {
        return cellButtons.stream()
                .filter(cb -> cb.getXPosition() == x && cb.getYPosition() == y)
                .findAny().orElse(null);
    }

    /**
     * Provides every {@code CellButton} neighbouring the one provided.
     *
     * <p>The search is performed on the {@code List&lt;CellButton&gt;}
     * of this instance.
     *
     * @param button the {@code CellButton} in question.
     * @return a {@code List&lt;CellButton&gt;} containing all buttons
     *         neighbouring the above cellButton.
     *         <i>Diagonal neighbours included.</i>
     */
    private List<CellButton> getNeighbours(CellButton button) {
        int x = button.getXPosition();
        int y = button.getYPosition();

        List<CellButton> neighbouringButtons = new ArrayList<>();

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
//        getNeighbours(button).stream()
//                .filter(CellButton::isInteractive)
//                .forEach(cellButton -> {
//                    cellButton.setUncovered();
//                    if (cellButton.getValue() == '0') autoUncoverZero(cellButton);
//                });
        for (CellButton cb : getNeighbours(button)) {
            if (cb.isInteractive()) {
                cb.setUncovered();
                if (cb.getValue() == '0') autoUncoverZero(cb);
            }
        }
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
