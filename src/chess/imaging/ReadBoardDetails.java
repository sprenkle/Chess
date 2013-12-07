package chess.imaging;

import boofcv.abst.calib.ConfigChessboard;
import boofcv.abst.calib.PlanarCalibrationDetector;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.core.image.ConvertBufferedImage;
import boofcv.factory.calib.FactoryPlanarCalibrationTarget;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.gui.feature.VisualizeFeatures;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageSInt16;
import boofcv.struct.image.ImageUInt8;
import chess.imaging.ChessBoardImage;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.jh.JHGrayFilter;
import georegression.struct.point.Point2D_F64;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.abs;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadBoardDetails implements WebcamImageTransformer {

    private static final JHGrayFilter GRAY = new JHGrayFilter();
    double[][][] ar;
    boolean savedBoard = false;
    ChessBoardImage cbi;

    @Override
    public BufferedImage transform(BufferedImage orig) {
        ColorModel cm = orig.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = orig.copyData(null);
        BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
         bi = DetectUtil.histogram(bi);
        ImageUInt8 gray = ConvertBufferedImage.convertFrom(bi, (ImageUInt8) null);
        ImageUInt8 edgeImage = new ImageUInt8(gray.width, gray.height);

        // Create a canny edge detector which will dynamically compute the threshold based on maximum edge intensity
        // It has also been configured to save the trace as a graph.  This is the graph created while performing
        // hysteresis thresholding.                                          2
        CannyEdge<ImageUInt8, ImageSInt16> canny = FactoryEdgeDetectors.canny(2, true, true, ImageUInt8.class, ImageSInt16.class);

        // The edge image is actually an optional parameter.  If you don't need it just pass in null
        canny.process(gray, 0.1f, 0.5f, edgeImage);

        // First get the contour created by canny
        List<EdgeContour> edgeContours = canny.getContours();
        // The 'edgeContours' is a tree graph that can be difficult to process.  An alternative is to extract
        // the contours from the binary image, which will produce a single loop for each connected cluster of pixels.
        // Note that you are only interested in external contours.
        List<Contour> contours = BinaryImageOps.contour(edgeImage, 8, null);

        // display the results
        BufferedImage visualBinary = VisualizeBinaryData.renderBinary(edgeImage, null);

        if (!savedBoard) {
            cbi = new ChessBoardImage();

            cbi.CalcCorners(visualBinary);
        }

//        double[][][] averages = new double[8][8][3];
        int[][] diff = new int[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int[] loc = cbi.getAvgSquareLocation(x, y);
                diff[x][y] = DetectUtil.getAvgDiff(bi, loc[0], loc[1], loc[2], loc[3]);
//                averages[x][y] = cbi.getAvgSquare(x, y, bi);
               // DetectUtil.displaySquare(cbi.getBoardDetails(), x, y, bi);
            }
        }

        if (!savedBoard) {
            savedBoard = true;
            BoardDetails bd = cbi.getBoardDetails();

  //          bd.setValues(averages);
            bd.setDiffValues(diff);
            FileOutputStream fout;
            try {
                fout = new FileOutputStream("C:\\dev\\Chess\\Chess\\boardDetail.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(bd);
                oos.close();
                fout.close();
            } catch (Exception ex) {
                Logger.getLogger(ChessPlayDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return bi;
    }

    public ReadBoardDetails() {

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
    }

    public static void main(String[] args) {
        new ReadBoardDetails();
    }

}
