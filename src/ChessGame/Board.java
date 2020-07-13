package ChessGame;

import ChessGame.AILogic.Step;
import ChessGame.Pieces.*;

import java.util.ArrayList;

public class Board {
    boolean canTradePawn = false;
    boolean gameOver = false;
    boolean check = false;
    PieceColor winColor;
    ArrayList<Field> fields;
    ArrayList<Piece> removedPeaces = new ArrayList<>();

    public Board() {
        fields = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            fields.add(new Field(Integer.toString(i/8) + Integer.toString(i%8), new Vector((int)i%8,(int)i/8),this));
        }
        placeStartPieces();
    }

    public Board(Board oldBoard){
        this.gameOver = oldBoard.gameOver;
        this.fields = new ArrayList<>();
        this.check = oldBoard.check;
        for (Field field: oldBoard.fields) {
            Field newField = new Field(field,this);
            fields.add(newField);
            Piece tocopy = field.getPiece();
            if(tocopy!=null)
                newField.setPiece(tocopy.copy(this));

        }
    }


    public Field getField(int id){
        return fields.get(id);
    }

    private void placeStartPieces() {
        placePawns();
        placeKings();
        placeQueenns();
        placeBishops();
        placeKnights();
        placeRooks();
    }

    private void placeRooks() {
        Rook rookBlack1 = new Rook(PieceColor.Black,new Vector(0,-1),this);
        Rook rookBlack2 = new Rook(PieceColor.Black,new Vector(0,-1),this);
        Rook rookWhite1 = new Rook(PieceColor.White,new Vector(0,1),this);
        Rook rookWhite2 = new Rook(PieceColor.White,new Vector(0,1),this);
        rookBlack1.placeToField(fields.get(0));
        rookBlack2.placeToField(fields.get(7));
        rookWhite1.placeToField(fields.get(56));
        rookWhite2.placeToField(fields.get(63));
    }

    private void placeKnights() {
        Knight kinghtBlack1 = new Knight(PieceColor.Black,new Vector(0,-1),this);
        Knight kinghtBlack2 = new Knight(PieceColor.Black,new Vector(0,-1),this);
        Knight kinghtWhite1 = new Knight(PieceColor.White,new Vector(0,1),this);
        Knight kinghtWhite2 = new Knight(PieceColor.White,new Vector(0,1),this);
        kinghtBlack1.placeToField(fields.get(1));
        kinghtBlack2.placeToField(fields.get(6));
        kinghtWhite1.placeToField(fields.get(57));
        kinghtWhite2.placeToField(fields.get(62));
    }

    private void placeBishops() {
        Bishop bishopBlack1 = new Bishop(PieceColor.Black,new Vector(0,-1),this);
        Bishop bishopBlack2 = new Bishop(PieceColor.Black,new Vector(0,-1),this);
        Bishop bishopWhite1 = new Bishop(PieceColor.White,new Vector(0,1),this);
        Bishop bishopWhite2 = new Bishop(PieceColor.White,new Vector(0,1),this);
        bishopBlack1.placeToField(fields.get(2));
        bishopBlack2.placeToField(fields.get(5));
        bishopWhite1.placeToField(fields.get(58));
        bishopWhite2.placeToField(fields.get(61));
    }

    private void placeQueenns() {
        Queen queenBlack = new Queen(PieceColor.Black,new Vector(0,-1),this);
        Queen queenWhite = new Queen(PieceColor.White,new Vector(0,1),this);
        queenBlack.placeToField(fields.get(3));
        queenWhite.placeToField(fields.get(59));
    }


    private void placeKings() {
        King kingBlack = new King(PieceColor.Black,new Vector(0,-1),this);
        King kingWhite = new King(PieceColor.White,new Vector(0,1),this);
        kingBlack.placeToField(fields.get(4));
        kingWhite.placeToField(fields.get(60));
    }

    private void placePawns() {
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(PieceColor.Black,new Vector(0,-1),this);
            fields.get(8+i).setPiece(pawn);
            pawn.placeToField(fields.get(8+i));
        }

        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(PieceColor.White,new Vector(0,1),this);
            //fields.get(48+i).setPiece(pawn);
            pawn.placeToField(fields.get(48+i));
        }
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void addRemovedPiece(Piece piece){
        removedPeaces.add(piece);
    }

    public void removeFromRemovedPieces(Piece piece){
        removedPeaces.remove(piece);
    }

    public ArrayList<Piece> getRemovedPeaces() {
        return removedPeaces;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Board simulateStep(Step step) {
        Board newBoard = new Board(this);
        newBoard.getFields().get(step.getPositionID()).getPiece().moveToField(newBoard.getFields().get(step.getTargetID()));
        return newBoard;
    }

    public boolean mustPromotePawn() {
        return canTradePawn;
    }

    public void setCanTradePawn(boolean canTradePawn) {
        this.canTradePawn = canTradePawn;
    }

    public void setCheck(boolean check, PieceColor checkGiver) {
        this.check = check;
        this.winColor = checkGiver;
    }

    public PieceColor getWinColor() {
        return winColor;
    }

    public boolean isCheck() {
        return check;
    }


    public void clearAllPossibleStepsCache(){
        for (Field f: fields) {
            Piece piece = f.getPiece();
            if(piece!=null){
                piece.clearPossibleStepsCache();
            }
        }
    }

    public void updatePossibleStepsCache(){
        for (Field f: fields) {
            Piece piece = f.getPiece();
            if(piece!=null){
                piece.updatePossibleStepsToCache();
            }
        }
    }
}
