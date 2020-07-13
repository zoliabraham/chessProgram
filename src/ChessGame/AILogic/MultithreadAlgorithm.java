package ChessGame.AILogic;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Pieces.Piece;
import ChessGame.Pieces.PieceColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MultithreadAlgorithm extends RecursiveTask<Step> {
    PieceColor pieceColor;
    Board board;
    int depth;
    int alpha;
    int beta;
    boolean maximizingPlayer;
    Step enterStep;
    public static long iterations=0;

    public MultithreadAlgorithm(PieceColor pieceColor, Board board, int depth, int alpha, int beta, boolean maximizingPlayerNext, Step enterStep) {
        this.pieceColor = pieceColor;
        this.board = board;
        this.depth = depth;
        this.alpha = alpha;
        this.beta = beta;
        this.maximizingPlayer = maximizingPlayerNext;
        this.enterStep = enterStep;
    }

    @Override
    protected Step compute() {
        iterations++;
        if(depth == 0 || board.isGameOver()){
            enterStep.setValue(BoardValueEvaluator.evaluate(board,pieceColor));
            return enterStep;
        }

        if(depth>3) {
            return splitToThreads();
        }
        else{
            Algorithm algorithm = new Algorithm(pieceColor);
            return algorithm.minimax(board, depth ,Integer.MIN_VALUE , Integer.MAX_VALUE , maximizingPlayer, enterStep); //fura hogy csak egyet indít, de ha a depth nem -1 akkor nincs hatása
        }

    }

    private Step splitToThreads() {
        try {
            if (maximizingPlayer) {
                List<MultithreadAlgorithm> subtasks =new ArrayList<MultithreadAlgorithm>();

                ArrayList<Step> possibleSteps = getPossibleSteps(true, board);
                subtasks.addAll(createSubtasks(possibleSteps,false));

                for (MultithreadAlgorithm threads : subtasks){
                    threads.fork();
                }

                Step maxEvalStep = null;
                for (MultithreadAlgorithm thread : subtasks) {
                    Step eval = thread.join();
                    eval.swapMoveData(thread.getStep());
                    if (maxEvalStep == null || maxEvalStep.getValue() < eval.getValue()) {
                        maxEvalStep = eval;
                    }
                }
                return maxEvalStep;
            }
            else {
                List<MultithreadAlgorithm> subtasks =new ArrayList<MultithreadAlgorithm>();

                ArrayList<Step> possibleSteps = getPossibleSteps(false, board);
                subtasks.addAll(createSubtasks(possibleSteps,true));

                for (MultithreadAlgorithm threads : subtasks){
                    threads.fork();
                }

                Step minEvalStep = null;
                for (MultithreadAlgorithm thread : subtasks) {
                    Step eval = thread.join();
                    eval.swapMoveData(thread.getStep());
                    if (minEvalStep == null || minEvalStep.getValue() > eval.getValue()) {
                        minEvalStep = eval;
                    }
                }
                return minEvalStep;
            }
        } catch (Exception e){
            enterStep.setValue(Integer.MIN_VALUE);
            System.err.println("Exception in prediciton, returning -infinity");
            return enterStep;
        }
    }

    private List<MultithreadAlgorithm> createSubtasks(ArrayList<Step> possibleSteps, boolean maximizingPlayerNext) {
        List<MultithreadAlgorithm> subtasks = new ArrayList<MultithreadAlgorithm>();
        for (Step step: possibleSteps) {
            Board simulatedBoard = board.simulateStep(step);
            MultithreadAlgorithm subtask = new MultithreadAlgorithm(pieceColor,simulatedBoard,depth-1,alpha,beta,maximizingPlayerNext, step);
            subtasks.add(subtask);
        }
        return subtasks;
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

    public Step getStep() {
        return enterStep;
    }
}
