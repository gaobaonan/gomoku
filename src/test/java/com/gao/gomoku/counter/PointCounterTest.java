package com.gao.gomoku.counter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * PointCounterTest
 * tesztelesi osztaly a PointCounter osztalynak
 * cc: tesztelesre hasznalt jatek palya
 */
public class PointCounterTest {

    AIChessCounter cc;
    PointCounter pc;

    /**
     * beallitas
     */
    @Before
    public void setUp(){
        cc = new AIChessCounter();
        pc = new PointCounter(cc);
    }

    /**
     * tamadasi szamolas teszteles 1
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
     * tamadasi szamolas teszteles 2
     */
    @Test
    public void testCountAttackPoint02(){
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,3,2);
        assertEquals(170, pc.countAttackPoint(1,3));
    }

    /**
     * tamadasi szamolas teszteles 3
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
     * tamadasi szamolas teszteles 4
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
     * vedekezesi szamolas teszteles 1
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
     * vedekezesi szamolas teszteles 2
     */
    @Test
    public void testCountDefenfPoint02(){
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(2,3,1);
        assertEquals(60, pc.countDefendPoint(1,3));
    }

    /**
     * vedekezesi szamolas teszteles 3
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
     * vedekezesi szamolas teszteles 4
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
