package com.gao.gomoku.counter;

/**
 * AIChessCounter
 * Ai visekedi szabalyokat tarol
 */
public class AIChessCounter extends ChessCounter {

    /**
     * konstruktor
     * beallitas
     */
    public AIChessCounter() {
        super();
        gameMode = GameMode.SINGLE;
    }

    /**
     * AI lepes
     * felhivja a mustPressed metodust, ha false ertek kap vissza, akkor felhivja a pointCounter metodust
     * a ket metodus kulonben fog reszletesen magyarazni
     */
    @Override
    public void step() {
        if (!mustPressed())
            pointCounter();
    }

    /**
     * megszamit az adott konek ket hatara lyokas-e
     *
     * @param x    x ertek
     * @param y    y ertek
     * @param mode irany, = 0: sor, 1: oszlop, 2: jobb-ferde, 3: bal-felde
     * @return hany darab nem lyokas hatara van
     */
    protected int countLimit(int x, int y, int mode) {
        if (chess[x][y] == 0 || mode < 0 || mode > 3) {
            throw new IllegalStateException();
        }
        int hasLimit = 0;
        int x1 = x;
        int x2 = x;
        int y1 = y;
        int y2 = y;

        while (true) {
            if (x1 < 0 || y1 < 0 || x1 >= 15 || y1 >= 15 || (chess[x1][y1] != 0 && chess[x1][y1] != chess[x][y])) {
                hasLimit++;
                break;
            } else if (chess[x1][y1] == 0)
                break;
            else {
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
            }
        }

        while (true) {
            if (x2 < 0 || y2 < 0 || x2 >= 15 || y2 >= 15 || (chess[x2][y2] != 0 && chess[x2][y2] != chess[x][y])) {
                hasLimit++;
                break;
            } else if (chess[x2][y2] == 0)
                break;
            else {
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
            }
        }
        return hasLimit;
    }

    /**
     * szamolasi logikus: 5 ko
     *
     * @param x x ertek
     * @param y y ertek
     * @return az adott helyen hany ilyen sorozatot szerepel
     */
    private int five(int x, int y) {
        int count = 0;
        for (int m = 0; m < 4; m++) {
            if (countLine(x, y, m) >= 5)
                count++;
        }
        return count;
    }

    /**
     * szamolasi logikus: 4 ko, 0 nem lyokas hatar
     *
     * @param x x ertek
     * @param y y ertek
     * @return az adott helyen hany ilyen sorozatot szerepel
     */
    private int livedFour(int x, int y) {
        int count = 0;
        for (int m = 0; m < 4; m++) {
            if (countLine(x, y, m) == 4 && countLimit(x, y, m) == 0)
                count++;
        }
        return count;
    }

    /**
     * szamolasi logikus: 4 ko, 1 nem lyokas hatar
     *
     * @param x x ertek
     * @param y y ertek
     * @return az adott helyen hany ilyen sorozatot szerepel
     */
    protected int blockedFour(int x, int y) {
        int count = 0;
        for (int m = 0; m < 4; m++) {
            if (countLine(x, y, m) == 4 && countLimit(x, y, m) == 1)
                count++;
        }
        return count;
    }

    /**
     * szamolasi logikus: 3 ko, 0 nem lyokas hatar
     *
     * @param x x ertek
     * @param y y ertek
     * @return az adott helyen hany ilyen sorozatot szerepel
     */
    protected int livedThree(int x, int y) {
        int count = 0;
        for (int m = 0; m < 4; m++) {
            if (countLine(x, y, m) == 3 && countLimit(x, y, m) == 0)
                count++;
        }
        return count;
    }

    /**
     * szamolasi logikus: 3 ko, 1 nem lyokas hatar
     *
     * @param x x ertek
     * @param y y ertek
     * @return az adott helyen hany ilyen sorozatot szerepel
     */
    protected int blockedThree(int x, int y) {
        int count = 0;
        for (int m = 0; m < 4; m++) {
            if (countLine(x, y, m) == 3 && countLimit(x, y, m) == 1)
                count++;
        }
        return count;
    }

    /**
     * szamolasi logikus: 2 ko, 0 nem lyokas hatar
     *
     * @param x x ertek
     * @param y y ertek
     * @return az adott helyen hany ilyen sorozatot szerepel
     */
    protected int livedTwo(int x, int y) {
        int count = 0;
        for (int m = 0; m < 4; m++) {
            if (countLine(x, y, m) == 2 && countLimit(x, y, m) == 0)
                count++;
        }
        return count;
    }


    /**
     * szamolasi logikus: 2 ko, 1 nem lyokas hatar
     *
     * @param x x ertek
     * @param y y ertek
     * @return az adott helyen hany ilyen sorozatot szerepel
     */
    protected int blockedTwo(int x, int y) {
        int count = 0;
        for (int m = 0; m < 4; m++) {
            if (countLine(x, y, m) == 2 && countLimit(x, y, m) == 1)
                count++;
        }
        return count;
    }

    /**
     * ellenorizik a playan van-e olyan pont, ahol muszaj lerakni
     *
     * @param color: 1 fekete, vedekezesi szempont; 2 feher, tamadasi szempont
     * @return letezik-e ilyen pont
     */
    protected boolean mustPressedLogic1(int color) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (chess[i][j] == 0) {
                    chess[i][j] = color;
                    if (five(i, j) >= 1) {
                        chess[i][j] = 2;
                        pushToStack(i, j);
                        return true;
                    }
                    chess[i][j] = 0;
                }
            }
        }
        return false;
    }

    /**
     * ellenorizik a playan van-e olyan pont, ahol muszaj lerakni
     * de kevesbe fontos mint az "elso" logikus
     *
     * @param color: 1 fekete, vedekezesi szempont; 2 feher, tamadasi szempont
     * @return letezik-e ilyen pont
     */
    protected boolean mustPressedLogic2(int color) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (chess[i][j] == 0) {
                    chess[i][j] = color;
                    if (livedFour(i, j) >= 1 ||
                            blockedFour(i, j) >= 2 ||
                            (blockedFour(i, j) >= 1 && livedThree(i, j) >= 1)) {
                        chess[i][j] = 2;
                        pushToStack(i, j);
                        return true;
                    }
                    chess[i][j] = 0;
                }
            }
        }
        return false;
    }

    /**
     * ellenorizik a playan van-e olyan pont, ahol muszaj lerakni
     * de kevesbe fontos mint az "elso" es a "masodik" logikus
     *
     * @param color: 1 fekete, vedekezesi szempont; 2 feher, tamadasi szempont
     * @return letezik-e ilyen pont
     */
    protected boolean mustPressedLogic3(int color) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (chess[i][j] == 0) {
                    chess[i][j] = color;
                    if (livedThree(i, j) >= 2) {
                        chess[i][j] = 2;
                        pushToStack(i, j);
                        return true;
                    }
                    chess[i][j] = 0;
                }
            }
        }
        return false;
    }

    /**
     * osszefoglal minden "fontos" lepesre szukseges logikat, ha van ilyen lepes, akkor odarak a kovet
     *
     * @return a palyan letezik-e ilyen "fontos" lepes
     */
    private boolean mustPressed() {
        return mustPressedLogic1(2) ||
                mustPressedLogic1(1) ||
                mustPressedLogic2(1) ||
                mustPressedLogic2(2) ||
                mustPressedLogic3(1) ||
                mustPressedLogic3(2);
    }

    /**
     * olyan szamolo, hogy elszamol a palyan hol van a leghatekonyabb lepes, es odarak a kovet
     * ezt csak akkor hivja fel a step metodus, ha mustPressed metodus false erteket ad vissza
     * azaz ezzel szamitott lepes kevesbe fontos
     * a PointCounter osztaly segitsegevel valosithato
     */
    private void pointCounter() {
        int x = -1;
        int y = -1;
        int maxPoint = -1;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (chess[i][j] == 0) {
                    PointCounter pc = new PointCounter(this);
                    int point = pc.countPoint(i, j);
                    if (point > maxPoint) {
                        x = i;
                        y = j;
                        maxPoint = point;
                    }
                }
            }
        }
        chess[x][y] = 2;
        pushToStack(x, y);
    }

}
