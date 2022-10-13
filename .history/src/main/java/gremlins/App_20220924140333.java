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
    public int start = 1;
    public String configPath;
    
    // ! Image variables 
    private PImage brickwall;
    private PImage[] brickwallDestruction;
    private PImage stonewall;
    // public PImage exit; 
    private PImage[] wizardSprites;
    private PImage gremlin;
    

    // ! Object area start
    private Wizard w; // Player controlled character 
    private ArrayList<Immobile> mapToDraw; // map 
    public Fireball f;
    private ArrayList<Gremlin> gArray;
    private ArrayList<Mobile> NPMobileEntities; // non projectile mobile entities

    // ! Object area end 
    public int properX, properY; 
    private int universalCounter = 0;
    private int frameCounter = 0;

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
        File f = new File("level1.txt");
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
            this.gremlin = loadImage(URLDecoder.decode(this.getClass().getResource("gremlin.png").getPath(), StandardCharsets.UTF_8.toString()));
            //this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
            //this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Could not find Wizard");
            e.printStackTrace();
        }

        // * Draws map
        mapToDraw = new ArrayList<Immobile>(); // ArrayList that contains all block objects to be drawn
        gArray = new ArrayList<Gremlin>();
        NPMobileEntities = new ArrayList<Mobile>();
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
                        gArray.add(new Gremlin(xCounter, yCounter, this.gremlin));
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
        NPMobileEntities.add(w);
        NPMobileEntities.addAll(gArray);     
    }

    public ArrayList<Immobile> getMap(){
        return this.mapToDraw;
    }

    public ArrayList<Mobile> getMobiles(){
        return this.NPMobileEntities;
    }

    public ArrayList<Gremlin> getGArray(){
        return this.gArray;
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
            if(f != null){
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

    public int gremlinAI(int dir){
        // assessment of current situation must be done 
        int x = (int) (Math.random() * (4) + 1);
        
        while (x == dir){
            //System.out.println("rerolled");
            x = (int) (Math.random() * (4) + 1);
        }
        //System.out.println(x);
        if (x == 1) {
            gArray.get((int) (random(0, 3))).up();
        }

        else if(x == 2){
            gArray.get((int) (random(0, 3))).right();
        }

        else if (x == 3){
            gArray.get((int) (random(0, 3))).down();
        }

        else if(x == 4){
            gArray.get((int) (random(0, 3))).left();
        }

        return x;

    }    
    public boolean fireballHit(int x){ // block
        for (int i = 0; i < mapToDraw.size(); i++){
            //System.out.println(i.getClass().getName().equals("gremlins.Destructable"));
            //System.out.println("Entered in and checking blocks" + i);
            if(mapToDraw.get(i) == f.collisionBlock && mapToDraw.get(i).getClass().getName().equals("gremlins.Destructable")){ // encountered a destructable block
                //System.out.println("entered in destructable");
                f.hit();
                mapToDraw.get(i).destroy(x);
                //f.collisionBlock = null;
                //i = null;
                if(universalCounter % 16 == 0){
                    f.collisionBlock = null;
                    mapToDraw.remove(f.collisionBlock);
                
                //mapToDraw.set(i, null);
                return true;
            }
            
            
        }
        f.hit();
        return false;
    }
    public boolean fireballHitGremlin(){
        for(Gremlin i : gArray){
            if(i == f.collisionEntity){
                System.out.println("bruh");
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(192,164,132);
        fill(0,0,0);
        w.draw(this);
        if(f != null){
            int collided = f.draw(this);
            if(collided == 1){ // fireball hit something and correct direction 
                frameCounter = universalCounter;
                System.out.println(frameCounter + " bruh");
                f.stop();
                fireballHit(universalCounter % 5);
                //System.out.println("Fireball collision block: " + f.collisionBlock);
                
                f = null;
            }
            
            
        }
        

        for (Immobile i : mapToDraw) {
            if(i != null){
                i.draw(this);
            }
            
        }
        //mapToDraw.get(0).draw(this);
        for (int i = 0; i < gArray.size(); i++) {
            gArray.get(i).draw(this);
            //NPMobileEntities.get(i).draw(this);
        }
        universalCounter += 1;
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
