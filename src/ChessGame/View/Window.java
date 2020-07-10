package ChessGame.View;

import ChessGame.Controller;
import ChessGame.Field;
import ChessGame.GameManager;
import ChessGame.ImageCache.ImageCache;
import ChessGame.Pieces.Piece;
import ChessGame.Pieces.PieceColor;
import ChessGame.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JPanel {
    JFrame frame = new JFrame("Chess program - ugyis meg leszel verve");
    GameManager gameManager;
    Controller controller;
    Renderer renderer;
    boolean pieceSelected=false;
    JPanel promotePanel;
    JLabel messageLabel;
    boolean endgame = false;


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

        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        setUpPromotePanel();
        layout.putConstraint(SpringLayout.WEST, promotePanel, 100, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, promotePanel, 0, SpringLayout.VERTICAL_CENTER, this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processClick(new Vector(e.getX(),e.getY()));
            }
        });

        messageLabel = new JLabel("Teszt");
        messageLabel.setForeground(new Color(255,255,255,255));
        messageLabel.setFont(new Font(messageLabel.getName(), Font.PLAIN, 40));
        this.add(messageLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, messageLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, messageLabel, 30, SpringLayout.NORTH, this);
        messageLabel.setVisible(false);
    }

    private void setUpPromotePanel() {
        promotePanel = new JPanel();
        promotePanel.setBackground(new Color(61, 61, 61, 255));
        //promotePanel.setPreferredSize(new Dimension(450,110));
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JButton button4 = new JButton();
        Dimension buttonSize = new Dimension(50,50);
        PieceColor selectedColor = PieceColor.White;
        setUpButton(button1,"queen", selectedColor,buttonSize);
        setUpButton(button2,"rook", selectedColor,buttonSize);
        setUpButton(button3,"bishop", selectedColor,buttonSize);
        setUpButton(button4,"knight", selectedColor,buttonSize);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.swapToQueen();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.swapToRook();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.swapToBishop();
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.swapToKnight();
            }
        });
        promotePanel.setVisible(false);
        this.add(promotePanel);
    }



    private void promotePawn(Piece promoteTo){
        controller.promotePawn(promoteTo);
    }

    private void setUpButton(JButton button, String pieceName, PieceColor pieceColor, Dimension buttonSize){
        button.setPreferredSize(new Dimension(100,100));
        button.setIcon(new ImageIcon(ImageCache.getImage("resources/" + pieceColor.getValue() + "/" + pieceName + ".png").getScaledInstance(buttonSize.width, buttonSize.height, Image.SCALE_SMOOTH)));
        button.setOpaque(true);
        button.setBackground(new Color(83, 83, 83, 255));
        promotePanel.add(button);
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
        if(controller.isGameEnded()){
            endgame = true;
            showMessage("Matt");
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderer.render(drawInfo);

    }



    void processClick(Vector clickPosition){
        if(!endgame) {
            FieldRenderable fieldRenderable = findClickedField(clickPosition);
            Field field = null;
            if (fieldRenderable != null)
                field = fieldRenderable.getField();

            if (field != null) {
                if (controller.getSelectedPiece() == null) {
                    Piece piece = field.getPiece();
                    controller.setSelectedPiece(piece);
                    controller.calculatePossibleMoves();

                } else {
                    controller.selectField(field);
                }
            }
            this.repaint();
        }
    }



    private FieldRenderable findClickedField(Vector clickPosition) {
        for (FieldRenderable f: renderer.getFieldRenderables()) {
            if(Vector.inSquare(clickPosition,f.position,f.size)){
                return f;
            }
        }
        return null;
    }

    public void showPromoteWindow() {
        promotePanel.setVisible(true);
        //frame.pack();
    }

    public void hidePromoteWindow(){
        promotePanel.setVisible(false);
        revalidate();
        repaint();
    }

    public void showMessage(String message){
        messageLabel.setText(message);
        messageLabel.setVisible(true);
        revalidate();
        repaint();
    }
}
