package TwentyFortyEight;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PFont;
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

    public static int GRID_SIZE = 4;
    public static final int CELLSIZE = 100; // Cell size in pixels
    public static final int CELL_BUFFER = 8; // Space between cells
    public static final int CLOCK_TOP = 50;
    public static int WIDTH = GRID_SIZE * CELLSIZE + CELL_BUFFER * (GRID_SIZE+1);
    public static int HEIGHT = WIDTH + CLOCK_TOP;
    public static final int FPS = 30;
    public static Cell[][] grid = new Cell[GRID_SIZE][GRID_SIZE];
    public static Cell[][] merge = new Cell[GRID_SIZE][GRID_SIZE];
    public static int currentQuan = 2;
    public static PShape squareBlank;
    public static PImage square2;
    public static PImage square4;
    public static PImage square8;
    public static PImage square16;
    public static PImage square32;
    public static PImage square64;
    public static PImage square128;
    public static PImage endPic;
    public static PFont f;
    public static int startTime;
    public static int currentTime;
    public static boolean flag;
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
        //GRID_SIZE = Integer.parseInt(args[0]);
        f = createFont("Georgia", 20);
        textFont(f);
        startTime = millis();
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
        square128 = loadImage("src/main/resources/TwentyFortyEight/128.png");
        square128.resize(CELLSIZE, CELLSIZE);
        endPic = loadImage("src/main/resources/TwentyFortyEight/endPic.png");
        endPic.resize(WIDTH, HEIGHT);
        noStroke();
        squareBlank = createShape(RECT, 0, 0, CELLSIZE, CELLSIZE, 25);
        
        frameRate(FPS);
        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                
                grid[j][i] = new Cell(-1, 
                    CELL_BUFFER*(i+1) + CELLSIZE*i,
                    CELL_BUFFER*(j+1) + CELLSIZE*j + CLOCK_TOP,
                    CELL_BUFFER*(i+1) + CELLSIZE*i, 
                    CELL_BUFFER*(j+1) + CELLSIZE*j + CLOCK_TOP);
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
        int key = event.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_UP){
            for (int j = 0; j < GRID_SIZE; j++){
                for (int i = 0; i < GRID_SIZE; i++){
                    if (grid[i][j].getValue() != -1){
                        Q.add(grid[i][j]);
                    }
                }
            
                for (int i = 0; i < GRID_SIZE; i++){
                    if (Q.peek() != null){
                        grid[i][j] = Q.remove();
                        if (Q.peek() != null && Q.peek().getValue() == grid[i][j].getValue()){
                            grid[i][j].setValue(grid[i][j].getValue()*2);
                            currentQuan -= 1;
                            Q.peek().targetX = CELL_BUFFER*(j+1) + CELLSIZE*j;
                            Q.peek().targetY = CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP;
                            merge[i][j] = Q.peek();
                            Q.remove();
                        }
                        grid[i][j].targetX = CELL_BUFFER*(j+1) + CELLSIZE*j;
                        grid[i][j].targetY =  CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP;
                    }
                    else{
                        grid[i][j] = new Cell(-1, 
                            CELL_BUFFER*(j+1) + CELLSIZE*j,
                            CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP,
                            CELL_BUFFER*(j+1) + CELLSIZE*j, 
                            CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP);
                    }
                }
            }
            
    }

        else if (key == java.awt.event.KeyEvent.VK_DOWN){
            for (int j = 0; j < GRID_SIZE; j++){
                for (int i = GRID_SIZE-1; i >= 0; i--){
                    if (grid[i][j].getValue() != -1){
                        Q.add(grid[i][j]);
                    }
                }
                for (int i = GRID_SIZE-1; i >= 0; i--){
                    if (Q.peek() != null){
                        grid[i][j] = Q.remove();
                        if (Q.peek() != null && Q.peek().getValue() == grid[i][j].getValue()){
                            grid[i][j].setValue(grid[i][j].getValue()*2);
                            currentQuan--;
                            Q.peek().targetX = CELL_BUFFER*(j+1) + CELLSIZE*j;
                            Q.peek().targetY = CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP;
                            merge[i][j] = Q.peek();
                            Q.remove();
                        }
                        grid[i][j].targetX = CELL_BUFFER*(j+1) + CELLSIZE*j;
                        grid[i][j].targetY =  CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP;
                    }
                    else{
                        grid[i][j] = new Cell(-1, 
                            CELL_BUFFER*(j+1) + CELLSIZE*j,
                            CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP,
                            CELL_BUFFER*(j+1) + CELLSIZE*j, 
                            CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP);
                    }
                    
                }
            }

        }

        else if (key == java.awt.event.KeyEvent.VK_LEFT){
            for (int i = 0; i < GRID_SIZE; i++){
                for (int j = 0; j < GRID_SIZE; j++){
                    if (grid[i][j].getValue() != -1){
                        Q.add(grid[i][j]);
                    }
                }
                for (int j = 0; j < GRID_SIZE; j++){
                    if (Q.peek() != null){
                        grid[i][j] = Q.remove();
                        if (Q.peek() != null && Q.peek().getValue() == grid[i][j].getValue()){
                            grid[i][j].setValue(grid[i][j].getValue()*2);
                            currentQuan--;
                            Q.peek().targetX = CELL_BUFFER*(j+1) + CELLSIZE*j;
                            Q.peek().targetY = CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP;
                            merge[i][j] = Q.peek();
                            Q.remove();
                        }
                        grid[i][j].targetX = CELL_BUFFER*(j+1) + CELLSIZE*j;
                        grid[i][j].targetY =  CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP;
                    }
                    else{
                        grid[i][j] = new Cell(-1, 
                            CELL_BUFFER*(j+1) + CELLSIZE*j,
                            CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP,
                            CELL_BUFFER*(j+1) + CELLSIZE*j, 
                            CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP);

                    }
                    
                }
            }

        }
        
        else if (key == java.awt.event.KeyEvent.VK_RIGHT){
            for (int i = 0; i < GRID_SIZE; i++){
                for (int j = GRID_SIZE - 1; j >= 0; j--){
                    if (grid[i][j].getValue() != -1){
                        Q.add(grid[i][j]);
                    }
                }
                for (int j = GRID_SIZE - 1; j >= 0; j--){
                    if (Q.peek() != null){
                        grid[i][j] = Q.remove();
                        if (Q.peek() != null && Q.peek().getValue() == grid[i][j].getValue()){
                            grid[i][j].setValue(grid[i][j].getValue()*2);
                            currentQuan--;
                            Q.peek().targetX = CELL_BUFFER*(j+1) + CELLSIZE*j;
                            Q.peek().targetY = CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP;
                            merge[i][j] = Q.peek();
                            Q.remove();
                        }
                        grid[i][j].targetX = CELL_BUFFER*(j+1) + CELLSIZE*j;
                        grid[i][j].targetY =  CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP;
                    }
                    else{
                        grid[i][j] = new Cell(-1, 
                            CELL_BUFFER*(j+1) + CELLSIZE*j,
                            CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP,
                            CELL_BUFFER*(j+1) + CELLSIZE*j, 
                            CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP);
                    }
                    
                }

            }

        }

        if (currentQuan < GRID_SIZE*GRID_SIZE){
            int new1X = random.nextInt(GRID_SIZE);
            int new1Y = random.nextInt(GRID_SIZE);
            while (grid[new1X][new1Y].getValue() != -1){
                new1X = random.nextInt(GRID_SIZE);
                new1Y = random.nextInt(GRID_SIZE);
            }
            
            grid[new1X][new1Y].setValue(random.nextInt(2)*2 + 2);
            currentQuan += 1;
        }
        

        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                System.out.print(grid[i][j].getValue() + " ");
            }
            System.out.println();
        }
        System.out.println();
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
        if (x > CELL_BUFFER && y > CLOCK_TOP + CELL_BUFFER && (x-CELL_BUFFER) %  (CELLSIZE + CELL_BUFFER) < CELLSIZE && (y-CELL_BUFFER-CLOCK_TOP) %  (CELLSIZE + CELL_BUFFER) < CELLSIZE){
            if (grid[(y-CELL_BUFFER-CLOCK_TOP)/(CELLSIZE+CELL_BUFFER)][(x-CELL_BUFFER)/(CELLSIZE+CELL_BUFFER)].getValue() == -1){
                grid[(y-CELL_BUFFER-CLOCK_TOP)/(CELLSIZE+CELL_BUFFER)][(x-CELL_BUFFER)/(CELLSIZE+CELL_BUFFER)].setValue(random.nextInt(2)*2 + 2);
                currentQuan++;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x > CELL_BUFFER && y > CELL_BUFFER+CLOCK_TOP && (x-CELL_BUFFER) %  (CELLSIZE + CELL_BUFFER) < CELLSIZE && (y-CELL_BUFFER-CLOCK_TOP) %  (CELLSIZE + CELL_BUFFER) < CELLSIZE){
            grid[(y-CELL_BUFFER-CLOCK_TOP)/(CELLSIZE+CELL_BUFFER)][(x-CELL_BUFFER)/(CELLSIZE+CELL_BUFFER)].hovered = true;
            //System.out.println("grid" + ((y-CELL_BUFFER-CLOCK_TOP)/(CELLSIZE+CELL_BUFFER)) + " " + ((x-CELL_BUFFER)/(CELLSIZE+CELL_BUFFER)) + " is hovered");
        }
        else{
            for (int i = 0; i < GRID_SIZE; i++){
                for (int j = 0; j < GRID_SIZE; j++){
                    grid[i][j].hovered = false;
                }
            }
        }
    }

    public boolean ends(){
        if (currentQuan < GRID_SIZE * GRID_SIZE){
            return false;
        }
        for (int i = 0; i < GRID_SIZE - 1; i++){
            for (int j = 0; j < GRID_SIZE - 1; j++){
                if (grid[i][j].getValue() == grid[i+1][j].getValue() || grid[i][j].getValue() == grid[i][j+1].getValue()){
                    return false;
                }
            }
        }
        for (int i = 0; i < GRID_SIZE-1; i++){
            if (grid[i][GRID_SIZE-1].getValue() == grid[i+1][GRID_SIZE-1].getValue()){
                return false;
            }
            if (grid[GRID_SIZE-1][i].getValue() == grid[GRID_SIZE-1][i+1].getValue()){
                return false;
            }
        }
        return true;
    }

    public void generateBackground(){
        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                if (grid[i][j].hovered == true){
                    squareBlank.setFill(color(230, 230, 230));
                }
                shape(squareBlank, CELL_BUFFER*(j+1) + CELLSIZE*j, CELL_BUFFER*(i+1) + CELLSIZE*i + CLOCK_TOP);
                squareBlank.setFill(color(216, 216, 216));
            }
        }
    }

    public void generateCells(){
        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                if (grid[i][j].getValue() == 2){
                    image(square2, grid[i][j].nowX, grid[i][j].nowY);
                    grid[i][j].move();
                }
                else if(grid[i][j].getValue() == 4){
                    image(square4, grid[i][j].nowX, grid[i][j].nowY);
                    grid[i][j].move();
                }
                else if(grid[i][j].getValue() == 8){
                    image(square8, grid[i][j].nowX, grid[i][j].nowY);
                    grid[i][j].move();
                }
                else if(grid[i][j].getValue() == 16){
                    image(square16, grid[i][j].nowX, grid[i][j].nowY);
                    grid[i][j].move();
                }
                else if(grid[i][j].getValue() == 32){
                    image(square32, grid[i][j].nowX, grid[i][j].nowY);
                    grid[i][j].move();
                }
                else if(grid[i][j].getValue() == 64){
                    image(square64, grid[i][j].nowX, grid[i][j].nowY);
                    grid[i][j].move();
                }
                else if (grid[i][j].getValue() == 128){
                    image(square128, grid[i][j].nowX, grid[i][j].nowY);
                    grid[i][j].move();
                }
            }
        }
    }

public void moveMerge(){
    for (int i = 0; i < GRID_SIZE; i++){
        for (int j = 0; j < GRID_SIZE; j++){
            if (merge[i][j] != null && (merge[i][j].nowX != merge[i][j].targetX || merge[i][j].nowY != merge[i][j].targetY)){
                if (merge[i][j].getValue() == 2){
                    image(square2, merge[i][j].nowX, merge[i][j].nowY);
                    merge[i][j].move();
                }
                else if(merge[i][j].getValue() == 4){
                    image(square4, merge[i][j].nowX, merge[i][j].nowY);
                    merge[i][j].move();
                }
                else if(merge[i][j].getValue() == 8){
                    image(square8, merge[i][j].nowX, merge[i][j].nowY);
                    merge[i][j].move();
                }
                else if(merge[i][j].getValue() == 16){
                    image(square16, merge[i][j].nowX, merge[i][j].nowY);
                    merge[i][j].move();
                }
                else if(merge[i][j].getValue() == 32){
                    image(square32, merge[i][j].nowX, merge[i][j].nowY);
                    merge[i][j].move();
                }
                else if(merge[i][j].getValue() == 64){
                    image(square64, merge[i][j].nowX, merge[i][j].nowY);
                    merge[i][j].move();
                }
                else if (merge[i][j].getValue() == 128){
                    image(square128, merge[i][j].nowX, merge[i][j].nowY);
                    merge[i][j].move();
                }
            }
            else{
                merge[i][j] = null;
            }

            
        }
    }
}
/*
    public void moveMerge(){
        while (merge.peek() != null){
            Cell temp = merge.peek();
            System.out.println("merging" + temp.getValue() + "from " + temp.nowX + " " + temp.nowY + " to " + temp.targetX + " " + temp.targetY);
            merge.remove();
            if (temp.nowX != temp.targetX || temp.nowY != temp.targetY){
                System.out.println("moving!" + temp.nowX + " " + temp.nowY);
                if (temp.getValue() == 2){
                    image(square2, temp.nowX, temp.nowY);
                    temp.move();
                }
                else if(temp.getValue() == 4){
                    image(square4, temp.nowX, temp.nowY);
                    temp.move();
                }
                else if(temp.getValue() == 8){
                    image(square8, temp.nowX, temp.nowY);
                    temp.move();
                }
                else if(temp.getValue() == 16){
                    image(square16, temp.nowX, temp.nowY);
                    temp.move();
                }
                else if(temp.getValue() == 32){
                    image(square32, temp.nowX, temp.nowY);
                    temp.move();
                }
                else if(temp.getValue() == 64){
                    image(square64, temp.nowX, temp.nowY);
                    temp.move();
                }
                else if (temp.getValue() == 128){
                    image(square128, temp.nowX, temp.nowY);
                    temp.move();
                }
            }
            temp = null;
            
        }
    }
    */

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        background(255);
        currentTime = millis();
        fill(0);
        text(String.format("%02d : %02d : %02d", (currentTime-startTime)/1000/60/60, (currentTime-startTime)/1000/60%60, (currentTime-startTime)/1000%60), WIDTH - 150, 40);
        generateBackground();
        moveMerge();
        generateCells();
        
        
        if (ends()){
            System.out.println("theend");
            
            imageMode(CENTER);
            image(endPic, width/2, height/2);
        }
    }

    public static void main(String[] args) {
        if (args.length == 1){
            GRID_SIZE = Integer.parseInt(args[0]);
            WIDTH = GRID_SIZE * CELLSIZE + CELL_BUFFER * (GRID_SIZE+1);
            HEIGHT = WIDTH + CLOCK_TOP;
            grid = new Cell[GRID_SIZE][GRID_SIZE];
        }
        PApplet.main("TwentyFortyEight.App");
    }

}
