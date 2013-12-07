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
public class BishopTest {
    
    public BishopTest() {
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
     * Test of validMoves method, of class Bishop.
     */
//    @Test
//    public void testValidMoves() {
//        System.out.println("validMoves");
//        Bishop instance = null;
//        ArrayList<PieceLocation> expResult = null;
//        ArrayList<PieceLocation> result = instance.validMoves();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of isValidMoveTo method, of class Bishop.
     */
    @Test
    public void testIsValidMoveTo() {
        try {
            System.out.println("isValidMoveTo");
            Board board = new Board();
            Bishop instance = new Bishop(Board.WHITE, 4,4);
            board.addPiece(instance);
            board.addPiece(new Rook(Board.BLACK, 6,6));
            
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(6,6)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(3,3)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(3,5)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(5,3)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(5,5)));
            
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(7,7)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4,4)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4,6)));
        } catch (InvalidLocationException ex) {
            Logger.getLogger(BishopTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
