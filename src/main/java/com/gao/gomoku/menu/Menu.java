package com.gao.gomoku.menu;

import com.gao.gomoku.file.FileFrame;
import com.gao.gomoku.game.GomokuBoard;
import com.gao.gomoku.counter.AIChessCounter;
import com.gao.gomoku.counter.ChessCounter;

import javax.swing.*;
import java.awt.*;

/**
 * Menu
 * a JFrame leszármazott ablak, a játék fő menü, ilyentől keztődik
 * cc: majd használt játék pálya
 * b: majd használt játék ablak
 * singlePalyer: egyedös játék kezdésére használt JButton
 * multiPlayer: több játékos játék kezdésére használt JButton
 * load: játék kinyításra használt JButton
 * exit: kilépésre használt JButton
 */
public class Menu extends JFrame {

    private ChessCounter cc;
    private GomokuBoard b;
    private JButton singlePlayer;
    private JButton multiPlayer;
    private JButton load;
    private JButton exit;

    /**
     * konstruktor
     * beállítás, UI tervezés
     */
    public Menu(){
        setBounds(600, 150,400, 450);
        setTitle("Gomoku");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        uisetting();
    }

    /**
     * UI tervezés
     */
    private void uisetting(){

        singlePlayer = new JButton("Egy játékos");
        singlePlayer.setPreferredSize(new Dimension(120,30));

        multiPlayer = new JButton("Két játékos");
        multiPlayer.setPreferredSize(new Dimension(120,30));

        load = new JButton("kinyitás");
        load.setPreferredSize(new Dimension(120,30));

        exit = new JButton("kilépés");
        exit.setPreferredSize(new Dimension(120,30));

        JLabel title = new JLabel("G o m o k u");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("gomokuFont", Font.BOLD,40));


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
        buttonPanel2.add(multiPlayer);

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

    /**
     * minden nyomógombnak az eseménynek beállítása
     */
    private void buttonListener(){
        singlePlayer.addActionListener(e -> {
            cc = new AIChessCounter();
            b = new GomokuBoard(cc);
            b.setVisible(true);
        });

        multiPlayer.addActionListener(e -> {
            cc = new ChessCounter();
            b = new GomokuBoard(cc);
            b.setVisible(true);
        });

        load.addActionListener(e -> new FileFrame(FileFrame.IO.LOAD, cc));

        exit.addActionListener(e -> dispose());
    }
}
