package com.gao.gomoku.file;

import com.gao.gomoku.counter.AIChessCounter;
import com.gao.gomoku.counter.ChessCounter;
import com.gao.gomoku.counter.ChessCounter.Step;
import com.gao.gomoku.game.GomokuBoard;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
public class FileFrame extends JFrame {

    public enum IO{SAVE, LOAD}

    private final List<JButton> list = new ArrayList<>();
    private JButton exit;

    /**
     * konstruktor
     * modell megdontes, beallitas
     * @param io: mukodesi modell
     * @param cc: rajta dolgoztt jatek palya
     */
    public FileFrame(IO io, ChessCounter cc){
        setBounds(700,200,200,280);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        GridLayout layout = new GridLayout(7,1);
        layout.setVgap(5);
        setLayout(layout);
        setVisible(true);

        uisetting();
        buttonListener(io, cc);
    }

    /**
     * beolvasasi metodus, io = LOAD eseten dolgozik
     * @param cc: rajta dolgoztt jatek palya
     * @param fileName: beolvasott fajlnak a neve
     * @throws IOException kivetel
     */
    protected void loadSetting(ChessCounter cc, String fileName) throws IOException, NullPointerException {
        String name = "/save/" + fileName + ".gmk";
        try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(name)))){
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
     * kiirasi metodus, io = SAVE eseten dolgozik
     * @param cc: rajta dolgoztt jatek palya
     * @param fileName: kiirt fajlnak a neve
     * @throws IOException kivetel
     */
    private void saveSetting(ChessCounter cc, String fileName) throws IOException, URISyntaxException {


        String name = "/save/" + fileName + ".gmk";
        URL url = getClass().getResource(name);
        File f = new File(url.toURI().getPath());
        try (FileWriter fw = new FileWriter(f.getAbsoluteFile())){
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
     * UI beallitas
     */
    private void uisetting(){

        JLabel text = new JLabel("Select from file:");
        add(text);
        for(int i = 0; i < 5 ; i++){
            JButton button = new JButton("game" + (i+1));
            button.setName("gamedata" + (i+1));
            add(button);
            list.add(button);
        }
        exit = new JButton("close");
        add(exit);
    }

    /**
     * minden nyomogombnak az esemenynek beallitasa
     * @param io: mukodesi modell
     * @param cc: rajta dolgoztt jatek palya
     */
    private void buttonListener(IO io, ChessCounter cc){
        exit.addActionListener(e -> dispose());

        for (int i = 0; i < 5; i++) {
            final int serialNumber = i;
            list.get(i).addActionListener(e -> {
                try {
                    if(io == IO.LOAD) loadSetting(cc, list.get(serialNumber).getName());
                    else saveSetting(cc, list.get(serialNumber).getName());
                } catch (NullPointerException | IOException | URISyntaxException exception) {
                    showMessageDialog(null, "File not found!", "warning", ERROR_MESSAGE);
                }
                dispose();
            });
        }
    }
}

