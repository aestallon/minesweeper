package hu.aestallon.minesweeper;


import javax.swing.*;
import java.awt.*;

import static hu.aestallon.minesweeper.Main.FS;
import static hu.aestallon.minesweeper.Main.USER_DIR;

public class CellButton extends JButton {
    private final int xPosition;
    private final int yPosition;

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
        this.setPreferredSize(new Dimension(MinesweeperFrame.CELL_SIZE, MinesweeperFrame.CELL_SIZE));
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setCellButtonImage("defaultImage");


    }

    public void setSus(boolean input) {
        this.isSus = input;
        if (input) setCellButtonImage("suspected");
        else setCellButtonImage("default");
    }

    public void setMine(boolean input) {
        this.isMine = input;
    }

    public void setUncovered() {
        flagUncovered();
        setCellButtonImage(value);
    }

    public void flagUncovered() {
        this.isUncovered = true;
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