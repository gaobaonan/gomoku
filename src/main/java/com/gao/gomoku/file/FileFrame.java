package com.gao.gomoku.file;

import com.gao.gomoku.counter.AIChessCounter;
import com.gao.gomoku.counter.ChessCounter;
import com.gao.gomoku.counter.ChessCounter.Step;
import com.gao.gomoku.game.GomokuBoard;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    JFrame frame;

    public FileFrame( JFrame f, ChessCounter cc){
        frame = f;
    }

    public void loadGame(ChessCounter cc) {
        openFileChooser().ifPresent(filePath -> {
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
    private void loadSetting(ChessCounter cc, String fileName) throws IOException {
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

    public void saveGame(ChessCounter cc) {
        openFileChooser().ifPresent(filePath-> {
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
    private void saveSetting(ChessCounter cc, String fileName) throws IOException {
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

    private Optional<String> openFileChooser(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("gomoku file extention","gmk");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
                    return Optional.of(chooser.getSelectedFile().getAbsolutePath());
        }
        return Optional.empty();
    }
}

