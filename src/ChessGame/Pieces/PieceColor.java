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

    public PieceColor reverse(){
        if (this==White)
            return Black;
        else
            return White;
    }
}
