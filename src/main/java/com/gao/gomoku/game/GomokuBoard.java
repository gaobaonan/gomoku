package com.gao.gomoku.game;

import com.gao.gomoku.counter.ChessCounter;

import javax.swing.*;

/**
 * GomokuBoard
 * JFrame leszarmazott osztaly, jatek ablak
 * a csomagban tartozott mas ket osztaly is ennek az osztalynak dolgozik
 * cc: a jatek palya, fo adatot tarol ebben
 */
public class GomokuBoard extends JFrame {

    private final ChessCounter cc;

    /**
     * konstruktor
     * beallitas, palyolvasas
     * @param cc: megadott palya, új jatek eseten sima inicializalas, beolvasas eseten pedig egy regi jatek palyat kap meg
     */
    public GomokuBoard(ChessCounter cc) {
        this.cc = cc;
        init();
    }

    /**
     * ablak inicializalas
     * ket metodusra bontok le
     */
    private void init(){
        basicSetting();
        uisetting();
    }

    /**
     * ablak beallitas
     */
    private void basicSetting(){
        //ablak beallitasok
        //cim
        setTitle("Gomoku");
        //meret
        setBounds(500, 100,750, 550);
        //azonos meret legyen
        setResizable(false);
        //bezaras
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //nem mutat meg
        setVisible(false);
    }

    /**
     * UI tervezes, UIediter osztalyval valositott
     * az ablakba be fogja szúrni az elkeszult grafikus jatek palya es a swing-es jatek UI
     */
    private void uisetting(){
        UIeditor ui = new UIeditor(this, cc);
        add(ui.createBoardPanel());
        add(ui.createUIPanel());
    }

}
