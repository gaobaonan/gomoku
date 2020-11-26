package com.gao.gomoku.file;

import com.gao.gomoku.counter.AIChessCounter;
import com.gao.gomoku.counter.ChessCounter;
import com.gao.gomoku.gameBoard.GomokuBoard;
import com.gao.gomoku.counter.ChessCounter.Step;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class FileFrame extends JFrame {

    public enum IO{ save, load }

    private JLabel text;
    private List<JButton> list = new ArrayList();
    private JButton exit;


    public FileFrame(IO io, ChessCounter cc){
        setBounds(700,200,200,280);
        GridLayout layout = new GridLayout(7,1);
        layout.setVgap(5);
        setLayout(layout);
        setVisible(true);

        UISetting();
        buttonSetting(io, cc);
    }

    private void UISetting(){
        text = new JLabel("válaszjon:");
        add(text);
        for(int i = 0; i < 5 ; i++){
            list.add(new JButton("game" + (i+1)));
            list.get(i).setName("gamedata" + (i+1));
            add(list.get(i));
        }
        exit = new JButton("kilépés");
        add(exit);
    }

    private void buttonSetting(IO io, ChessCounter cc){
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        for (int i = 0; i < 5; i++) {
            final int serialNumber = i;
            list.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(io == IO.load) loadSetting(cc, list.get(serialNumber).getName());
                        else saveSetting(cc, list.get(serialNumber).getName());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    dispose();
                }
            });
        }
    }

    private void saveSetting(ChessCounter cc, String buttonName) throws IOException {

        String name = "save/" + buttonName + ".gmk";
        FileWriter fw = null;
        try {
            fw = new FileWriter(name);
        }
        catch (IOException exc){
            System.out.println("file not found");
        }

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
        fw.close();
    }

    private void loadSetting(ChessCounter cc, String buttonName) throws IOException {
        String name = "save/" + buttonName + ".gmk";
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
        catch(FileNotFoundException exception){
            showMessageDialog(null, "Fájl nem található!", "Hiba", ERROR_MESSAGE);
        }
    }
}


