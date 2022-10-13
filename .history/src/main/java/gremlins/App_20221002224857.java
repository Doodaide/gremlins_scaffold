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
    private PImage portal;
    private PImage slime;
    private PImage powerUp;

    // ! Object area start
    private JSONObject json;

    private Wizard w; // Player controlled character 
    private ArrayList<Immobile> mapToDraw; // map 
    //public Fireball f;
    private ArrayList<Gremlin> gArray;
    public ArrayList<Mobile> NPMobileEntities; // non projectile mobile entities
    private ArrayList<Fireball> existingFireballs;
    private ArrayList<ArrayList<Entity>> allObjects;

    private ArrayList<ArrayList<SlimeBall>> allBalls;
    private ArrayList<SlimeBall>[] balls;

    // ! Object area end 
    public int properX, properY; 
    private int universalCounter = 0;
    private int seconds = 0;
    private int frameCounter = 0;
    
    private int lives;
    private int deaths = 0;
    private JSONArray levelSpecs;
    private boolean won;
    private boolean passedLvl2 = false;
    private boolean passedLvl1 = false;
    private double wizardCooldown;
    private double enemyCooldown;
    private int level;

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
        json = loadJSONObject(this.configPath);
        this.lives = json.getInt("lives");
        //this.livesToDraw = this.lives;
        levelSpecs = json.getJSONArray("levels");
        String layout = "";
        if(!passedLvl2 && passedLvl1){
            JSONObject level2 = levelSpecs.getJSONObject(1);
            layout = level2.getString("layout");
            this.enemyCooldown = level2.getDouble("enemy_cooldown") * FPS;
            this.wizardCooldown = level2.getDouble("wizard_cooldown") * FPS;
            this.level = 2;
        }

        else if (!passedLvl1){
            JSONObject level1 = levelSpecs.getJSONObject(0);
            layout = level1.getString("layout");
            this.enemyCooldown = level1.getDouble("enemy_cooldown") * FPS;
            this.wizardCooldown = level1.getDouble("wizard_cooldown") * FPS;
            this.level = 1;
        }
        
        ArrayList<Character[]> mapContents = new ArrayList<Character[]>(); // temporary list
        
        // * Parse file
        try {
            if(layout.equals("")){
                throw new FileNotFoundException("Bruh moment");
            }
            File f = new File(layout); //File f = new File("level0.txt"); //
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
            this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
            this.portal = loadImage(this.getClass().getResource("portal.png").getPath().replace("%20", " "));
            this.powerUp = loadImage(this.getClass().getResource("Powerup_Freeze.png").getPath().replace("%20", " "));
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
                        gArray.add(new Gremlin(xCounter, yCounter, this.gremlin, this.slime));
                    }

                    else if (k == 'E'){ // ! Exit ... CHANGE LATER
                        mapToDraw.add(new ExitPortal(xCounter, yCounter, this.portal));
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
        w.setManaCooldown((int)this.wizardCooldown);
        NPMobileEntities.add(w);
        NPMobileEntities.addAll(gArray);
        //allBalls = new ArrayList<ArrayList<SlimeBall>>();
        //for(int i = 0; i < gArray.size(); i++){
           // allBalls.add(null);
        //}
        //System.out.println(allBalls.size());
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

    public ArrayList<Fireball> getExistingFireballs(){
        return this.existingFireballs;
    }
    
    public double getEnemyCooldown(){
        return this.enemyCooldown;
    }

    public double getWizardCooldown(){
        return this.wizardCooldown;
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
        if (key == 39){ //right arrow 
            w.right();
            //System.out.println("right");
        }
        if (key == 38){ //up arrow 
            w.up();
            //System.out.println(w.getX() + " " + w.getY());
        }
        if (key == 40){ //down arrow
            w.down();
            //System.out.println("down");
        }
        if (key == 32){ //space
            System.out.println("Wizard cooldown measurement: " + this.wizardCooldown);
            if((w.getX() % 20 == 0 || w.getY() % 20 == 0) && w.getManaCoolDown() >= this.wizardCooldown){
                //System.out.println("Fireball added");
                w.setManaCooldown(0);
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
                //System.out.println("Hit something " + collided);
                //System.out.println("Entity hit " + f.getCollisionEntity());

                // a block was hit
                if((collided == 1 || f.getDecaying()) && f.getCollisionEntity() == null){ 
                    //System.out.println("Entered the collided loop");
                    // fireball has hit something or the fireball is decaying and the block it is hitting is viable 
                    f.setDecaying(true); // set decay state to true
                    //System.out.println("Collision block: " + f.collisionBlock);
                    if(f.collisionBlock.getViable()){
                        mapToDraw.get(mapToDraw.indexOf(f.collisionBlock)).setViable(false);
                        f.stop();
                        f.hit();
                    }
                    //f.collisionBlock.setViable(false);
                    //System.out.println(mapToDraw.get(mapToDraw.indexOf(f.collisionBlock)).getViable());
                    //System.out.println("Hit block name" + " " + f.collisionBlock);
                    if(f.getFrameCounter() == 16){ // always check if it's the end of the sequence 
                        //System.out.println("Bruh omomnet");
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
                        f.incFrameCounter();
                    }
                    if(f != null){
                        f.incFrameCounter(); // if the fireball has not hit an indestructable entity 
                    }
                    
                }
                
                // not a block that was hit
                else if(f.getCollisionEntity() != null){
                    if(f.getCollisionEntity().getClass().getName().equals("gremlins.Gremlin")){
                        //System.out.println("Gremlin HAS BEEN HIT");
                        if(gArray.indexOf(f.collisionEntity) != -1){
                            if(fireballHitGremlin(f)){
                                f.stop();
                                f.hit();
                                //System.out.println("Fireball hit gremlin passed");
                                this.gremlinRespawn(gArray.get(gArray.indexOf(f.collisionEntity)));
                                //gArray.set(gArray.indexOf(f.collisionEntity), null); // !RESPAWN MECHANICS GO HERE. 
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

    public void gremlinRespawn(Gremlin g){
        // wizard coordinates:
        int wX = w.getX(); 
        int wY = w.getY();
        int newX, newY;
        boolean canRespawnX = false;
        boolean canRespawnY = false;
        Random rn = new Random();
        int xTest = wX;
        int yTest = wY;

        // Random number generator: 
        while(!canRespawnX || !canRespawnY){
            if(!canRespawnX){
                xTest = rn.nextInt(34) * 20;
                System.out.println("x Respawn looping");
                if(xTest == 0){
                    continue;
                }
                else if((xTest >= wX + w.WIDTH+ 200 || xTest <= wX - 200) || 
                ((xTest < wX + w.WIDTH + 200 && xTest > wX - 200) && ((yTest > wY + 200 + w.HEIGHT) || (yTest < wY - 200)))){ // too far left or right. Essentially both coords are valid. 
                    boolean canPass = true;
                    for(Immobile i : mapToDraw){
                        if(i != null){
                            if(i.getX() == xTest && i.getY() == yTest){
                                canPass = false;
                            }
                        }
                    }
                    if(canPass){
                        System.out.println("X: " + xTest + " Y: " + yTest);
                        canRespawnX = true;
                        canRespawnY = true;
                    }
                }
            }

            if(!canRespawnY){
                yTest = rn.nextInt(31) * 20; 
                System.out.println("Y respawn looping");
                if(yTest == 0){
                    continue;
                }
                else if((yTest >= wY + w.HEIGHT + 200 || yTest <= wY - 200) || 
                (yTest < wY + w.HEIGHT + 200 && yTest > wY - 200) && (xTest > wX + w.WIDTH + 200 || xTest < wX - 200)){
                    boolean canPass = true;
                    for(Immobile i : mapToDraw){
                        if(i != null){
                            if(i.getX() == xTest && i.getY() == yTest){
                                canPass = false;
                            }
                        }
                    }
                    if(canPass){
                        System.out.println("X: " + xTest + " Y: " + yTest);
                        canRespawnX = true;
                        canRespawnY = true;
                    }
                }
            }
            
        }
        
        g.setX(xTest); 
        g.setY(yTest);

    }

    // Draws basic screen stuff
    public void basicGraphics(){
        background(192,164,132);
        textSize(20);
        text("Lives: ", 20, HEIGHT - 25);
        String lvlMessage = "Level " + this.level + "/2";
        text(lvlMessage, 240, HEIGHT - 25);
        fill(255, 255, 255);
    }

    // Draws wizard associated stuff
    public int wizardGraphics(){
        int retVal = w.tick(this);
        if(retVal == 10){ // 10 is death 
            this.deaths += 1;
            this.setup();
            this.lives -= deaths;
            return 0 ;
        }
        
        for(int i = 0; i < this.lives; i++){
            this.image(this.wizardSprites[1], 20 + i*20 +60, HEIGHT - 40);
        }

        w.draw(this);
        return retVal;
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        this.basicGraphics();
        int retVal = this.wizardGraphics();
        this.fireballMechanics();

        for (Immobile i : mapToDraw) {
            if(i != null){
                i.draw(this);
            }
            
        }

        for(Gremlin g : gArray){
            if(g != null){
                g.tick(this);
                // checks if gremlin can shoot 
                
                g.draw(this);
                
            }
        }
        UpdateArrayList<Fireball> updater1 = new UpdateArrayList<Fireball>();
        this.existingFireballs = updater1.update(existingFireballs);
        updater1 = null;

        UpdateArrayList<Gremlin> updater2 = new UpdateArrayList<Gremlin>();
        this.gArray = updater2.update(gArray);
        updater2 = null;

        /*
         * UpdateArrayList<ArrayList<SlimeBall>> updater3 = new UpdateArrayList<ArrayList<SlimeBall>>();
        this.allBalls = updater3.slimeBallUpdate(this.allBalls);
         */

        ArrayList<Mobile> temp2 = new ArrayList<Mobile>();
        for (Mobile entity : this.NPMobileEntities) {
            if(!entity.getClass().getName().equals("gremlins.Fireball") && !entity.getClass().getName().equals("gremlins.Gremlin") && !entity.getClass().getName().equals("gremlins.SlimeBall")){
                temp2.add(entity);
                
            }
        }
        this.NPMobileEntities = temp2;
        
        temp2 = null;

        this.NPMobileEntities.addAll(this.gArray);
        this.NPMobileEntities.addAll(this.existingFireballs);
        
        /*
         * for(ArrayList<SlimeBall> arr : allBalls){
            this.NPMobileEntities.addAll(arr);
        }
         */
        if(universalCounter == 60){
            seconds += 1;
            //System.out.println(seconds);
            universalCounter = 0;
        }

        universalCounter += 1;
        
        if(this.lives <= 0){
            background(219, 160, 9);
            textSize(40);
            fill(107, 9, 219);
            text("Game over", WIDTH*0.2f, HEIGHT*0.4f);
            return;
        }

        else if(retVal == 20 && passedLvl1){ // Win condition (pass lvl 2)
            background(219, 160, 9);
            textSize(40);
            fill(107, 9, 219);
            text("YOU WIN", WIDTH*0.2f, HEIGHT*0.4f);
            return;
        }

        else if(retVal == 20){  // win condition (pass lvl 1)
            won = true;
            this.passedLvl1 = true;
            this.deaths = 0;
            this.setup();
            won = false;
        }
        
        
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
