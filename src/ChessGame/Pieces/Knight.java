package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(PieceColor pieceColor, Vector forward, Board board) {
        super("knight",forward,board);
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        ArrayList<Field> possibleMoves = new ArrayList<>();
        ArrayList<Vector> vectors = getMoveVectors();

        for (Vector v: vectors) {
            Field target = getFieldInDirection(v);
            if(target!=null){
                if(target.isEmpty() || target.isEnemy(pieceColor)){
                    possibleMoves.add(target);
                }
            }
        }
        return possibleMoves;
    }

    private ArrayList<Vector> getMoveVectors() {
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(2,1));
        vectors.add(new Vector(2,-1));
        vectors.add(new Vector(-2,1));
        vectors.add(new Vector(-2,-1));
        vectors.add(new Vector(1,2));
        vectors.add(new Vector(-1,2));
        vectors.add(new Vector(1,-2));
        vectors.add(new Vector(-1,-2));
        return vectors;
    }

    @Override
    public boolean isMovePossible(Field field) {
        return false;
    }
}
