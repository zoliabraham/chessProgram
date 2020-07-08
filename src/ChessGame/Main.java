package ChessGame;

import ChessGame.View.Window;

public class Main {
    Controller controller;
    GameManager gameManager;
    Window window;

    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {
        this.controller = new Controller();
        this.gameManager = new GameManager();
        controller.setGameManager(gameManager);
        gameManager.setController(controller);

        this.window = new Window(gameManager,controller);
        window.repaint();

    }
}
