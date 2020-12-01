package com.gao.gomoku.counter;

import java.util.EmptyStackException;
import java.util.Stack;

import static javax.swing.JOptionPane.*;

/**
 * ChessCounter
 * Jatek adat tarolasi/ kezelesi osztaly
 * benne tartalmaz minden jatek szukseges adat
 * gamemode: a jatek modja, van SINGLE es MULTI ertek
 * turn: most melyik jatekos jon, van BLACK es WHITE ertek
 * playable: most a jatek vegzik-e
 * chess: palya adat leiras
 * stepStack: lepes tarolo, Step tipusú erteket tarol
 * Step osztaly: megmutat egy lepesnek a helye(x: x ertek, y: y ertek)
 */
public class ChessCounter {

    public static class Step {
        private final int x;
        private final int y;

        public Step(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public enum GameMode {SINGLE, MULTI}

    public enum Turn {BLACK, WHITE}

    protected GameMode gameMode;
    protected Turn turn;
    protected boolean playable;

    protected int[][] chess;
    protected Stack<Step> stepStack;

    /**
     * konstruktor
     * beallitas, adat initalizalas
     */
    public ChessCounter() {
        chess = new int[15][15];
        stepStack = new Stack<>();
        turn = Turn.BLACK;
        playable = true;
        gameMode = GameMode.MULTI;
        init();
    }

    /**
     * getter: palya adat
     *
     * @param x x ertek
     * @param y y ertek
     * @return adott helyi ertek
     */
    public int getValueAt(int x, int y) {
        return chess[x][y];
    }

    /**
     * getter: playable
     *
     * @return playable ertek
     */
    public boolean getPlayable() {
        return playable;
    }

    /**
     * getter: turn
     *
     * @return turn ertek
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * getter: gameMode
     *
     * @return gameMode ertek
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * getter: stepStack
     *
     * @return egesz Stack(stepStack)
     */
    public Stack<Step> getStepStack() {
        Stack<Step> ss = new Stack<>();
        for (Step s : stepStack)
            ss.push(s);
        return ss;
    }

    /**
     * setter: palya adat
     *
     * @param x x ertek
     * @param y y ertek
     * @param v adott helyi ertek
     */
    public void setChess(int x, int y, int v) {
        chess[x][y] = v;
    }

    /**
     * setter: playable
     *
     * @param p playable ertek
     */
    public void setPlayable(boolean p) {
        playable = p;
    }

    /**
     * setter: turn
     *
     * @param t turn ertek
     */
    public void setTurn(Turn t) {
        turn = t;
    }

    /**
     * setter: stepStack
     *
     * @param x lepesnek x ertek
     * @param y lepesnek y ertek
     */
    public void pushToStack(int x, int y) {
        stepStack.push(new Step(x, y));
    }

    /**
     * visszalepes
     * egyedul jatek eseten ket lepest visszater, multi eseten pedig egyet
     *
     * @return sikerul-e visszalepni(ha ures a palya, akkor false)
     */
    public boolean cancel() {
        boolean changeTurn = !stepStack.empty();
        try {
            Step lastStep = stepStack.pop();
            setChess(lastStep.x, lastStep.y, 0);
            if (gameMode == GameMode.SINGLE) {
                Step lastStep2 = stepStack.pop();
                setChess(lastStep2.x, lastStep2.y, 0);
            }
        } catch (EmptyStackException exception) {
            throw new EmptyStackException();
        }
        return changeTurn;
    }

    /**
     * inicializalas
     * minden palya ertek nullava valik
     */
    public void init() {
        stepStack = new Stack<>();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                chess[i][j] = 0;
            }
        }
    }

    /**
     * megmutat hogy most vegzik-e a jatek, ha igen, akkor hogyan
     * a win fuggveny eredmenytol fugg
     */
    public void showOFWin() {
        int winner = this.win();
        String title = "game over";
        switch (winner) {
            case 1:
                showMessageDialog(null, "Black win!", title, INFORMATION_MESSAGE);
                break;
            case 2:
                showMessageDialog(null, "White win!", title, INFORMATION_MESSAGE);
                break;
            case 3:
                showMessageDialog(null, "Draw!", title, INFORMATION_MESSAGE);
                break;
            default:
                break;
        }
    }

    /**
     * AI lepes, az AIChessCounter osztalyban valositani fog
     */
    public void step() {
    }

    /**
     * szamol az adott helyi konek sorozatnak hosszat
     *
     * @param x    x ertek
     * @param y    y ertek
     * @param mode irany, = 0: sor, 1: oszlop, 2: jobb-ferde, 3: bal-felde
     * @return elszamolt hosszat
     */
    protected int countLine(int x, int y, int mode) {
        if (mode < 0 || mode > 3) throw new IllegalStateException();
        if (chess[x][y] == 0) return 0;
        int count = -1;
        int x1 = x;
        int x2 = x;
        int y1 = y;
        int y2 = y;

        while (true) {
            if (x1 < 0 || y1 < 0 || x1 >= 15 || y1 >= 15 || count >= 5) break;
            else if (chess[x1][y1] == chess[x][y]) {
                count++;
                switch (mode) {
                    case 0:
                        x1++;
                        break;
                    case 1:
                        y1++;
                        break;
                    case 2: {
                        x1++;
                        y1--;
                        break;
                    }
                    case 3: {
                        x1++;
                        y1++;
                        break;
                    }
                    default:
                        throw new IllegalStateException();
                }
            } else
                break;
        }

        while (true) {
            if (x2 < 0 || y2 < 0 || x2 >= 15 || y2 >= 15 || count >= 5) break;
            else if (chess[x2][y2] == chess[x][y]) {
                count++;
                switch (mode) {
                    case 0:
                        x2--;
                        break;
                    case 1:
                        y2--;
                        break;
                    case 2: {
                        x2--;
                        y2++;
                        break;
                    }
                    case 3: {
                        x2--;
                        y2--;
                        break;
                    }
                    default:
                        throw new IllegalStateException();
                }
            } else
                break;
        }
        return count;
    }

    /**
     * szamol az adott helyi konek sorozatoknak maximalis hosszat
     *
     * @param x x ertek
     * @param y y ertek
     * @return elszamolt hosszat
     */
    protected int countAllLine(int x, int y) {
        int tmp1 = Math.max(countLine(x, y, 0), countLine(x, y, 1));
        int tmp2 = Math.max(countLine(x, y, 2), countLine(x, y, 3));
        return Math.max(tmp1, tmp2);
    }

    /**
     * arra szamol, hogy a jatek vegzik-e, es ha igen, hogyan
     *
     * @return 0: egyik sem nyer, 1: fekete megnyri, 2: feher megnyeri, 3: dontetlen
     */
    protected int win() {
        if (!stepStack.empty()) {
            int x = stepStack.peek().getX();
            int y = stepStack.peek().getY();
            if (countAllLine(x, y) >= 5) {
                playable = false;
                return chess[x][y];
            }

            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if (chess[i][j] == 0) return 0;
                }
            }
            playable = false;
            return 3;
        }
        return 0;
    }

}
