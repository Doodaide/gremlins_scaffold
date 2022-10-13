package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.Random;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


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

    // ! Object area start
    public Wizard w;



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

        try {
            w = new Wizard(50, 50, 2, 
            loadImage(URLDecoder.decode(this.getClass().getResource("wizard2.png").getPath(), StandardCharsets.UTF_8.toString())), // up imgRight, imgDown, imgLeft, projectile)
            loadImage(URLDecoder.decode(this.getClass().getResource("wizard1.png").getPath(), StandardCharsets.UTF_8.toString())),  // right 
            loadImage(URLDecoder.decode(this.getClass().getResource("wizard3.png").getPath(), StandardCharsets.UTF_8.toString())), // down
            loadImage(URLDecoder.decode(this.getClass().getResource("wizard0.png").getPath(), StandardCharsets.UTF_8.toString())), // left  
            loadImage(URLDecoder.decode(this.getClass().getResource("fireball.png").getPath(), StandardCharsets.UTF_8.toString())))
            
            
            
            
            
        } catch (Exception e) {
            // TODO: handle exception
        }

        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        //this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        //this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        //this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        

        JSONObject conf = loadJSONObject(new File(this.configPath));


    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){

    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){

    }


    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
