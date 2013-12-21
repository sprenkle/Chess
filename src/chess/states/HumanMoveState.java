/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.states;

import chess.CameraToBoard;
import chess.ChessUtil;
import chess.imaging.SquareValue;
import chess.pieces.ChessPiece;
import static chess.states.State.engine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.joda.time.DateTime;

/**
 *
 * @author David
 */
public class HumanMoveState extends State {

    private DateTime lastMatched = null;

    public HumanMoveState() {
        name = "HumanMoveState";
    }

    @Override
    public State stateProcess(int[][] squareValues, int nonMatchingSquares, int pieceTaken) {
        if (nonMatchingSquares == 1 ^ pieceTaken == 1) {
            if (lastMatched == null) {
                lastMatched = DateTime.now();
                stateLogger.debug("Set lastMatched to " + lastMatched.toString());
            }

            if (lastMatched.isBefore(DateTime.now().minusSeconds(secondsForBoardCheck))) {
                stateLogger.debug(String.format("lastMatched is past %s seconds. LastMatched=%s Now =%s", secondsForBoardCheck, lastMatched.toString(), DateTime.now().toString()));
                ArrayList<ChessPiece>[] activeList = engine.getBoard().getActivePieces();
                ChessPiece missingPiece = null;
                for (ChessPiece activePiece : activeList[humanColor]) {
                    boolean foundPiece = false;
                    for (SquareValue sv2 : diffList.subList(0, engine.getNumActivePieces())) {
                        if (activePiece.getLocation().getX() == sv2.x && activePiece.getLocation().getY() == sv2.y) {
                            foundPiece = true;
                        }
                    }
                    if (!foundPiece) {
                        missingPiece = activePiece;
                    }
                }
                SquareValue sq = null;
                for (SquareValue indexSv : diffList.subList(0, engine.getNumActivePieces())) {
                    if (engine.getBoard().getPiece(indexSv.x, indexSv.y) == null && indexSv.value > emptySquareDetectionValue) {
                        sq = indexSv;
                    }
                }
                if (sq == null) {
                    ArrayList<SquareValue> opponentSquare = new ArrayList<>();

                    for (ChessPiece indexSv : engine.getBoard().getActivePieces()[engineColor]) {
                        double diff = Math.abs(squareValues[indexSv.getLocation().getX()][indexSv.getLocation().getY()]
                                - goodSquareValues[indexSv.getLocation().getX()][indexSv.getLocation().getY()]);
                        opponentSquare.add(new SquareValue(indexSv.getLocation().getX(), indexSv.getLocation().getY(), diff));
                    }

                    Collections.sort(opponentSquare, new Comparator<SquareValue>() {
                        @Override
                        public int compare(SquareValue v1, SquareValue v2) {
                            return Double.compare(v2.value, v1.value);
                        }
                    });
                    
                    sq = opponentSquare.get(0);
                }
                if(missingPiece == null){
                    stateLogger.debug("MissingPiece is null");
                    lastMatched = null;
                    return this;
                }
                stateLogger.debug(String.format("missingPiece x=%d y=%d movedTo x=%d y=%d", missingPiece.getLocation().getX(), missingPiece.getLocation().getY(), sq.x, sq.y));
                String humanMove = ChessUtil.convertXYtoChessMove(missingPiece.getLocation().getX(), missingPiece.getLocation().getY(), sq.x, sq.y);
                String move = engine.makeMove(humanMove);
                if (move.equals("Invalid Move")) {
                    stateLogger.info("Human Made Invalid Move " + humanMove);
                    lastMatched = null;
                    stateLogger.debug("Set lastMatched to Null");
                    return this;
                }
                stateLogger.info(String.format("Human made %s", move));
                return State.getState(State.ENGINESTATE);
            }
        } else {
            lastMatched = null;
            stateLogger.debug("Set lastMatched to Null");
        }
        return this;
    }

    @Override
    public void initialize() {

    }

}
