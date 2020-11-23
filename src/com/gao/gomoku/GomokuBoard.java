package com.gao.gomoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GomokuBoard extends JFrame {

    UIediter ui;
    private ChessCounter cc;

    public GomokuBoard(ChessCounter cc) {
        this.cc = cc;
        initUI();
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
            cc.setPlayable(false);
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
        setBounds(500, 100,750, 550);
        //azonos méret legyen
        setResizable(false);
        //bezárás
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //nem mutat meg
        setVisible(false);

        //mouseListenert használ
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(cc.getPlayable()) {
                    int x = e.getX();
                    int y = e.getY();
                    //判断点击是否在棋盘内
                    if (x >= 15 && x < 465 && y >= 55 && y < 505) {
                        x = (x - 15) / 30;
                        y = (y - 55) / 30;

                        if (cc.getValueAt(x,y) == 0) {
                            cc.refresh();
                            if (cc.getTurn() == ChessCounter.Turn.blackTurn) {
                                cc.setChess(x,y,1);
                                cc.setTurn(ChessCounter.Turn.whiteTurn);
                            }
                            else {
                                cc.setChess(x,y,2);
                                cc.setTurn(ChessCounter.Turn.blackTurn);
                            }

                            if(cc.getGameMode() == ChessCounter.GameMode.single){
                                cc.setPlayable(false);
                                cc.step();
                                cc.setTurn(ChessCounter.Turn.blackTurn);
                                cc.setPlayable(true);
                            }
                            win();
                        }
                    }
                }
                repaint();
            }
        });
    }

    private void UISetting(){
        ui = new UIediter(this, cc);
        add(ui.UIBox());
    }

}
