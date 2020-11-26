package com.gao.gomoku.gameBoard;

import com.gao.gomoku.counter.ChessCounter;

import javax.swing.*;
import java.awt.*;

public class GomokuBoard extends JFrame {

    UIediter ui;
    private ChessCounter cc;

    public GomokuBoard(ChessCounter cc) {
        this.cc = cc;
        init();
    }

    private void init(){
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
    }

    private void UISetting(){
//        setLayout(new FlowLayout());
        ui = new UIediter(cc);
        add(ui.createBoardPanel());
        add(ui.createUIPanel());
    }

}
