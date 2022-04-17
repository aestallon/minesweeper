package hu.aestallon.minesweeper;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An extended {@link JPanel} displaying an interactive Minesweeper
 * board.
 */
public class GamePanel extends JPanel implements MouseInputListener {

    /** Contains every {@link CellButton} present in this instance. */
    private final List<CellButton> cellButtons;

    /**
     * Constructs an instance with the given size (both height and width)
     * and number of mines.
     *
     * @param size      the {@code int} height and width of the board
     * @param mineCount the {@code int} number of mines in the board
     */
    public GamePanel(int size, int mineCount) {
        cellButtons = new ArrayList<>();
        Minefield minefield = new Minefield(size, mineCount);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                CellButton cellButton = new CellButton(i, j, minefield.getCell(i, j));
                this.add(cellButton);
                cellButtons.add(cellButton);
                cellButton.addMouseListener(this);
            }
        }
        this.setLayout(new GridLayout(size, size, 0, 0));
        this.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        CellButton button = (CellButton) event.getSource();
        if (SwingUtilities.isLeftMouseButton(event) &&
                !button.isSus() &&
                button.isInteractive()) {

            button.reveal();

            if (button.isMine()) {
                cellButtons.forEach(CellButton::setPassive);
                JOptionPane.showMessageDialog(null, "Sajnos vesztettél :(");
            } else {
                if (button.getValue() == '0') autoRevealZeros(button);
                if (isVictory()) {
                    cellButtons.forEach(CellButton::setPassive);
                    JOptionPane.showMessageDialog(null, "Gratulálunk nyertél!!!!");
                }
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
        return cellButtons.stream()
                .filter(cb -> cb.isNeighbourOf(button))
                .collect(Collectors.toList());
    }

    /**
     * Reveals all {@code CellButton}s neighbouring the one provided.
     * If any of these newly revealed buttons have a value of '0',
     * the method is recursively called on them.
     *
     * <p>This method can be used to automatically reveal chunks
     * of '0' valued cellButtons of the instance, and the neighbouring
     * trivial safe cells as well. Non-trivial cells won't be affected.
     *
     * @param button the cellButton where the reveal should start.
     *               <b>It should be called only on buttons with value
     *               {@code '0'}!</b>
     */
    private void autoRevealZeros(CellButton button) {
        for (CellButton cb : getNeighbours(button)) {
            if (cb.isInteractive()) {
                cb.reveal();
                if (cb.getValue() == '0') autoRevealZeros(cb);
            }
        }
    }

    /**
     * Checks if the current game-state qualifies as a victory
     * or not.
     *
     * @return true, if the game is won, false otherwise.
     */
    /*
     * The game is considered "won", if all cells that are not
     * hiding a mine are either revealed (their 'interactive'
     * attribute is false) or not revealed but not suspected
     * either. This state means that every mine cell is either
     * untouched or marked as suspected - and no safe cell is
     * marked as suspected either.
     *
     * Thus, we can count the cells for which it is true that:
     *     1) they are not hiding mines, and
     *     2) they have not been revealed.
     * If the number of such cells is exactly '0', the game can
     * be considered won.
     */
    private boolean isVictory() {
        long coveredNonMines = cellButtons.stream()
                .filter(cb -> !cb.isMine())
                .filter(CellButton::isInteractive)
                .count();
        return coveredNonMines == 0;
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
