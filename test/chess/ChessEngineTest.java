/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.pieces.Board;
import chess.pieces.BoardTest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class ChessEngineTest {

    public ChessEngineTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class ChessEngine.
     */
    @Test
    public void testMain() {
        BufferedReader br = null;
        ArrayList<String> playerMoves = new ArrayList<>();
        ArrayList<String> engineMoves = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader("testchess.txt"));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (count++ % 2 == 0) {
                    playerMoves.add(line);
                } else {
                    engineMoves.add(line);
                }
                // do something with line.
            }
        } catch (Exception ex) {
            Logger.getLogger(BoardTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(BoardTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ChessEngine engine = new ChessEngine(new TestUCI(engineMoves));

        for (String playerMove : playerMoves) {
            engine.makeMove(playerMove);
        }

    }

    @Test
    public void testSetup() {
        ChessEngine engine = new ChessEngine(new StockFishUCI());
        //   (new Thread(engine)).start();
        engine.newGame();

        assertTrue(engine.isAbleToMove(0));
        assertTrue(engine.isAbleToMove(1));

        assertFalse(engine.isInCheck(0));
        assertFalse(engine.isInCheck(1));
    }

    @Test
    public void testGame1() {
        ArrayList<String> uciMoveList = new ArrayList<String>();
        uciMoveList.add("d8d7");
        uciMoveList.add("c8d7");
        uciMoveList.add("d7d5");
        uciMoveList.add("e7e6");

        ChessEngine engine = new ChessEngine(new TestUCI(uciMoveList));
        //   (new Thread(engine)).start();
        engine.newGame();
        engine.makeMove("e2e4");
        engine.makeMove("d2d4");
        engine.makeMove("f1b5");
        engine.makeMove("b5d7");
    }

    @Test
    public void testCastle() {
        ArrayList<String> uciMoveList = new ArrayList<String>();
        
        uciMoveList.add("h7h6");
        uciMoveList.add("e6e5");
        uciMoveList.add("f6e4");
        uciMoveList.add("g8f6");
        uciMoveList.add("d5e4");
        uciMoveList.add("d7d5");
        uciMoveList.add("e7e6");

        ChessEngine engine = new ChessEngine(new TestUCI(uciMoveList));
        //   (new Thread(engine)).start();
        engine.newGame();
        engine.makeMove("e2e4");
        engine.makeMove("f1d3");
        engine.makeMove("g1f3");
        engine.makeMove("d3e4");
        engine.makeMove("b1c3");
        assertEquals("e6e5", engine.makeMove("c3e4"));
        assertEquals("h7h6", engine.makeMove("e1g1"));
    }

    
    
    private class TestUCI implements UCIInterface {

        private final ArrayList<String> moves;

        public TestUCI(ArrayList<String> moves) {
            this.moves = moves;
        }

        @Override
        public String sendCommandAndWait(String command, String waitForString) {
            if ("uci".equals(command)) {
                return "uciok";
            }
            if ("isready".equals(command)) {
                return "readyok";
            }
            if (command.contains("go")) {
                String move = "bestmove " + moves.remove(moves.size() - 1) + " ponder";
                System.out.println(command + "  " + move);
                return move;
            }
            return "";
        }

        @Override
        public void sendCommand(String command) {
            // do nothing
        }

    }

}
