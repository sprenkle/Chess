/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

/**
 *
 * @author David
 */
public class CameraToBoard {
    public static int getX(int x){
        return 7 - x ;
    }
    
    public static int getY(int y){
        return y;
    }
}
