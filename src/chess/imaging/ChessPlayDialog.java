package chess.imaging;

import chess.ChessEngine;
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
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChessPlayDialog implements WebcamImageTransformer {
    BoardDetails boardDetails;
    ChessBoardImage cbi;
    ChessEngine engine;

    @Override
    public BufferedImage transform(BufferedImage orig) {
        ColorModel cm = orig.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = orig.copyData(null);
        BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

        ArrayList<SquareValue> diffList = new ArrayList<>();
        
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                diffList.add(new SquareValue(x,y, cbi.getDiffSquare(x, y, bi)));
            }
        }
        
       Collections.sort(diffList, new Comparator<SquareValue>(){
            @Override
            public int compare(SquareValue v1, SquareValue v2){
                return Double.compare(v2.value, v1.value);
            }
        });
       
        for(int i = 0 ; i < 32 ; i++){
            SquareValue diffValue = diffList.get(i);
            DetectUtil.displaySquare(boardDetails, diffValue.x, diffValue.y, bi);
        }
        
        CheckForMove(diffList);
        
        return bi;
    }

    public ChessPlayDialog() {
        String filename = "C:\\dev\\Chess\\Chess\\boardDetail.ser";

        FileInputStream fis ;
        ObjectInputStream in ;

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
    
    private void CheckForMove(ArrayList<SquareValue> diffList){
        Board board = engine.getBoard();
        SquareValue emptySquare = null;
        ArrayList<ChessPiece>[] activePieces = board.getActivePieces();
        
        for(int i = 0; i < activePieces[0].size() + activePieces[1].size(); i++){
            SquareValue sv = diffList.get(i);
            ChessPiece cp = board.getPiece(sv.x, sv.y);
            if(cp == null){
                if(emptySquare == null){
                    emptySquare = sv;
                }
                System.out.println("Piece Moved");
            }
        }
    }

}
