package com.gao.gomoku;

public class Main {
    public static void main(String[] args){
        //JFrame gomoku = new Wu();
        //gomoku.setVisible(true);

        GomokuBoard gm = new GomokuBoard(0);
        gm.setVisible(true);

        AIChessCounter aicc = new AIChessCounter();
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                System.out.println(aicc.getChess()[i][j]);
            }
        }
    }
}
