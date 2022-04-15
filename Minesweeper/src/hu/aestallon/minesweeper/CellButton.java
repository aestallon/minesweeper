package hu.aestallon.minesweeper;


import javax.swing.*;
import java.awt.*;

import static hu.aestallon.minesweeper.Main.FS;
import static hu.aestallon.minesweeper.Main.USER_DIR;

/**
 * An extended {@link JButton} serving as an interactive cell for a
 * minefield.
 *
 * <p>Instances should be placed inside a {@link GamePanel}, and their
 * behaviour should be controlled from there using some sort of action
 * listener interface.
 */
public class CellButton extends JButton {
    private final int xPosition;
    private final int yPosition;
    private final char value;

    private boolean isSus = false;
    private boolean interactive = true;

    /**
     * Constructs a new instance with the grid positions provided.
     *
     * @param xPosition {@code int} horizontal position in the grid
     * @param yPosition {@code int} vertical position in the grid.
     * @param value     a {@code char} value.
     */
    public CellButton(int xPosition, int yPosition, char value) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.value = value;

        this.setBorderPainted(false);
        this.setBorder(null);
        this.setIconTextGap(0);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(GameFrame.CELL_SIZE, GameFrame.CELL_SIZE));
        this.setCellButtonImage("defaultImage");
    }

    /**
     * Flags the instance as "suspected to be hiding a mine", or "not
     * suspected to be hiding a mine". The appearance of the instance
     * is changed accordingly.
     *
     * @param input a {@code boolean}
     */
    public void setSus(boolean input) {
        this.isSus = input;
        if (input) setCellButtonImage("suspected");
        else setCellButtonImage("defaultImage");
    }

    /**
     * Flags the instance as "uncovered by the user", and changes its
     * appearance according to the value stored in it.
     *
     * <p>The instance will be marked non-interactive, and as such
     * interacting with it should not yield any results.
     */
    public void setUncovered() {
        setPassive();
        setCellButtonImage(String.valueOf(value));
    }

    /**
     * Flags the instance passive without changing its appearance.
     *
     * <p>The purpose of this is to make the instance non-interactive.
     */
    public void setPassive() {
        interactive = false;
    }

    /**
     * Sets the specified image file as the button's icon.
     *
     * @param filename the {@code String} name of an image file. It must be
     *                 one of the files found in the {@code graphics} folder
     *                 of the installation directory. The file extension
     *                 {@code ".png"} is automatically appended.
     */
    private void setCellButtonImage(String filename) {
        ImageIcon image = new ImageIcon(USER_DIR + FS + "graphics" + FS + filename + ".png");
        this.setIcon(image);
    }

    /**
     * @return true if the instance is suspected to be hiding
     *         a mine, false otherwise.
     */
    public boolean isSus() {
        return isSus;
    }

    /**
     * @return true if the instance is interactive, false
     *         otherwise.
     */
    public boolean isInteractive() {
        return interactive;
    }

    /**
     * Checks if the instance is "hiding" a mine or not.
     *
     * @return true if this instance's {@code value} corresponds
     *         to a {@code mine}, false otherwise.
     */
    public boolean isMine() {
        return value == Minefield.MINE;
    }

    public char getValue() {
        return value;
    }

    /**
     * Checks if another {@code CellButton} is the Moore-neighbour
     * of this instance, based on stored `x` and `y` positions.
     *
     * @param anotherCB a {@code CellButton}
     * @return true, if they are neighbours, otherwise false.
     */
    public boolean isNeighbourOf(CellButton anotherCB) {
        if (this.equals(anotherCB)) return false;
        int deltaX = this.xPosition - anotherCB.xPosition;
        int deltaY = this.yPosition - anotherCB.yPosition;
        return deltaX >= -1 && deltaX <= 1 && deltaY >= -1 && deltaY <= 1;
    }

}