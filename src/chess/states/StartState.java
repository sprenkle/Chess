/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.states;

import chess.imaging.SquareValue;
import chess.pieces.ChessPiece;
import java.util.HashMap;

/**
 *
 * @author David
 */
public class StartState extends State {

    
    public StartState(){
        name = "StartState";
    }
    
    @Override
    public State stateProcess(int[][] squareValues,ChessPiece cp, SquareValue sq) {
        if(sq != null){
            int i = 0;
        }
        return this;
    }
    
}
