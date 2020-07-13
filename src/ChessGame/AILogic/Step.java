package ChessGame.AILogic;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Pieces.Piece;

public class Step {
    Piece piece;
    Field target;
    int targetID;
    Field position;
    int positionID;
    float maxValue = Integer.MIN_VALUE;

    public Step() {
    }

    public Step(Piece piece, Field target, Field position, Board board) {
        this.piece = piece;
        this.target = target;
        this.position = position;
        positionID = board.getFields().indexOf(position);
        targetID = board.getFields().indexOf(target);
    }

    public float getValue() {
        return maxValue;
    }

    public void setValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public Piece getPiece() {
        return piece;
    }

    public Field getTarget() {
        return target;
    }

    public int getTargetID() {
        return targetID;
    }

    public Field getPosition() {
        return position;
    }

    public int getPositionID() {
        return positionID;
    }

    public void swapMoveData(Step step){
        this.piece = step.piece;
        this.target = step.target;
        this.position = step.position;
        this.positionID = step.positionID;
        this.targetID = step.targetID;
    }
}
