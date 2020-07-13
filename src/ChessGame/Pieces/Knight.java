package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(PieceColor pieceColor, Vector forward, Board board) {
        super("knight",3,forward,board);
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        if(possibleStepsCache!=null){
            return possibleStepsCache;
        }
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
        addAllPossibleStepsToCache(possibleMoves);
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

    @Override
    public ArrayList<Field> getPossibleMovesIfDefend() {
        ArrayList<Field> possibleMoves = new ArrayList<>();
        for (Vector v: getMoveVectors()) {
            Field target = getFieldInDirection(v);
            if(target!=null)
                possibleMoves.add(target);
        }
        return possibleMoves;
    }

    @Override
    public Piece copy(Board newBoard) {
        Knight newKnight = new Knight(this.pieceColor,this.forward,newBoard);
        newKnight.pieceColor = pieceColor;
        newKnight.value = value;
        newKnight.field = newBoard.getField(board.getFields().indexOf(getField()));
        newKnight.id = id;
        newKnight.x = x;
        newKnight.y = y;
        newKnight.firstStep = firstStep;
        return newKnight;
    }

    @Override
    public float getValue() {
        return super.getValue() + getPossibleMoves().size()/4.f;
    }
}
