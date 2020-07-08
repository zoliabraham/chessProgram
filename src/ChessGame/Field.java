package ChessGame;

import ChessGame.Pieces.DummyPiece;
import ChessGame.Pieces.Piece;
import ChessGame.Pieces.PieceColor;

public class Field {
    Vector position;
    Piece piece;
    String name;
    boolean markedPassible = false;
    Board board;

    public Field(String name, Vector position, Board board) {
        this.name = name;
        this.position = position;
        this.board = board;
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
        markedPassible = b;
    }

    public boolean isMarkedPassible() {
        return markedPassible;
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

    public void placeDummy(){
        if(getPiece()==null)
            setPiece(new DummyPiece("dummy",new Vector(0,1),board));
    }

    void removeDummy(){
        if(getPiece().getName().equals("dummy")) {
            setPiece(null);
        }
    }

    public void removeCurrentPiece() {
        board.addRemovedPiece(getPiece());
        setPiece(null);
    }
}
