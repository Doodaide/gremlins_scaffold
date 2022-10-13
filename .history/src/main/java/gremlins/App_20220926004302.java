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
    //public Fireball f;
    private ArrayList<Gremlin> gArray;
    private ArrayList<Mobile> NPMobileEntities; // non projectile mobile entities
    private ArrayList<Fireball> existingFireballs = new ArrayList<Fireball>();

    // ! Object area end 
    public int properX, properY; 
    private int universalCounter = 0;
    private int frameCounter = 0;
    private boolean decaying = false;

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
        File f = new File("level0.txt");
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
        existingFireballs = new ArrayList<Fireball>();
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
        if (key == 32){ //space
            if(w.getX() % 20 == 0 || w.getY() % 20 == 0){
                //System.out.println("Fireball added");
                existingFireballs.add(new Fireball(w.getX(), w.getY(), wizardSprites[4]));
                Fireball f = existingFireballs.get(existingFireballs.size() - 1);
                
                //f = w.shoot();
                if(f != null){
                    if(w.direction == 0){
                        System.out.println("up");
                        f.up();
                    }
        
                    else if(w.direction == 1){
                        System.out.println("right ");
                        f.right();
                    }
        
                    else if(w.direction == 2){
                        System.out.println("down");
                        f.down();
                    }
        
                    else if(w.direction == 3){
                        System.out.println("left");
                        f.left();
                    }
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
            System.out.println("Map element         " + mapToDraw.get(i));
            if(mapToDraw.get(i) != null){
                if(mapToDraw.get(i) == existingFireballs.get(existingFireballs.size() - 1).collisionBlock && 
                mapToDraw.get(i).getClass().getName().equals("gremlins.Destructable")){ // encountered a destructable block
                //System.out.println("entered in destructable");
                existingFireballs.get(existingFireballs.size() - 1).hit();
                mapToDraw.get(i).destroy(x);
                return true;
            }
            } 
        }
        existingFireballs.get(existingFireballs.size() - 1).hit();
        return false;
    }
    public boolean fireballHitGremlin(){
        for(Gremlin i : gArray){
            if(i == existingFireballs.get(existingFireballs.size() - 1).collisionEntity){
                System.out.println("bruh");
                return true;
            }
        }
        return false;
    }
    
    public void fireballMechanics(){
        for(Fireball f: existingFireballs){ // checks through every fireball
            if( f!= null){
                int collided = f.draw(this); // draws a non-null fireball
                // fireball hit something new (1st condition)
                // or if the fireball has hit something and is in a decay sequence
                // and that the block it has hit is viable. 
                if((collided == 1 || f.getDecaying()) && f.collisionBlock.getViable()){ 
                    f.setDecaying(true); // set decay state to true
                    f.hit(); // stop drawing the fireball
                    
                    if(f.getFrameCounter() == 16){ // always check if it's the end of the sequence 
                        //f.collisionBlock.update(); 
                        if(f.collisionBlock.getViable()){ // block is not currently decaying 
                            if(mapToDraw.indexOf(f.collisionBlock) != -1){ // the block actually exists
                                if(fireballHit(0)){
                                    mapToDraw.set(mapToDraw.indexOf(f.collisionBlock), null);
                                }
                            }
                            
                            System.out.println("Fireball nulled");
                            //f = null;
                            existingFireballs.set(existingFireballs.indexOf(f), null);
                            f = null;
                        }
                    }

                    else if(f.getFrameCounter() % 4 == 0){ // been either 0, 4, 8, 12, 16 frames since firball hit 
                        if(f.collisionBlock.update() == 0){ // indestructable block was hit

                        }
                    }

                    if(f != null){
                        f.incFrameCounter(); // if the fireball has not hit an indestructable entity 
                    }
                }
            }
        }
    }
    
    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(192,164,132);
        fill(0,0,0);
        w.draw(this);
        System.out.println(this.existingFireballs.size());
        /* 
        for(Fireball f: existingFireballs){ // checks through every fireball
            if( f!= null){
                int collided = f.draw(this); // draws a non-null fireball

                if(collided == 1 || f.getDecaying()){ // fireball hit something new (1st condition), or the fireball has hit something and is in a decay sequence
                    f.setDecaying(true); // set decay state to true
                    f.hit(); // stop drawing the fireball
                    
                    if(f.getFrameCounter() == 16){ // always check if it's the end of the sequence 
                        //f.collisionBlock.update(); 
                        if(f.collisionBlock.getViable()){ // block is not currently decaying 
                            if(mapToDraw.indexOf(f.collisionBlock) != -1){ // the block actually exists
                                if(fireballHit(0)){
                                    mapToDraw.set(mapToDraw.indexOf(f.collisionBlock), null);
                                }
                            }
                            
                            System.out.println("Fireball nulled");
                            //f = null;
                            existingFireballs.set(existingFireballs.indexOf(f), null);
                            f = null;
                        }
                    }

                    else if(f.getFrameCounter() % 4 == 0){ // been either 0, 4, 8, 12, 16 frames since firball hit 
                        if(f.collisionBlock.update() == 0){ // indestructable block was hit

                        }
                    }

                    if(f != null){
                        f.incFrameCounter(); // if the fireball has not hit an indestructable entity 
                    }
                }
            }
        }
        */


        for (Fireball f : existingFireballs){
            //System.out.println(f);
            if(f != null){  
                int collided = f.draw(this);
                if((collided == 1 || f.getDecaying()) && f.collisionBlock.getViable()){ // fireball hit something and correct direction 
                    System.out.println("Collided");
                        f.setDecaying(true);
                        //f.stop();
                        f.hit();
                        System.out.println("Fireball collision block: " + f.collisionBlock);
                        if(f.getFrameCounter() == 16){
                            //frameCounter = 0;
                            //f.collisionBlock.update(); // ! Problem!!!! 
                            System.out.println("Collision block: " + f.collisionBlock.getViable());
                            System.out.println("Collsiion block index: " + mapToDraw.indexOf(f.collisionBlock));
                            if(f.collisionBlock.getViable()){
                                if(mapToDraw.indexOf(f.collisionBlock) != -1){
                                    if(fireballHit(0)){
                                        //mapToDraw.remove(f.collisionBlock);
                                        mapToDraw.set(mapToDraw.indexOf(f.collisionBlock), null);
                                    }
                                }
                                
                                System.out.println("Fireball nulled");
                                //f = null;
                                existingFireballs.set(existingFireballs.indexOf(f), null);
                                f = null;
                            }
                            
                        }    
                        else if(f.getFrameCounter() % 4 == 0){
                            System.out.println(f + " " + f.getFrameCounter());
                            System.out.println("Fireball Collision block: " + f.collisionBlock);
                            if(f != null && f.collisionBlock != null){
                                System.out.println("Fireball: " + f);
                                if(f.collisionBlock.update() == 0){ // indestructable
                                    existingFireballs.set(existingFireballs.indexOf(f), null);
                                    f = null;
                                }
                            }
                            else{
                                System.out.println("peepeepoopoo"); // !HERE ERROR FIX LATER 
                            }
                        }
                        if(f != null){
                            f.incFrameCounter(); 
                        }
                        
                }
                if((collided == 1 || f.getDecaying()) && f.collisionBlock.getViable()){
                    f = null;
                    System.out.println("Bruh moment");

                }
                
                
                
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
        ArrayList<Fireball> temp = new ArrayList<Fireball>();
        for (Fireball e : existingFireballs) {
            if(e != null){
                temp.add(e);
            }
        }
        
        this.existingFireballs = temp;
        temp = null;
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
