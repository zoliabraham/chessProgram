package ChessGame.View;

import java.awt.*;
import java.awt.image.ImageObserver;

public class DrawInfo {
    public Graphics2D g2d;
    public ImageObserver imageObserver;

    public DrawInfo(Graphics2D g2d, ImageObserver imageObserver) {
        this.g2d = g2d;
        this.imageObserver = imageObserver;
    }
}
