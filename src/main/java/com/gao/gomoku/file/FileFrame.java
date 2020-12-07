package com.gao.gomoku.file;

import com.gao.gomoku.counter.AIChessCounter;
import com.gao.gomoku.counter.ChessCounter;
import com.gao.gomoku.counter.ChessCounter.Step;
import com.gao.gomoku.game.GomokuBoard;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * FileFrame
 * a fajl kezelesre meghasznalt ablak
 * az osztalyi belul metodussal lehet .gmk tipusú fajlokat beolvasni es kiirni
 * list: ArrayList-ben tarolt JButton lista, minden JButton egy azonos fajlot lehet elerni
 * exit: kilepesre hasznalt JButton
 * IO tipus: parameterkent kapott oszdaly mukodesi modell, az osztaly egyszer csak egy modellben fog dolgozni: kiir vagy beolvas
 */
public class FileFrame {

    public static void loadGame(JFrame f,ChessCounter cc) {
        openFileChooser(f).ifPresent(filePath -> {
            try {
                loadSetting(cc, filePath);
            } catch (IOException e) {
                e.printStackTrace();
                showMessageDialog(null, "File not found!", "warning", ERROR_MESSAGE);
            }
        });
    }

    /**
     * beolvasasi metodus, io = LOAD eseten dolgozik
     * @param cc: rajta dolgoztt jatek palya
     * @param fileName: beolvasott fajlnak a neve
     * @throws IOException kivetel
     */
    private static void loadSetting(ChessCounter cc, String fileName) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
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

    public static void saveGame(JFrame f, ChessCounter cc) {
        openFileChooser(f).ifPresent(filePath-> {
            try {
                saveSetting(cc, filePath + ".gmk");
            } catch (IOException e) {
                e.printStackTrace();
                showMessageDialog(null, "File not found!", "warning", ERROR_MESSAGE);
            }
        });
    }

    /**
     * kiirasi metodus, io = SAVE eseten dolgozik
     * @param cc: rajta dolgoztt jatek palya
     * @param fileName: kiirt fajlnak a neve
     * @throws IOException kivetel
     */
    private static void saveSetting(ChessCounter cc, String fileName) throws IOException {
        try (FileWriter fw = new FileWriter(fileName)){
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

    private static Optional<String> openFileChooser(JFrame f){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("gomoku file extention","gmk");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(f);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
                    return Optional.of(chooser.getSelectedFile().getAbsolutePath());
        }
        return Optional.empty();
    }
}

