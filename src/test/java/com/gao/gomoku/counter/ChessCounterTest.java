package com.gao.gomoku.counter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChessCounterTest {

    ChessCounter cc;

    @Before
    public void setUp(){
        cc = new ChessCounter();
    }

    @Test
    public void testCountLine1() {
        cc.setChess(0,0,1);
        cc.setChess(1,1,1);
        cc.setChess(2,2,1);
        cc.setChess(3,3,1);
        cc.setChess(4,4,1);

        assertEquals(5, cc.countLine(0, 0, 3));
    }
}
