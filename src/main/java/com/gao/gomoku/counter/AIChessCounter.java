package com.gao.gomoku.counter;

/**
 * AIChessCounter
 * Ai visekédi szabályokat tárol
 */
public class AIChessCounter extends ChessCounter {

    /**
     * konstruktor
     * beállítás
     */
    public AIChessCounter() {
        super();
        gameMode = GameMode.SINGLE;
    }

    /**
     * AI lépés
     * felhívja a mustPressed metódust, ha false érték kap vissza, akkor felhívja a pointCounter metódust
     * a két metódus különben fog részletesen magyarázni
     */
    @Override
    public void step() {
        if (!mustPressed())
            pointCounter();
    }

    /**
     * megszámít az adott követnek két határa lyokas-e
     *
     * @param x    x érték
     * @param y    y érték
     * @param mode írány, = 0: sor, 1: oszlop, 2: jobb-ferde, 3: bal-felde
     * @return hány darab nem lyokas határa van
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
     * számolási logikus: 5 követ
     *
     * @param x x érték
     * @param y y érték
     * @return az adott helyen hány ilyen sorozatot szerepel
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
     * számolási logikus: 4 követ, 0 nem lyokas határ
     *
     * @param x x érték
     * @param y y érték
     * @return az adott helyen hány ilyen sorozatot szerepel
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
     * számolási logikus: 4 követ, 1 nem lyokas határ
     *
     * @param x x érték
     * @param y y érték
     * @return az adott helyen hány ilyen sorozatot szerepel
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
     * számolási logikus: 3 követ, 0 nem lyokas határ
     *
     * @param x x érték
     * @param y y érték
     * @return az adott helyen hány ilyen sorozatot szerepel
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
     * számolási logikus: 3 követ, 1 nem lyokas határ
     *
     * @param x x érték
     * @param y y érték
     * @return az adott helyen hány ilyen sorozatot szerepel
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
     * számolási logikus: 2 követ, 0 nem lyokas határ
     *
     * @param x x érték
     * @param y y érték
     * @return az adott helyen hány ilyen sorozatot szerepel
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
     * számolási logikus: 2 követ, 1 nem lyokas határ
     *
     * @param x x érték
     * @param y y érték
     * @return az adott helyen hány ilyen sorozatot szerepel
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
     * ellenőrizik a pláyán van-e olyan pont, ahol muszáj lerakni
     *
     * @param color: 1 fekete, védekezési szempont; 2 fehér, támadási szempont
     * @return létezik-e ilyen pont
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
     * ellenőrizik a pláyán van-e olyan pont, ahol muszáj lerakni
     * de kevésbé fontos mint az "első" logikus
     *
     * @param color: 1 fekete, védekezési szempont; 2 fehér, támadási szempont
     * @return létezik-e ilyen pont
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
     * ellenőrizik a pláyán van-e olyan pont, ahol muszáj lerakni
     * de kevésbé fontos mint az "első" és a "második" logikus
     *
     * @param color: 1 fekete, védekezési szempont; 2 fehér, támadási szempont
     * @return létezik-e ilyen pont
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
     * összefoglal minden "fontos" lépésre szükséges logikát, ha van ilyen lépés, akkor odarak a követet
     *
     * @return a pályán létezik-e ilyen "fontos" lépés
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
     * olyan számoló, hogy elszámol a pályán hol van a leghatékonyabb lépés, és odarak a követet
     * ezt csak akkor hívja fel a step metódus, ha mustPressed metódus false értéket ad vissza
     * azaz ezzel számított lépés kevésbé fontos
     * a PointCounter osztály segítségével valósítható
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
