package ChessGame.AILogic;

import ChessGame.Board;
import ChessGame.Controller;
import ChessGame.Field;
import ChessGame.GameManager;
import ChessGame.Pieces.Piece;
import ChessGame.Pieces.PieceColor;

import java.util.ArrayList;

public class Algorithm {
    PieceColor pieceColor;
    GameManager gameManager;
    Controller controller;


    public Algorithm(PieceColor pieceColor, GameManager gameManager, Controller controller) {
        this.pieceColor = pieceColor;
        this.gameManager = gameManager;
        this.controller = controller;
    }

    public int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer){
        if(depth == 0 || board.isGameOver()){
            return BoardValueEvaluator.evaluate(board,pieceColor);
        }

        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;
            ArrayList<Step> possibleSteps = getPossibleSteps(true,board);

            for (Step step: possibleSteps) {
                Board simulatedBoard = board.simulateStep(step);
                int eval = minimax(simulatedBoard,depth-1,alpha,beta,false);
                maxEval = Math.max(maxEval,eval);
                alpha = Math.max(alpha,eval);
                if (beta <= alpha)
                    break;
            }
            return maxEval;
        }
        else {
            int minEval = Integer.MAX_VALUE;
            ArrayList<Step> possibleSteps = getPossibleSteps(false,board);

            for (Step step: possibleSteps) {
                Board simulatedBoard = board.simulateStep(step);
                int eval = minimax(simulatedBoard,depth-1,alpha,beta,true);
                minEval = Math.min(minEval,eval);
                beta = Math.min(beta,eval);
                if (beta <= alpha)
                    break;

            }
            return minEval;
        }
    }



    public Step minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, Step enterStep){
        if(depth == 0 || board.isGameOver()){
            enterStep.setValue(BoardValueEvaluator.evaluate(board,pieceColor));
            return enterStep;
        }

        try {
            if (maximizingPlayer) {
                //int maxEval = Integer.MIN_VALUE;
                Step maxEvalStep = null;
                ArrayList<Step> possibleSteps = getPossibleSteps(true, board);

                for (Step step : possibleSteps) {
                    Board simulatedBoard = board.simulateStep(step);
                    Step eval = minimax(simulatedBoard, depth - 1, alpha, beta, false, step);
                    //maxEval = Math.max(maxEval,eval.getMaxValue());
                    eval.swapMoveData(step);

                    if (maxEvalStep == null || maxEvalStep.getValue() < eval.getValue()) {
                        maxEvalStep = eval;
                    }

                    alpha = Math.max(alpha, eval.getValue());
                    if (beta <= alpha)
                        break;
                }
                return maxEvalStep;
            } else {
                int minEval = Integer.MAX_VALUE;
                Step minEvalStep = null;
                ArrayList<Step> possibleSteps = getPossibleSteps(false, board);

                for (Step step : possibleSteps) {
                    Board simulatedBoard = board.simulateStep(step);
                    Step eval = minimax(simulatedBoard, depth - 1, alpha, beta, true, step);
                    //minEval = Math.min(minEval,eval.getValue());
                    eval.swapMoveData(step);
                    if (minEvalStep == null || minEvalStep.getValue() > eval.getValue()) {
                        minEvalStep = eval;
                    }
                    beta = Math.min(beta, eval.getValue());
                    if (beta <= alpha)
                        break;
                }
                return minEvalStep;
            }
        }catch (Exception e){
            enterStep.setValue(Integer.MIN_VALUE);
            return enterStep;
        }
    }

    private ArrayList<Step> getPossibleSteps(boolean maximizingPlayer,Board board) {
        ArrayList<Step> possibleSteps = new ArrayList<>();
        for (Field f: board.getFields()) {
            Piece piece = f.getPiece();
            if(piece!=null){
                if(maximizingPlayer){
                    if(piece.getPieceColor()==pieceColor){
                        for (Field stepField: piece.getPossibleMoves()) {
                            possibleSteps.add(new Step(piece,stepField,f,board));
                        }
                    }
                }
                else {
                    if(piece.getPieceColor()!=pieceColor){
                        for (Field stepField: piece.getPossibleMoves()) {
                            possibleSteps.add(new Step(piece,stepField,f,board));
                        }
                    }
                }

            }
        }
        return possibleSteps;
    }
}
