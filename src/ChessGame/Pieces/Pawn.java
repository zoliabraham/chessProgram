package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Controller;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(PieceColor pieceColor, Vector forward, Board board) {
        super("pawn",forward, board);
        name = "pawn";
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
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

        if(getField().getPosition().y==0||getField().getPosition().y==7){
            Controller.tradePawn = true;
        }

        return possibleMoves;
    }

    @Override
    public boolean isMovePossible(Field field) {
        return false;
    }
}
