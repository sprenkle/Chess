/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.imaging;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author David
 */
public class ChessBoardImage {

    private BoardDetails boardDetails;

    public ChessBoardImage() {
        boardDetails = new BoardDetails();
    }

    public ChessBoardImage(int top, int bottom, int left, int right, double squareWidth, double squareHeight) {
        boardDetails = new BoardDetails(left, right, top, bottom, squareWidth, squareHeight);
    }

    public BoardDetails getBoardDetails() {
        return boardDetails;
    }

    public void CalcCorners(BufferedImage bi) {

        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        ArrayList<Integer> bottom = new ArrayList<>();
        ArrayList<Integer> top = new ArrayList<>();

        for (int x = 0; x < bi.getWidth(); x++) {
            BoardLine boardLine = new BoardLine(new Point(x, 0));
            for (int y = 0; y < bi.getHeight(); y++) {
                boardLine.addPixel(bi.getRGB(x, y));
            }
            if (boardLine.foundLine()) {
                top.add(boardLine.getStart());
                bottom.add(boardLine.getEnd());
//                for (int y = boardLine.getStart(); y < boardLine.getEnd(); y++) {
//                    bi.setRGB(x, y, 0xFFFFFF);
//                }
            }
        }

        for (int y = 0; y < bi.getHeight(); y++) {
            BoardLine boardLine = new BoardLine(new Point(0, y));
            for (int x = 0; x < bi.getWidth(); x++) {
                boardLine.addPixel(bi.getRGB(x, y));
            }
            if (boardLine.foundLine()) {
                left.add(boardLine.getStart());
                right.add(boardLine.getEnd());
                for (int x = boardLine.getStart(); x < boardLine.getEnd(); x++) {
                    bi.setRGB(x, y, 0xFFFFFF);
                }
            }
        }

        Collections.sort(left);
        Collections.sort(right);
        Collections.sort(top);
        Collections.sort(bottom);

        if (right.size() > 4) {
            boardDetails.setRight(right.get(right.size() / 2));
        }
        if (left.size() > 4) {
            boardDetails.setLeft(left.get(left.size() / 2));
        }
        boardDetails.setBottom(bottom.get(bottom.size() / 2));
        boardDetails.setTop(top.get(top.size() / 2));

        boardDetails.setSquareHeight((double) (boardDetails.getBottom() - boardDetails.getTop()) / 8.0);
        boardDetails.setSquareWidth((double) (boardDetails.getRight() - boardDetails.getLeft()) / 8.0);
    }

    public boolean isDifferent(int x, int y, BufferedImage bi, double[][][] rgb) {
        int xs = boardDetails.getSquare(x, y).x;
        int xl = boardDetails.getSquare(x, y).width + boardDetails.getSquare(x, y).x;
        int ys = boardDetails.getSquare(x, y).y;
        int yl = boardDetails.getSquare(x, y).y + boardDetails.getSquare(x, y).height;
        double[] rgbAvg = new double[3];
        int size = 0;
        int countDiff = 0;
        for (int x1 = xs; x1 < xl; x1++) {
            for (int y1 = ys; y1 < yl; y1++) {
                Color color = new Color(bi.getRGB(x1, y1));

                double diff = abs(color.getRed() - rgb[x][y][0]) + abs(color.getGreen() - rgb[x][y][1]) + abs(color.getBlue() - rgb[x][y][2]);

                if (diff > 60) {
                    countDiff++;
                }
            }
        }

        return countDiff > 5;
    }

    public int[] getAvgSquareLocation(int x, int y) {
        int[] loc = new int[4];
        loc[0] = boardDetails.getSquare(x, y).x;
        loc[1] = boardDetails.getSquare(x, y).y;
        loc[2] = boardDetails.getSquare(x, y).width + boardDetails.getSquare(x, y).x;
        loc[3] = boardDetails.getSquare(x, y).height + boardDetails.getSquare(x, y).y;
  
        return loc;
    }

    public double[] getAvgSquare(int x, int y, BufferedImage bi) {

        int xs = boardDetails.getSquare(x, y).x;
        int xl = boardDetails.getSquare(x, y).width + boardDetails.getSquare(x, y).x;
        int ys = boardDetails.getSquare(x, y).y;
        int yl = boardDetails.getSquare(x, y).y + boardDetails.getSquare(x, y).height;
        double[] rgbAvg = new double[3];
        int size = 0;

        for (int x1 = xs; x1 < xl; x1++) {
            for (int y1 = ys; y1 < yl; y1++) {
                Color color = new Color(bi.getRGB(x1, y1));

                rgbAvg[0] += color.getRed();
                rgbAvg[1] += color.getGreen();
                rgbAvg[2] += color.getBlue();
                size++;
            }
        }

        return new double[]{rgbAvg[0] / size, rgbAvg[1] / size, rgbAvg[2] / size};
    }

        public int getDiffSquare(int x, int y, BufferedImage bi) {
            ArrayList<Integer> pixelRedValues = new ArrayList<>();
            ArrayList<Integer> pixelGreenValues = new ArrayList<>();
            ArrayList<Integer> pixelBlueValues = new ArrayList<>();
            
            
            
        int xs = boardDetails.getSquare(x, y).x;
        int xl = boardDetails.getSquare(x, y).width + boardDetails.getSquare(x, y).x;
        int ys = boardDetails.getSquare(x, y).y;
        int yl = boardDetails.getSquare(x, y).y + boardDetails.getSquare(x, y).height;

        for (int x1 = xs; x1 < xl; x1++) {
            for (int y1 = ys; y1 < yl; y1++) {
                Color color = new Color(bi.getRGB(x1, y1));
                pixelRedValues.add(color.getRed());
                pixelGreenValues.add(color.getGreen());
                pixelBlueValues.add(color.getBlue());
            }
        }
        
        Collections.sort(pixelRedValues);
        Collections.sort(pixelGreenValues);
        Collections.sort(pixelBlueValues);
        
        int total = 0;
        for(int i = 0 ; i < 10 ; i++){
            total += abs(pixelRedValues.get(i + 10) - pixelRedValues.get(pixelRedValues.size() - 20 + i)) +
                    abs(pixelGreenValues.get(i + 10) - pixelRedValues.get(pixelGreenValues.size() - 20 + i)) +
                    abs(pixelBlueValues.get(i + 10) - pixelRedValues.get(pixelBlueValues.size() - 20 + i));
        }

        return total;
    }

    
}
