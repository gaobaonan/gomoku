//package com.gao.gomoku.file;
//
//import com.gao.gomoku.counter.AIChessCounter;
//import com.gao.gomoku.counter.ChessCounter;
//import com.gao.gomoku.counter.ChessCounter.GameMode;
//import com.gao.gomoku.counter.ChessCounter.Turn;
//import com.gao.gomoku.file.FileFrame;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Stack;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class FileFrameTest {
//
//    private ChessCounter cc;
//    FileFrame f;
//
//    /**
//     * olvasás
//     * azt fogom tesztelni, hogy rendes olvas-e minden adatot
//     * az eredeti fájl:
//     *
//     * MULTI BLACK playable               <---- beállítás
//     * 000000000000000
//     * 010000000000000
//     * 002000000000000
//     * 000000000000000
//     * 000000000000000
//     * 000000000000000
//     * 000000000000000
//     * 000000000000000                    <---- játék pálya
//     * 000000000000000
//     * 000000000000000
//     * 000000000000000
//     * 000000000000000
//     * 000000000000000
//     * 000000000000000
//     * 000000000000000
//     * 1 1                                <---- lépés Stack
//     * 2 2
//     *
//     * @throws IOException: io kivétel
//     */
//    @Before
//    public void setUp() throws IOException {
//        cc = new ChessCounter();
//        f = new FileFrame(FileFrame.IO.LOAD,cc);
//        f.loadSetting(cc, "gamedata5");
//    }
//
//    /**
//     * beállítás ellenőrizés 1
//     */
//    @Test
//    public void tsetLoadSetting01(){
//        assertEquals(GameMode.MULTI, cc.getGameMode());
//    }
//
//    /**
//     * beállítás ellenőrizés 2
//     */
//    @Test
//    public void tsetLoadSetting02(){
//        assertEquals(Turn.BLACK, cc.getTurn());
//    }
//
//    /**
//     * beállítás ellenőrizés 3
//     */
//    @Test
//    public void tsetLoadSetting03(){
//        assertTrue(cc.getPlayable());
//    }
//
//    /**
//     * követ ellenőrizés 1
//     */
//    @Test
//    public void tsetLoadSetting04(){
//        assertEquals(1, cc.getValueAt(2,1));
//    }
//
//    /**
//     * követ ellenőrizés 2
//     */
//    @Test
//    public void tsetLoadSetting05(){
//        assertEquals(2, cc.getValueAt(2,2));
//    }
//
//    /**
//     * Stack ellenőrizés
//     */
//    @Test
//    public void tsetLoadSetting06(){
//        ChessCounter.Step step = new ChessCounter.Step(2, 2);
//        assertEquals(step, cc.getStepStack().pop());
//    }
//}
