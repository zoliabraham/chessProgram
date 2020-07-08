package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(PieceColor pieceColor, Vector forward, Board board) {
        super("rook",forward,board);
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        ArrayList<Vector> vectors = getMoveVectors();
        return getPossibleFieldsInDirections(vectors);
    }

    private ArrayList<Vector> getMoveVectors() {
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(1,0));
        vectors.add(new Vector(-1,0));
        vectors.add(new Vector(0,1));
        vectors.add(new Vector(0,-1));
        return vectors;
    }

    @Override
    public boolean isMovePossible(Field field) {
        return false;
    }

}
