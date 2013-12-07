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
public class KnightTest {
    
    public KnightTest() {
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

//    /**
//     * Test of validMoves method, of class Knight.
//     */
//    @Test
//    public void testValidMoves() {
//        System.out.println("validMoves");
//        Knight instance = null;
//        ArrayList<PieceLocation> expResult = null;
//        ArrayList<PieceLocation> result = instance.validMoves();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of isValidMoveTo method, of class Knight.
     */
    @Test
    public void testIsValidMoveTo() {
        try {
            System.out.println("isValidMoveTo");
            Board board = new Board();
            PieceLocation location = new PieceLocation(4,4);
            Bishop instance = new Bishop(Board.WHITE, 4,4);
            board.addPiece(instance);
            
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(3,3)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(3,5)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(5,3)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(5,5)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(1,7)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(1,8)));
            
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4,4)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4,6)));
        } catch (InvalidLocationException ex) {
            Logger.getLogger(KnightTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
