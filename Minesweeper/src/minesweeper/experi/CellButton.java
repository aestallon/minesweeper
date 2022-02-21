package minesweeper.experi;


import javax.swing.*;
import java.awt.*;

import static minesweeper.experi.Main.fs;
import static minesweeper.experi.Main.userDir;
import static minesweeper.experi.MsFrame.CELL_SIZE;

public class CellButton extends JButton {
    int xPosition;
    int yPosition;
    boolean isSus;
    boolean isUncovered;
    boolean isMine;
    String value;


    public CellButton(int horizontalPosition, int verticalPosition) {
        xPosition = horizontalPosition;
        yPosition = verticalPosition;
        this.setBorderPainted(false);
        this.setBorder(null);
        this.setIconTextGap(0);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        this.setHorizontalAlignment(SwingConstants.LEFT);


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

    public void setCellButtonImage(String filename) {
        ImageIcon image = new ImageIcon(userDir + fs + "graphics" + fs + filename + ".png");
        this.setIcon(image);
        this.setHorizontalAlignment(SwingConstants.LEFT);
    }

}