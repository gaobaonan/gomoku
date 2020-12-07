package com.gao.gomoku.menu;

import com.gao.gomoku.counter.AIChessCounter;
import com.gao.gomoku.counter.ChessCounter;
import com.gao.gomoku.game.GomokuBoard;

import javax.swing.*;
import java.awt.*;

import static com.gao.gomoku.file.FileFrame.loadGame;

/**
 * Menu
 * a JFrame leszarmazott ablak, a jatek fo menu, ilyentol keztodik
 * cc: majd hasznalt jatek palya
 * b: majd hasznalt jatek ablak
 * singlePalyer: egyedos jatek kezdesere hasznalt JButton
 * multiPlayer: tobb jatekos jatek kezdesere hasznalt JButton
 * load: jatek kinyitasra hasznalt JButton
 * exit: kilepesre hasznalt JButton
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
     * beallitas, UI tervezes
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
     * UI tervezes
     */
    private void uisetting(){

        singlePlayer = new JButton("single player");
        singlePlayer.setPreferredSize(new Dimension(120,30));

        multiPlayer = new JButton("multiplayer");
        multiPlayer.setPreferredSize(new Dimension(120,30));

        load = new JButton("load");
        load.setPreferredSize(new Dimension(120,30));

        exit = new JButton("exit");
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
     * minden nyomogombnak az esemenynek beallitasa
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

        load.addActionListener(e -> loadGame(this, cc));

        exit.addActionListener(e -> {
            dispose();
            if(b != null)
                b.dispose();
        });
    }
}
