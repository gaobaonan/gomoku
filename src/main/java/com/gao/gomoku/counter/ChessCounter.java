package com.gao.gomoku.counter;

import java.util.EmptyStackException;
import java.util.Stack;

import static javax.swing.JOptionPane.*;

/**
 * ChessCounter
 * Játék adat tárolási/ kezelési osztály
 * benne tartalmaz minden játék szükséges adat
 * gamemode: a játék módja, van SINGLE és MULTI érték
 * turn: most melyik játékos jön, van BLACK és WHITE érték
 * playable: most a játék végzik-e
 * chess: pálya adat leírás
 * stepStack: lépés tárolő, Step típusú értéket tárol
 * Step osztály: megmutat egy lépésnek a helye(x: x érték, y: y érték)
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
     * beállítás, adat initálizálás
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
     * getter: pálya adat
     *
     * @param x x érték
     * @param y y érték
     * @return adott helyi érték
     */
    public int getValueAt(int x, int y) {
        return chess[x][y];
    }

    /**
     * getter: playable
     *
     * @return playable érték
     */
    public boolean getPlayable() {
        return playable;
    }

    /**
     * getter: turn
     *
     * @return turn érték
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * getter: gameMode
     *
     * @return gameMode érték
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * getter: stepStack
     *
     * @return egész Stack(stepStack)
     */
    public Stack<Step> getStepStack() {
        Stack<Step> ss = new Stack<>();
        for (Step s : stepStack)
            ss.push(s);
        return ss;
    }

    /**
     * setter: pálya adat
     *
     * @param x x érték
     * @param y y érték
     * @param v adott helyi érték
     */
    public void setChess(int x, int y, int v) {
        chess[x][y] = v;
    }

    /**
     * setter: playable
     *
     * @param p playable érték
     */
    public void setPlayable(boolean p) {
        playable = p;
    }

    /**
     * setter: turn
     *
     * @param t turn érték
     */
    public void setTurn(Turn t) {
        turn = t;
    }

    /**
     * setter: stepStack
     *
     * @param x lépésnek x értek
     * @param y lépésnek y értek
     */
    public void pushToStack(int x, int y) {
        stepStack.push(new Step(x, y));
    }

    /**
     * visszalépés
     * egyedül játék esetén két lépést visszatér, multi esetén pedig egyet
     *
     * @return sikerül-e visszalépni(ha üres a pálya, akkor false)
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
     * inicializálás
     * minden pálya érték nullává válik
     */
    public void init() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                chess[i][j] = 0;
            }
        }
    }

    /**
     * megmutat hogy most végzik-e a játék, ha igen, akkor hogyan
     * a win függvény eredménytól függ
     */
    public void showOFWin() {
        int winner = this.win();
        String title = "Vége a játéknak";
        switch (winner) {
            case 1:
                showMessageDialog(null, "Fekete nyert!", title, INFORMATION_MESSAGE);
                break;
            case 2:
                showMessageDialog(null, "Fehér nyert!", title, INFORMATION_MESSAGE);
                break;
            case 3:
                showMessageDialog(null, "Döntetlen!", title, INFORMATION_MESSAGE);
                break;
            default:
                break;
        }
    }

    /**
     * AI lépés, az AIChessCounter osztályban valósítani fog
     */
    public void step() {
    }

    /**
     * számol az adott helyi követnek sorozatnak hosszát
     *
     * @param x    x érték
     * @param y    y érték
     * @param mode írány, = 0: sor, 1: oszlop, 2: jobb-ferde, 3: bal-felde
     * @return elszámolt hosszát
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
     * számol az adott helyi követnek sorozatoknak maximális hosszát
     *
     * @param x x érték
     * @param y y érték
     * @return elszámolt hosszát
     */
    protected int countAllLine(int x, int y) {
        int tmp1 = Math.max(countLine(x, y, 0), countLine(x, y, 1));
        int tmp2 = Math.max(countLine(x, y, 2), countLine(x, y, 3));
        return Math.max(tmp1, tmp2);
    }

    /**
     * arra számol, hogy a játék vegzik-e, és ha igen, hogyan
     *
     * @return 0: egyik sem nyer, 1: fekete megnyri, 2: fehér megnyeri, 3: döntetlen
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
