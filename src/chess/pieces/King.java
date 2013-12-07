/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import chess.exceptions.InvalidLocationException;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class King extends ChessPiece {

    public King(int color, int x, int y) throws InvalidLocationException {
        super(color, x, y);
    }

    @Override
    public ArrayList<PieceLocation> validMoves(Board board) {
        ArrayList<PieceLocation> validMoves = new ArrayList<>();
        validMovesInDirection(board, validMoves, 0, 1);
        validMovesInDirection(board, validMoves, 1, 0);
        validMovesInDirection(board, validMoves, 0, -1);
        validMovesInDirection(board, validMoves, -1, 0);
        validMovesInDirection(board, validMoves, 1, 1);
        validMovesInDirection(board, validMoves, 1, -1);
        validMovesInDirection(board, validMoves, -1, 1);
        validMovesInDirection(board, validMoves, -1, -1);
        return validMoves;
    }

    @Override
    public boolean isValidMoveTo(Board board, PieceLocation location) {
        int xDiff = abs(location.getX() - this.location.getX());
        int yDiff = abs(location.getY() - this.location.getY());
        if ((yDiff == 1 && (xDiff == 0 || xDiff == 1)) || (xDiff == 1 && (yDiff == 1 || yDiff == 0))) {
            ChessPiece pieceAtPosition = board.getPiece(location.getX(), location.getY());
            if (pieceAtPosition == null || pieceAtPosition.getColor() != this.getColor()) {
                return true;
            }
        }

        if (!getHasMoved() && (location.getX() == 2 || location.getX() == 6)
                && !board.isKingInCheck(getColor())) {
            if (location.getX() == 2) {
                return board.getPiece(1,this.location.getY()) == null
                        && board.getPiece(2,this.location.getY()) == null
                        && board.getPiece(3,this.location.getY()) == null;
            }
            return board.getPiece(5,this.location.getY()) == null
                    && board.getPiece(6,this.location.getY()) == null;
        }
        return false;
    }

}
