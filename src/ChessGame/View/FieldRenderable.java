package ChessGame.View;

import ChessGame.Field;
import ChessGame.Pieces.PieceColor;
import ChessGame.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class FieldRenderable extends Renderable{
    Field field;
    Color color;
    ArrayList<Renderable> renderables = new ArrayList<>();

    public FieldRenderable(Field field, Vector position, Vector size, PieceColor color) {
        this.field = field;
        this.position = position;
        this.size = size;
        if (color==PieceColor.Black)
            this.color = new Color(57, 57, 57, 255);
        else
            this.color = new Color(187, 187, 187, 255);
    }

    @Override
    public void draw(DrawInfo drawInfo) {
        update();
        drawInfo.g2d.setColor(color);
        drawInfo.g2d.fill(new Rectangle2D.Float(position.x,position.y,size.x,size.y));
        float strokeWidth=4;

        if(field.isMarkedPossible()){
            if(field.getPiece()!=null){
                drawInfo.g2d.setColor(new Color(196, 0, 0, 255));
                drawInfo.g2d.setStroke(new BasicStroke(strokeWidth));
            }
            else{
                drawInfo.g2d.setColor(new Color(255, 208, 3, 255));
                drawInfo.g2d.setStroke(new BasicStroke(4));
            }
            drawInfo.g2d.draw(new Rectangle2D.Float(position.x+strokeWidth/2,position.y+strokeWidth/2,size.x - strokeWidth,size.y- strokeWidth));
        }
        else if(field.isMarkMoved()){
            drawInfo.g2d.setColor(new Color(42, 189, 61, 255));
            drawInfo.g2d.setStroke(new BasicStroke(4));
            drawInfo.g2d.draw(new Rectangle2D.Float(position.x+strokeWidth/2,position.y+strokeWidth/2,size.x - strokeWidth,size.y- strokeWidth));
        }
        renderContainedrenderables(drawInfo);
    }

    void update(){
        renderables.clear();
        if(field.getPiece()!=null){
            renderables.add(new PieceRenderable(field.getPiece(),position,size));
        }
    }

    private void renderContainedrenderables(DrawInfo drawInfo) {
        if (renderables != null) {
            for (Renderable r : renderables) {
                r.draw(drawInfo);
            }
        }
    }

    public Field getField() {
        return field;
    }
}
