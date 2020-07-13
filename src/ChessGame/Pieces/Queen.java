package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(PieceColor pieceColor, Vector forward, Board board) {
        super("queen",11,forward,board);
        name = "queen";
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
        Queen newQueen = new Queen(this.pieceColor,this.forward,newBoard);
        newQueen.pieceColor = pieceColor;
        newQueen.value = value;
        newQueen.field = newBoard.getField(board.getFields().indexOf(getField()));
        newQueen.id = id;
        newQueen.x = x;
        newQueen.y = y;
        newQueen.firstStep = firstStep;
        return newQueen;
    }

    @Override
    public float getValue() {
        float value = super.getValue();
        value = value - Math.abs(getField().getPosition().x-3.5f)*0.3f - Math.abs(getField().getPosition().y-3.5f)*0.3f + getPossibleMoves().size()*0.1f;
        return value;
    }
}
