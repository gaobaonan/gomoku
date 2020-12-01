package com.gao.gomoku.game;

import com.gao.gomoku.counter.ChessCounter;

import java.awt.*;

/**
 * Painting
 * segitoosztaly, statikus rajzolasi metodust tartalmaz
 * szepen rajzolhat egy gomoku jatek palyat
 * a kezdesi ponttol keztodik palyat rajzolni
 * X_P: kezdesi pontnak az x erteke
 * Y_P: kezdesi pontnak az y erteke
 * C_R: lerajzolt konek a sugara
 * B_S: palyan tartozott kockanak a hosszat
 * a palya kivul meg rajzolni fog egy kulso ablakot, hogy szep legyen az egesz palya
 * W_S: ablaknak a vastagsaga
 * SP: ablak es palya kozott helynek a szelessege
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
     * osszes konek a rajzolasa, egy megkapott dinamikus jatek palya alapjan rajzol
     * @param g: Graphics tipusú adott rajzpalya
     * @param cc: adott jatek palya
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
     * a palyanak es az ablaknak a rajzolasa
     * @param g: Graphics tipusú adott rajzpalya
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
