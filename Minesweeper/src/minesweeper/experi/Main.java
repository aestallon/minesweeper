package minesweeper.experi;

import java.io.File;

public class Main {
    static final String FS = System.getProperty("file.separator");
    static final String USER_DIR = System.getProperty("user.dir");

    private static final String[] GRAPHICS_FILES =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "x", "defaultImage", "suspected"};

    public static void main(String[] args) {
        try {
            CheckIfGraphicsSpritesExist();
            new MsFrame();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * A simple method for checking the existence of the image files necessary for the program's operation.
     *
     * @throws Exception if at least one file is missing. <i>Custom message included.</i>
     */
    private static void CheckIfGraphicsSpritesExist() throws Exception {
        for (String filename : GRAPHICS_FILES) {
            File file = new File(USER_DIR + FS + "graphics" + FS + filename + ".png");
            if (!file.exists()) throw new Exception("One or more sprites cannot be found. " +
                    "Please check the integrity of the `graphics` folder.");
        }
    }

}
