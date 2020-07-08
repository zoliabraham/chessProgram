package ChessGame;

public class GameManager {
    Controller controller;
    Board board;


    public void setController(Controller controller) {
        this.controller = controller;
        this.board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public Controller getController() {
        return controller;
    }
}
