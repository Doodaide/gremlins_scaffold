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

    // ! Object area start
    public Wizard w;
    public ArrayList<Character[]> mapContents;


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
        File f = new File("level1.txt");
        ArrayList<Character[]> mapContents = new ArrayList<Character[]>();
        
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

        try {
            w = new Wizard(20, 20, 2, 
            loadImage(URLDecoder.decode(this.getClass().getResource("wizard2.png").getPath(), StandardCharsets.UTF_8.toString())), // up imgRight, imgDown, imgLeft, projectile)
            loadImage(URLDecoder.decode(this.getClass().getResource("wizard1.png").getPath(), StandardCharsets.UTF_8.toString())),  // right 
            loadImage(URLDecoder.decode(this.getClass().getResource("wizard3.png").getPath(), StandardCharsets.UTF_8.toString())), // down
            loadImage(URLDecoder.decode(this.getClass().getResource("wizard0.png").getPath(), StandardCharsets.UTF_8.toString())), // left  
            loadImage(URLDecoder.decode(this.getClass().getResource("fireball.png").getPath(), StandardCharsets.UTF_8.toString())));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Could not find Wizard");
            e.printStackTrace();
        }
        ArrayList<Immobile> border = new ArrayList<Immobile>();
            for (Character[] j : mapContents){
                for(Character k :j){
                    if(k == 'X'){
                        // stone block
                        border.add(new Indestructable(0, 0, this.stonewall));
                    }

                    else if (k == ' '){
                        // Space 
                        ;
                    }

                    else if (k =='B'){
                        // brick
                        ;
                    }

                    else if (k == 'W'){
                        // wizard 
                        ;
                    }

                    else if (k == 'G'){
                        // gremlin
                        ;
                    }

                    else if (k == 'E'){
                        // exit
                        ;
                    }
                    //System.out.print(k);
                }
                //System.out.println();
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
        background(0,0,0);
        fill(100,100,100);
        w.draw(this);
        for (Indestructable i : border) {
            
        }
        
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
