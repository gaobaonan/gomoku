package com.gao.gomoku.gameBoard;

import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame {
    public MessageFrame(String str){
        setBounds(700,350,200, 70);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout());
        setVisible(true);

        JLabel l = new JLabel(str);
        l.setFont(new Font("messageFont", 0, 15));
        add(l);
    }
}
