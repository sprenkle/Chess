package prototype.servos;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pi
 */
public class TestOpenCv {
    public static void main(String[] arg){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = Mat.eye(3,3,CvType.CV_8UC1);
        System.out.println("mat = " + mat.dump());


        System.out.println("Hello World");
    }
    
    
}
