package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(PieceColor pieceColor, Vector forward, Board board) {
        super("queen",forward,board);
        name = "queen";
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
