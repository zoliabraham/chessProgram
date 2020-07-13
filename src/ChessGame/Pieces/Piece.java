package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public abstract class Piece {
    float value;
    protected PieceColor pieceColor;
    protected Field field;
    protected String name;
    protected Vector forward;
    int id;
    int x ;
    int y ;
    Board board;
    boolean firstStep = true;
    ArrayList<Field> possibleStepsCache;

    public Piece(String name,int value, Vector forward, Board board) {
        this.name = name;
        this.value = value;
        this.forward = forward;
        this.board = board;
    }

    public abstract ArrayList<Field> getPossibleMoves();
    public abstract boolean isMovePossible(Field field);


    public void removeFromBoard(){
        this.field=null;
    }

    public void moveToField(Field field){
        //jelenlegi nulllázása
        if(this.field!=null){
            this.field.setPiece(null);
        }
        this.field = field;
        Piece previusPiece = field.getPiece(); //jelenlegi eltávolítása
        if(previusPiece!=null){
            field.removeCurrentPiece();
        }
        field.setPiece(this);
        id = board.getFields().indexOf(field);
        x = id%8;
        y = id/8;
        firstStep = false;
        checkForCheckAndCheckMate();
        board.updatePossibleStepsCache();
    }

    protected Piece checkForCheckAndCheckMate(){
        for (Field f: board.getFields()) {
            Piece p = f.getPiece();
            if(p!=null && p.getName().equals("king")){
                /*if(f.isAttacked(p.getPieceColor())){
                    board.setCheck(true,p.pieceColor.reverse());
                    return p;
                }*/
            }
        }
        board.setCheck(false,null);
        return null;
    }


    public void placeToField(Field field){
        if(this.field!=null){
            this.field.setPiece(null);
        }
        this.field = field;
        field.setPiece(this);
        id = board.getFields().indexOf(field);
        x = id%8;
        y = id/8;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public Field getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    Field getFieldInDirection(Vector targetDirection){
        if(forward.isEqual(new Vector(0,1))){
            targetDirection = targetDirection.reverseY();
        }
        int targetX = x + targetDirection.x;
        int targetY = y + targetDirection.y;
        if(targetX<0 || targetX>7 || targetY<0 || targetY>7)
            return null;

        int targetID = targetY*8 + targetX;
        return board.getField(targetID);
    }

    ArrayList<Field> getPossibleFieldsInDirections(ArrayList<Vector> vectors) {
        ArrayList<Field> possibleMoves = new ArrayList<>();
        for (Vector direction: vectors) {
            possibleMoves.addAll(getPossibleFieldsInDirectionAtack(direction));
        }
        return possibleMoves;
    }

    ArrayList<Field> getPossibleFieldsInDirectionAtack(Vector direction) {
        ArrayList<Field> possibleMoves = new ArrayList<>();
        int i=2;
        Field target = getFieldInDirection(direction.muliply(1));
        boolean canContinue= target!=null && target.isEmpty();
        while (canContinue){
            possibleMoves.add(target);

            Vector d = direction.muliply(i);
            target = getFieldInDirection(d);
            canContinue = target!=null && target.isEmpty();
            i++;
        }
        if(target != null && target.isEnemy(pieceColor))
            possibleMoves.add(target);

        return possibleMoves;
    }

    ArrayList<Field> getPossibleFieldsInDirectionDefend(Vector direction) {
        ArrayList<Field> possibleMoves = new ArrayList<>();
        int i=2;
        Field target = getFieldInDirection(direction.muliply(1));
        boolean canContinue= target!=null && target.isEmpty();
        while (canContinue){
            possibleMoves.add(target);

            Vector d = direction.muliply(i);
            target = getFieldInDirection(d);
            canContinue = target!=null && target.isEmpty();
            i++;
        }
        if(target != null && !target.isEnemy(pieceColor))
            possibleMoves.add(target);

        return possibleMoves;
    }

    public float getValue() {
        return value;
    }

    public boolean isFirstStep() {
        return firstStep;
    }

    public abstract ArrayList<Field> getPossibleMovesIfDefend();

    public abstract Piece copy(Board newBoard);

    public Vector getForward() {
        return forward;
    }

    public ArrayList<Field> getPossibleStepsCache() {
        return possibleStepsCache;
    }

    public void clearPossibleStepsCache(){
        if(possibleStepsCache!=null){
            possibleStepsCache.clear();
        }
    }

    public void addAllPossibleStepsToCache(ArrayList<Field> possibleSteps){
        if(possibleStepsCache==null) {
            possibleStepsCache = new ArrayList<>();
        }
        else{
            possibleStepsCache.clear();
        }
        possibleStepsCache.addAll(possibleSteps);
    }

    public void updatePossibleStepsToCache(){
        possibleStepsCache = null;
        getPossibleMoves();
    }

}
