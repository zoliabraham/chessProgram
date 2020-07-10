package ChessGame;

import ChessGame.AILogic.Algorithm;
import ChessGame.AILogic.BoardValueEvaluator;
import ChessGame.AILogic.Step;
import ChessGame.Pieces.*;
import ChessGame.View.Window;

import java.util.ArrayList;

public class Controller {
    final boolean player1bot = false;
    final boolean player2bot = true;

    GameManager gameManager;
    Piece selectedPiece;
    ArrayList<Field> possibleMoves;
    Piece pawnToPromote;
    PieceColor player1Color = PieceColor.White;
    PieceColor player2Color = PieceColor.Black;
    PieceColor activeColor = PieceColor.White;
    Window window;
    boolean gameEnded = false;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void calculatePossibleMoves() {
        if(selectedPiece!=null) {
            possibleMoves = selectedPiece.getPossibleMoves();
            for (Field f : possibleMoves) {
                f.markPossible(true);
            }
        }
    }

    public void selectField(Field field){
        clearMarkedMoveds();
        if(possibleMoves.contains(field)) {
            moveToField(field);
        }
        else if(field.equals(selectedPiece.getField())){ //ha újra rákattint a bábura, tűnjön el a kijelölés
            setSelectedPiece(null);
            clearPossibleMoves();
        }
    }

    private void clearMarkedMoveds() {
        for (Field f: gameManager.getBoard().getFields()) {
            f.setMarkMoved(false);
        }
    }

    //TODO ha a király sakkban van

    private void moveToField(Field field) {
        clearMarkedMoveds();
        selectedPiece.getField().setMarkMoved(true);
        selectedPiece.moveToField(field);
        selectedPiece.getField().setMarkMoved(true);
        setSelectedPiece(null);
        clearPossibleMoves();
        if(gameManager.getBoard().isGameOver()){
            endGame();
        }
        else if(gameManager.getBoard().isCheck()){
            showCheck(true);
        } else {
            showCheck(false);
        }
        window.paintImmediately(0,0,window.getWidth(),window.getHeight());
        System.out.println("BoardValue = " + BoardValueEvaluator.evaluate(gameManager.getBoard(),PieceColor.White));
        if(!gameManager.getBoard().mustPromotePawn())
            switchActivePlayer();
        else {
            if(isBot(activeColor)){
                findPawnToPromote();
                swapToQueen();
            }
            else {
                preparePromotePawn();
            }
        }
    }



    private void preparePromotePawn() {
        findPawnToPromote();
        window.showPromoteWindow();
    }

    private void findPawnToPromote() {
        for (Field f: gameManager.getBoard().getFields()) {
            Piece p = f.getPiece();
            if(p!=null && p.getName().equals("pawn") && (f.getPosition().y==0 || f.getPosition().y==7)){
                pawnToPromote = p;
            }
        }
    }

    public void promotePawn(Piece promoteTo) {
        Field promoteOn = pawnToPromote.getField();
        promoteTo.placeToField(promoteOn);
        gameManager.getBoard().setCanTradePawn(false);

        window.hidePromoteWindow();
        window.paintImmediately(0,0,window.getWidth(),window.getHeight());
        switchActivePlayer();
    }

    public void swapToQueen() {
        promotePawn(new Queen(pawnToPromote.getPieceColor(),pawnToPromote.getForward(),gameManager.getBoard()));
    }

    public void swapToRook() {
        promotePawn(new Rook(pawnToPromote.getPieceColor(),pawnToPromote.getForward(),gameManager.getBoard()));
    }

    public void swapToBishop() {
        promotePawn(new Bishop(pawnToPromote.getPieceColor(),pawnToPromote.getForward(),gameManager.getBoard()));
    }

    public void swapToKnight() {
        promotePawn(new Knight(pawnToPromote.getPieceColor(),pawnToPromote.getForward(),gameManager.getBoard()));
    }

    public void setSelectedPiece(Piece selectedPiece){
        if(selectedPiece==null){
            this.selectedPiece = selectedPiece;
        }
        else if(selectedPiece.getPieceColor()==activeColor) {
            this.selectedPiece = selectedPiece;
        }
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void clearPossibleMoves() {
        for (Field f: gameManager.getBoard().getFields()) {
            f.markPossible(false);
        }
    }


    public void switchActivePlayer(){
        activeColor=activeColor.reverse();
        if(activeColor== player1Color){
            firstPlayerMove();
        }
        else {
            secondPlayerMove();
        }

    }

    void firstPlayerMove() {
        if(player1bot){
            /*Algorithm algorithm = new Algorithm(player1Color,gameManager,this);
            int maxVal = algorithm.minimax(gameManager.getBoard(),5 ,Integer.MIN_VALUE , Integer.MAX_VALUE ,true);
            System.out.println("maxval = " + maxVal);*/

            Algorithm algorithm = new Algorithm(player1Color,gameManager,this);
            Step step = algorithm.minimax(gameManager.getBoard(), 4 ,Integer.MIN_VALUE , Integer.MAX_VALUE ,true, new Step());
            System.out.println("maxval = " + step.getValue());

            setSelectedPiece(gameManager.getBoard().getField(step.getPositionID()).getPiece());
            moveToField(gameManager.getBoard().getField(step.getTargetID()));
        }
    }

    void secondPlayerMove() {
        if(player2bot){
            Algorithm algorithm = new Algorithm(player2Color,gameManager,this);
            Step step  = algorithm.minimax(gameManager.getBoard(),4,Integer.MIN_VALUE,Integer.MAX_VALUE,true,new Step());

            System.out.println("maxval = " + step.getValue());
            setSelectedPiece(gameManager.getBoard().getField(step.getPositionID()).getPiece());
            moveToField(gameManager.getBoard().getField(step.getTargetID()));
        }
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    private void endGame() {
        gameEnded = true;
        window.showMessage("Matt : " + gameManager.getBoard().getWinColor().getValue() + " nyert");
        window.repaint();
    }

    private void showCheck(boolean b) {
        if(b)
            window.showMessage("Sakk");
        else
            window.hideMessage();
        window.repaint();
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    private boolean isBot(PieceColor activeColor) {
        if(player1Color == activeColor && player1bot)
            return true;
        if(player2Color == activeColor && player2bot)
            return true;

        return false;
    }
}
