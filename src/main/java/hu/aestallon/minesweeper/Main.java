package hu.aestallon.minesweeper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class Main {
    public static final Map<String, BufferedImage> sprites = new HashMap<>();

    private static final String[] SPRITE_NAMES =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "x", "defaultImage", "suspected"};

    private Main() {}

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
        for (String spriteName : SPRITE_NAMES) {
            InputStream inputStream = Main.class
                    .getResourceAsStream("/graphics/" + spriteName + ".png");
            if (inputStream == null) {
                throw new IllegalArgumentException("The provided sprite name (" + spriteName + ") is invalid!");
            }
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            sprites.put(spriteName, bufferedImage);
        }
    }

}
