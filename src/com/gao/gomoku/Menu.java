package com.gao.gomoku;

import net.miginfocom.layout.BoundSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    private ChessCounter cc;
    GomokuBoard b;

    private Box b1;
    private JLabel title;
    private JButton singlePlayer;
    private JButton multiplayer;
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

        exit = new JButton("kilép");
        exit.setPreferredSize(new Dimension(120,30));

        title= new JLabel("G o m o k u");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("gomokuFont",1,40));


        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(Box.createVerticalStrut(30));
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(100));

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new FlowLayout());
        buttonPanel1.add(singlePlayer);

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new FlowLayout());
        buttonPanel2.add(multiplayer);

        JPanel buttonPanel3 = new JPanel();
        buttonPanel3.setLayout(new FlowLayout());
        buttonPanel3.add(exit);


        titlePanel.add(buttonPanel1);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(buttonPanel2);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(buttonPanel3);
        titlePanel.add(Box.createVerticalStrut(90));
        add(titlePanel);

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

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
