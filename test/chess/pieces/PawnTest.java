/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import chess.exceptions.InvalidLocationException;
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
public class PawnTest {

    public PawnTest() {
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
     * Test of isValidMoveTo method, of class Pawn.
     */
    @Test
    public void testIsValidMoveTo() {
        try {
            System.out.println("isValidMoveTo");
            Board board = new Board();
            Pawn instance = new Pawn(Board.WHITE, 4, 4);
            board.addPiece(instance);

            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(4, 5)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(4, 6)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(5, 5)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(3, 5)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(0, 4)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4, 7)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4, 1)));

            board.addPiece(new Rook(Board.BLACK, 5, 5));
            board.addPiece(new Rook(Board.BLACK, 3, 5));

            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(5, 5)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(3, 5)));
        } catch (InvalidLocationException ex) {
            Logger.getLogger(PawnTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsValidMoveTo2() {
        try {
            System.out.println("isValidMoveTo");
            Board board = new Board();
            Pawn instance = new Pawn(Board.WHITE, 7, 6);
            board.addPiece(instance);

            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(7, 7)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(6, 7)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(3, 5)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(0, 4)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4, 7)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4, 1)));

            board.addPiece(new Rook(Board.BLACK, 6, 7));

            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(6, 7)));
        } catch (InvalidLocationException ex) {
            Logger.getLogger(PawnTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetValidMoves() {
        System.out.println("isValidMoveTo");
        Board board = new Board();
        Pawn pawn = null;
        try {
            pawn = new Pawn(Board.WHITE, 7, 6);
        } catch (InvalidLocationException ex) {
            Logger.getLogger(PawnTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ArrayList<PieceLocation> lpl =pawn.validMoves(board);
        
        
    }

}
