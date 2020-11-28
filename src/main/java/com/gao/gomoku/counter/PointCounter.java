package com.gao.gomoku.counter;

public class PointCounter {

    protected AIChessCounter cc;

    public PointCounter(AIChessCounter cc){
        this.cc = cc;
    }

    public int countPoint(int x, int y){
        int point;
        point = countAttackPoint(x,y) + countDefendPoint(x,y);
        return point;
    }

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
