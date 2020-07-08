package ChessGame.Pieces;

import ChessGame.Board;
import ChessGame.Field;
import ChessGame.Vector;

import java.util.ArrayList;

public class DummyPiece extends Piece{
    public DummyPiece(String name, Vector forward, Board board) {
        super(name, forward, board);
    }

    @Override
    public ArrayList<Field> getPossibleMoves() {
        return new ArrayList<>();
    }

    @Override
    public boolean isMovePossible(Field field) {
        return false;
    }
}
