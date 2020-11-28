package com.gao.gomoku.counter;

import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;

public class ChessCounterTest {

    ChessCounter cc;

    @Before
    public void setUp(){
        cc = new ChessCounter();
    }

    @Test
    public void testCountLine01() {
        cc.setChess(0,0,1);
        cc.setChess(1,0,1);
        cc.setChess(2,0,1);
        cc.setChess(3,0,1);
        cc.setChess(4,0,1);
        assertEquals(5, cc.countLine(0, 0, 0));
    }

    @Test
    public void testCountLine02() {
        cc.setChess(0,0,1);
        cc.setChess(0,1,1);
        cc.setChess(0,2,1);
        cc.setChess(0,3,1);
        cc.setChess(0,4,1);
        assertEquals(5, cc.countLine(0, 0, 1));
    }

    @Test
    public void testCountLine03() {
        cc.setChess(0,5,1);
        cc.setChess(1,4,1);
        cc.setChess(2,3,1);
        cc.setChess(3,2,1);
        cc.setChess(4,1,1);
        assertEquals(5, cc.countLine(0, 5, 2));
    }

    @Test
    public void testCountLine04() {
        cc.setChess(0,0,1);
        cc.setChess(1,1,1);
        cc.setChess(2,2,1);
        cc.setChess(3,3,1);
        cc.setChess(4,4,1);
        assertEquals(5, cc.countLine(0, 0, 3));
    }

    @Test
    public void testCountLine05() {
        cc.setChess(1,1,1);
        cc.setChess(1,0,1);
        cc.setChess(0,0,1);
        cc.setChess(0,2,1);
        assertEquals(1, cc.countLine(1, 1, 0));
    }

    @Test
    public void testCountLine06() {
        cc.setChess(1, 1, 1);
        cc.setChess(0, 1, 1);
        cc.setChess(0, 0, 1);
        cc.setChess(0, 2, 1);
        assertEquals(1, cc.countLine(1, 1, 1));
    }

    @Test
    public void testCountLine07() {
        cc.setChess(1,1,1);
        cc.setChess(1,0,1);
        cc.setChess(0,1,1);
        cc.setChess(2,2,1);
        assertEquals(1, cc.countLine(1, 1, 2));
    }

    @Test
    public void testCountLine08() {
        cc.setChess(1,1,1);
        cc.setChess(1,0,1);
        cc.setChess(0,1,1);
        cc.setChess(0,2,1);
        assertEquals(1, cc.countLine(1, 1, 3));
    }

    @Test
    public void testCountLine09() {
        cc.setChess(1,1,0);
        assertEquals(0, cc.countLine(1, 1, 0));
    }

    /**
     * hiba tesztelés: hibás mode szám
     */
    @Test(expected = IllegalStateException.class)
    public void testCountLine10() {
        cc.countLine(1, 1, -1);
    }

    @Test
    public void testCountAll01(){
        cc.setChess(0,0,1);
        cc.setChess(0,1,1);
        cc.setChess(0,2,1);
        cc.setChess(0,3,1);
        cc.setChess(0,4,1);
        assertEquals(5, cc.countAllLine(0,0));
    }

    @Test
    public void testCountAll02() {
        cc.setChess(0, 0, 1);
        cc.setChess(1, 0, 1);
        cc.setChess(2, 0, 1);
        cc.setChess(3, 0, 1);
        cc.setChess(4, 0, 1);
        assertEquals(5, cc.countAllLine(0, 0));
    }

    @Test
    public void testCountAll03() {
        cc.setChess(0, 0, 1);
        cc.setChess(1, 1, 1);
        cc.setChess(2, 2, 1);
        cc.setChess(3, 3, 1);
        cc.setChess(4, 4, 1);
        assertEquals(5, cc.countAllLine(0, 0));
    }

    @Test
    public void testCountAll04() {
        cc.setChess(0, 4, 1);
        cc.setChess(1, 3, 1);
        cc.setChess(2, 2, 1);
        cc.setChess(3, 1, 1);
        cc.setChess(4, 0, 1);
        assertEquals(5, cc.countAllLine(0, 4));
    }

    @Test
    public void testCountAll05() {
        cc.setChess(0, 0, 1);
        cc.setChess(1, 0, 1);
        cc.setChess(2, 0, 1);
        cc.setChess(3, 0, 1);
        cc.setChess(4, 0, 1);
        cc.setChess(5, 0, 1);
        cc.setChess(6, 0, 1);
        assertEquals(5, cc.countAllLine(0, 0));
    }

    @Test
    public void testCountAll06() {
        cc.setChess(0, 0, 0);
        assertEquals(0, cc.countAllLine(0, 0));
    }

    /**
     * visszelépés tesztelés egyedös modell 1
     */
    @Test
    public void testCancel01(){
        cc.setChess(2,2,1);
        cc.pushToStack(2,2);
        cc.cancel();
        assertEquals(0, cc.getValueAt(2,2));
    }

    /**
     * visszelépés tesztelés egyedös modell 2
     */
    @Test
    public void testCancel02(){
        cc.setChess(2,2,1);
        cc.pushToStack(2,2);
        cc.setChess(3,3,2);
        cc.pushToStack(3,3);
        cc.cancel();
        cc.cancel();
        assertEquals(0, cc.getValueAt(2,2));
    }

    /**
     * visszelépés tesztelés több játékos modell 1
     */
    @Test
    public void testCancel03(){
        AIChessCounter aicc = new AIChessCounter();
        aicc.setChess(2,2,1);
        aicc.pushToStack(2,2);
        aicc.setChess(3,3,2);
        aicc.pushToStack(3,3);
        aicc.cancel();
        assertEquals(0, aicc.getValueAt(2,2));
    }

    /**
     * visszelépés tesztelés több játékos modell 2
     */
    @Test
    public void testCancel04(){
        AIChessCounter aicc = new AIChessCounter();
        aicc.setChess(2,2,1);
        aicc.pushToStack(2,2);
        aicc.setChess(3,3,2);
        aicc.pushToStack(3,3);
        aicc.setChess(4,4,1);
        aicc.pushToStack(4,4);
        aicc.setChess(5,5,2);
        aicc.pushToStack(5,5);
        aicc.cancel();
        aicc.cancel();
        assertEquals(0, aicc.getValueAt(2,2));
    }

    /**
     * üres Stack tesztelés
     */
    @Test(expected = EmptyStackException.class)
    public void testCancel05(){
        cc.cancel();
    }

    /**
     * gyöztes tesztelés 1
     */
    @Test
    public void testWin01(){
        cc.setChess(0,1,1);
        cc.setChess(0,2,1);
        cc.setChess(0,3,1);
        cc.setChess(0,4,1);
        cc.setChess(0,5,1);
        cc.pushToStack(0,5);
        assertEquals(1, cc.win());
    }

    /**
     * gyöztes tesztelés 2
     */
    @Test
    public void testWin02(){
        cc.setChess(0,1,2);
        cc.setChess(0,2,2);
        cc.setChess(0,3,2);
        cc.setChess(0,4,2);
        cc.setChess(0,5,2);
        cc.pushToStack(0,5);
        assertEquals(2, cc.win());
    }


    /**
     * gyöztes tesztelés 3
     */
    @Test
    public void testWin03(){
        assertEquals(0, cc.win());
    }


    /**
     * húzás tesztelés
     * itt a pályán 1 és 2 érték helyett -1 töltök fel, rényeg, hogy keződik a játék, aközben senki nem nyeri meg, és tele van a pálya
     */
    @Test
    public void testWin04(){
        for (int i = 0; i < 15; i++){
            for (int j = 0 ; j < 15; j++){
                cc.setChess(i,j,-1);
            }
        }
        cc.setChess(0,0,1);
        cc.pushToStack(0,0);
        assertEquals(3, cc.win());
    }
}
