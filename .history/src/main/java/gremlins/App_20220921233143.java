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
    
    // ! Image variables 
    public PImage brickwall;
    public PImage[] brickwallDestruction;
    public PImage stonewall;
    // public PImage exit; 
    public PImage[] wizardSprites;

    // ! Object area start
    public Wizard w; // Player controlled character 
    public ArrayList<Immobile> mapToDraw; // map 
    public Fireball f;

    // ! Object area end 
    public int properX, properY; 

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

        // * Read level file 
        // ! NEED TO MODIFIY WITH JSON LATER
        File f = new File("testLevel.txt");
        ArrayList<Character[]> mapContents = new ArrayList<Character[]>(); // temporary list
        
        // * Parse file
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
        
        // * load all images
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
            this.brickwallDestruction = new PImage[4];
            this.brickwallDestruction[0] = loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
            this.brickwallDestruction[1] = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
            this.brickwallDestruction[2] = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
            this.brickwallDestruction[3] = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
                
            
            //this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
            //this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
            //this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Could not find Wizard");
            e.printStackTrace();
        }

        // * Draws map
        mapToDraw = new ArrayList<Immobile>(); // ArrayList that contains all block objects to be drawn
        int xCounter = 0;
        int yCounter = 0; 
        int incrementer = 20;
        int wizStartX = 0, wizStartY = 0;
        for (Character[] j : mapContents){

                for(Character k :j){
                    // System.out.println("X " + xCounter);
                    // System.out.println("Y "+ yCounter);
                    if(k == 'X'){ // stone block
                        mapToDraw.add(new Indestructable(xCounter, yCounter, this.stonewall));
                    }

                    else if (k == ' '){ // Space 
                        ;
                    }

                    else if (k =='B'){ // Brick
                        mapToDraw.add(new Destructable(xCounter, yCounter, this.brickwall, this.brickwallDestruction)); 
                    }

                    else if (k == 'W'){ // wizard spawnpoint
                        wizStartX = xCounter;
                        wizStartY = yCounter;
                    }

                    else if (k == 'G'){ // ! Gremlin spawnpoint ... CHANGE LATER 
                        ;
                    }

                    else if (k == 'E'){ // ! Exit ... CHANGE LATER
                        ;
                    }
                    xCounter += incrementer; 
                }
                yCounter += incrementer;
                xCounter = 0;
            }

        JSONObject conf = loadJSONObject(new File(this.configPath));

        // * Initialise objects 
        // Wizard
        w = new Wizard(wizStartX, wizStartY, 2, this.wizardSprites[0], this.wizardSprites[1], this.wizardSprites[2], this.wizardSprites[3], this.wizardSprites[4]);
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode(); //
        //System.out.println(key); 
        if(key == 37){ //left arrow 
            w.left();
            //System.out.println("left");
        }
        else if (key == 39){ //right arrow 
            w.right();
            //System.out.println("right");
        }
        else if (key == 38){ //up arrow 
            w.up();
            //System.out.println(w.getX() + " " + w.getY());
        }
        else if (key == 40){ //down arrow
            w.down();
            //System.out.println("down");
        }
        else if (key == 32){ //space
            f = w.shoot();
            if(w.direction == 0){
                f.up();
            }

            else if(w.direction == 1){
                f.right();
            }

            else if(w.direction == 2){
                f.down();
            }

            else if(w.direction == 3){
                f.left();
            }
        }
    }
    
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == 38){ // up
            w.yStop();
        }

        if(key == 40){ // down
            w.yStop();
        }

        if(key == 37){ // left 
            w.xStop();
        }

        if (key == 39){ // right
            w.xStop();
        }
    }

    private void movementCompensation(){
        while(w.getY() % 20 != 0){
            w.up();
            w.setY(w.getY() - 2);
        }
        while(w.getY() % 20 != 0){
            w.down();
            w.draw(this);
            System.out.println("down released");
        }
        while(w.getX() % 20 != 0){
            w.left();
            w.draw(this);
            System.out.println("left released");
        }
        while(w.getX() % 20 != 0){
            w.right();
            w.draw(this);
            System.out.println("right released");
        }


    }
    
    public boolean fireballHit(){
        for (Immobile i : mapToDraw){
            if(i == f.collisionBlock){ // encountered a destructable block
                System.out.println("entered in destructable");
                //System.out.println(i.getClass().getName());
                if(fireballHitObject(f.collisionBlock)){
                System.out.println("hit detected");
                i.destroy();
                mapToDraw.remove(f.collisionBlock);
                return true;
                }
            }
            
            
        }
        //f = null;
        f.stop();
        return false;
    }
    
    public boolean fireballHitObject(Entity i){
        //System.out.println(i.getX() + " " + i.getY());
        //System.out.println(f.getX() + " " + f.getY());
        if((i.getX() - 20 <= f.getX() && i.getY() == f.getY()) // || // hit left of block
            //(i.getX() == f.getX() && i.getY() == f.getY() + 20) || // hit top of block
            //(i.getX() == f.getX() - 20 && i.getY() == f.getY()) || // hit right of block
            //(i.getX() == f.getX() && i.getY() == f.getY() - 20) // hit bottom of block
        ){
            System.out.println("Hit detected from FireBall Hit object method");
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(192,164,132);
        fill(0,0,0);
        //this.movementCompensation();
        w.draw(this);
        if(f != null){
            int collided = f.draw(this);
            if(collided == 1){ // fireball hit something
                System.out.println("Fireball hit");
                fireballHit();
                System.out.println("Fireball hit exit");
                System.out.println("Fireball collision block: " + f.collisionBlock);
                f = null;
            }
            
        }
        

        for (Immobile i : mapToDraw) {
            if(i != null){
                i.draw(this);
            }
            
        }
        //mapToDraw.get(0).draw(this);
        

    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
