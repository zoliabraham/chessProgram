package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class King extends Piece {
    public King(PieceColor pieceColor, Vector forward, Board board) {
        super("king",10000, forward,board);
        name = "king";
        this.pieceColor=pieceColor;
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {

        ArrayList<Field> possibleMoves = new ArrayList<>();
        ArrayList<Vector> vectors = getMoveVectors();

        for (Vector v: vectors) {
            Field target = getFieldInDirection(v);
            if(target!=null && !target.isDefended(pieceColor.reverse())){
                if(target.isEmpty() || target.isEnemy(pieceColor)){
                    possibleMoves.add(target);
                }
            }
        }

        //castling
        possibleMoves.addAll(getCastling());


        return possibleMoves;
    }

    private ArrayList<Field> getCastling() { // ki lehet e vele menni sakkból?
        ArrayList<Field> possibleMoves = new ArrayList<>();
        ArrayList<Vector> castlingVectors = new ArrayList<>();
        castlingVectors.add(new Vector(-2,0));
        castlingVectors.add(new Vector(2,0));
        if(canCastleLeft(castlingVectors)){
            possibleMoves.add(getFieldInDirection(castlingVectors.get(0).reverseY()));
        }
        if(canCastleRight(castlingVectors)){
            possibleMoves.add(getFieldInDirection(castlingVectors.get(1).reverseY()));
        }
        return possibleMoves;
    }

    private boolean canCastleLeft(ArrayList<Vector> castlingVectors) {
        Field castleField = getFieldInDirection(new Vector(-4,0));
        if(castleField!=null) {
            Piece rook = castleField.getPiece();
            if (!isInCheck() && isFirstStep() && rook != null && rook.isFirstStep()) {
                ArrayList<Field> fields = getPossibleFieldsInDirectionAtack(castlingVectors.get(0).getDirection()); //left
                if (fields.size() == 3) {
                    for (int i = 0; i < 2; i++) {
                        if (fields.get(i).isAttacked(pieceColor)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canCastleRight(ArrayList<Vector> castlingVectors) {
        Field castleField = getFieldInDirection(new Vector(3,0));
        if(castleField!=null) {
            Piece rook = castleField.getPiece();
            if (!isInCheck() && isFirstStep() && rook != null && rook.isFirstStep()) {
                ArrayList<Field> fields = getPossibleFieldsInDirectionAtack(castlingVectors.get(1).getDirection());
                if (fields.size() == 2) {
                    for (int i = 0; i < 1; i++) {
                        if (fields.get(i).isAttacked(pieceColor)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
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

    boolean isInCheck(){
        return getField().isAttacked(pieceColor);
    }

    @Override
    public boolean isMovePossible(Field field) {
        return false;
    }

    @Override
    public void moveToField(Field field) {
        Field oldField = getField();
        int distance = Math.abs(oldField.getPosition().x - field.getPosition().x);
        super.moveToField(field);
        if(distance>1){
            if(oldField.getPosition().x-field.getPosition().x>0){
                getFieldInDirection(new Vector(-2,0)).getPiece().moveToField(getFieldInDirection(new Vector(1,0)));
            }
            else {
                getFieldInDirection(new Vector(1,0)).getPiece().moveToField(getFieldInDirection(new Vector(-1,0)));
            }
        }
    }

    @Override
    public ArrayList<Field> getPossibleMovesIfDefend() {
        ArrayList<Field> possibleMoves = new ArrayList<>();
        for (Vector direction: getMoveVectors()) {
            Field friendField = getFieldInDirection(direction);
            if(friendField!=null && friendField.isAttacked(pieceColor)){
                possibleMoves.add(friendField);
            }
        }
        return possibleMoves;
    }

    @Override
    public Piece copy(Board newBoard) {
        King newKing = new King(this.pieceColor,this.forward,newBoard);
        newKing.pieceColor = pieceColor;
        newKing.value = value;
        newKing.field = newBoard.getField(board.getFields().indexOf(getField()));
        newKing.id = id;
        newKing.x = x;
        newKing.y = y;
        newKing.firstStep = firstStep;
        return newKing;
    }

    @Override
    public void removeFromBoard() {
        board.setGameOver(true);
    }
}
