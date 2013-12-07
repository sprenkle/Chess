/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.pieces;

import chess.exceptions.InvalidLocationException;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class Empty extends ChessPiece {

    public Empty(int color, int x, int y) throws InvalidLocationException {
        super(color, x, y);
    }

    @Override
    public ArrayList<PieceLocation> validMoves(Board board) {
        return new ArrayList<>();
    }

    @Override
    public boolean isValidMoveTo(Board board, PieceLocation location) {
        return false;
    }
    
}
