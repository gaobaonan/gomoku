package com.gao.gomoku.counter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointCounterTest {

    AIChessCounter cc;
    PointCounter pc;

    @Before
    public void setUp(){
        cc = new AIChessCounter();
        pc = new PointCounter(cc);
    }

    /**
     * támadási számolás tesztelés 1
     */
    @Test
    public void testCountAttackPoint01(){
        cc.setChess(1,0,2);
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,3,2);
        assertEquals(210, pc.countAttackPoint(1,3));
    }

    /**
     * támadási számolás tesztelés 2
     */
    @Test
    public void testCountAttackPoint02(){
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,3,2);
        assertEquals(170, pc.countAttackPoint(1,3));
    }

    /**
     * támadási számolás tesztelés 3
     */
    @Test
    public void testCountAttackPoint03(){
        cc.setChess(1,0,2);
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,3,2);
        cc.setChess(3,3,2);
        cc.setChess(4,3,1);
        assertEquals(150, pc.countAttackPoint(1,3));
    }

    /**
     * támadási számolás tesztelés 4
     */
    @Test
    public void testCountAttackPoint04(){
        cc.setChess(1,0,2);
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,3,2);
        cc.setChess(3,3,1);
        assertEquals(131, pc.countAttackPoint(1,3));
    }

    /**
     * védekezési számolás tesztelés 1
     */
    @Test
    public void testCountDefenfPoint01(){
        cc.setChess(1,0,1);
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(2,3,1);
        assertEquals(50, pc.countDefendPoint(1,3));
    }

    /**
     * védekezési számolás tesztelés 2
     */
    @Test
    public void testCountDefenfPoint02(){
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(2,3,1);
        assertEquals(60, pc.countDefendPoint(1,3));
    }

    /**
     * védekezési számolás tesztelés 3
     */
    @Test
    public void testCountDefenfPoint03(){
        cc.setChess(1,0,1);
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(2,3,1);
        cc.setChess(3,3,1);
        cc.setChess(4,3,2);
        assertEquals(35, pc.countDefendPoint(1,3));
    }

    /**
     * védekezési számolás tesztelés 4
     */
    @Test
    public void testCountDefenfPoint04(){
        cc.setChess(1,0,1);
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(2,3,1);
        cc.setChess(3,3,2);
        assertEquals(31, pc.countDefendPoint(1,3));
    }
}
