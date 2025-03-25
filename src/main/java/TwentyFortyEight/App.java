package TwentyFortyEight;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int GRID_SIZE = 4; // 4x4 grid
    public static final int CELLSIZE = 100; // Cell size in pixels
    public static final int CELL_BUFFER = 8; // Space between cells
    public static final int WIDTH = GRID_SIZE * CELLSIZE + CELL_BUFFER * (GRID_SIZE+1);
    public static final int HEIGHT = WIDTH;
    public static final int FPS = 30;
    public static Cell[][] grid = new Cell[GRID_SIZE][GRID_SIZE];
    public static int currentQuan = 2;
    public static PShape squareBlank;
    public static PImage square2;
    public static PImage square4;
    public static PImage square8;
    public static PImage square16;
    public static PImage square32;
    public static PImage square64;
    public static PImage squareElse;
    public static Queue<Cell> Q = new LinkedList<Cell>();

    public static Random random = new Random();

    public String configPath;

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player
     * and map elements.
     */
    @Override
    public void setup() {
        
        square2 = loadImage("src/main/resources/TwentyFortyEight/2.png");
        square2.resize(CELLSIZE, CELLSIZE);
        square4 = loadImage("src/main/resources/TwentyFortyEight/4.png");
        square4.resize(CELLSIZE, CELLSIZE);
        square8 = loadImage("src/main/resources/TwentyFortyEight/8.png");
        square8.resize(CELLSIZE, CELLSIZE);
        square16 = loadImage("src/main/resources/TwentyFortyEight/16.png");
        square16.resize(CELLSIZE, CELLSIZE);
        square32 = loadImage("src/main/resources/TwentyFortyEight/32.png");
        square32.resize(CELLSIZE, CELLSIZE);
        square64 = loadImage("src/main/resources/TwentyFortyEight/64.png");
        square64.resize(CELLSIZE, CELLSIZE);
        squareElse = loadImage("src/main/resources/TwentyFortyEight/128.png");
        squareElse.resize(CELLSIZE, CELLSIZE);
        
        noStroke();
        frameRate(FPS);
        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                
                grid[j][i] = new Cell(-1);

            }
        }

        int new1X = random.nextInt(GRID_SIZE);
        int new1Y = random.nextInt(GRID_SIZE);
        int new2X = random.nextInt(GRID_SIZE);
        int new2Y = random.nextInt(GRID_SIZE);

        while (new1X == new2X && new1Y == new2Y){
            new2X = random.nextInt(GRID_SIZE);
            new2Y = random.nextInt(GRID_SIZE);
        }
        
        grid[new1X][new1Y].setValue(random.nextInt(2)*2 + 2);
        grid[new2X][new2Y].setValue(random.nextInt(2)*2 + 2);
        // See PApplet javadoc:
        // loadJSONObject(configPath)
        // loadImage(this.getClass().getResource(filename).getPath().toLowerCase(Locale.ROOT).replace("%20",
        // " "));

        // create attributes for data storage, eg board
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    
    @Override
    public void keyPressed(KeyEvent event) {


        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                System.out.print(grid[i][j].getValue() + " ");
            }
            System.out.println();
        }

        int key = event.getKeyCode();
       
        if (key == java.awt.event.KeyEvent.VK_UP){
            for (int j = 0; j < GRID_SIZE; j++){
                for (int i = 0; i < GRID_SIZE; i++){
                    if (grid[i][j].getValue() != -1){
                        Q.add(grid[i][j]);
                        grid[i][j] = new Cell(-1);
                    }
                }
                for (int i = 0; i < GRID_SIZE; i++){
                    if (Q.peek() != null){
                        grid[i][j] = Q.remove();
                        if (Q.peek() != null && Q.peek().getValue() == grid[i][j].getValue()){
                            grid[i][j].setValue(grid[i][j].getValue()*2);
                            currentQuan -= 1;
                            Q.remove();
                        }
                    }
                    
                }
            }

        }

        else if (key == java.awt.event.KeyEvent.VK_DOWN){
            for (int j = 0; j < GRID_SIZE; j++){
                for (int i = GRID_SIZE-1; i >= 0; i--){
                    if (grid[i][j].getValue() != -1){
                        Q.add(grid[i][j]);
                        grid[i][j] = new Cell(-1);
                    }
                }
                for (int i = GRID_SIZE-1; i >= 0; i--){
                    if (grid[i][j].getValue() == -1 && Q.peek() != null){
                        grid[i][j] = Q.remove();
                        System.out.println(grid[i][j]);
                        if (Q.peek() != null && Q.peek().getValue() == grid[i][j].getValue()){
                            grid[i][j].setValue(grid[i][j].getValue()*2);
                            currentQuan -= 1;
                            Q.remove();
                        }
                    }
                    
                }
            }

        }

        else if (key == java.awt.event.KeyEvent.VK_LEFT){
            for (int i = 0; i < GRID_SIZE; i++){
                for (int j = 0; j < GRID_SIZE; j++){
                    if (grid[i][j].getValue() != -1){
                        Q.add(grid[i][j]);
                        grid[i][j] = new Cell(-1);
                    }
                }
                for (int j = 0; j < GRID_SIZE; j++){
                    if (Q.peek() != null){
                        grid[i][j] = Q.remove();
                        if (Q.peek() != null && Q.peek().getValue() == grid[i][j].getValue()){
                            grid[i][j].setValue(grid[i][j].getValue()*2);
                            currentQuan -= 1;
                            Q.remove();
                        }
                    }
                    
                }
            }

        }
        
        else if (key == java.awt.event.KeyEvent.VK_RIGHT){
            for (int i = 0; i < GRID_SIZE; i++){
                for (int j = GRID_SIZE - 1; j >= 0; j--){
                    if (grid[i][j].getValue() != -1){
                        Q.add(grid[i][j]);
                        grid[i][j] = new Cell(-1);
                    }
                }
                for (int j = GRID_SIZE - 1; j >= 0; j--){
                    if (Q.peek() != null){
                        grid[i][j] = Q.remove();
                        if (Q.peek() != null && Q.peek().getValue() == grid[i][j].getValue()){
                            grid[i][j].setValue(grid[i][j].getValue()*2);
                            currentQuan -= 1;
                            Q.remove();
                        }
                    }
                    
                }
            }

        }

        boolean temp = false;
        if (currentQuan + 2 < GRID_SIZE*GRID_SIZE){
            int new1X = random.nextInt(GRID_SIZE);
            int new1Y = random.nextInt(GRID_SIZE);
            int new2X = random.nextInt(GRID_SIZE);
            int new2Y = random.nextInt(GRID_SIZE);
            while ((new1X == new2X && new1Y == new2Y) || grid[new1X][new1Y].getValue() != -1 || grid[new2X][new2Y].getValue() != -1){
                new1X = random.nextInt(GRID_SIZE);
                new1Y = random.nextInt(GRID_SIZE);
                new2X = random.nextInt(GRID_SIZE);
                new2Y = random.nextInt(GRID_SIZE);
            }
            
            grid[new1X][new1Y].setValue(random.nextInt(2)*2 + 2);
            grid[new2X][new2Y].setValue(random.nextInt(2)*2 + 2);
            currentQuan += 2;
        }
        
        else if (currentQuan == GRID_SIZE*GRID_SIZE - 1){
            for (int i = 0; i < GRID_SIZE; i++){
                for (int j = 0; j < GRID_SIZE; j++){
                    if (grid[i][j].getValue() == -1){
                        grid[i][j].setValue(random.nextInt(2)*2 + 2);
                        currentQuan += 1;
                        temp = true;
                        break;
                    }
                }
                if (temp == true){
                    break;
                }
            }
        }
        
    }
    

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if ()

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        background(255);
        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                
                if (grid[i][j].getValue() == 2){
                    image(square2, CELL_BUFFER*(j+1) + CELLSIZE*j, CELL_BUFFER*(i+1) + CELLSIZE*i);
                }
                else if(grid[i][j].getValue() == 4){
                    image(square4, CELL_BUFFER*(j+1) + CELLSIZE*j, CELL_BUFFER*(i+1) + CELLSIZE*i);
                }
                else if(grid[i][j].getValue() == 8){
                    image(square8, CELL_BUFFER*(j+1) + CELLSIZE*j, CELL_BUFFER*(i+1) + CELLSIZE*i);
                }
                else if(grid[i][j].getValue() == 16){
                    image(square16, CELL_BUFFER*(j+1) + CELLSIZE*j, CELL_BUFFER*(i+1) + CELLSIZE*i);
                }
                else if(grid[i][j].getValue() == 32){
                    image(square32, CELL_BUFFER*(j+1) + CELLSIZE*j, CELL_BUFFER*(i+1) + CELLSIZE*i);
                }
                else{
                    squareBlank = createShape(RECT, 0, 0, CELLSIZE, CELLSIZE, 25);
                    squareBlank.setFill(color(216, 216, 216));
                    shape(squareBlank, CELL_BUFFER*(j+1) + CELLSIZE*j, CELL_BUFFER*(i+1) + CELLSIZE*i);
                }
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("TwentyFortyEight.App");
    }

}
