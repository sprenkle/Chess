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
import org.opencv.core.Mat;

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



        return bi;
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
    /**  
    * Converts/writes a Mat into a BufferedImage.  
    *  
    * @param matrix Mat of type CV_8UC3 or CV_8UC1  
    * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY  
    */  
   public static BufferedImage matToBufferedImage(Mat matrix) {  
     int cols = matrix.cols();  
     int rows = matrix.rows();  
     int elemSize = (int)matrix.elemSize();  
     byte[] data = new byte[cols * rows * elemSize];  
     int type;  
     matrix.get(0, 0, data);  
     switch (matrix.channels()) {  
       case 1:  
         type = BufferedImage.TYPE_BYTE_GRAY;  
         break;  
       case 3:  
         type = BufferedImage.TYPE_3BYTE_BGR;  
         // bgr to rgb  
         byte b;  
         for(int i=0; i<data.length; i=i+3) {  
           b = data[i];  
           data[i] = data[i+2];  
           data[i+2] = b;  
         }  
         break;  
       default:  
         return null;  
     }  
     BufferedImage image2 = new BufferedImage(cols, rows, type);  
     image2.getRaster().setDataElements(0, 0, cols, rows, data);  
     return image2;  
   }  

}
