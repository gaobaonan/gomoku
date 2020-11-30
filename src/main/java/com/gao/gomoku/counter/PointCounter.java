package com.gao.gomoku.counter;

/**
 * PointCounter
 * egy adott pályán logikusan megkeres a leg hatékonyabb lépés
 * cc: a megadott pálya
 */
public class PointCounter {

    protected AIChessCounter cc;

    /**
     * konstruktor
     * pálya olvasás
     * @param cc
     */
    public PointCounter(AIChessCounter cc){
        this.cc = cc;
    }

    /**
     * hatás számolási logika, elszámol egy adott helynek a lépés hatása
     * a logika két metódussal bontok le
     * @param x x érték
     * @param y y érték
     * @return megszámolt lépés hatása(integer típusú ponttal jelöli meg)
     */
    public int countPoint(int x, int y){
        int point;
        point = countAttackPoint(x,y) + countDefendPoint(x,y);
        return point;
    }

    /**
     * hatás számolási logika, elszámol egy adott helynek a lépésnek a támadási hatása
     * @param x x érték
     * @param y y érték
     * @return megszámolt lépés hatása(integer típusú ponttal jelöli meg)
     */
    protected int countAttackPoint(int x, int y){
        if(cc.getValueAt(x,y) != 0) throw new IllegalStateException("place is not empty!");
        int point = 0;
        cc.setChess(x,y,2);

        point += cc.blockedFour(x,y) * 130;
        point += cc.livedThree(x,y) * 90;
        point += cc.blockedThree(x,y) * 20;
        point += cc.livedTwo(x,y) * 80;
        point += cc.blockedTwo(x,y);

        cc.setChess(x,y,0);
        return point;
    }

    /**
     * hatás számolási logika, elszámol egy adott helynek a lépésnek a védekezési hatása
     * @param x x érték
     * @param y y érték
     * @return megszámolt lépés hatása(integer típusú ponttal jelöli meg)
     */
    protected int countDefendPoint(int x, int y){
        if(cc.getValueAt(x,y) != 0) throw new IllegalStateException("place is not empty!");
        int point = 0;
        cc.setChess(x,y,1);

        point += cc.blockedFour(x,y) * 30;
        point += cc.livedThree(x,y) * 40;
        point += cc.blockedThree(x,y) * 5;
        point += cc.livedTwo(x,y) * 20;
        point += cc.blockedTwo(x,y);

        cc.setChess(x,y,0);
        return point;
    }
}
