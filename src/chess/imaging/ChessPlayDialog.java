package chess.imaging;

import chess.ImageUtil;
import chess.states.State;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class ChessPlayDialog implements WebcamImageTransformer {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    int imageNumber = 0;
    private State state = State.getState(State.STARTSTATE);

    @Override
    public BufferedImage transform(BufferedImage orig) {
        ColorModel cm = orig.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = orig.copyData(null);
        BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        ImageUtil.savePng(bi, String.format("images\\chessImage%04d.png", imageNumber++));
        state = state.process(bi);
        return bi;
    }

    public ChessPlayDialog() {

        Webcam webcam = Webcam.getDefault();

        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.setImageTransformer(this);
       
        webcam.open(false);

        JFrame window = new JFrame("Test Transformer");

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setFillArea(true);

        window.add(panel);
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new ChessPlayDialog();
    }



}
