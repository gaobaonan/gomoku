package com.gao.gomoku.counter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * AIChessCounterTest
 * tesztelési osztály a AIChessCounter osztálynak
 * cc: tesztelésre használt játék pálya
 */
public class AIChessCounterTest {

    AIChessCounter cc;

    @Before
    public void setUp(){
        cc = new AIChessCounter();
    }

    @Test
    public void countLimitTest01(){
        cc.setChess(1,1,1);
        cc.setChess(2,1,1);
        cc.setChess(3,1,1);
        cc.setChess(4,1,1);
        cc.setChess(5,1,2);
        assertEquals(1, cc.countLimit(1,1,0));
    }

    @Test
    public void countLimitTest02(){
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(1,3,1);
        cc.setChess(1,4,1);
        cc.setChess(1,5,2);
        assertEquals(1, cc.countLimit(1,1,1));
    }

    @Test
    public void countLimitTest03(){
        cc.setChess(5,1,1);
        cc.setChess(4,2,1);
        cc.setChess(3,3,1);
        cc.setChess(2,4,1);
        cc.setChess(1,5,2);
        assertEquals(1, cc.countLimit(5,1,2));
    }

    @Test
    public void countLimitTest04(){
        cc.setChess(1,1,1);
        cc.setChess(2,2,1);
        cc.setChess(3,3,1);
        cc.setChess(4,4,1);
        cc.setChess(5,5,2);
        assertEquals(1, cc.countLimit(1,1,3));
    }

    @Test
    public void countLimitTest05(){
        cc.setChess(1,1,1);
        cc.setChess(1,2,2);
        cc.setChess(0,2,2);
        cc.setChess(2,2,2);
        assertEquals(0, cc.countLimit(1,1,0));
    }

    @Test
    public void countLimitTest06(){
        cc.setChess(1,1,1);
        cc.setChess(2,1,2);
        cc.setChess(0,2,2);
        cc.setChess(2,2,2);
        assertEquals(0, cc.countLimit(1,1,1));
    }

    @Test
    public void countLimitTest07(){
        cc.setChess(1,1,1);
        cc.setChess(2,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,2,2);
        assertEquals(0, cc.countLimit(1,1,2));
    }

    @Test
    public void countLimitTest08(){
        cc.setChess(1,1,1);
        cc.setChess(2,1,2);
        cc.setChess(1,2,2);
        cc.setChess(0,2,2);
        assertEquals(0, cc.countLimit(1,1,3));
    }

    @Test
    public void countLimitTest09(){
        cc.setChess(1,1,1);
        cc.setChess(2,1,1);
        cc.setChess(3,1,1);
        cc.setChess(4,1,1);
        assertEquals(0, cc.countLimit(1,1,0));
    }

    @Test
    public void countLimitTest10(){
        cc.setChess(0,1,1);
        cc.setChess(1,1,1);
        cc.setChess(2,1,1);
        cc.setChess(3,1,1);
        assertEquals(1, cc.countLimit(1,1,0));
    }

    /**
     * hiba testelés 1: üres az adott hely
     */
    @Test(expected = IllegalStateException.class)
    public void countLimitTest11(){
        cc.setChess(0,0,0);
        cc.countLimit(0,0,0);
    }

    /**
     * hiba testelés 2: hibás mode szám
     */
    @Test(expected = IllegalStateException.class)
    public void countLimitTest12(){
        cc.setChess(0,0,1);
        cc.countLimit(0,0,-1);
    }

    /**
     * öt darab követ támadás eset 1
     */
    @Test
    public void mustPressedLogic1Test01(){
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(1,3,2);
        cc.setChess(1,4,2);
        cc.mustPressedLogic1(2);
        assertEquals(2, Math.max(cc.getValueAt(1,0), cc.getValueAt(1,5)));
    }

    /**
     * öt darab követ támadás eset 2
     */
    @Test
    public void mustPressedLogic1Test02(){
        cc.setChess(1,0,2);
        cc.setChess(1,1,2);
        cc.setChess(1,3,2);
        cc.setChess(1,4,2);
        cc.mustPressedLogic1(2);
        assertEquals(2, cc.getValueAt(1,2));
    }

    /**
     * öt darab követ védekezés eset 1
     */
    @Test
    public void mustPressedLogic1Test03(){
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(1,3,1);
        cc.setChess(1,4,1);
        cc.setChess(1,5,2);
        cc.mustPressedLogic1(1);
        assertEquals(2, cc.getValueAt(1,0));
    }

    /**
     * öt darab követ védekezés eset 2
     */
    @Test
    public void mustPressedLogic1Test04(){
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(1,4,1);
        cc.setChess(1,5,1);
        cc.mustPressedLogic1(1);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * negy darab követ támadás eset 1
     */
    @Test
    public void mustPressedLogic2Test01(){
        cc.setChess(1,2,2);
        cc.setChess(1,3,2);
        cc.setChess(1,4,2);
        cc.mustPressedLogic2(2);
        assertEquals(2, Math.max(cc.getValueAt(1,1), cc.getValueAt(1,5)));
    }

    /**
     * negy darab követ támadás eset 2
     */
    @Test
    public void mustPressedLogic2Test02(){
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(1,4,2);
        cc.mustPressedLogic2(2);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * negy darab követ írány tesztelés 1
     */
    @Test
    public void mustPressedLogic2Test03(){
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(1,3,2);
        cc.mustPressedLogic2(2);
        assertEquals(0, cc.getValueAt(1,0));
    }

    /**
     * negy darab követ írány tesztelés 2
     */
    @Test
    public void mustPressedLogic2Test04(){
        cc.setChess(1,2,2);
        cc.setChess(1,3,2);
        cc.setChess(1,4,2);
        cc.setChess(1,6,1);
        cc.mustPressedLogic2(2);
        assertEquals(2, cc.getValueAt(1,1));
    }

    /**
     * negy-három darab követ támadás eset 1
     */
    @Test
    public void mustPressedLogic2Test05(){
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,3,2);
        cc.setChess(3,3,2);
        cc.setChess(4,3,2);
        cc.setChess(5,3,1);
        cc.mustPressedLogic2(2);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * negy-három darab követ támadás eset 2
     */
    @Test
    public void mustPressedLogic2Test06(){
        cc.setChess(1,2,2);
        cc.setChess(1,4,2);
        cc.setChess(0,3,2);
        cc.setChess(2,3,2);
        cc.setChess(3,3,2);
        cc.mustPressedLogic2(2);
        assertEquals(2, cc.getValueAt(1,3));
    }


    /**
     * dupla négy darab követ támadás eset 1
     */
    @Test
    public void mustPressedLogic2Test07(){
        cc.setChess(1,0,2);
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,3,2);
        cc.setChess(3,3,2);
        cc.setChess(4,3,2);
        cc.setChess(5,3,1);
        cc.mustPressedLogic2(2);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * dupla négy darab követ támadás eset 2
     */
    @Test
    public void mustPressedLogic2Test08(){
        cc.setChess(1,0,2);
        cc.setChess(1,1,2);
        cc.setChess(1,2,2);
        cc.setChess(2,3,2);
        cc.setChess(3,3,2);
        cc.setChess(4,3,2);
        cc.setChess(5,3,1);
        cc.mustPressedLogic2(2);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * négy darab követ védekezés eset 1
     */
    @Test
    public void mustPressedLogic2Test09(){
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(1,3,1);
        cc.mustPressedLogic2(1);
        assertEquals(2, Math.max(cc.getValueAt(1,0), cc.getValueAt(1,4)));
    }

    /**
     * négy darab követ védekezés eset 2
     */
    @Test
    public void mustPressedLogic2Test10(){
        cc.setChess(1,1,1);
        cc.setChess(1,3,1);
        cc.setChess(1,4,1);
        cc.mustPressedLogic2(1);
        assertEquals(2, cc.getValueAt(1,2));
    }

    /**
     * negy-három darab követ védekezés eset 1
     */
    @Test
    public void mustPressedLogic2Test11(){
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(2,3,1);
        cc.setChess(3,3,1);
        cc.setChess(4,3,1);
        cc.setChess(5,3,2);
        cc.mustPressedLogic2(1);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * negy-három darab követ védekezés eset 2
     */
    @Test
    public void mustPressedLogic2Test12(){
        cc.setChess(1,2,1);
        cc.setChess(1,4,1);
        cc.setChess(0,3,1);
        cc.setChess(2,3,1);
        cc.setChess(3,3,1);
        cc.mustPressedLogic2(1);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * dupla négy darab követ védekezés eset 1
     */
    @Test
    public void mustPressedLogic2Test13(){
        cc.setChess(1,0,1);
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(2,3,1);
        cc.setChess(3,3,1);
        cc.setChess(4,3,1);
        cc.setChess(5,3,2);
        cc.mustPressedLogic2(1);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * dupla négy darab követ védekezés eset 2
     */
    @Test
    public void mustPressedLogic2Test14(){
        cc.setChess(1,0,1);
        cc.setChess(1,1,1);
        cc.setChess(1,2,1);
        cc.setChess(2,3,1);
        cc.setChess(3,3,1);
        cc.setChess(4,3,1);
        cc.setChess(5,3,2);
        cc.mustPressedLogic2(1);
        assertEquals(2, cc.getValueAt(1,3));
    }

    /**
     * dupla három darab követ támadás eset 1
     */
    @Test
    public void mustPressedLogic3Test01(){
        cc.setChess(2,1,2);
        cc.setChess(2,2,2);
        cc.setChess(3,3,2);
        cc.setChess(4,3,2);
        cc.mustPressedLogic3(2);
        assertEquals(2, cc.getValueAt(2,3));
    }

    /**
     * dupla három darab követ támadás eset 2
     */
    @Test
    public void mustPressedLogic3Test02(){
        cc.setChess(2,2,2);
        cc.setChess(2,4,2);
        cc.setChess(1,3,2);
        cc.setChess(3,3,2);
        cc.mustPressedLogic3(2);
        assertEquals(2, cc.getValueAt(2,3));
    }

    /**
     * dupla három darab követ védekezés eset 1
     */
    @Test
    public void mustPressedLogic3Test03(){
        cc.setChess(2,1,1);
        cc.setChess(2,2,1);
        cc.setChess(3,3,1);
        cc.setChess(4,3,1);
        cc.mustPressedLogic3(1);
        assertEquals(2, cc.getValueAt(2,3));
    }

    /**
     * dupla három darab követ védezezés eset 2
     */
    @Test
    public void mustPressedLogic3Test04(){
        cc.setChess(2,2,1);
        cc.setChess(2,4,1);
        cc.setChess(1,3,1);
        cc.setChess(3,3,1);
        cc.mustPressedLogic3(1);
        assertEquals(2, cc.getValueAt(2,3));
    }
}
