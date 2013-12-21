/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import chess.exceptions.InvalidLocationException;
import chess.exceptions.InvalidMoveException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
public class BoardTest {

    public BoardTest() {
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
     * Test of makeMove method, of class Board.
     */
    @Test
    public void testMakeMove_String()  {
        Board instance = new Board();

        String[] moves = new String[]{"e2e4", "e7e6", "d2d4", "d7d5", "e4d5", "e6d5", "a2a4", "g8f6", "b2b4", "f8b4", "c2c3", "b4d6", "f2f4", "e8g8", "g2g4", "f8e8", "c1e3", "f6g4", "h2h4", "g4e3", "f4f5", "d6g3", "e1d2", "e3d1"
        };

        for (String move : moves) {
            try {
                instance.makeMove(move);
            } catch (InvalidMoveException ex) {
                Logger.getLogger(BoardTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    @Test
    public void testFile()  {
        BufferedReader br = null;
        try {
            Board instance = new Board();
            br = new BufferedReader(new FileReader("testchess.txt"));
            String line;
            while((line = br.readLine()) != null) {
                
                instance.makeMove(line);
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

    }
    
    @Test
    public void testCheckForMate(){
        try {
            Board board = new Board();
            King king = new King(Board.WHITE, 1,5);
            board.addPiece(king);
            Queen queen = new Queen(Board.WHITE, 3,7);
            board.addPiece(queen);
            King bKing = new King(Board.BLACK, 0,7);
            board.addPiece(bKing);
            
            assertTrue(board.isKingInCheck(Board.BLACK));
            assertFalse(board.isAbleToMove(Board.BLACK));

            assertFalse(board.isKingInCheck(Board.WHITE));
            assertTrue(board.isAbleToMove(Board.WHITE));

        } catch (Exception ex) {
            Logger.getLogger(BoardTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError();
        }
    }

    @Test
    public void testCheckForStaleMate(){
        try {
            Board board = new Board();
            board.addPiece(new King(Board.WHITE, 1,5));
            board.addPiece(new Queen(Board.WHITE, 3,5));
            board.addPiece(new King(Board.BLACK, 0,7));
            
            assertFalse("Black king should not be in check",board.isKingInCheck(Board.BLACK));
            assertFalse("Baclk king should not be able to move", board.isAbleToMove(Board.BLACK));

            assertFalse(board.isKingInCheck(Board.WHITE));
            assertTrue(board.isAbleToMove(Board.WHITE));

        } catch (Exception ex) {
            Logger.getLogger(BoardTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError();
        }
    }

    @Test
    public void testCheckForFalseStaleMate(){
        try {
            Board board = new Board();
            King king = new King(Board.WHITE, 1,5);
            board.addPiece(king);
            Queen queen = new Queen(Board.WHITE, 3,5);
            board.addPiece(queen);
            King bKing = new King(Board.BLACK, 0,7);
            board.addPiece(bKing);
            Pawn pawn = new Pawn(Board.BLACK, 7,6);
            board.addPiece(pawn);
            
            assertFalse(board.isKingInCheck(Board.BLACK));
            assertTrue(board.isAbleToMove(Board.BLACK));

            assertFalse(board.isKingInCheck(Board.WHITE));
            assertTrue(board.isAbleToMove(Board.WHITE));

        } catch (Exception ex) {
            Logger.getLogger(BoardTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError();
        }
    }

}
