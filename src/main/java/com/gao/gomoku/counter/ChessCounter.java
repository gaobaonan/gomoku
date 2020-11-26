package com.gao.gomoku.counter;

import java.util.EmptyStackException;
import java.util.Stack;

import static javax.swing.JOptionPane.*;

public class ChessCounter {

    public class Step{
        private int x;
        private int y;
        public Step(int x, int y){ this.x = x; this.y = y; }
        public int getX(){ return x; }
        public int getY(){ return y; }
    }

    public enum GameMode{SINGLE, MULTI}
    public enum Turn{BLACK, WHITE}

    protected GameMode gameMode;
    protected Turn turn;
    protected boolean playable;

    protected int[][] chess;
    Stack<Step> stepStack;

    public ChessCounter(){
        chess = new int[15][15];
        stepStack = new Stack<Step>();
        turn = Turn.BLACK;
        playable = true;
        gameMode = GameMode.MULTI;
        init();
    }

    //getter
    public int getValueAt(int x, int y){
        return chess[x][y];
    }

    public boolean getPlayable() { return playable; }

    public Turn getTurn() { return turn; }

    public GameMode getGameMode() { return gameMode; }

    public Stack<Step> getStepStack(){
        Stack<Step> ss = new Stack<Step>();
        for(Step s : stepStack)
            ss.push(s);
        return ss;
    }

    //setter
    public void setChess(int x, int y, int v) { chess[x][y] = v; }

    public void setPlayable(boolean p) { playable = p; }

    public void setTurn(Turn t) { turn = t; }

    public void pushToStack(int x, int y){ stepStack.push(new Step(x,y)); }

    //visszalépés
    public boolean cancel(){
        boolean changeTurn = !stepStack.empty();
        try {
            Step lastStep = stepStack.pop();
            setChess(lastStep.x, lastStep.y, 0);
            if (gameMode == GameMode.SINGLE){
                Step lastStep2 = stepStack.pop();
                setChess(lastStep2.x, lastStep2.y, 0);
            }
        }
        catch (EmptyStackException exception){
            showMessageDialog(null, "Üres a pálya!", "", INFORMATION_MESSAGE);
        }
        return changeTurn;
    }

    //inicializálás
    public void init(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                chess[i][j] = 0;
            }
        }
    }

    //megnyerés
    //számol az öszzes darabnak követett sorozatoknak maximális hosszát
    //return 0: egyik sem nyer, 1: fekete megnyri, 2: fehér megnyeri
    public void win(){
        if(!stepStack.empty()){
            int x = stepStack.peek().getX();
            int y = stepStack.peek().getY();
            if(countAll(x,y) >= 5){
                playable = false;
                String winner;
                if( chess[x][y] == 1) winner = "Fekate";
                else winner = "Fehér";
                showMessageDialog(null, winner + " nyert!", "Vége a játéknak", INFORMATION_MESSAGE);
                return;
            }

            for(int i = 0; i < 15; i++){
                for(int j = 0; j < 15; j++){
                    if(chess[i][j] == 0) return;
                }
            }
            playable = false;
            showMessageDialog(null, "Húz!", "Vége a játéknak", INFORMATION_MESSAGE);
        }

    }

    //no-op
    //sss
    public void step(){ }

    //számol az adott helyi darabnak követett sorozatnak hosszát
    //mode = 0: sor, 1: oszlop, 2: jobb-ferde, 3: bal-felde
    protected int countLine(int x, int y, int mode){
        int count = -1;
        int x1 = x, x2 = x, y1 = y, y2 = y;

        while (true){
            if(x1 < 0 || y1 < 0 || x1 >= 15 || y1 >= 15 || count >= 5)  break;
            else if(chess[x1][y1] == chess[x][y]) {
                count++;
                switch(mode){
                    case 0:
                        x1++;
                        break;
                    case 1:
                        y1++;
                        break;
                    case 2:
                        x1++; y1--;
                        break;
                    case 3:
                        x1++; y1++;
                        break;}
            }
            else
                break;
        }

        while (true){
            if(x2 < 0 || y2 < 0 || x2 >= 15 || y2 >= 15 || count >= 5)  break;
            else if(chess[x2][y2] == chess[x][y]) {
                count++;
                switch(mode){
                    case 0:
                        x2--;
                        break;
                    case 1:
                        y2--;
                        break;
                    case 2:
                        x2--; y2++;
                        break;
                    case 3:
                        x2--; y2--;
                        break;
                }
            }
            else
                break;
        }
        return count;
    }

    //számol az adott helyi darabnak követett sorozatoknak maximális hosszát
    protected int countAll(int x, int y){
        int tmp1 = Math.max(countLine(x, y, 0), countLine(x, y, 1));
        int tmp2 = Math.max(countLine(x, y, 2), countLine(x, y, 3));
        int lengthMax = Math.max(tmp1, tmp2);

        return lengthMax;
    }
}
