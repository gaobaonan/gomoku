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
 * statikus metodusokat tartalmazo osztaly, mely a jatekok kiirasaert es beolvasasert felelos
 */
public class FileFrame {

    /**
     * beolvasasi metodus
     * @param f: rajta dolgozott frame
     * @param cc: rajta dolgoztt jatek palya
     */
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
     * beolvasasi metodus segelete
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

    /**
     * kiirasi metodus
     * @param f: rajta dolgozott frame
     * @param cc: rajta dolgoztt jatek palya
     */
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
     * kiirasi metodus
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

    /**
     * fajlvalastasi ablakot hivas metodus
     * @param f: rajta dolgozott frame
     * @return munka helyzet, sikerul-e fajlt olvasni
     */
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

