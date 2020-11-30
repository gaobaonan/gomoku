package com.gao.gomoku.game;

import com.gao.gomoku.counter.ChessCounter;

import java.awt.*;

/**
 * Painting
 * segítőosztály, statikus rajzolási metódust tartalmaz
 * szépen rajzolhat egy gomoku játék pályát
 * a kezdési ponttól keztödik pályát rajzolni
 * X_P: kézdési pontnak az x értéke
 * Y_P: kézdési pontnak az y értéke
 * C_R: lerajzolt követnek a sugára
 * B_S: pályán tartozott kockának a hosszát
 * a pálya kivül még rajzolni fog egy külső ablakot, hogy szép legyen az egész pálya
 * W_S: ablaknak a vastagsága
 * SP: ablak és pálya között helynek a szélessége
 */
public class Painting {

    private static final int X_P = 30;
    private static final int Y_P = 40;
    private static final int C_R = 9;
    private static final int B_S = 30;
    private static final int P_R = 3;
    private static final int W_S = 4;
    private static final int SP = 15;

    /**
     * összes követnek a rajzolása, egy megkapott dinamikus játék pálya alapján rajzol
     * @param g: Graphics típusú adott rajzpálya
     * @param cc: adott játék pálya
     */
    public static void chessPainting(Graphics g, ChessCounter cc){

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                //
                if (cc.getValueAt(i,j) == 1) {
                    int tempX = i * B_S + X_P;
                    int tempY = j * B_S + Y_P;
                    g.fillOval(tempX - C_R, tempY - C_R, C_R *2, C_R *2);
                }
                //
                if (cc.getValueAt(i,j) == 2) {
                    int tempX = i * B_S + X_P;
                    int tempY = j * B_S + Y_P;
                    g.setColor(Color.WHITE);
                    g.fillOval(tempX - C_R, tempY - C_R, C_R *2, C_R *2);
                    g.setColor(Color.BLACK);
                    g.drawOval(tempX - C_R, tempY - C_R, C_R *2, C_R *2);
                }
            }
        }
    }

    /**
     * a pályának és az ablaknak a rajzolása
     * @param g: Graphics típusú adott rajzpálya
     */
    public static void chessboardPainting(Graphics g){

        g.setColor(Color.orange);
        g.fillRect(15,25,450,450);
        g.setColor(Color.BLACK);

        //
        for (int i = 0; i < 15; i++) {
            g.drawLine(X_P, Y_P + B_S * i, X_P + B_S * 14, Y_P + B_S * i);
            g.drawLine(X_P + B_S * i, Y_P, X_P + B_S * i, Y_P + B_S * 14);
        }

        g.fillRect(X_P - W_S /2 - SP, Y_P - W_S /2 - SP, W_S, B_S *15 + W_S);
        g.fillRect(X_P - W_S /2 - SP, Y_P - W_S /2 - SP, B_S *15 + W_S, W_S);
        g.fillRect(X_P - W_S /2 - SP + B_S *15, Y_P - W_S /2 - SP, W_S, B_S *15 + W_S);
        g.fillRect(X_P - W_S /2 - SP, Y_P - W_S /2 - SP + B_S *15, B_S *15 + W_S, W_S);

        //
        g.fillOval(X_P + B_S *3 - P_R, Y_P + B_S *3 - P_R, P_R *2, P_R *2);
        g.fillOval(X_P + B_S *3 - P_R, Y_P + B_S *11 - P_R, P_R *2, P_R *2);
        g.fillOval(X_P + B_S *11 - P_R, Y_P + B_S *3 - P_R, P_R *2, P_R *2);
        g.fillOval(X_P + B_S *11 - P_R, Y_P + B_S *11 - P_R, P_R *2, P_R *2);
        g.fillOval(X_P + B_S *7 - P_R, Y_P + B_S *7 - P_R, P_R *2, P_R *2);
    }


}
