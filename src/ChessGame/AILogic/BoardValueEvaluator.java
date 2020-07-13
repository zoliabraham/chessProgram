package ChessGame.AILogic;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Pieces.Piece;
import ChessGame.Pieces.PieceColor;

import java.util.ArrayList;
import java.util.Random;

public class BoardValueEvaluator {
    static int randomFactor = 4;
    static int pieceValueMultiplier=30;
    static int defendOwnPieces=5;
    static int noStepingInDanger=1;
    static int atackEnemy=1;
    static int atackEnemyDefense=1;
    static int checkDefendImportance=1;
    static int checkAttackImportance=1;



    public static int evaluate(Board board, PieceColor pieceColor) {
        if(board.isGameOver()){
            return checkWin(board, pieceColor);
        }
        int boardValue = 0;
        boardValue += getBoardValues(board, pieceColor);
        Random r = new Random();
        return boardValue + r.nextInt(randomFactor)-randomFactor/2;
    }

    private static int getBoardValues(Board board, PieceColor pieceColor) {
        int pieceValues = 0;
        int positionValues = 0;
        int checkValues = 0;
        int friendPieces=0;
        int enemyPieces=0;
        for (Field f: board.getFields()) {
            Piece p = f.getPiece();
            if(p!=null){
                if(p.getPieceColor()==pieceColor){
                    friendPieces++;
                }
                else
                    enemyPieces++;
            }
        }


        for (Field field : board.getFields()) {
            pieceValues += getPieceValue(pieceColor, field);
            positionValues += getPositionValue(pieceColor, field);
            checkValues += getCheckValue(pieceColor, field, friendPieces, enemyPieces);
        }

        return pieceValues + positionValues + checkValues;
    }

    private static int getPieceValue(PieceColor pieceColor, Field field) {
        int pieceValues = 0;
        Piece piece = field.getPiece();
        if (piece != null) {
            int value = (int) field.getPiece().getValue();
            if (piece.getPieceColor() == pieceColor)
                pieceValues += value * pieceValueMultiplier;
            else
                pieceValues -= value * pieceValueMultiplier;
        }
        return pieceValues;
    }

    private static int getPositionValue(PieceColor pieceColor,Field field) {
        int positionValues=0;
        Piece piece = field.getPiece();
        if(piece!=null){
            int pieceValue = (int)piece.getValue();
            if(piece.getPieceColor()==pieceColor){
                if(field.isAttacked(pieceColor)) { //enemy atacking friends
                    positionValues -= pieceValue * field.getAtackingPieces(pieceColor).size() * defendOwnPieces;
                }

                if(field.isDefended(pieceColor) && !piece.getName().equals("king")) { // defendingFriends
                    positionValues += pieceValue * field.getDefendingPieces(pieceColor).size() * noStepingInDanger;
                }
            }

            if(piece.getPieceColor()!=pieceColor){
                PieceColor reversedColor = pieceColor.reverse();
                if(field.isAttacked(reversedColor)) { //atackingEnemy
                    positionValues += pieceValue * field.getAtackingPieces(reversedColor).size() * atackEnemy;
                }

                if(field.isDefended(reversedColor)  && !piece.getName().equals("king")) { //trying to kill enemy defense
                    positionValues -= pieceValue * field.getDefendingPieces(reversedColor).size() * atackEnemyDefense;
                }
            }
        }
        return positionValues;
    }

    private static int getCheckValue(PieceColor pieceColor, Field field, int friendPieces, int enemyPieces) {
        int checkValue = 0;
        Piece piece = field.getPiece();

        if(piece!=null){
            if(piece.getName().equals("king")){
                ArrayList<Field> possibleMoves = piece.getPossibleMoves();
                if (piece.getPieceColor()==pieceColor){
                    if(field.isAttacked(pieceColor)){ //defend
                        checkValue -= (8-possibleMoves.size())*checkDefendImportance * (16-friendPieces);
                    }
                }
                else {
                    if(field.isAttacked(pieceColor.reverse())){ //attack
                        checkValue += (8-possibleMoves.size())*checkAttackImportance * (16-enemyPieces);
                    }
                }
            }
        }
        return checkValue;
    }

    private static int checkWin(Board board, PieceColor pieceColor) {
        if(board.getWinColor()==pieceColor){
            return Integer.MAX_VALUE;
        }
        else
            return Integer.MIN_VALUE;
    }
}