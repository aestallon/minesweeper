package minesweeper.experi;


import javax.swing.*;
import java.awt.*;

import static minesweeper.experi.Main.fs;
import static minesweeper.experi.Main.userDir;
import static minesweeper.experi.MsFrame.CELL_SIZE;

public class CellButton extends JButton {
    private int xPosition;
    private int yPosition;
    private boolean isSus = false;
    private boolean isUncovered = false;
    private boolean isMine;
    private String value;


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

    public void setCellButtonImage(String filename) {
        ImageIcon image = new ImageIcon(userDir + fs + "graphics" + fs + filename + ".png");
        this.setIcon(image);
        this.setHorizontalAlignment(SwingConstants.LEFT);
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
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