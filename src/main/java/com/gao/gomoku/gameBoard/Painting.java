package com.gao.gomoku.gameBoard;

import com.gao.gomoku.counter.ChessCounter;

import java.awt.*;

public class Painting {

    private static final int xStartPoint = 30;
    private static final int yStartPoint = 40;

    private static final int chessRadius = 9;

    private static final int blockSzie = 30;
    private static final int windowThick = 4;
    private static final int spacing = 15;
    private static final int circleRadius = 3;


    public static void chessPainting(Graphics g, ChessCounter cc){

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                //
                if (cc.getValueAt(i,j) == 1) {
                    int tempX = i * blockSzie + xStartPoint;
                    int tempY = j * blockSzie + yStartPoint;
                    g.fillOval(tempX - chessRadius, tempY - chessRadius, chessRadius*2, chessRadius*2);
                }
                //
                if (cc.getValueAt(i,j) == 2) {
                    int tempX = i * blockSzie + xStartPoint;
                    int tempY = j * blockSzie + yStartPoint;
                    g.setColor(Color.WHITE);
                    g.fillOval(tempX - chessRadius, tempY - chessRadius, chessRadius*2, chessRadius*2);
                    g.setColor(Color.BLACK);
                    g.drawOval(tempX - chessRadius, tempY - chessRadius, chessRadius*2, chessRadius*2);
                }
            }
        }
    }

    public static void ChessboardPainting(Graphics g){

        g.setColor(Color.orange);
        g.fillRect(15,25,450,450);
        g.setColor(Color.BLACK);

        //
        for (int i = 0; i < 15; i++) {
            g.drawLine(xStartPoint, yStartPoint + blockSzie * i, xStartPoint + blockSzie * 14, yStartPoint + blockSzie * i);
            g.drawLine(xStartPoint + blockSzie * i, yStartPoint, xStartPoint + blockSzie * i, yStartPoint + blockSzie * 14);
        }

        g.fillRect(xStartPoint - windowThick/2 - spacing,yStartPoint - windowThick/2 -spacing,windowThick,blockSzie*15 + windowThick);
        g.fillRect(xStartPoint - windowThick/2 - spacing,yStartPoint - windowThick/2 -spacing,blockSzie*15 + windowThick,windowThick);
        g.fillRect(xStartPoint - windowThick/2 - spacing + blockSzie*15,yStartPoint - windowThick/2 -spacing, windowThick,blockSzie*15 + windowThick);
        g.fillRect(xStartPoint - windowThick/2 - spacing,yStartPoint - windowThick/2 -spacing + blockSzie*15,blockSzie*15 + windowThick,windowThick);

        //
        g.fillOval(xStartPoint + blockSzie*3 - circleRadius, yStartPoint + blockSzie*3 - circleRadius, circleRadius*2, circleRadius*2);
        g.fillOval(xStartPoint + blockSzie*3 - circleRadius, yStartPoint + blockSzie*11 - circleRadius, circleRadius*2, circleRadius*2);
        g.fillOval(xStartPoint + blockSzie*11 - circleRadius, yStartPoint + blockSzie*3 - circleRadius, circleRadius*2, circleRadius*2);
        g.fillOval(xStartPoint + blockSzie*11 - circleRadius, yStartPoint + blockSzie*11 - circleRadius, circleRadius*2, circleRadius*2);
        g.fillOval(xStartPoint + blockSzie*7 - circleRadius, yStartPoint + blockSzie*7 - circleRadius, circleRadius*2, circleRadius*2);
    }


}
