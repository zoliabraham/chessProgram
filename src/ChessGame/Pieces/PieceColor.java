package ChessGame.Pieces;

public enum PieceColor {
    White("white"),Black("black");

    private final String value;

    PieceColor(String n) {
        this.value = n;
    }

    public String getValue() {
        return value;
    }
}
