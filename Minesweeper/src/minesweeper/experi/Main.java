package minesweeper.experi;

import java.io.File;

public class Main {
    static final String fs = System.getProperty("file.separator");
    static final String userDir = System.getProperty("user.dir");
    static final String[] graphicsFiles = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "x", "defaultImage", "suspected"};

    public static void main(String[] args) {
        try {
            CheckIfGraphicsSpritesExist();
            new MsFrame();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void CheckIfGraphicsSpritesExist() throws Exception {
        for (String filename : graphicsFiles) {
            File file = new File(userDir + fs + "graphics" + fs + filename + ".png");
            if (!file.exists()) throw new Exception("One or more sprites cannot be found. " +
                    "Please check the integrity of the `graphics` folder.");
        }
    }

}
