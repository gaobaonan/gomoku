package com.gao.gomoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GomokuBoard extends JFrame {

    private ChessCounter cc;
    private boolean turn = true;
    private boolean playable = true;
    private int gameMode;

    private JButton cancel;
    private JButton restart;
    private JPanel blackPicture;
//    private JPanel whitePicture;

    public GomokuBoard(int gamemode) {
        initUI();

        this.gameMode = gamemode;
        if(gamemode == 0)
            cc = new AIChessCounter();
        else
            cc = new ChessCounter();
        cc.setChess(7,7,2);
    }

    public void win(){
        if (cc.winner() != 0) {
            String winner = "";
            if (cc.winner() == 1) winner = "Fekete";
            else winner = "Fehér";
            System.out.println(winner + " megnyert!!!!");
            playable = false;
        }
    }

    public void paint(Graphics g) {

        super.paint(g);
        g.setColor(Color.orange);
        g.fillRect(15,55,450,450);
        g.setColor(Color.BLACK);

        //棋盘
        for (int i = 0; i < 15; i++) {
            g.drawLine(30, 70 + 30 * i, 450, 70 + 30 * i);
            g.drawLine(30 + 30 * i, 70, 30 + 30 * i, 490);
        }

        g.fillRect(13,53,4,454);
        g.fillRect(13,53,454,4);
        g.fillRect(463,53,4,454);
        g.fillRect(13,503,454,4);

        //标注点位
        g.fillOval(117, 157, 6, 6);
        g.fillOval(117, 397, 6, 6);
        g.fillOval(357, 397, 6, 6);
        g.fillOval(357, 157, 6, 6);
        g.fillOval(237, 277, 6, 6);



        //绘制棋子
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                //黑子
                if (cc.getValueAt(i,j) == 1) {
                    int tempX = i * 30 + 30;
                    int tempY = j * 30 + 70;
                    g.fillOval(tempX - 9, tempY - 9, 18, 18);
                }
                //白子
                if (cc.getValueAt(i,j) == 2) {
                    int tempX = i * 30 + 30;
                    int tempY = j * 30 + 70;
                    g.setColor(Color.WHITE);
                    g.fillOval(tempX - 9, tempY - 9, 18, 18);
                    g.setColor(Color.BLACK);
                    g.drawOval(tempX - 9, tempY - 9, 18, 18);
                }
            }
        }
    }

    private void initUI(){
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
                            System.out.println("X=" + e.getX() + " Y=" + e.getY());
                            if (turn) cc.getChess()[x][y] = 1;
                            else cc.getChess()[x][y] = 2;
                            turn = !turn;
                        }
                    }
                    repaint();
                    win();

                    if(gameMode == 0){
                        playable = false;
                        cc.step();
                        turn = !turn;
                        playable = true;
                    }
                    win();
                }
            }
        });
        //nem mutat meg
        setVisible(false);

        //nyomogombok beállítások
        restart = new JButton("újrakezdés");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.init();
                playable = true;
                repaint();
                turn = true;
            }
        });

        cancel = new JButton("visszalépés");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(playable){
                    cc.cancel();
                    repaint();
                    if(gameMode == 1)
                        turn = !turn;
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
