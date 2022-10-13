package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import java.util.*;
import java.io.*;


public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public int leftBound = 20;
    public int rightBound = 700;
    public int upperBound = 20;
    public int lowerBound = 660;

    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public String configPath;
    
    public PImage brickwall;
    public PImage stonewall;
    // public PImage exit; 
    public PImage[] wizardSprites;

    // ! Object area start
    public Wizard w; // Player controlled character 
    public ArrayList<Immobile> mapToDraw;

    // ! Object area end 

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);

        // Read level file 
        // ! NEED TO MODIFIY WITH JSON LATER
        File f = new File("level1.txt");
        ArrayList<Character[]> mapContents = new ArrayList<Character[]>(); // temporary list
        
        try {
            Scanner sc = new Scanner(f);
            
            while(sc.hasNextLine()){
                Character [] temp = new String(
                    sc.nextLine().trim().toCharArray()).chars()
                    .mapToObj(c-> (char) c).toArray(Character[]::new);
                mapContents.add(temp);
                    
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("bruh moment");
        } catch (IndexOutOfBoundsException e){
            System.out.println("map too big bro");
        }
        
        
        mapToDraw = new ArrayList<Immobile>(); // ArrayList that contains all block objects to be drawn
        int xCounter = 0;
        int yCounter = 0; 
        int incrementer = 20;

            for (Character[] j : mapContents){

                for(Character k :j){
                    System.out.println("X " + xCounter);
                    System.out.println("Y "+ yCounter);
                    if(k == 'X'){
                        // stone block
                        mapToDraw.add(new Indestructable(xCounter, yCounter, this.stonewall));
                        xCounter += incrementer;
                        System.out.println("Stone generated");
                    }

                    else if (k == ' '){
                        // Space 
                        xCounter += incrementer;
                        System.out.println("Space generated");
                    }

                    else if (k =='B'){
                        // brick
                        mapToDraw.add(new Indestructable(xCounter, yCounter, this.brickwall)); // ! TO CHANGE LATER
                        xCounter += incrementer;
                    }

                    else if (k == 'W'){
                        // ! wizard coordinates start here 

                        xCounter += incrementer;
                    }

                    else if (k == 'G'){
                        // gremlin
                        xCounter += incrementer; // ! CHANGE LATER
                    }

                    else if (k == 'E'){
                        // exit
                        xCounter += incrementer; // ! CHANGE LATER
                    }
                    //System.out.print(k);
                }
                yCounter += incrementer;
                xCounter = 0;
            }
        try {
            this.wizardSprites = new PImage[5];
            this.wizardSprites[0] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard2.png").getPath(), StandardCharsets.UTF_8.toString())); // up
            this.wizardSprites[1] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard1.png").getPath(), StandardCharsets.UTF_8.toString()));  // right 
            this.wizardSprites[2] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard3.png").getPath(), StandardCharsets.UTF_8.toString())); // down
            this.wizardSprites[3] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard0.png").getPath(), StandardCharsets.UTF_8.toString())); // left  
            this.wizardSprites[4] = loadImage(URLDecoder.decode(this.getClass().getResource("fireball.png").getPath(), StandardCharsets.UTF_8.toString())); // fireball
            // Load images during setup
            this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
            this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
            //this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
            //this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
            //this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
            w = new Wizard(20, 20, 2, this.wizardSprites[0], this.wizardSprites[1], this.wizardSprites[2], this.wizardSprites[3], this.wizardSprites[4]);
        } catch (UnsupportedEncodingException e) {
            System.err.println("Could not find Wizard");
            e.printStackTrace();
        }

        JSONObject conf = loadJSONObject(new File(this.configPath));


    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode(); //
        System.out.println(key);

        if(key == 37){ //left arrow 
            w.left();
            System.out.println("left");
        }
        else if (key == 39){ //right arrow 
            w.right();
            System.out.println("right");
        }
        else if (key == 38){ //up arrow 
            w.up();
            System.out.println("up");
        }
        else if (key == 40){ //down arrow
            w.down();
            System.out.println("down");
        }
        else if (key == 32){ //space
        System.out.println("Pew");
        
        }
    }
    
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 38 || key == 40) {
            w.stop();
        }

        if (key == 37 || key == 39){
            w.stop();
        }
    }


    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(192,164,132);
        fill(0,0,0);
        w.draw(this);
        for (Immobile i : mapToDraw) {
            i.draw(this);
        }
        //mapToDraw.get(0).draw(this);
        

    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
