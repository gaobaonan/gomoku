package com.gao.gomoku.gameBoard;

import com.gao.gomoku.file.FileFrame;
import com.gao.gomoku.counter.ChessCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIediter {

    ChessCounter cc;

    private JPanel boardPanel;
    private JPanel UIPanel;
    private JButton cancel;
    private JButton restart;
    private JButton save;
    private JPanel blackPicture;
    private JPanel whitePicture;

    public UIediter(ChessCounter cc){
        this.cc = cc;
        boardPainting();
        UISetting();
        buttonListener();
    }

    public JPanel createBoardPanel(){
        return boardPanel;
    }

    public JPanel createUIPanel(){
        return UIPanel;
    }

    private void boardPainting(){
        boardPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Painting.ChessboardPainting(g);
                Painting.chessPainting(g, cc);
            }
        };
        boardPanel.setBounds(0,0,500,550);

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
                            cc.refresh();
                            if (cc.getTurn() == ChessCounter.Turn.BLACK) {
                                cc.setChess(x,y,1);
                                cc.setTurn(ChessCounter.Turn.WHITE);
                            }
                            else {
                                cc.setChess(x,y,2);
                                cc.setTurn(ChessCounter.Turn.BLACK);
                            }

                            if(cc.getGameMode() == ChessCounter.GameMode.SINGLE){
                                cc.setPlayable(false);
                                cc.step();
                                cc.setTurn(ChessCounter.Turn.BLACK);
                                cc.setPlayable(true);
                            }
                        }
                    }
                }
                boardPanel.repaint();
                cc.win();
            }
        });


    }

    private void buttonListener(){
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.init();
                cc.setPlayable(true);
                cc.setTurn(ChessCounter.Turn.BLACK);
                boardPanel.repaint();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cc.getPlayable()){
                    cc.cancel();
                    if(cc.getGameMode() == ChessCounter.GameMode.MULTI)
                        if (cc.getTurn() == ChessCounter.Turn.BLACK) cc.setTurn(ChessCounter.Turn.WHITE);
                        else cc.setTurn(ChessCounter.Turn.BLACK);
                    boardPanel.repaint();

                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileFrame ff =new FileFrame(FileFrame.IO.save, cc);
            }
        });
    }

    private void UISetting(){

        restart = new JButton("újrakezdés");
        cancel = new JButton("visszalépés");
        save = new JButton("mentés");

        blackPicture = new JPanel(){
            ImageIcon icon= new ImageIcon("resources/heiqi.png");
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);    //To change body of overridden methods use File | Settings | File Templates.
                g.drawImage(icon.getImage(),0,0,120,120,null);
            }
        };
        blackPicture.setSize(120,120);

        whitePicture = new JPanel(){
            ImageIcon icon= new ImageIcon("resources/baiqi.png");
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);    //To change body of overridden methods use File | Settings | File Templates.
                g.drawImage(icon.getImage(),0,0,120,120,null);
            }
        };
        whitePicture.setSize(120,120);

        UIPanel = new JPanel();
        Box b2 = Box.createVerticalBox();
        UIPanel.add(Box.createHorizontalStrut(500));
        UIPanel.add(blackPicture);
        UIPanel.add(b2);
        b2.add(restart);
        b2.add(Box.createVerticalStrut(50));
        b2.add(cancel);
        b2.add(save);
        b2.add(blackPicture);
    }
}