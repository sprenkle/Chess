/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.states;

import chess.imaging.SquareValue;
import chess.pieces.ChessPiece;

/**
 *
 * @author David
 */
public class UnknownPositionState extends State {
    
    public UnknownPositionState(){
        name = "UnknownPositionState";
    }
    
    @Override
    public State stateProcess(int[][] squareValues,ChessPiece cp, SquareValue sq) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
