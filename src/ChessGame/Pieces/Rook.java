package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(PieceColor pieceColor, Vector forward, Board board) {
        super("rook",6,forward,board);
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        if(possibleStepsCache!=null){
            return possibleStepsCache;
        }
        ArrayList<Vector> vectors = getMoveVectors();

        ArrayList<Field> possibleMoves = getPossibleFieldsInDirections(vectors);
        addAllPossibleStepsToCache(possibleMoves);
        return possibleMoves;
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

    @Override
    public ArrayList<Field> getPossibleMovesIfDefend() {
        ArrayList<Field> possibleMoves = new ArrayList<>();
        for (Vector direction: getMoveVectors()) {
            ArrayList<Field> fieldsInDirection = getPossibleFieldsInDirectionDefend(direction);
            Field friendField=null;
            if(fieldsInDirection.size()>0)
                friendField = getFieldInDirection(direction.muliply(fieldsInDirection.size()+1));
            if(friendField!=null){
                fieldsInDirection.add(friendField);
            }
            possibleMoves.addAll(fieldsInDirection);
        }
        return possibleMoves;
    }

    @Override
    public Piece copy(Board newBoard) {
        Rook newRook = new Rook(this.pieceColor,this.forward,newBoard);
        newRook.pieceColor = pieceColor;
        newRook.value = value;
        newRook.field = newBoard.getField(board.getFields().indexOf(getField()));
        newRook.id = id;
        newRook.x = x;
        newRook.y = y;
        newRook.firstStep = firstStep;
        return newRook;
    }

    @Override
    public float getValue() {
        float value = super.getValue();
        value = value - Math.abs(getField().getPosition().x-3.5f)*0.3f + getPossibleMoves().size()*0.2f;
        return value;
    }

}
