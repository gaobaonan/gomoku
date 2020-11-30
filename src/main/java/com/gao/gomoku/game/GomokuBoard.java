package com.gao.gomoku.game;

import com.gao.gomoku.counter.ChessCounter;

import javax.swing.*;

/**
 * GomokuBoard
 * JFrame leszármazott osztály, játék ablak
 * a csomagban tartozott más két osztály is ennek az osztálynak dolgozik
 * cc: a játék pálya, fő adatot tárol ebben
 */
public class GomokuBoard extends JFrame {

    private ChessCounter cc;

    /**
     * konstruktor
     * beállítás, pályolvasás
     * @param cc: megadott pálya, új játék esetén sima inicializálás, beolvasás esetén pedig egy régi játék pályát kap meg
     */
    public GomokuBoard(ChessCounter cc) {
        this.cc = cc;
        init();
    }

    /**
     * ablak inicializálás
     * két metódusra bontok le
     */
    private void init(){
        basicSetting();
        uisetting();
    }

    /**
     * ablak beállítás
     */
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

    /**
     * UI tervezés, UIediter osztályval valósított
     * az ablakba be fogja szúrni az elkészült grafikus játék pálya és a swing-es játék UI
     */
    private void uisetting(){
        UIediter ui = new UIediter(cc);
        add(ui.createBoardPanel());
        add(ui.createUIPanel());
    }

}
