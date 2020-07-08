package ChessGame.ImageCache;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class NamedImage {
    String fileName;
    Image image;

    public NamedImage(String fileName) {
        try {
            setFileName(fileName);
            image = ImageIO.read(new File(fileName));

        } catch (IOException e) {
            //e.printStackTrace();
            System.err.println("file name not found: " + fileName);
            setFileName(fileName);
            try {
                image = ImageIO.read(new File("resources/black/pawn.png"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public Image getImage() {
        return image;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
