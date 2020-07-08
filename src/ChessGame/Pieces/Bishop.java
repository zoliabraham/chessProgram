package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(PieceColor pieceColor, Vector forward, Board board) {
        super("bishop",forward,board);
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        ArrayList<Vector> vectors = getMoveVectors();

        return getPossibleFieldsInDirections(vectors);
    }

    private ArrayList<Vector> getMoveVectors() {
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(1,1));
        vectors.add(new Vector(-1,1));
        vectors.add(new Vector(-1,-1));
        vectors.add(new Vector(1,-1));
        return vectors;
    }

    @Override
    public boolean isMovePossible(Field field) {
        return false;
    }
}
