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
    public ArrayList<Mobile> NPMobileEntities; // non projectile mobile entities
    private ArrayList<Fireball> existingFireballs = new ArrayList<Fireball>();
    private ArrayList<ArrayList<Entity>> allObjects = new ArrayList<ArrayList<Entity>>();
    

    // ! Object area end 
    public int properX, properY; 
    private int universalCounter = 0;
    private int seconds = 0;
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
        allObjects = new ArrayList<ArrayList<Entity>>();
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
        w = new Wizard(wizStartX, wizStartY, 2, this.wizardSprites);
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
                        //System.out.println("up");
                        f.up();
                    }
        
                    else if(w.direction == 1){
                        //System.out.println("right ");
                        f.right();
                    }
        
                    else if(w.direction == 2){
                        //System.out.println("down");
                        f.down();
                    }
        
                    else if(w.direction == 3){
                        //System.out.println("left");
                        f.left();
                    }
                }
                
            }
            NPMobileEntities.addAll(existingFireballs);
            
            
            
        }
        
        if(key == 65){ //a
            gArray.get(0).left();
            //System.out.println("left");
        }
        else if (key == 68){ //d
            gArray.get(0).right();
            //System.out.println("right");
        }
        else if (key == 87){ //w
            gArray.get(0).up();
            //System.out.println(w.getX() + " " + w.getY());
        }
        else if (key == 83){ //s
            gArray.get(0).down();
            //System.out.println("down");
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

        if(key == 65){ //a
            gArray.get(0).xStop();
            //System.out.println("left");
        }
        else if (key == 68){ //d
            gArray.get(0).xStop();
            //System.out.println("right");
        }
        else if (key == 87){ //w
            gArray.get(0).yStop();
            //System.out.println(w.getX() + " " + w.getY());
        }
        else if (key == 83){ //s
            gArray.get(0).yStop();
            //System.out.println("down");
        }
    }

    // dir = current direction the gremlin is facing 
    // ! special case of U shaped entrance to consider
    public int gremlinAI(int dir, Gremlin g){
        /*
         * // assessment of current situation must be done 

        int nonIdealDirection = dir;
        
        //boolean[] travelOptions = getSideAV();

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
         */
        if(g.getX() % 20 ==0 && g.getY() % 20 == 0){
            //System.out.println("Can draw");
            boolean canMove = false;
            // dir is the current direction the gremlin is travelling in
            // find counterpart
            int prevLoc = 5;
            if(dir == 0){prevLoc = 2;}
            else if(dir == 1){prevLoc = 3;}
            else if(dir == 2){prevLoc = 0;}
            else if(dir == 3){prevLoc = 1;}
            while(!canMove){
                // ! Questionable Code !!!!!!!!!!!!!
                int x = (int) (Math.random() * (4));
                g.setDirection(x);
                boolean[] travelOptions = g.getSideAV();
                for (boolean b : travelOptions) {
                    //System.out.print(b + " ");
                }
                //System.out.println();
                if(travelOptions[x]){
                    //System.out.println("broken out");
                    canMove = true;
                }
            }

            return prevLoc; // return proper direction to travel 
        }
        //System.out.println("Can't draw");
        return dir;

    }    
    public boolean fireballHit(Fireball f){ // block
        for(Immobile i : mapToDraw){
            if(i != null){
                if(i == f.collisionBlock && i.getClass().getName().equals("gremlins.Destructable")){
                    i.destroy(0);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean fireballHitGremlin(Fireball f){
        for(Gremlin g : gArray){
            if(g != null){
                if(g == f.collisionEntity){
                    System.out.println("bruh");
                    return true;
                }
            }
        }
        return false;
    }
    
    public void fireballMechanics(){
        for(Fireball f: existingFireballs){ // checks through every fireball
            //System.out.println(f);
            if(f!= null){
                int collided = f.tick(this); // draws a non-null fireball
                f.draw(this);
                System.out.println("Hit something " + collided);
                System.out.println("Entity hit " + f.getCollisionEntity());

                // a block was hit
                if((collided == 1 || f.getDecaying()) && f.getCollisionEntity() == null){ 
                    System.out.println("Entered the collided loop");
                    // fireball has hit something or the fireball is decaying and the block it is hitting is viable 
                    f.setDecaying(true); // set decay state to true
                    System.out.println("Collision block: " + f.collisionBlock);
                    if(f.collisionBlock.getViable()){
                        mapToDraw.get(mapToDraw.indexOf(f.collisionBlock)).setViable(false);
                        f.stop();
                        f.hit();
                    }
                    //f.collisionBlock.setViable(false);
                    System.out.println(mapToDraw.get(mapToDraw.indexOf(f.collisionBlock)).getViable());
                    System.out.println("Hit block name" + " " + f.collisionBlock);
                    if(f.getFrameCounter() == 16){ // always check if it's the end of the sequence 
                        System.out.println("Bruh omomnet");
                        if(mapToDraw.indexOf(f.collisionBlock) != -1){ // the block actually exists
                            if(fireballHit(f)){
                                f.collisionBlock.setViable(false);
                                mapToDraw.set(mapToDraw.indexOf(f.collisionBlock), null);
                            }
                        }
                        existingFireballs.set(existingFireballs.indexOf(f), null);
                        f = null;

                        }
                    else if(f.getFrameCounter() % 4 == 0){ // been either 0, 4, 8, 12, 16 frames since firball hit 
                        if(f.collisionBlock.update() == 0){ // indestructable block was hit

                        }
                    }
                    if(f != null){
                        f.incFrameCounter(); // if the fireball has not hit an indestructable entity 
                    }
                    
                }
                
                // not a block that was hit
                else if(f.getCollisionEntity() != null){
                    if(f.getCollisionEntity().getClass().getName().equals("gremlins.Gremlin")){
                        System.out.println("Gremlin HAS BEEN HIT");
                        if(gArray.indexOf(f.collisionEntity) != -1){
                            if(fireballHitGremlin(f)){
                                f.stop();
                                f.hit();
                                System.out.println("Fireball hit gremlin passed");
                                
                                gArray.set(gArray.indexOf(f.collisionEntity), null);
                                //System.out.println(gArray.get(gArray.indexOf(f.collisionEntity)));
                                existingFireballs.set(existingFireballs.indexOf(f), null);
                                f = null;
                            }
                        }
                        
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
        //fill(0,0,0);
        w.tick(this);

        while(w.getFrameCounter() < 10){
            w.draw(this);
           
        }
        w.setFrameCounter(0);
        fireballMechanics();
        
        
        
        // see 4 repetitions as there are 4 block updates. 

        for (Immobile i : mapToDraw) {
            if(i != null){
                i.draw(this);
            }
            
        }
        //mapToDraw.get(0).draw(this);
        //System.out.println(gArray.size());
        for (int i = 0; i < gArray.size(); i++) {
            if(gArray.get(i) != null){
                gremlinAI(gArray.get(i).getDirection(), gArray.get(i));
                gArray.get(i).tick(this);
                gArray.get(i).draw(this);
            }
        }
        /*
         *  for (int i = 0; i < gArray.size(); i++) {
            if(gArray.get(i) != null){
                gArray.get(i).draw(this);
            }
            //NPMobileEntities.get(i).draw(this);
        }
         */
       
        
        
        UpdateArrayList<Fireball> updater1 = new UpdateArrayList<Fireball>();
        this.existingFireballs = updater1.update(existingFireballs);
        updater1 = null;

        UpdateArrayList<Gremlin> updater2 = new UpdateArrayList<Gremlin>();
        this.gArray = updater2.update(gArray);
        updater2 = null;

        ArrayList<Mobile> temp2 = new ArrayList<Mobile>();
        for (Mobile entity : this.NPMobileEntities) {
            if(!entity.getClass().getName().equals("gremlins.Fireball") && !entity.getClass().getName().equals("gremlins.Gremlin") && !!entity.getClass().getName().equals("gremlins.SlimeBall")){
                temp2.add(entity);
            }
        }
        this.NPMobileEntities = temp2;
        temp2 = null;

        this.NPMobileEntities.addAll(this.gArray);
        this.NPMobileEntities.addAll(this.existingFireballs);
        if(universalCounter == 60){
            seconds += 1;
            System.out.println(seconds);
            universalCounter = 0;
        }

        universalCounter += 1;
        
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
