package ChessGame.View;

import ChessGame.Controller;
import ChessGame.Field;
import ChessGame.GameManager;
import ChessGame.Pieces.Piece;
import ChessGame.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JPanel {
    JFrame frame = new JFrame("Chess program - ugyis meg leszel verve");
    GameManager gameManager;
    Controller controller;
    Renderer renderer;
    boolean pieceSelected=false;

    public Window(GameManager gameManager, Controller controller) {
        setupFrame();
        try {
            Thread.sleep(10); //legyen ideje kirajzolni az ablakot
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.gameManager = gameManager;
        this.controller = controller;
        this.renderer = new Renderer(gameManager,this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processClick(new Vector(e.getX(),e.getY()));
            }
        });
    }

    private void setupFrame() {
        frame.pack();
        //frame.setSize(1200, 700);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(49, 49, 49, 255));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        DrawInfo drawInfo = new DrawInfo(g2d, this);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderer.render(drawInfo);

    }

    void processClick(Vector clickPosition){
        FieldRenderable fieldRenderable = findClickedField(clickPosition);
        Field field=null;
        if(fieldRenderable!=null)
             field = fieldRenderable.getField();

        if(Controller.tradePawn){
            Piece selectedPiece = getSelectedPiece(clickPosition);
            controller.tradeSelected(selectedPiece);
        }
        else if(field!=null) {
            if (controller.getSelectedPiece() == null) { //TODO félre kattintás
                Piece piece = field.getPiece();
                controller.setSelectedPiece(piece);
                controller.calculatePossibleMoves();

            } else {
                controller.selectField(field);
            }
        }
        this.repaint();
    }

    private Piece getSelectedPiece(Vector clickPosition) {
        int panelIndex;
        if(clickPosition.x<getWidth()/2)
            panelIndex = 0;
        else
            panelIndex = 1;
        int size = renderer.getRemovedPiecesContainers().get(panelIndex).getSize().x;
        int selectID = (int)((float)((float)clickPosition.y/(float)getHeight())*16);
        System.out.print("Select id : " + selectID);
        PieceRenderable pieceRenderable = renderer.getRemovedPiecesContainers().get(panelIndex).getPieceRenderables().get(selectID);
        return pieceRenderable.getPiece();
    }


    private FieldRenderable findClickedField(Vector clickPosition) {
        for (FieldRenderable f: renderer.getFieldRenderables()) {
            if(Vector.inSquare(clickPosition,f.position,f.size)){
                return f;
            }
        }
        return null;
    }

}
