package prototype.servos;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

//import org.opencv.highgui.VideoCapture;


public class Webcam {
    public static void main (String args[]) {
        System.out.println("Hello, OpenCV");
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.NATIVE_LIBRARY_NAME.toString());
        VideoCapture camera = new VideoCapture(0);
        try 
        {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!camera.isOpened()){
            System.out.println("Camera Error");
        }
        else{
            System.out.println("Camera OK?");
        }
        Mat frame = new Mat();
        camera.read(frame);
    }
}