package ChessGame.View;

import ChessGame.GameManager;
import ChessGame.Pieces.PieceColor;
import ChessGame.Vector;

import java.util.ArrayList;

public class Renderer {
    ArrayList<Renderable> renderables = new ArrayList<>();
    ArrayList<FieldRenderable> fieldRenderables;
    ArrayList<RemovedPiecesContainer> removedPiecesContainers;
    public Renderer(GameManager gameManager, Window window) {
        createBoardRenderable(window,gameManager);
        createRemovedPanels(window,gameManager);
    }

    void render(DrawInfo drawInfo) {
        for (Renderable n : renderables) {
            n.draw(drawInfo);
        }
    }

    private void createRemovedPanels(Window window, GameManager gameManager) {
        removedPiecesContainers = new ArrayList<>();
        int panelWidth = window.getHeight()/16;
        int margin=2;
        RemovedPiecesContainer removedPiecesContainerLeft = new RemovedPiecesContainer(gameManager,new Vector(margin,margin),new Vector(panelWidth-2*margin,window.getHeight()-2*margin),PieceColor.White);
        RemovedPiecesContainer removedPiecesContainerRight = new RemovedPiecesContainer(gameManager,new Vector(window.getWidth()-panelWidth+margin,margin),new Vector(panelWidth-2*margin,window.getHeight()-2*margin),PieceColor.Black);
        removedPiecesContainers.add(removedPiecesContainerLeft);
        removedPiecesContainers.add(removedPiecesContainerRight);
        renderables.addAll(removedPiecesContainers);
    }

    void createBoardRenderable(Window window,GameManager gameManager){
        fieldRenderables = new ArrayList<>();
        int arraySize = 8;
        int startX; int startY=100;
        int endX; int endY=window.getHeight()-100;
        int fieldSize = Math.abs(endY - startY) / arraySize;
        startX = window.getWidth()/2 - 4*fieldSize;

        PieceColor color = PieceColor.White;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int posX = startX + i*fieldSize;
                int posY = startY + j*fieldSize;
                Vector pos = new Vector( posX,posY);
                Vector size = new Vector(fieldSize,fieldSize);
                FieldRenderable fr = new FieldRenderable(gameManager.getBoard().getField(j*8+i),pos,size,color);
                fieldRenderables.add(fr);

                if(color==PieceColor.White)
                    color = PieceColor.Black;
                else
                    color = PieceColor.White;
            }
            if(color==PieceColor.White)
                color = PieceColor.Black;
            else
                color = PieceColor.White;
        }
        renderables.addAll(fieldRenderables);
    }

    public ArrayList<FieldRenderable> getFieldRenderables() {
        return fieldRenderables;
    }

    public ArrayList<RemovedPiecesContainer> getRemovedPiecesContainers() {
        return removedPiecesContainers;
    }
}
