// Import the basic graphics classes.  
// The problem here is that we read the image with OpenCV into a Mat object.  
// But OpenCV for java doesn't have the method "imshow", so, we got to use  
// java for that (drawImage) that uses Image or BufferedImage.  
// So, how to go from one the other... Here is the way...  

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.CLAHE;

import static org.opencv.imgproc.Imgproc.Canny;

public class Panel extends JPanel {

    private static final long serialVersionUID = 1L;
    private BufferedImage image;

    // Create a constructor method  
    public Panel() {
        super();
    }

    private BufferedImage getimage() {
        return image;
    }

    private void setimage(BufferedImage newimage) {
        image = newimage;
        return;
    }

    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public static BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int) matrix.elemSize();
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
                for (int i = 0; i < data.length; i = i + 3) {
                    b = data[i];
                    data[i] = data[i + 2];
                    data[i + 2] = b;
                }
                break;
            default:
                return null;
        }
        BufferedImage image2 = new BufferedImage(cols, rows, type);
        image2.getRaster().setDataElements(0, 0, cols, rows, data);
        return image2;
    }

    public void paintComponent(Graphics g) {
        BufferedImage temp = getimage();
        if (temp != null) {
            g.drawImage(temp, 10, 10, temp.getWidth(), temp.getHeight(), this);
        }
    }

    public static void main(String arg[]) {
        // Load the native library.  
        System.loadLibrary("opencv_java248");
        JFrame frame = new JFrame("BasicPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        Panel panel = new Panel();
        frame.setContentPane(panel);
        frame.setVisible(true);
        Mat webcam_image = new Mat();
        Mat image;
        BufferedImage temp;
        VideoCapture capture = new VideoCapture(0);
        if (capture.isOpened()) {
            while (true) {
                capture.read(webcam_image);
                if (!webcam_image.empty() && webcam_image.width() > 0 && webcam_image.height() > 0) {
                   image = processImage(webcam_image);
                    if (webcam_image != null) {
                        frame.setSize(webcam_image.width() + 40, webcam_image.height() + 60);
                        temp = matToBufferedImage(image);
                        panel.setimage(temp);
                        panel.repaint();
                    }
                } else {
                    System.out.println(" --(!) No captured frame -- Break!");
                    // break;  
                }
            }
        }
        return;
    }

    private static Mat processImage(Mat image) {
        if (image.empty()) {
            return null;
        }
        Mat thresholdImage = new Mat(image.width(),image.height() ,  CvType.CV_8UC3);
        Imgproc.cvtColor(image, thresholdImage, Imgproc.COLOR_RGB2GRAY);
      //  Imgproc.Canny(image, thresholdImage, 80, 100, 3, false);
  //      Imgproc.HoughLinesP(image, image, WIDTH, WIDTH, WIDTH);
        
        
//        Mat mYuv = new Mat();
        Mat mRgba = new Mat();
//        //Mat thresholdImage = new Mat(getFrameHeight() + getFrameHeight() / 2, getFrameWidth(), CvType.CV_8UC1);
//        //mYuv.put(0, 0, data);
//        Imgproc.cvtColor(mYuv, mRgba, Imgproc.COLOR_YUV420sp2RGB, 4);
//        Imgproc.cvtColor(mRgba, thresholdImage, Imgproc.COLOR_RGB2GRAY, 4);
//       // Imgproc.Canny(thresholdImage, thresholdImage, 80, 100, 3);
        Mat lines = new Mat();
         int threshold = 100;
           int minLineSize = 20;
           int lineGap = 5;
       //    Imgproc.HoughLinesP(thresholdImage, lines, 1, Math.PI / 180, threshold, minLineSize, lineGap);
        
        CLAHE clahe;
//    Imgproc.HoughLinesP(thresholdImage, lines, 1, Math.PI/180, threshold, minLineSize, lineGap);
//
//    for (int x = 0; x < lines.cols() && x < 1000; x++) 
//    {
//          double[] vec = lines.get(0, x);
//          double x1 = vec[0], 
//                 y1 = vec[1],
//                 x2 = vec[2],
//                 y2 = vec[3];
//          Point start = new Point(x1, y1);
//          Point end = new Point(x2, y2);
//
//          Core.line(image, start, end, new Scalar(255,0,0), 3);
//
//    }
//        
//        
//        return mRgba;
   //     clahe = new CLAHE();
           
MatOfKeyPoint mokp = new MatOfKeyPoint();
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.PYRAMID_HARRIS);
        fd.detect(thresholdImage, mokp);

        if (!mokp.empty()) {
          Features2d.drawKeypoints(thresholdImage, mokp, thresholdImage, new Scalar(255, 0, 255), Features2d.DRAW_RICH_KEYPOINTS);
           }
           
    return thresholdImage;
    }
}
