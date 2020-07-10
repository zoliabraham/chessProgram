package ChessGame;

import ChessGame.Pieces.DummyPiece;
import ChessGame.Pieces.Piece;
import ChessGame.Pieces.PieceColor;

import java.util.ArrayList;

public class Field {
    Vector position;
    Piece piece;
    String name;
    boolean markedPossible = false;
    Board board;
    boolean markMoved = false;

    public Field(String name, Vector position, Board board) {
        this.name = name;
        this.position = position;
        this.board = board;
    }

    public Field(Field oldField,Board newBoard) {
        this.name = oldField.name;
        this.position = oldField.position;
        this.board = newBoard;
        boolean markedPassible = oldField.markedPossible;
        //this.piece = oldField.piece.copy(newBoard);

    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public String getName() {
        return name;
    }

    public void markPossible(boolean b) {
        markedPossible = b;
    }

    public boolean isMarkedPossible() {
        return markedPossible;
    }

    public boolean isMarkMoved() {
        return markMoved;
    }

    public void setMarkMoved(boolean markMoved) {
        this.markMoved = markMoved;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public boolean isEnemy(PieceColor color) {
        if (isEmpty()) {
            return false;
        }
        return piece.getPieceColor() != color;
    }

    public Vector getPosition() {
        return position;
    }

    public void swapPiece() {
        System.out.println("swap");

    }

    public boolean isAttacked(PieceColor sameTeamColor) {
        placeDummy();
        for (Field f : board.getFields()) {
            Piece piece = f.getPiece();
            if (piece != null && piece.getPieceColor() != sameTeamColor && !piece.getName().equals("king")) {
                for (Field f2 : piece.getPossibleMoves()) {
                    if (f2.equals(this)) {
                        removeDummy();
                        return true;
                    }
                }
            }

        }
        removeDummy();
        return false;
    }

    public boolean isDefended(PieceColor sameTeamColor) {
        for (Field f : board.getFields()) {
            Piece piece = f.getPiece();
            if (piece != null && piece.getPieceColor() == sameTeamColor && !piece.getName().equals("king")) {
                for (Field f2 : piece.getPossibleMovesIfDefend()) {
                    if (f2.equals(this)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public ArrayList<Piece> getAtackingPieces(PieceColor sameTeamColor) {
        ArrayList<Piece> atackingPieces = new ArrayList<>();
        placeDummy();
        for (Field f : board.getFields()) {
            Piece piece = f.getPiece();
            if (piece != null && piece.getPieceColor() != sameTeamColor && !piece.getName().equals("king")) {
                for (Field f2 : piece.getPossibleMoves()) {
                    if (f2.equals(this)) {
                        atackingPieces.add(piece);
                        break;
                    }
                }
            }

        }
        removeDummy();
        return atackingPieces;
    }

    public ArrayList<Piece> getDefendingPieces(PieceColor sameTeamColor) {
        ArrayList<Piece> defendPieces = new ArrayList<>();
        for (Field f : board.getFields()) {
            Piece piece = f.getPiece();
            if (piece != null && piece.getPieceColor() == sameTeamColor && !piece.getName().equals("king")) {
                for (Field f2 : piece.getPossibleMovesIfDefend()) {
                    if (f2.equals(this)) {
                        defendPieces.add(piece);
                        break;
                    }
                }
            }
        }
        return defendPieces;
    }

    public void placeDummy() {
        if (getPiece() == null)
            setPiece(new DummyPiece("dummy", new Vector(0, 1), board));
    }

    void removeDummy() {
        if (getPiece().getName().equals("dummy")) {
            setPiece(null);
        }
    }

    public void removeCurrentPiece() {
        board.addRemovedPiece(getPiece());
        getPiece().removeFromBoard();
        setPiece(null);
    }
}
