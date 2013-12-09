/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.imaging;

import boofcv.abst.feature.detect.line.DetectLineHoughPolar;
import boofcv.abst.feature.detect.line.DetectLineSegmentsGridRansac;
import boofcv.alg.enhance.EnhanceImageOps;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.ConvertBufferedImage;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.factory.feature.detect.line.FactoryDetectLineAlgs;
import boofcv.gui.ListDisplayPanel;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.gui.feature.ImageLinePanel;
import boofcv.gui.image.ShowImages;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageSInt16;
import boofcv.struct.image.ImageSingleBand;
import boofcv.struct.image.ImageUInt8;
import chess.pieces.ChessPiece;
import georegression.struct.line.LineParametric2D_F32;
import georegression.struct.line.LineSegment2D_F32;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author David
 */
public class DetectUtil {

    // adjusts edge threshold for identifying pixels belonging to a line
    private static final float edgeThreshold = 25;
    // adjust the maximum number of found lines in the image
    private static final int maxLines = 10;

    public static void drawLine(LineParametric2D_F32 line, BufferedImage image) {

        //  if(line.getPointOnLine(edgeThreshold))
        for (int x = 0; x < image.getWidth(); x++) {
            int y = (int) (line.getPointOnLine(x).y);
            if (y >= 0 && y < image.getHeight()) {
                image.setRGB(x, y, 0x0FFFFFF);
            }
        }
    }

    public static int getAvgDiff(BufferedImage bi, int x, int y, int width, int height) {
        int sum = 0;
        try {
            ArrayList<Integer> diffList = new ArrayList<>();
            for (int w = x; w < width; w++) {
                for (int h = y; h < height; h++) {
                    Color prevColor = new Color(bi.getRGB(x - 1, y));
                    Color color = new Color(bi.getRGB(w, h));

                    int diff = abs(prevColor.getRed() - color.getRed()) + abs(prevColor.getGreen() - color.getGreen()) + abs(prevColor.getBlue() - color.getBlue());
                    diffList.add(diff);
                }
            }

            Collections.sort(diffList);
            int quarter = diffList.size() / 4;

            for (int i = quarter; i < quarter * 2; i++) {
                sum += diffList.get(i);
            }

        } catch (Exception e) {
            sum = -1;
        }
        return sum;
    }

    public static BufferedImage drawEdges(BufferedImage orig) {
        ImageUInt8 gray = ConvertBufferedImage.convertFrom(orig, (ImageUInt8) null);
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
        return VisualizeBinaryData.renderBinary(edgeImage, null);

    }

    /**
     * Detects lines inside the image using different types of Hough detectors
     *
     * @param image Input image.
     * @param imageType Type of image processed by line detector.
     * @param derivType Type of image derivative.
     */
    public static <T extends ImageSingleBand, D extends ImageSingleBand>
            List<LineParametric2D_F32> detectLines(BufferedImage image,
                    Class<T> imageType,
                    Class<D> derivType) {
        // convert the line into a single band image
        T input = ConvertBufferedImage.convertFromSingle(image, null, imageType);

        // Comment/uncomment to try a different type of line detector
        DetectLineHoughPolar<T, D> detector = FactoryDetectLineAlgs.houghPolar(3, 30, 2, Math.PI / 180,
                edgeThreshold, maxLines, imageType, derivType);
//		DetectLineHoughFoot<T,D> detector = FactoryDetectLineAlgs.houghFoot(3, 8, 5, edgeThreshold,
//				maxLines, imageType, derivType);
//		DetectLineHoughFootSubimage<T,D> detector = FactoryDetectLineAlgs.houghFootSub(3, 8, 5, edgeThreshold,
//				maxLines, 2, 2, imageType, derivType);

        return detector.detect(input);
    }

    /**
     * Detects segments inside the image
     *
     * @param image Input image.
     * @param imageType Type of image processed by line detector.
     * @param derivType Type of image derivative.
     */
    public static <T extends ImageSingleBand, D extends ImageSingleBand>
            void detectLineSegments(BufferedImage image,
                    Class<T> imageType,
                    Class<D> derivType) {
        // convert the line into a single band image
        T input = ConvertBufferedImage.convertFromSingle(image, null, imageType);

        // Comment/uncomment to try a different type of line detector
        DetectLineSegmentsGridRansac<T, D> detector = FactoryDetectLineAlgs.lineRansac(40, 30, 2.36, true, imageType, derivType);

        List<LineSegment2D_F32> found = detector.detect(input);

        // display the results
        ImageLinePanel gui = new ImageLinePanel();
        gui.setBackground(image);
        gui.setLineSegments(found);
        gui.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

        ShowImages.showWindow(gui, "Found Line Segments");
    }

    public static void main(String args[]) {
        BufferedImage input = UtilImageIO.loadImage("../data/evaluation/simple_objects.jpg");

        detectLines(input, ImageUInt8.class, ImageSInt16.class);

        // line segment detection is still under development and only works for F32 images right now
        detectLineSegments(input, ImageFloat32.class, ImageFloat32.class);
    }

    public static void displaySquare(BoardDetails bd, int x, int y, BufferedImage bi) {

        int xs = bd.getSquare(x, y).x;
        int xl = bd.getSquare(x, y).width + bd.getSquare(x, y).x;
        int ys = bd.getSquare(x, y).y;
        int yl = bd.getSquare(x, y).y + bd.getSquare(x, y).height;
        for (int x1 = xs; x1 < xl; x1++) {
            for (int y1 = ys; y1 < yl; y1++) {
                if (DetectUtil.whiteSquare(x, y)) {
                    bi.setRGB(x1, y1, 0x00000000);
                } else {
                    bi.setRGB(x1, y1, 0xFFFFFF);
                }
            }
        }
    }

    public static void displaySquare(ChessPiece cp, BoardDetails bd, int x, int y, BufferedImage bi) {
        try {
            int xs = bd.getSquare(x, y).x;
            int ys = bd.getSquare(x, y).y;

            displayText(bi, cp.getName(), xs, ys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayText(BufferedImage old, String s, int x, int y) {
        Graphics2D g2d = old.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.red);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(s, x, y + 15);
        g2d.dispose();
    }

    public static BufferedImage histogram(BufferedImage buffered) {
        ImageUInt8 gray = ConvertBufferedImage.convertFrom(buffered, (ImageUInt8) null);
        ImageUInt8 adjusted = new ImageUInt8(gray.width, gray.height);

        int histogram[] = new int[256];
        int transform[] = new int[256];

        ImageStatistics.histogram(gray, histogram);
        EnhanceImageOps.equalize(histogram, transform);
        EnhanceImageOps.applyTransform(gray, transform, adjusted);
//		panel.addImage(ConvertBufferedImage.convertTo(adjusted,null),"Global");
// 
//		EnhanceImageOps.equalizeLocal(gray, 50, adjusted, histogram, transform);
//		panel.addImage(ConvertBufferedImage.convertTo(adjusted,null),"Local");
// 
//		panel.addImage(ConvertBufferedImage.convertTo(gray,null),"Original");
// 
//		panel.setPreferredSize(new Dimension(gray.width,gray.height));
//		ShowImages.showWindow(panel,"Histogram");
        return ConvertBufferedImage.convertTo(adjusted, null);
    }

    public static boolean whiteSquare(int x, int y) {
        if (y % 2 == 0) {
            return x % 2 == 0;
        } else {
            return x % 2 != 0;
        }
    }

}
