/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class TestLogger {

    public TestLogger() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void runLog() {
        BufferedReader br = null;
        try {
            ChessEngine engine = new ChessEngine(new TestUCI());
            engine.newGame();
            br = new BufferedReader(new FileReader("logs/ChessMoves.log"));
            String line;
            try {
                ArrayList<String> moveList = new ArrayList<>();
                boolean lastLineError = false;
                while ((line = br.readLine()) != null) {
                    if ("New Game".equals(line)) {
                        lastLineError = false;
                        moveList = new ArrayList<>();
                    } else if (line.contains("Error") && !lastLineError) {
                        lastLineError = true;
                        moveList.add(line.substring(6));
                    } else if(!line.isEmpty()){
                        lastLineError = false;
                        moveList.add(line);
                    }
                    // do something with line.
                    //  System.out.println(line);
                }
                for (int i = 0; i < moveList.size(); i++) {
                    String m = moveList.get(i);
                    if (!m.isEmpty()) {
                        String rm = engine.makeMove(m);

//                        if(i % 2 == 0){
//                        String rm = engine.makeMove(m);
//                    }else{
//                        engine.makeMove(m);
//                    }
                        System.out.println(rm);
                    }
                }
                int i = 0;
            } catch (IOException ex) {
            }
        } catch (FileNotFoundException ex) {
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
            }
        }
    }
}
