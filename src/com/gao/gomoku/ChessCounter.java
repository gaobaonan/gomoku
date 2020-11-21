package com.gao.gomoku;

public class ChessCounter {

    protected int[][] chess;
    protected int[][] lastTimeChess;

    public ChessCounter(){
        chess = new int[15][15];
        lastTimeChess = new int[15][15];
        init();
    }

    //getter
    public int[][] getChess(){
        return chess;
    }

    public int getValueAt(int x, int y){
        return chess[x][y];
    }

    public void setChess(int x, int y, int v){
        chess[x][y] = v;
    }

    public int[][] getSetLastTimeChess(){
        return lastTimeChess;
    }

    //frisítés, visszalépésre készült
    public void refresh(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                lastTimeChess[i][j] = chess[i][j];
            }
        }
    }

    //visszalépés
    public void cancel(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                chess[i][j] = lastTimeChess[i][j];
            }
        }
    }

    //inicializálás
    public void init(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                chess[i][j] = 0;
                lastTimeChess[i][j] = chess[i][j];
            }
        }
    }

    //megnyerés
    //számol az öszzes darabnak követett sorozatoknak maximális hosszát
    //return 0: egyik sem nyer, 1: fekete megnyri, 2: fehér megnyeri
    public int winner(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                if(chess[i][j] == 1 || chess[i][j] == 2){
                    if(countAll(i,j) >= 5){
                        return chess[i][j];
                    }
                }
            }
        }
        return 0;
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
