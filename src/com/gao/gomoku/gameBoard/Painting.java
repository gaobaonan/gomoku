package com.gao.gomoku.gameBoard;

import com.gao.gomoku.counter.ChessCounter;

import java.awt.*;

public class Painting {

    public static void chessPainting(Graphics g, ChessCounter cc){

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                //黑子
                if (cc.getValueAt(i,j) == 1) {
                    int tempX = i * 30 + 30;
                    int tempY = j * 30 + 40;
                    g.fillOval(tempX - 9, tempY - 9, 18, 18);
                }
                //白子
                if (cc.getValueAt(i,j) == 2) {
                    int tempX = i * 30 + 30;
                    int tempY = j * 30 + 40;
                    g.setColor(Color.WHITE);
                    g.fillOval(tempX - 9, tempY - 9, 18, 18);
                    g.setColor(Color.BLACK);
                    g.drawOval(tempX - 9, tempY - 9, 18, 18);
                }
            }
        }
    }

    public static void ChessboardPainting(Graphics g){

        g.setColor(Color.orange);
        g.fillRect(15,25,450,450);
        g.setColor(Color.BLACK);

        //棋盘
        for (int i = 0; i < 15; i++) {
            g.drawLine(30, 40 + 30 * i, 450, 40 + 30 * i);
            g.drawLine(30 + 30 * i, 40, 30 + 30 * i, 460);
        }

        g.fillRect(13,23,4,454);
        g.fillRect(13,23,454,4);
        g.fillRect(463,23,4,454);
        g.fillRect(13,473,454,4);

        //标注点位
        g.fillOval(117, 157, 6, 6);
        g.fillOval(117, 397, 6, 6);
        g.fillOval(357, 397, 6, 6);
        g.fillOval(357, 157, 6, 6);
        g.fillOval(237, 277, 6, 6);
    }


}
