package ChessGame.View;

import ChessGame.ImageCache.ImageCache;
import ChessGame.Pieces.Piece;
import ChessGame.Vector;

import java.awt.*;

public class PieceRenderable extends Renderable{
    Image texture;
    Piece piece;
    public PieceRenderable(Piece piece, Vector position, Vector size) {
        this.piece = piece;
        this.size = size;
        this.position = position;
        String fileName;
        try {
            fileName = "resources/" + piece.getPieceColor().getValue() + "/" + piece.getName() + ".png";
        }catch (Exception e){
            fileName ="";
        }

        this.texture = ImageCache.getImage(fileName);
    }

    @Override
    public void draw(DrawInfo drawInfo) {
        int margin = 5;
        Image newImage = texture.getScaledInstance(size.x - margin*2, size.y-margin*2, Image.SCALE_SMOOTH);
        int x = Math.round(position.x+margin);
        int y = Math.round(position.y+margin);
        drawInfo.g2d.drawImage(newImage, x, y, drawInfo.imageObserver);
    }

    public Piece getPiece() {
        return piece;
    }
}
