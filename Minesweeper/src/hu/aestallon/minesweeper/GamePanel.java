package hu.aestallon.minesweeper;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GamePanel extends JPanel implements MouseInputListener {
    private final List<CellButton> cellButtons;

    public GamePanel(int size, int mineCount) {
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
        if (SwingUtilities.isLeftMouseButton(event) &&
                !button.isSus() &&
                button.isInteractive()) {

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
     * Provides every {@code CellButton} neighbouring the one provided.
     *
     * <p>The search is performed on the {@link #cellButtons} of this
     * instance.
     *
     * @param button the {@code CellButton} in question.
     * @return a {@code List&lt;CellButton&gt;} containing all buttons
     *         neighbouring the above cellButton.
     *         <i>Diagonal neighbours included.</i>
     */
    private List<CellButton> getNeighbours(CellButton button) {
        int x = button.getXPosition(), y = button.getYPosition();
        return cellButtons.stream()
                .filter(cb -> (x - cb.getXPosition() <= 1) && (x - cb.getXPosition() >= -1))
                .filter(cb -> (y - cb.getYPosition() <= 1) && (y - cb.getYPosition() >= -1))
                .dropWhile(cb -> cb.getXPosition() == x && cb.getYPosition() == y)
                .collect(Collectors.toList());
    }

    /**
     * Uncovers all {@code CellButton}s neighbouring the one provided.
     * If any of these newly uncovered buttons have a value of '0',
     * the method is recursively called on them.
     *
     * <p>This method can be used to automatically uncover chunks
     * of '0' valued cellButtons of the instance, and the neighbouring
     * trivial safe cells as well. Non-trivial cells won't be affected.
     *
     * @param button the cellButton where the uncovering should start.
     *               <b>It should be called only on buttons with value
     *               {@code '0'}!</b>
     */
    private void autoUncoverZero(CellButton button) {
        for (CellButton cb : getNeighbours(button)) {
            if (cb.isInteractive()) {
                cb.setUncovered();
                if (cb.getValue() == '0') autoUncoverZero(cb);
            }
        }
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
