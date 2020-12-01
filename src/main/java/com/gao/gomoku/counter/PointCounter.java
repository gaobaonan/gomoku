package com.gao.gomoku.counter;

/**
 * PointCounter
 * egy adott palyan logikusan megkeres a leg hatekonyabb lepes
 * cc: a megadott palya
 */
public class PointCounter {

    protected AIChessCounter cc;

    /**
     * konstruktor
     * palya olvasas
     * @param cc
     */
    public PointCounter(AIChessCounter cc){
        this.cc = cc;
    }

    /**
     * hatas szamolasi logika, elszamol egy adott helynek a lepes hatasa
     * a logika ket metodussal bontok le
     * @param x x ertek
     * @param y y ertek
     * @return megszamolt lepes hatasa(integer tipusú ponttal jeloli meg)
     */
    public int countPoint(int x, int y){
        int point;
        point = countAttackPoint(x,y) + countDefendPoint(x,y);
        return point;
    }

    /**
     * hatas szamolasi logika, elszamol egy adott helynek a lepesnek a tamadasi hatasa
     * @param x x ertek
     * @param y y ertek
     * @return megszamolt lepes hatasa(integer tipusú ponttal jeloli meg)
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
     * hatas szamolasi logika, elszamol egy adott helynek a lepesnek a vedekezesi hatasa
     * @param x x ertek
     * @param y y ertek
     * @return megszamolt lepes hatasa(integer tipusú ponttal jeloli meg)
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
