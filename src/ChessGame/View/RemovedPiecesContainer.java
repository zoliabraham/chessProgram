package ChessGame.View;

import ChessGame.GameManager;
import ChessGame.Pieces.Piece;
import ChessGame.Pieces.PieceColor;
import ChessGame.Vector;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class RemovedPiecesContainer extends Renderable{
    PieceColor pieceColor;
    ArrayList<PieceRenderable> pieceRenderables = new ArrayList<>();
    GameManager gameManager;
    boolean active = false;

    public RemovedPiecesContainer(GameManager gameManager, Vector position, Vector size, PieceColor pieceColor) {
        this.gameManager = gameManager;
        this.position = position;
        this.size = size;
        this.pieceColor = pieceColor;
    }

    @Override
    public void draw(DrawInfo drawInfo) {
        update();
        if(active) {
            drawInfo.g2d.setColor(new Color(213, 176, 21, 255));
            drawInfo.g2d.setStroke(new BasicStroke(3));
            drawInfo.g2d.draw(new RoundRectangle2D.Float(position.x, position.y, size.x, size.y, 10, 10));
        }
        for (Renderable r: pieceRenderables) {
            r.draw(drawInfo);
        }
    }

    private void update() {
        Piece selected = gameManager.getController().getSelectedPiece();
        if(selected != null && selected.getPieceColor()==pieceColor && gameManager.getController().isTradePawn())
            active = true;
        else
            active = false;
        pieceRenderables.clear();
        int i=0;
        Vector pieceSize = new Vector(size.x,size.x);
        for (Piece p: gameManager.getBoard().getRemovedPeaces()) {
            if(p.getPieceColor()==pieceColor) {
                pieceRenderables.add(new PieceRenderable(p, new Vector(position.x, position.y + i * pieceSize.x), pieceSize));
                i++;
            }
        }
    }

    public ArrayList<PieceRenderable> getPieceRenderables() {
        return pieceRenderables;
    }
}
