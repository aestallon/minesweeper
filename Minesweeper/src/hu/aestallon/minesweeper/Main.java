package hu.aestallon.minesweeper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static final Map<String, BufferedImage> sprites = new HashMap<>();

    private static final String FS = System.getProperty("file.separator");
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String[] SPRITE_NAMES =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "x", "defaultImage", "suspected"};

    public static void main(String[] args) {
        try {
            loadSprites();
            new GameFrame();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Checks the existence of the image files necessary for the
     * program's operation.
     *
     * @throws IOException if at least one file is missing.
     */
    private static void loadSprites() throws IOException {
        for (String filename : SPRITE_NAMES) {
            File file = new File(USER_DIR + FS + "graphics" + FS + filename + ".png");
            BufferedImage bufferedImage = ImageIO.read(file);
            sprites.put(filename, bufferedImage);
        }
    }

}
