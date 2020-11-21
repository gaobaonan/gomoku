package com.gao.gomoku;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Sat Oct 31 18:42:35 CET 2020
 */



/**
 * @author unknown
 */
public class Wu extends JFrame {
    public Wu() {
        initComponents();
    }

    private void startActionPerformed(ActionEvent e) {
        game.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        title = new JLabel();
        button1 = new JButton();
        start = new JButton();
        game = new GomokuBoard(0);

        //======== this ========
        setMaximizedBounds(null);
        setMinimumSize(new Dimension(250, 250));
        setTitle("Gomoku");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- title ----
        title.setText("Gomoku");
        title.setFont(title.getFont().deriveFont(title.getFont().getSize() + 20f));
        contentPane.add(title, "cell 4 3 7 5");

        //---- button1 ----
        button1.setText("SinglePlayer");
        contentPane.add(button1, "cell 5 12 1 2");

        //---- start ----
        start.setText("Multiplayer");
        start.addActionListener(e -> startActionPerformed(e));
        contentPane.add(start, "cell 5 14 1 2");
        pack();
        setLocationRelativeTo(getOwner());

        //======== game ========
        {
            game.setMinimumSize(new Dimension(750, 530));
            var gameContentPane = game.getContentPane();
            gameContentPane.setLayout(new BoxLayout(gameContentPane, BoxLayout.Y_AXIS));
            game.pack();
            game.setLocationRelativeTo(game.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel title;
    private JButton button1;
    private JButton start;
    private GomokuBoard game;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
