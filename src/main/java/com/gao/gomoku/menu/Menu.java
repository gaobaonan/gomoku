package com.gao.gomoku.menu;

import com.gao.gomoku.file.FileFrame;
import com.gao.gomoku.gameBoard.GomokuBoard;
import com.gao.gomoku.counter.AIChessCounter;
import com.gao.gomoku.counter.ChessCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    private ChessCounter cc;
    private GomokuBoard b;

    private Box b1;
    private JLabel title;
    private JButton singlePlayer;
    private JButton multiplayer;
    private JButton load;
    private JButton exit;

    public Menu(){
        setBounds(600, 150,400, 450);
        setTitle("Gomoku");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        UIsetting();

    }

    private void UIsetting(){

        singlePlayer = new JButton("Egy játékos");
        singlePlayer.setPreferredSize(new Dimension(120,30));

        multiplayer = new JButton("Két játékos");
        multiplayer.setPreferredSize(new Dimension(120,30));

        load = new JButton("kinyitás");
        load.setPreferredSize(new Dimension(120,30));

        exit = new JButton("kilép");
        exit.setPreferredSize(new Dimension(120,30));

        title= new JLabel("G o m o k u");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("gomokuFont",1,40));


        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(Box.createVerticalStrut(30));
        menuPanel.add(title);
        menuPanel.add(Box.createVerticalStrut(100));

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new FlowLayout());
        buttonPanel1.add(singlePlayer);

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new FlowLayout());
        buttonPanel2.add(multiplayer);

        JPanel buttonPanel3 = new JPanel();
        buttonPanel3.setLayout(new FlowLayout());
        buttonPanel3.add(load);


        JPanel buttonPanel4 = new JPanel();
        buttonPanel4.setLayout(new FlowLayout());
        buttonPanel4.add(exit);


        menuPanel.add(buttonPanel1);
        menuPanel.add(Box.createVerticalStrut(5));
        menuPanel.add(buttonPanel2);
        menuPanel.add(Box.createVerticalStrut(5));
        menuPanel.add(buttonPanel3);
        menuPanel.add(Box.createVerticalStrut(5));
        menuPanel.add(buttonPanel4);
        menuPanel.add(Box.createVerticalStrut(80));
        add(menuPanel);

        buttonListener();
    }

    private void buttonListener(){

        singlePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc = new AIChessCounter();
                b = new GomokuBoard(cc);
                b.setVisible(true);
            }
        });

        multiplayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc = new ChessCounter();
                b = new GomokuBoard(cc);
                b.setVisible(true);
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileFrame f = new FileFrame(FileFrame.IO.load, cc);
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
