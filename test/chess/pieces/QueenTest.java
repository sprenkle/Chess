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
public class QueenTest {
    
    public QueenTest() {
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
     * Test of isValidMoveTo method, of class Queen.
     */
    @Test
    public void testIsValidMoveTo() {
        try {
            System.out.println("isValidMoveTo");
            Board board = new Board();
            Queen instance = new Queen(Board.WHITE, 4,4);
            board.addPiece(instance);
            board.addPiece(new Rook(Board.BLACK, 4,1));
            
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(4,6)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(4,3)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(3,4)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(5,4)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(0,4)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(4,7)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(4,1)));
            
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4,0)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(5,6)));
            
            // bishop tests
            board.addPiece(new Rook(Board.BLACK, 6,6));
            
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(6,6)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(3,3)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(3,5)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(5,3)));
            assertEquals(true, instance.isValidMoveTo(board, new PieceLocation(5,5)));
            
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(7,7)));
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(4,4)));
            
            assertEquals(false, instance.isValidMoveTo(board, new PieceLocation(5,7)));
        } catch (InvalidLocationException ex) {
            Logger.getLogger(QueenTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
