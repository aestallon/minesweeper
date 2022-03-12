package minesweeper.experi;


import javax.swing.*;
import java.awt.*;

import static minesweeper.experi.Main.FS;
import static minesweeper.experi.Main.USER_DIR;
import static minesweeper.experi.MsFrame.CELL_SIZE;

public class CellButton extends JButton {
    private int xPosition;
    private int yPosition;
    private boolean isSus = false;
    private boolean isUncovered = false;
    private boolean isMine;

    /**
     * The string value of a minefield's cell corresponding to this button.
     */
    private String value;

    /**
     * An extended JButton serving as an interactable cell for a minefield. It should be placed inside a
     * <code>minesweeperGrid</code>. The parameters denote the position of the cellButton in its container's grid
     * layout.<p></p>
     * Every instance of this class should be put into a collection, and the parent container should
     * control their/its interaction by implementing the <code>MouseInputListener</code> interface.
     *
     * @param xPosition Horizontal position in the grid.
     * @param yPosition Vertical position in the grid.
     */
    public CellButton(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        this.setBorderPainted(false);
        this.setBorder(null);
        this.setIconTextGap(0);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setCellButtonImage("defaultImage");


    }

    public void setSus(boolean input) {
        this.isSus = input;
    }

    public void setMine(boolean input) {
        this.isMine = input;
    }

    public void setUncovered(boolean input) {
        this.isUncovered = input;
    }

    /**
     * Sets the specified image file as the button's icon.
     *
     * @param filename the name of the image file. It must be one of the files found in the <code>graphics</code>
     *                 folder of the installation directory. The file extension <code>.png</code> is automatically
     *                 appended.
     */
    public void setCellButtonImage(String filename) {
        ImageIcon image = new ImageIcon(USER_DIR + FS + "graphics" + FS + filename + ".png");
        this.setIcon(image);
        this.setHorizontalAlignment(SwingConstants.LEFT);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }


    public boolean isSus() {
        return isSus;
    }

    public boolean isUncovered() {
        return isUncovered;
    }

    public boolean isMine() {
        return isMine;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}