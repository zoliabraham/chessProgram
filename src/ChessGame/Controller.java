package ChessGame;

import ChessGame.Pieces.Piece;

import java.util.ArrayList;

public class Controller {
    GameManager gameManager;
    Piece selectedPiece;
    ArrayList<Field> possibleMoves;
    public static boolean tradePawn = false;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    ArrayList<Field> getSelectedMoves(Field field){
        Piece piece = field.getPiece();
        return piece.getPossibleMoves();
    }

    public void calculatePossibleMoves() {
        possibleMoves = selectedPiece.getPossibleMoves();
        for (Field f: possibleMoves) {
            f.markPossible(true);
        }
    }

    public void selectField(Field field){
        if(possibleMoves.contains(field)) {
            selectedPiece.moveToField(field);
            setSelectedPiece(null);
            clearPossibleMoves();
        }
        else if(field.equals(selectedPiece.getField())){
            setSelectedPiece(null);
            clearPossibleMoves();
        }
    }

    public void tradeSelected(Piece piece){
        System.out.println("Trade: " + getSelectedPiece().getName() + " to " + piece);
        Piece selectedPiece = getSelectedPiece();
        piece.moveToField(selectedPiece.getField());
        gameManager.getBoard().removeFromRemovedPieces(piece);
        Controller.tradePawn=false;
        setSelectedPiece(null);
    }

    public void setSelectedPiece(Piece selectedPiece){
        this.selectedPiece = selectedPiece;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void clearPossibleMoves() {
        for (Field f: gameManager.getBoard().getFields()) {
            f.markPossible(false);
        }
    }

    public boolean isTradePawn() {
        return tradePawn;
    }
}
