package com.gao.gomoku.gameBoard;

import com.gao.gomoku.counter.ChessCounter;
import com.gao.gomoku.file.FileFrame;
import com.gao.gomoku.counter.ChessCounter.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIediter {

    ChessCounter cc;

    private JPanel boardPanel;
    private JPanel mainPanel;
    private JButton cancel;
    private JButton restart;
    private JButton save;
    private JButton exit;


    public UIediter(ChessCounter cc){
        this.cc = cc;
        boardSetting();
        uisetting();
        buttonListener();
    }

    public JPanel createBoardPanel(){
        return boardPanel;
    }

    public JPanel createUIPanel(){
        return mainPanel;
    }

    private void boardSetting(){
        boardPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Painting.chessboardPainting(g);
                Painting.chessPainting(g, cc);
            }
        };
        boardPanel.setBounds(0,0,470,550);

        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(cc.getPlayable()) {
                    int x = e.getX();
                    int y = e.getY();
                    int distance = 30;

                    //
                    if (x >= 15 && x < 15 + distance * 15 && y >= 25 && y < 25 + distance * 15 && cc.getPlayable()) {
                        x = (x - 15) / distance;
                        y = (y - 25) / distance;

                        if (cc.getValueAt(x,y) == 0) {
                            if (cc.getTurn() == ChessCounter.Turn.BLACK) {
                                cc.setChess(x,y,1);
                                cc.setTurn(ChessCounter.Turn.WHITE);
                            }
                            else {
                                cc.setChess(x,y,2);
                                cc.setTurn(ChessCounter.Turn.BLACK);
                            }
                            cc.pushToStack(x,y);
                            boardPanel.repaint();
                            cc.win();

                            if(cc.getGameMode() == ChessCounter.GameMode.SINGLE && cc.getPlayable()){
                                cc.setPlayable(false);
                                cc.step();
                                cc.setTurn(ChessCounter.Turn.BLACK);
                                cc.setPlayable(true);
                                boardPanel.repaint();
                                cc.win();
                            }
                        }
                    }
                }
            }
        });


    }

    private void buttonListener(){
        restart.addActionListener(e -> {
            cc.init();
            cc.setPlayable(true);
            cc.setTurn(ChessCounter.Turn.BLACK);
            boardPanel.repaint();
        });
        cancel.addActionListener(e -> {
            if(cc.getPlayable()){
                if(cc.cancel() && cc.getGameMode() == ChessCounter.GameMode.MULTI)
                    if (cc.getTurn() == ChessCounter.Turn.BLACK) cc.setTurn(ChessCounter.Turn.WHITE);
                    else cc.setTurn(ChessCounter.Turn.BLACK);
                boardPanel.repaint();

            }
        });
        save.addActionListener(e -> new FileFrame(FileFrame.IO.SAVE, cc));
        exit.addActionListener(e -> System.exit(0));
    }

    private void uisetting(){
        //nyomogobok
        restart = new JButton("újrakezdés");
        restart.setPreferredSize(new Dimension(110,30));
        cancel = new JButton("visszalépés");
        cancel.setPreferredSize(new Dimension(110,30));
        save = new JButton("mentés");
        save.setPreferredSize(new Dimension(110,30));
        exit = new JButton("kilépés");
        exit.setPreferredSize(new Dimension(110,30));

        //képek
        JPanel blackPicture = new JPanel() {
            final ImageIcon icon = new ImageIcon("resources/heiqi.png");
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);    //To change body of overridden methods use File | Settings | File Templates.
                g.drawImage(icon.getImage(), 0, 0, 120, 120, null);
            }
        };
        blackPicture.setPreferredSize(new Dimension(120,120));
        JPanel whitePicture = new JPanel() {
            final ImageIcon icon = new ImageIcon("resources/baiqi.png");
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);    //To change body of overridden methods use File | Settings | File Templates.
                g.drawImage(icon.getImage(), 0, 0, 120, 120, null);
            }
        };
        whitePicture.setPreferredSize(new Dimension(120,120));

        //sövegablakok
        Font f = new Font("labelFont", Font.PLAIN, 20);
        JLabel blackLabel1 = new JLabel("1P:");
        blackLabel1.setFont(f);
        JLabel blackLabel2 = new JLabel("Játékos");
        blackLabel2.setFont(f);
        JLabel whiteLabel1 = new JLabel("2P: ");
        whiteLabel1.setFont(f);
        JLabel whiteLabel2 = new JLabel((cc.getGameMode() == GameMode.SINGLE?"Számítógép": "Játékos"));
        whiteLabel2.setFont(f);

        //nyomogombnak parkolása
        JPanel buttonPanel1 = new JPanel(new FlowLayout());
        buttonPanel1.add(cancel);
        buttonPanel1.add(restart);
        JPanel buttonPanel2 = new JPanel((new FlowLayout()));
        buttonPanel2.add(save);
        buttonPanel2.add(exit);

        JPanel labelPanel1 = new JPanel();
        labelPanel1.setLayout(new BoxLayout(labelPanel1, BoxLayout.Y_AXIS));
        labelPanel1.add(blackLabel1);
        labelPanel1.add(blackLabel2);
        labelPanel1.add(Box.createVerticalStrut(60));
        JPanel labelPanel2 = new JPanel();
        labelPanel2.setLayout(new BoxLayout(labelPanel2, BoxLayout.Y_AXIS));
        labelPanel2.add(whiteLabel1);
        labelPanel2.add(whiteLabel2);
        labelPanel2.add(Box.createVerticalStrut(60));


        JPanel picturePanel1 = new JPanel();
        picturePanel1.setLayout(new BoxLayout(picturePanel1, BoxLayout.X_AXIS));
        picturePanel1.add(blackPicture);
        picturePanel1.add(labelPanel1);
        picturePanel1.add(Box.createHorizontalStrut(40));
        JPanel picturePanel2 = new JPanel();
        picturePanel2.setLayout(new BoxLayout(picturePanel2, BoxLayout.X_AXIS));
        picturePanel2.add(whitePicture);
        picturePanel2.add(labelPanel2);
        picturePanel2.add(Box.createHorizontalStrut(25));

        JPanel uipanel = new JPanel();
        uipanel.setLayout(new BoxLayout(uipanel,BoxLayout.Y_AXIS));
        uipanel.add(Box.createVerticalStrut(20));
        uipanel.add(picturePanel1);
        uipanel.add(Box.createVerticalStrut(60));
        uipanel.add(buttonPanel1);
        uipanel.add(buttonPanel2);
        uipanel.add(Box.createVerticalStrut(60));
        uipanel.add(picturePanel2);
        uipanel.add(Box.createVerticalStrut(20));

        mainPanel = new JPanel();
        mainPanel.setLayout( new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        mainPanel.add(Box.createHorizontalStrut(470));
        mainPanel.add(uipanel);


    }
}