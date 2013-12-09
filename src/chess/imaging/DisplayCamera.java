package chess.imaging;

import boofcv.abst.calib.ConfigChessboard;
import boofcv.abst.calib.PlanarCalibrationDetector;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.feature.shapes.ShapeFittingOps;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.misc.ImageStatistics;
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
import java.awt.BasicStroke;
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

public class DisplayCamera implements WebcamImageTransformer {

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

        ImageUInt8 gray = ConvertBufferedImage.convertFrom(bi, (ImageUInt8) null);
      
        ImageUInt8 edgeImage = new ImageUInt8(gray.width, gray.height);
        
        ImageFloat32 img = ConvertBufferedImage.convertFrom(bi, (ImageFloat32) null);

        // Create a canny edge detector which will dynamically compute the threshold based on maximum edge intensity
        // It has also been configured to save the trace as a graph.  This is the graph created while performing
        // hysteresis thresholding.                                          2
        CannyEdge<ImageFloat32, ImageFloat32> canny = FactoryEdgeDetectors.canny(2, true, true, ImageFloat32.class, ImageFloat32.class);

        // The edge image is actually an optional parameter.  If you don't need it just pass in null
        canny.process(img, 0.1f, 0.9f, edgeImage);
        

        // First get the contour created by canny
        List<EdgeContour> edgeContours = canny.getContours();
        // The 'edgeContours' is a tree graph that can be difficult to process.  An alternative is to extract
        // the contours from the binary image, which will produce a single loop for each connected cluster of pixels.
        // Note that you are only interested in external contours.
        List<Contour> contours = BinaryImageOps.contour(edgeImage, 8, null);

        // display the results
        BufferedImage visualBinary = VisualizeBinaryData.renderBinary(edgeImage, null);

//        if (!savedBoard) {
            cbi = new ChessBoardImage();

            cbi.CalcCorners(visualBinary);
//        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
               DetectUtil.displaySquare(cbi.getBoardDetails(), x, y, bi);
            }
        }


        return visualBinary;
    }

    public DisplayCamera() {

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
        new DisplayCamera();
    }
    
    	/**
	 * Fits polygons to found contours around binary blobs.
	 */
//	public BufferedImage void fitBinaryImage(ImageFloat32 input) {
// 
//		ImageUInt8 binary = new ImageUInt8(input.width,input.height);
//		BufferedImage polygon = new BufferedImage(input.width,input.height,BufferedImage.TYPE_INT_RGB);
// 
//		// the mean pixel value is often a reasonable threshold when creating a binary image
//		double mean = ImageStatistics.mean(input);
// 
//		// create a binary image by thresholding
//		ThresholdImageOps.threshold(input, binary, (float) mean, true);
// 
//		// reduce noise with some filtering
//		ImageUInt8 filtered = BinaryImageOps.erode8(binary,null);
//		filtered = BinaryImageOps.dilate8(filtered, null);
// 
// 
//                return ;
//	}

}
