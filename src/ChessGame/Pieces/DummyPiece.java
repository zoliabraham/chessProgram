package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class DummyPiece extends Piece{
    public DummyPiece(String name, Vector forward, Board board) {
        super(name,0, forward, board);
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        return new ArrayList<>();
    }

    @Override
    public boolean isMovePossible(Field field) {
        return false;
    }

    @Override
    public ArrayList<Field> getPossibleMovesIfDefend() {
        return new ArrayList<Field>();
    }

    @Override
    public Piece copy(Board newBoard) {
        return null;
    }
}
