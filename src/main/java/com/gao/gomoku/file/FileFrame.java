package com.gao.gomoku.file;

import com.gao.gomoku.counter.AIChessCounter;
import com.gao.gomoku.counter.ChessCounter;
import com.gao.gomoku.game.GomokuBoard;
import com.gao.gomoku.counter.ChessCounter.Step;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * FileFrame
 * a fájl kezelésre meghasznált ablak
 * az osztályi belül metódussal lehet .gmk típusú fájlokat beolvasni és kiírni
 * list: ArrayList-ben tárolt JButton lista, minden JButton egy azonos fájlot lehet elérni
 * exit: kilépésre használt JButton
 * IO típus: paraméterként kapott oszdály müködési modell, az osztály egyszer csak egy modellben fog dolgozni: kiír vagy beolvas
 */
public class FileFrame extends JFrame {

    public enum IO{SAVE, LOAD}

    private final List<JButton> list = new ArrayList<>();
    private JButton exit;

    /**
     * konstruktor
     * modell megdöntés, beállítás
     * @param io: müködési modell
     * @param cc: rajta dolgoztt játék pálya
     */
    public FileFrame(IO io, ChessCounter cc){
        setBounds(700,200,200,280);
        GridLayout layout = new GridLayout(7,1);
        layout.setVgap(5);
        setLayout(layout);
        setVisible(true);

        uisetting();
        buttonSetting(io, cc);
    }

    /**
     * beolvasási metódus, io = LOAD esetén dolgozik
     * @param cc: rajta dolgoztt játék pálya
     * @param fileName: beolvasott fájlnak a neve
     * @throws IOException kivétel
     */
    protected void loadSetting(ChessCounter cc, String fileName) throws IOException {
        String name = "save/" + fileName + ".gmk";
        try(BufferedReader br = new BufferedReader(new FileReader(name))){
            String[] firstLine =  br.readLine().split(" ");

            if(firstLine[0].equals("SINGLE")) cc = new AIChessCounter();
            else cc = new ChessCounter();
            cc.setTurn(ChessCounter.Turn.valueOf(firstLine[1]));
            cc.setPlayable(firstLine[2].equals("playable"));
            for (int i = 0; i < 15; i++){
                char[] line = br.readLine().toCharArray();
                for(int j = 0; j < 15; j++){
                    cc.setChess(i, j, Character.getNumericValue(line[j]));
                }
            }
            while (true){
                String stackLine = br.readLine();
                if(stackLine != null){
                    String[] stackParameter = stackLine.split(" ");
                    cc.pushToStack(Integer.parseInt(stackParameter[0]), Integer.parseInt(stackParameter[1]));
                }
                else break;
            }

            GomokuBoard b = new GomokuBoard(cc);
            b.setVisible(true);
        }
    }

    /**
     * kiírási metódus, io = SAVE esetén dolgozik
     * @param cc: rajta dolgoztt játék pálya
     * @param fileName: kiírt fájlnak a neve
     * @throws IOException kivétel
     */
    private void saveSetting(ChessCounter cc, String fileName) throws IOException {

        String name = "save/" + fileName + ".gmk";
        try (FileWriter fw = new FileWriter(name)){
            fw.write(cc.getGameMode().toString() + " ");
            fw.write(cc.getTurn().toString() + " ");
            if(cc.getPlayable()) fw.write("playable\r\n");
            else fw.write("notplayable\r\n");
            for (int i = 0; i < 15; i++){
                for (int j = 0; j < 15; j++){
                    fw.write(cc.getValueAt(i,j) + "");
                }
                fw.write("\r\n");
            }
            Stack<Step> stepStack = cc.getStepStack();
            for(Step s : stepStack)
                fw.write(s.getX() + " " + s.getY() + "\r\n");
        }
    }

    /**
     * UI beállítás
     */
    private void uisetting(){
        JLabel text = new JLabel("válaszjon:");
        add(text);
        for(int i = 0; i < 5 ; i++){
            JButton button = new JButton("game" + (i+1));
            button.setName("gamedata" + (i+1));
            add(button);
            list.add(button);
        }
        exit = new JButton("kilépés");
        add(exit);
    }

    /**
     *
     * @param io
     * @param cc
     */
    private void buttonSetting(IO io, ChessCounter cc){
        exit.addActionListener(e -> dispose());

        for (int i = 0; i < 5; i++) {
            final int serialNumber = i;
            list.get(i).addActionListener(e -> {
                try {
                    if(io == IO.LOAD) loadSetting(cc, list.get(serialNumber).getName());
                    else saveSetting(cc, list.get(serialNumber).getName());
                } catch (IOException exception) {
                    showMessageDialog(null, "Fájl nem található!", "hiba", ERROR_MESSAGE);
                }
                dispose();
            });
        }
    }
}


