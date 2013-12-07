/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.exceptions.InvalidLocationException;
import chess.exceptions.InvalidMoveException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author David
 */
public class ChessUtil {

    public static String[] ConvertChessMove(String move) throws InvalidMoveException {
        
        Pattern pattern = Pattern.compile("([abcdefgh][12345678])([abcdefgh][12345678])");
        Matcher matcher = pattern.matcher(move);
        
        if(!matcher.matches()) throw new InvalidMoveException();
        
        String[] rv = new String[2];
        rv[0] = matcher.group(1);
        rv[1] = matcher.group(2);
        return rv;
    }

    
    
    public static int[] ConvertLocation(String location) throws InvalidLocationException {
        int[] rv = new int[2];
        
        Pattern pattern = Pattern.compile("[abcdefgh][12345678]");
        Matcher matcher = pattern.matcher(location);

        if (matcher.matches()) {
            switch (location.substring(0, 1)) {
                case "a":
                    rv[0] = 0;
                    break;
                case "b":
                    rv[0] = 1;
                    break;
                case "c":
                    rv[0] = 2;
                    break;
                case "d":
                    rv[0] = 3;
                    break;
                case "e":
                    rv[0] = 4;
                    break;
                case "f":
                    rv[0] = 5;
                    break;
                case "g":
                    rv[0] = 6;
                    break;
                case "h":
                    rv[0] = 7;
                    break;
            }

            rv[1] = Integer.parseInt(location.substring(1, 2))-1;
        } else {
            throw new InvalidLocationException();
        }
        
        return rv;
    }

}
