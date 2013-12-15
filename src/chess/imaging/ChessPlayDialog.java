package chess.imaging;

import chess.states.State;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ChessPlayDialog implements WebcamImageTransformer {

    private State state = State.getState(State.STARTSTATE);

    @Override
    public BufferedImage transform(BufferedImage orig) {
        ColorModel cm = orig.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = orig.copyData(null);
        BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

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
