package ChessGame.ImageCache;

import java.awt.*;
import java.util.ArrayList;

/**
 * Gyorsítótárba helyezi a képeket a játék, így gyorsabban tölhetőek be, mint kiolvasva.
 */
public class ImageCache {
    static ArrayList<NamedImage> images = new ArrayList<>();

    public static Image getImage(String fileName) {
        for (NamedImage image : images) {
            if (image.getFileName().equals(fileName)) {
                return image.getImage();
            }
        }
        NamedImage namedImage = new NamedImage(fileName);
        images.add(namedImage);
        return namedImage.getImage();
    }
}
