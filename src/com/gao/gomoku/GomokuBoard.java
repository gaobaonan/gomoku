package com.gao.gomoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GomokuBoard extends JFrame {

    private enum Turn{blackTurn, whiteTurn}
    private enum GameMode{single, multi}

    private ChessCounter cc;
    private boolean playable = true;
    private Turn turn;
    private GameMode gameMode;

    private JButton cancel;
    private JButton restart;
    private JPanel blackPicture;
//    private JPanel whitePicture;

    public GomokuBoard(int gameMode) {
        initUI();

        turn = Turn.blackTurn;
        this.gameMode = GameMode.values()[gameMode];
        if(this.gameMode == GameMode.single)
            cc = new AIChessCounter();
        else
            cc = new ChessCounter();
        cc.setChess(7,7,2);
    }

    public void paint(Graphics g) {

        super.paint(g);
        Painting.boardPainting(g);
        Painting.chessPainting(g, cc);
    }

    private void win(){
        if (cc.winner() != 0) {
            String winner = "";
            if (cc.winner() == 1) winner = "Fekete";
            else winner = "Fehér";
            System.out.println(winner + " megnyert!!!!");
            playable = false;
        }
    }

    private void initUI(){

        basicSetting();
        UISetting();

    }

    private void basicSetting(){

        //ablak beállítások
        //cím
        setTitle("Gomoku");
        //méret
        setSize(750, 550);
        //azonos méret legyen
        setResizable(false);
        //bezárás
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        //mouseListenert használ
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(playable) {
                    cc.refresh();
                    int x = e.getX();
                    int y = e.getY();
                    //判断点击是否在棋盘内
                    if (x >= 15 && x < 465 && y >= 55 && y < 505) {
                        x = (x - 15) / 30;
                        y = (y - 55) / 30;

                        if (cc.getChess()[x][y] == 0) {

                            if (turn == Turn.blackTurn) {
                                cc.getChess()[x][y] = 1;
                                turn = Turn.whiteTurn;
                            }
                            else {
                                cc.getChess()[x][y] = 2;
                                turn = Turn.blackTurn;
                            }

                            repaint();
                            win();

                            if(gameMode == GameMode.single){
                                playable = false;
                                cc.step();
                                turn = Turn.blackTurn;
                                playable = true;
                            }
                            win();
                        }
                    }
                }
            }
        });
        //nem mutat meg
        setVisible(false);
    }

    private void UISetting(){
        //nyomogombok beállítások
        restart = new JButton("újrakezdés");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.init();
                playable = true;
                repaint();
                turn = Turn.blackTurn;
            }
        });

        cancel = new JButton("visszalépés");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(playable){
                    cc.cancel();
                    repaint();
                    if(gameMode == GameMode.multi)
                        if (turn == Turn.blackTurn) turn = Turn.whiteTurn;
                        else turn = Turn.blackTurn;

                }
            }
        });

        blackPicture = new JPanel(){
            ImageIcon icon= new ImageIcon("resources/heiqi.png");
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);    //To change body of overridden methods use File | Settings | File Templates.
                g.drawImage(icon.getImage(),0,0,120,120,null);
            }
        };
        blackPicture.setSize(120,120);
        blackPicture.setVisible(true);

        Box b1 = Box.createHorizontalBox();
        Box b2 = Box.createVerticalBox();
        add(b1);
        b1.add(Box.createHorizontalStrut(500));
        b1.add(blackPicture);
        b1.add(b2);
        b2.add(restart);
        b2.add(Box.createVerticalStrut(50));
        b2.add(cancel);
        b2.add(blackPicture);
    }

}
