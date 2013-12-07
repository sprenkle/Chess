/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import chess.ChessUtil;
import chess.exceptions.InvalidLocationException;
import chess.exceptions.InvalidMoveException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Board {

    public static int WHITE = 0;
    public static int BLACK = 1;

    private ChessPiece king[];
    private ChessPiece[][] board = new ChessPiece[8][8];
    private final ArrayList<ChessPiece>[] activePieces = new ArrayList[2];
    private final ArrayList<ChessPiece>[] capturedPieces;

    public Board() {
        this.capturedPieces = new ArrayList[2];
        king = new ChessPiece[2];
        activePieces[0] = new ArrayList<>();
        activePieces[1] = new ArrayList<>();
        capturedPieces[0] = new ArrayList<>();
        capturedPieces[1] = new ArrayList<>();
        //setupStartingPosition();
    }

    public void setStartingPositionBoard() {
        setupStartingPosition();
    }

    public ChessPiece getPiece(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }
        return board[x][y];
    }

    public ChessPiece removePiece(int x, int y) {
        ChessPiece rp = board[x][y];
        return removePiece(rp);
    }

    public ChessPiece removePiece(ChessPiece rp) {
        if (rp == null) {
            return null;
        }
        activePieces[rp.getColor()].remove(rp);
        capturedPieces[rp.getColor()].remove(rp);
        removePieceFromBoard(rp);
        return rp;
    }

    public ArrayList<ChessPiece>[] getActivePieces(){
        return activePieces;
    }
    
    private ChessPiece capturePiece(ChessPiece rp) {
        if (rp == null) {
            return null;
        }
        activePieces[rp.getColor()].remove(rp);
        capturedPieces[rp.getColor()].add(rp);
        
        removePieceFromBoard(rp);
        return null;
    }

    private void addToActiveList(ChessPiece cp){
        if(!activePieces[cp.getColor()].contains(cp)){
            activePieces[cp.getColor()].add(cp);
        }
    }
    
    public void addPiece(ChessPiece piece) {
        addPieceToBoard(piece);
        if (piece.getClass() != Empty.class) {
            addToActiveList(piece);
            if (piece.getClass() == King.class) {
                king[piece.getColor()] = piece;
            }
        }
    }

    public boolean makeMove(PieceLocation from, PieceLocation to) {
        if (validMove(from, to)) {
            ChessPiece fromPiece = getPiece(from.getX(), from.getY());
            ChessPiece toPiece = getPiece(to.getX(), to.getY());
            
            // Castle
            if(fromPiece.getClass() == King.class 
                    && fromPiece.getHasMoved() 
                        && (to.getX() == 2 || to.getX() == 6)){
                if(to.getX() == 2){
                    try {
                        makeMove(new PieceLocation(0, to.getY()), new PieceLocation(3, to.getY()));
                    } catch (InvalidLocationException ex) {
                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    try {
                        makeMove(new PieceLocation(7, to.getY()), new PieceLocation(5, to.getY()));
                    } catch (InvalidLocationException ex) {
                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }

            if (toPiece != null) {
                capturePiece(toPiece);
            }
            fromPiece.SetLocation(to.getX(), to.getY());
            fromPiece.setHasMoved();
            addPieceToBoard(fromPiece);
            board[from.getX()][from.getY()] = null;
            return true;
        }
        return false;
    }

    public boolean makeMove(String move) throws InvalidMoveException {
        String[] location = ChessUtil.ConvertChessMove(move);
        try {
            int[] from = ChessUtil.ConvertLocation(location[0]);
            int[] to = ChessUtil.ConvertLocation(location[1]);

            return makeMove(new PieceLocation(from[0], from[1]), new PieceLocation(to[0], to[1]));
        } catch (InvalidLocationException ex) {
            throw new InvalidMoveException();
        }
    }

    private boolean validMove(PieceLocation from, PieceLocation to) {
        try {

            // Check if piece can actually move there
            ChessPiece fromPiece = board[from.getX()][from.getY()];
            if (fromPiece == null || !fromPiece.isValidMoveTo(this, to)) {
                return false;
            }

            // Check if own king is in check as result of move
            ChessPiece orginalDestPiece = this.removePiece(to.getX(), to.getY());
            fromPiece.SetLocation(to.getX(), to.getY());
            addPiece(fromPiece);
            board[from.getX()][from.getY()] = null;

            boolean rv = !isKingInCheck(fromPiece.getColor());

            fromPiece.SetLocation(from.getX(), from.getY());
            addPiece(fromPiece);
            if (orginalDestPiece != null) {
                addPiece(orginalDestPiece);
            }else{
                board[to.getX()][to.getY()] = null;
            }

            return rv;
        } catch (Exception ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isKingInCheck(int color) {
        int opponentColor = color == 0 ? 1 : 0;

        for (ChessPiece piece : activePieces[opponentColor]) {
            if (piece.isValidMoveTo(this, king[color].getLocation())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAbleToMove(int color) {
        int opponentColor = color == 1 ? 0 : 1;
        ArrayList<ChessPiece> tl = (ArrayList<ChessPiece>) activePieces[color].clone();
        for (ChessPiece myCp : tl) {
            ArrayList<PieceLocation> plArray = myCp.validMoves(this);
            PieceLocation currentPieceLocation = myCp.getLocation();
            removePiece(myCp);
            for (PieceLocation pl : plArray) {
                ChessPiece fromPiece = this.removePiece(pl.getX(), pl.getY());
                myCp.SetLocation(pl);
                addPiece(myCp);
                boolean kingInCheck = isKingInCheck(color);
                removePiece(myCp);
                if (fromPiece != null) {
                    addPiece(fromPiece);
                }
                if (!kingInCheck) {
                    myCp.SetLocation(currentPieceLocation);
                    addPiece(myCp);
                    return true;
                }
            }
            myCp.SetLocation(currentPieceLocation);
            addPiece(myCp);
        }

        return false;
    }

    private void addPieceToBoard(ChessPiece piece) {
        board[piece.getLocation().getX()][piece.getLocation().getY()] = piece;
    }

    private void removePieceFromBoard(ChessPiece piece) {
        board[piece.getLocation().getX()][piece.getLocation().getY()] = null;
    }

    private void setupStartingPosition() {
        try {
            activePieces[WHITE] = new ArrayList<>();
            activePieces[BLACK] = new ArrayList<>();
            capturedPieces[WHITE] = new ArrayList<>();
            capturedPieces[BLACK] = new ArrayList<>();
            board = new ChessPiece[8][8];
            addPiece(new Rook(WHITE, 0, 0));
            addPiece(new Knight(WHITE, 1, 0));
            addPiece(new Bishop(WHITE, 2, 0));
            addPiece(new Queen(WHITE, 3, 0));
            addPiece(new King(WHITE, 4, 0));
            addPiece(new Bishop(WHITE, 5, 0));
            addPiece(new Knight(WHITE, 6, 0));
            addPiece(new Rook(WHITE, 7, 0));
            addPiece(new Pawn(WHITE, 0, 1));
            addPiece(new Pawn(WHITE, 1, 1));
            addPiece(new Pawn(WHITE, 2, 1));
            addPiece(new Pawn(WHITE, 3, 1));
            addPiece(new Pawn(WHITE, 4, 1));
            addPiece(new Pawn(WHITE, 5, 1));
            addPiece(new Pawn(WHITE, 6, 1));
            addPiece(new Pawn(WHITE, 7, 1));

            addPiece(new Rook(BLACK, 0, 7));
            addPiece(new Knight(BLACK, 1, 7));
            addPiece(new Bishop(BLACK, 2, 7));
            addPiece(new Queen(BLACK, 3, 7));
            addPiece(new King(BLACK, 4, 7));
            addPiece(new Bishop(BLACK, 5, 7));
            addPiece(new Knight(BLACK, 6, 7));
            addPiece(new Rook(BLACK, 7, 7));
            addPiece(new Pawn(BLACK, 0, 6));
            addPiece(new Pawn(BLACK, 1, 6));
            addPiece(new Pawn(BLACK, 2, 6));
            addPiece(new Pawn(BLACK, 3, 6));
            addPiece(new Pawn(BLACK, 4, 6));
            addPiece(new Pawn(BLACK, 5, 6));
            addPiece(new Pawn(BLACK, 6, 6));
            addPiece(new Pawn(BLACK, 7, 6));

//            for (int x = 0; x < 8; x++) {
//                for (int y = 0; y < 8; y++) {
//                    if (board[x][y] != null) {
//                        activePieces[board[x][y].returnColor()].add(board[x][y]);
//                    }
//                }
//            }
        } catch (InvalidLocationException e) {

        }
    }
}
