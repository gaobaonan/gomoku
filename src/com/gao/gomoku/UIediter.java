package com.gao.gomoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIediter {

    ChessCounter cc;

    GomokuBoard board;
    Box b1;
    private JButton cancel;
    private JButton restart;
    private JPanel blackPicture;
    //    private JPanel whitePicture;

    public UIediter(GomokuBoard b, ChessCounter cc){
        board = b;
        this.cc = cc;
        buttonSetting();
        packing();
    }

    public Box UIBox(){
        return b1;
    }

    private void buttonSetting(){
        restart = new JButton("újrakezdés");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.init();
                cc.setPlayable(true);
                cc.setTurn(ChessCounter.Turn.blackTurn);
                board.repaint();
            }
        });

        cancel = new JButton("visszalépés");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cc.getPlayable()){
                    cc.cancel();
                    if(cc.getGameMode() == ChessCounter.GameMode.multi)
                        if (cc.getTurn() == ChessCounter.Turn.blackTurn) cc.setTurn(ChessCounter.Turn.whiteTurn);
                        else cc.setTurn(ChessCounter.Turn.blackTurn);
                    board.repaint();

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
    }

    private void packing(){
        b1 = Box.createHorizontalBox();
        Box b2 = Box.createVerticalBox();
        b1.add(Box.createHorizontalStrut(500));
        b1.add(blackPicture);
        b1.add(b2);
        b2.add(restart);
        b2.add(Box.createVerticalStrut(50));
        b2.add(cancel);
        b2.add(blackPicture);
    }
}