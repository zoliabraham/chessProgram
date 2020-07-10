package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(PieceColor pieceColor, Vector forward, Board board) {
        super("bishop",3,forward,board);
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        ArrayList<Vector> vectors = getMoveVectors();
        ArrayList<Field> possibleMoves = getPossibleFieldsInDirections(vectors);
        return possibleMoves;
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
        Bishop newBishop = new Bishop(this.pieceColor,this.forward,newBoard);
        newBishop.pieceColor = pieceColor;
        newBishop.value = value;
        newBishop.field = newBoard.getField(board.getFields().indexOf(getField()));
        newBishop.id = id;
        newBishop.x = x;
        newBishop.y = y;
        newBishop.firstStep = firstStep;
        return newBishop;
    }
}
