package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(PieceColor pieceColor, Vector forward, Board board) {
        super("pawn",1,forward, board);
        name = "pawn";
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        if(possibleStepsCache!=null){
            return possibleStepsCache;
        }

        ArrayList<Field> possibleMoves = new ArrayList<>();

        //forward
        Field target = getFieldInDirection(new Vector(0, 1));
        if (target != null && target.isEmpty()) {
            possibleMoves.add(target);

            //doubleForward
            if (firstStep) {
                target = getFieldInDirection(new Vector(0, 2));
                if (target != null && target.isEmpty())
                    possibleMoves.add(target);
            }
        }
        //hitLeft
        target = getFieldInDirection(new Vector(-1, 1));
        if (target != null && !target.isEmpty() && target.getPiece().getPieceColor()!=pieceColor)
            possibleMoves.add(target);

        //hitRight
        target = getFieldInDirection(new Vector(1, 1));
        if (target != null && !target.isEmpty() && target.getPiece().getPieceColor()!=pieceColor)
            possibleMoves.add(target);

        addAllPossibleStepsToCache(possibleMoves);
        return possibleMoves;
    }

    @Override
    public boolean isMovePossible(Field field) {
        return false;
    }

    @Override
    public ArrayList<Field> getPossibleMovesIfDefend() {
        ArrayList<Field> defendSteps = new ArrayList<>();
        Field target = getFieldInDirection(new Vector(-1, 1));
        if(target!=null){
            defendSteps.add(target);
        }
        target = getFieldInDirection(new Vector(1, 1));
        if(target!=null){
            defendSteps.add(target);
        }
        return defendSteps;
    }

    @Override
    public Piece copy(Board newBoard) {
        Pawn newPawn = new Pawn(this.pieceColor,this.forward,newBoard);
        newPawn.pieceColor = pieceColor;
        newPawn.value = value;
        newPawn.field = newBoard.getField(board.getFields().indexOf(getField()));
        newPawn.id = id;
        newPawn.x = x;
        newPawn.y = y;
        newPawn.firstStep = firstStep;
        return newPawn;
    }

    @Override
    public float getValue() {
        if(getField().getPosition().y==0 || getField().getPosition().y==0) {
            return 9;
        }

        int startY = 0;
        if(forward.y == 1)
            startY = 6;
        else
            startY = 1;

        int value = 1;
        if(Float.compare(Math.abs(startY - getField().getPosition().y),0.f)==0){
            value += Math.abs(startY - getField().getPosition().y)*0.2f;
        }
        return value;
    }

    @Override
    public void moveToField(Field field) {
        super.moveToField(field);
        if(getField().getPosition().y==0||getField().getPosition().y==7){
            board.setCanTradePawn(true);
        }
    }
}
