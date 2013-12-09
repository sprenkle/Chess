package chess.imaging;

import chess.ChessEngine;
import chess.ChessUtil;
import chess.StockFishUCI;
import chess.pieces.Board;
import chess.pieces.ChessPiece;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamLock;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.jh.JHGrayFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChessPlayDialog implements WebcamImageTransformer {

    private BoardDetails boardDetails;
    private ChessBoardImage cbi;
    private ChessEngine engine;
    private double[][] lastMoveValues;
    private int boardSameCount = 0;
    private boolean madeFirstMove = false;

    @Override
    public BufferedImage transform(BufferedImage orig) {
        ColorModel cm = orig.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = orig.copyData(null);
        BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

        ArrayList<SquareValue> diffList = new ArrayList<>();

        ArrayList<SquareValue> whiteList = new ArrayList<>();
        ArrayList<SquareValue> blackList = new ArrayList<>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                diffList.add(new SquareValue(x, y, cbi.getDiffSquare(x, y, bi)));
            }
        }

        Collections.sort(diffList, new Comparator<SquareValue>() {
            @Override
            public int compare(SquareValue v1, SquareValue v2) {
                return Double.compare(v2.value, v1.value);
            }
        });

        CheckForMove(diffList, bi);

        return bi;
    }

    public ChessPlayDialog() {
        String filename = "C:\\dev\\Chess\\Chess\\boardDetail.ser";

        FileInputStream fis;
        ObjectInputStream in;

        try {
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            boardDetails = (BoardDetails) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        cbi = new ChessBoardImage(boardDetails.getTop(), boardDetails.getBottom(), boardDetails.getLeft(), boardDetails.getRight(), boardDetails.getSquareWidth(), boardDetails.getSquareHeight());

        Webcam webcam = Webcam.getDefault();

        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.setImageTransformer(this);

        webcam.open();

        JFrame window = new JFrame("Test Transformer");

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setFillArea(true);

        window.add(panel);
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        engine = new ChessEngine(new StockFishUCI());
        engine.newGame();
    }

    public static void main(String[] args) {
        new ChessPlayDialog();
    }

    private void CheckForMove(ArrayList<SquareValue> diffList, BufferedImage bi) {
        if(engine == null || engine.getBoard() == null) return;
        Board board = engine.getBoard();
        SquareValue emptySquare = null;
        ArrayList<ChessPiece>[] activePieces = board.getActivePieces();

        boolean matched = true;
        SquareValue missingPiece = null;
        ChessPiece movedPiece = null;
        
        for (int i = 0; i < activePieces[0].size() + activePieces[1].size(); i++) {
            SquareValue sv = diffList.get(i);
            ChessPiece cp = board.getPiece(sv.x, sv.y);

            if (cp != null || missingPiece == null) {
                if(cp == null) missingPiece = sv;
                DetectUtil.displaySquare(cp, boardDetails, sv.x, sv.y, bi);
            } else {
                matched = false;
                boardSameCount = 0;
            }
        }

        if(missingPiece != null){
            // TODO need to add the turn color here
            for(ChessPiece cp : activePieces[0]){
                boolean foundPiece = false;
                for (int i = 0; i < activePieces[0].size() + activePieces[1].size(); i++) {
                    SquareValue sv = diffList.get(i);
                    if(sv.x == cp.getLocation().getX() && sv.y == cp.getLocation().getY()){
                        foundPiece = true;
                        break;
                    }
                }
                if(!foundPiece){
                   movedPiece = cp; 
                   engine.makeMove(ChessUtil.convertXYtoChessMove(cp.getLocation().getX(), cp.getLocation().getY(),missingPiece.x, missingPiece.y));
                }
            }
        }
        
        if (matched) {
            boardSameCount++;
        }

        if (matched) {
            for (int i = 0; i < activePieces[0].size() + activePieces[1].size(); i++) {
                SquareValue sv = diffList.get(i);
                ChessPiece cp = board.getPiece(sv.x, sv.y);

                if(cp != null)DetectUtil.displaySquare(cp, boardDetails, sv.x, sv.y, bi);
                boardSameCount = 0;
            }
        }

        
        
    }

}
