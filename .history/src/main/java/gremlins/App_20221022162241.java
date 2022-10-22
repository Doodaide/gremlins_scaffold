package gremlins;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
/**
 * App class: The main body of code extending PApplet, that integrates all of the individual object classes, and dictates how 
 * the game runs 
 */
public class App extends PApplet {
     
    /**
     * The dimensions of the app window generated (in pixels)
     * All static final variables, so no modifications are possible
     */
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    /**
     * These are a set of hardset boundaries that the no mobile objects can pass 
     */
    public static final int LEFTBOUND = 20;
    public static final int RIGHTBOUND = 700;
    public static final int UPPERBOUND = 20;
    public static final int LOWERBOUND = 660;

    /**
     * the PImage variable for the destructable immobile entity 
     */
    public PImage brickwall;

    /**
     * the PImage variable for the indestructable immobile entity 
     */
    public PImage stonewall;

    /**
     * the PImage variable for the mobile gremlin entity, the enemy of the player 
     */
    public PImage gremlin;

    /**
     * the PImage variable for the immobile portal entity, the goal to reach
     */
    public PImage portal;

    /**
     * the PImage variable for the gremlin's slime projectile 
     */
    public PImage slime;

    /**
     * the PImage variable for the immobile powerup entity
     */
    public PImage powerUp;

    /**
     * the PImage variable of the gremlin under the effect of the powerUp
     */
    public PImage frozenG;

    /**
     * the PImage of the gremlin's slime under the effect of the powerUp
     */
    public PImage frozenS;

    /**
     * A PImage array that contains the destruction sequence of the destructable immobile entities
     */
    public PImage[] brickwallDestruction; 
    
    /**
     * A PImage array that contains the different sprites associated with the wizard
     */
    public PImage[] wizardSprites;

    /**
     * A PImage array that contains the modified wizard sprite that is used in the teleport UI
     */
    public PImage tPIcon;

    /**
     * the PImage variable associated with a Kahmehameha beam that is fired left or right 
     */
    public PImage hBeam; 

    /**
     * the PImage variable associated with a Kamehameha beam that is fired upwards or downwards 
     */
    public PImage vBeam; 

    /**
     * the PImage variable associated with the game over screen. 
     */
    public PImage JTK; 

    /**
     * Wizard object 
     */
    private Wizard w; 

    /**
     * map ArrayList 
     */
    private ArrayList<Immobile> mapToDraw; 

    /**
     * gremlin arrayList 
     */
    private ArrayList<Gremlin> gArray; 

    /**
     * All mobile entity arrayList
     */
    private ArrayList<Mobile> NPMobileEntities; 

    /**
     * Json reader object 
     */
    private JSONReader jr;

    /**
     * Powerup object 
     */
    private FreezePotion p;

    /**
     * Beam object 
     */
    private Kamehameha k;

    /**
     * FPS (How many frames the game will update per second)
     */
    public static final int FPS = 60;

    /**
     * Where the json file is located 
     */
    private String configPath; 
    
    /**
     * Counter that updates every tick. Cycles in cycles of 60
     */
    private int universalCounter = 0;

    /**
     * Seconds is another counter used to keep track of time 
     */
    private int seconds = 0;

    /**
     * The lives (or more accurately the number of times) the player is allowed to die
     */
    private int lives;

    /**
     * the level the player is on
     */
    private int level;

    /**
     * How many times the player has died already
     */
    private int deaths = 0;

    /**
     * The amount of time between each potion spawn 
     */
    private int innateCooldown = 0;

    /**
     * The number of charges allowed for each level
     */
    private int kCharge = 3;

    /**
     * coordinates of the first powerup spawn
     */
    private int[] powerUpCoords;

    /**
     * The cooldown for the wizard to shoot 
     */
    private double wizardCooldown;

    /**
     * The cooldown for the enemy to shoot 
     */
    private double enemyCooldown;
    
    /**
     * Whether the player has "won". If so, the game will stop updating 
     */
    private boolean won;

    /**
     * whether level 2 has passed (final win)
     */
    private boolean passedLvl2 = false;

    /**
     * Whether level 1 has passed (progress to level 2)
     */
    private boolean passedLvl1 = false;

    /**
     * Whether there exists a powerup on the screen 
     */
    private boolean powerUpSpawned = false; 

    /**
     * Whether the game can be restarted 
     */
    private boolean restart = false;

    /**
     * Whether gremlin and slimeball movements should be updated 
     */
    private boolean freezeActive = false;

    /**
     * another control variable for the powerup 
     */
    private boolean added = false;

    /**
     * whether the powerup has been consumed once. This allows the powerup to spawn in pseudorandom locations 
     */
    private boolean powerUpConsumedOnce = false; 

    /**
     * Whether the kamehameha beam has hit 
     */
    private boolean kHit = false; 

    /**
     * whether the instructions screen is up
     */
    private boolean instructionsUp = false;

    /**
     * Constructor for App instance 
     */
    public App(){
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
     * Many objects are instantiated within other helper classes such as DrawMap that produces a something readable
     * from the config path. However, other instances such as the wizard are instantiated here 
    */
    public void setup() {
        frameRate(FPS);
        this.seconds = 0;
        this.universalCounter = 0;
        kHit = false; 
        kCharge = 3;
        this.added = false;
        this.setFreezeActive(false);
        // Read JSON file 
        jr = new JSONReader(this.configPath);
        this.lives = (int) Math.round(jr.getSpecs().get("lives"));
        String layout = "";
        if(!passedLvl2 && passedLvl1){
            try {
                jr.readSpecs(1);
            } catch (Exception e) {
                System.err.println("Invalid input");
            }

            layout = jr.getLayout();
            this.enemyCooldown = jr.getSpecs().get("enemy_cooldown") * FPS;
            this.wizardCooldown = jr.getSpecs().get("wizard_cooldown") * FPS;
            this.level = 2;
        }

        else if (!passedLvl1){
            try {
                jr.readSpecs(0);
            } catch (Exception e) {
                System.err.println("Invalid input");
            }
            layout = jr.getLayout(); 
            this.enemyCooldown = jr.getSpecs().get("enemy_cooldown") * FPS;
            this.wizardCooldown = jr.getSpecs().get("wizard_cooldown") * FPS;
            this.level = 1;
        }
        try {
            this.wizardSprites = new PImage[5];
            this.wizardSprites[0] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard2.png").getPath(), StandardCharsets.UTF_8.toString())); // up
            this.wizardSprites[1] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard1.png").getPath(), StandardCharsets.UTF_8.toString()));  // right 
            this.wizardSprites[2] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard3.png").getPath(), StandardCharsets.UTF_8.toString())); // down
            this.wizardSprites[3] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard0.png").getPath(), StandardCharsets.UTF_8.toString())); // left  
            this.wizardSprites[4] = loadImage(URLDecoder.decode(this.getClass().getResource("fireball.png").getPath(), StandardCharsets.UTF_8.toString())); // fireball
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
            this.frozenG = loadImage(this.getClass().getResource("frozenGremlin.png").getPath().replace("%20", " "));
            this.frozenS = loadImage(this.getClass().getResource("frozenSlime.png").getPath().replace("%20", " "));
            this.tPIcon = loadImage(this.getClass().getResource("wizardTP.png").getPath().replace("%20", " "));
            this.hBeam = loadImage(this.getClass().getResource("Beam_Horizontal.png").getPath().replace("%20", " "));
            this.vBeam = loadImage(this.getClass().getResource("Beam_Vertical.png").getPath().replace("%20", " "));
            this.JTK = loadImage(this.getClass().getResource("JTK.png").getPath().replace("%20", " "));
            this.JTK.resize(HEIGHT, WIDTH);
       
        } catch (UnsupportedEncodingException e) {
            System.err.println("Could not find an image");
            e.printStackTrace();
        }

        ReadMap r = new ReadMap();
        try {
            r.parseLayout(layout);
        } catch (Exception e) {
            System.err.println("Error in reading map");
        }

        mapToDraw = new ArrayList<Immobile>(); // ArrayList that contains all block objects to be drawn
        gArray = new ArrayList<Gremlin>();
        NPMobileEntities = new ArrayList<Mobile>();
        powerUpCoords = new int[2];
        powerUpCoords[0] = 999; 
        powerUpCoords[1] = 999; 
        this.powerUpConsumedOnce = false; 
        
        DrawMap d = new DrawMap();
        d.generateMap(r.getMapContents(), this);
        w = new Wizard(d.getWizardStart()[0], d.getWizardStart()[1], 2, this.wizardSprites);
        w.setManaCooldown((int)this.wizardCooldown);
        NPMobileEntities.add(w);
        NPMobileEntities.addAll(gArray);
        this.innateCooldown = 0;
        jr = null;
        r = null;
        d = null;
        w.setTpCooldown(0);
    }
    
    /**
     * Getter method for the Current kamehameha beam reference 
     * @return the reference to the current kamehameha object 
     */
    public Kamehameha getK(){
        if(this.k != null){
            return this.k;
        }
        return null;
    }

    /**
     * Getter method for whether the freeze effect is active. 
     * @return boolean determining whether this is true or false
     */
    public boolean getFreezeActive(){
        return this.freezeActive;
    }

    /**
     * Setter method for whether the freeze effect is active or not 
     * @param b a boolean corresponding to whether the effect is active 
     */
    public void setFreezeActive(boolean b){
        this.freezeActive = b;
    }

    /**
     * Getter method for the ArrayList that stores all the Immobiles to be drawn
     * @return an ArrayList containing the Immobile entities that should be drawn onscreen.
     */
    public ArrayList<Immobile> getMap(){
        return this.mapToDraw;
    }

    /**
     * Getter method for 'all mobiles' in the game. Mainly used for collisions
     * @return an ArrayList containing all Mobile entities that should be drawn onscreen 
     */
    public ArrayList<Mobile> getMobiles(){
        return this.NPMobileEntities;
    }

    /**
     * Getter method for all gremlins in the game 
     * @return an ArrayList containing all gremlins that should be drawn onscreen
     */
    public ArrayList<Gremlin> getGArray(){
        return this.gArray;
    }
    
    /**
     * Getter method for the enemy's cooldown between each fire. 
     * @return a double that determines the enemy's firing cooldown
     */
    public double getEnemyCooldown(){
        return this.enemyCooldown;
    }

    /**
     * Getter method for the wizard's cooldown between each hit of the spacebar 
     * @return a double that determines how long till the next valid fireball can be generated 
     */
    public double getWizardCooldown(){
        return this.wizardCooldown;
    }

    /**
     * Getter method for whether the powerup is spawned and available for collision 
     * @return boolean detenoting whether the powerup can be 'hit' or not
     */
    public boolean getPowerUpAvailable(){
        return this.powerUpSpawned;
    }

    /**
     * Setter method for whether the powerup has spawned 
     * @param b boolean detenoting whether the powerup can be hit or not 
     */
    public void setPowerUpSpawned(boolean b){
        this.powerUpSpawned = b;
    }
    
    /**
     * Getter method for the reference to the powerup 
     * @return an object reference for the FreezePotion object 
     */
    public FreezePotion getPowerUp(){
        return this.p;
    }

    /**
     * Getter method for the reference to the wizard 
     * @return an object reference for the Wizard object 
     */
    public Wizard getWizard(){
        return this.w;
    }
    
    /**
     * getter method for the seconds elapsed in a certain level 
     * @return integer that denotes the seconds elapsed 
     */
    public int getSeconds(){
        return this.seconds;
    }

    /**
     * Getter method for the cooldown until the next powerup spawns 
     * @return an integer that determines how long until the next powerup can spawn
     */
    public int getInnateCooldown(){
        return this.innateCooldown;
    }

    /**
     * Getter method for the powerup coordinates 
     * @return returns an integer array that lists the coordinates in form (x, y) 
     */
    public int[] getPowerUpCoords(){
        return this.powerUpCoords;
    }

    /**
     * Setter method for the powerup coordinates 
     * @param x x coordinate of the powerup's new location 
     * @param y y coordinate of the powerup's new location 
     */
    public void setPowerUpCoords(int x, int y){
        this.powerUpCoords[0] = x;
        this.powerUpCoords[1] = y;
    }
    
    /**
     * Setter method for whether the powerup has been consumed once 
     * @param t boolean determining whether the powerup has been consumed once 
     */
    public void setPowerUpConsumedOnce(boolean t){
        this.powerUpConsumedOnce = t;
    }

    /**
     * Getter method for whether the powerup has been consumed once 
     * @return boolean for whether the powerup has been consumed once 
     */
    public boolean getPowerUpConsumedOnce(){
        return this.powerUpConsumedOnce;
    }

    /**
     * Getter method for the number of charges left of the kahmehameha beam 
     * @return integer that determines how many charges of the kahmehameha beam one has left 
     */
    public int getKCharge(){
        return this.kCharge;
    }

    /**
     * Getter method for whether the instructions for the game have been toggled on 
     * @return boolean determining whether the instructions are up or not 
     */
    public boolean getInstructionsUP(){
        return this.instructionsUp;
    }

    /**
     * Setter method for whether the instructions for the game are toggled on or not 
     * @param b boolean determining whether the instructions are up or not. 
     */
    public void setInstructionssUp(boolean b){
        this.instructionsUp = b;
    }

    /**
     * Receive a key pressed on the keyboard 
     * Probably an overridden method from PApplet 
     */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode(); 
        if(key == 37){ //left arrow 
            w.left();
        }
        
        if (key == 39){ //right arrow 
            w.right();
        }
        
        if (key == 38){ //up arrow 
            w.up();
        }
        
        if (key == 40){ //down arrow
            w.down();
        }
        
        if (key == 32 && k == null){ //space 
            if(w.getManaCoolDown() >= this.wizardCooldown){
                w.setManaCooldown(0);
                w.shoot();
                NPMobileEntities.addAll(w.getExistingFireballs());    
            }
        }
        
        else if (key == 84 && k == null){ // t
            if(w.getTpCooldown() == 0 && (w.getX() % 20 == 0 && w.getY() % 20 == 0)){
                w.tp(this);
                w.setTeleported(true);
                w.setTpCooldown(5 * FPS);
            }
        }
    
        else if (key == 82){ // r
            if(this.kCharge != 0 && w.getX() % 20 == 0 && w.getY() % 20 == 0){

                if(w.getDirection() == 0 || w.getDirection() == 2){
                    k = new Kamehameha(w.getX(), w.getY(), this.vBeam);
                }
    
                else{
                    k = new Kamehameha(w.getX(), w.getY(), this.hBeam);
                }
                this.kCharge -= 1; 
            }
        }
    
        if(key == 72 && !instructionsUp){ // h
            this.instructionsUp = true;
        }

        else if(key == 72 && instructionsUp){ // h
            this.instructionsUp = false;
        }
    }
    
    /**
     * Receive key released signal from the keyboard.
     * Probably an overridden method from PApplet 
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

    /**
     * An organisational method that sections off a part of the wizard's updates for the game 
     * Including how many deaths, its tick and draw, and what to draw. 
     * @return integer determining whether the game should switch to the loss state (10), or won state (20)
     */
    private int wizardGraphics(){
        int retVal = w.tick(this);
        if(retVal == 10){ // 10 is death 
            this.deaths += 1;
            this.setup();
            this.lives -= deaths;
            this.powerUpSpawned = false;
            this.innateCooldown = 0;
            p = null;
            powerUpSpawned = false;
            return 0;
        }
        
        for(int i = 0; i < this.lives; i++){
            this.image(this.wizardSprites[1], 20 + i*20 +60, HEIGHT - 50);
        }
        w.draw(this);
        return retVal;
    }

    /**
     * An organisational method that sections off part of the map updates for the game
     * This includes drawing all of the blocks that are not assigned as null in the mapToDraw arrayList. 
     */
    private void mapGraphics(){
        for (Immobile i : mapToDraw) {
            if(i != null){
                i.draw(this);
            }
        }
    }
    
    /**
     * An organisational method that sections off part of the gremlin updates for the game 
     * This includes drawing and ticking all of the non-null gremlins
     */
    private void gremlinGraphics(){
        for(Gremlin g : gArray){
            if(g != null){
                g.tick(this);
                g.draw(this);
            }
        }
    }
    
    /**
     * An organisational method that sections off part of the arrayList updates for the game 
     * This just deletes all the null entries in the arrayLists to prevent memory leaks and 
     * let garbage collector pick up any objects that aren't used anymore 
     */
    public void arrayListUpdates(){
        UpdateArrayList<Fireball> updater1 = new UpdateArrayList<Fireball>();
        w.setExistingFireballs(updater1.update(w.getExistingFireballs())); 
        updater1 = null;

        UpdateArrayList<Gremlin> updater2 = new UpdateArrayList<Gremlin>();
        this.gArray = updater2.update(gArray);
        updater2 = null;

        ArrayList<Mobile> temp2 = new ArrayList<Mobile>();
        for (Mobile entity : this.NPMobileEntities) {
            if(!entity.getClass().getName().equals("gremlins.Fireball") && !entity.getClass().getName().equals("gremlins.Gremlin") && !entity.getClass().getName().equals("gremlins.SlimeBall")){
                temp2.add(entity);
                
            }
        }
        this.NPMobileEntities = temp2;
        
        temp2 = null;

        this.NPMobileEntities.addAll(this.gArray);
        this.NPMobileEntities.addAll(w.getExistingFireballs());

        UpdateArrayList<Immobile> updater3 = new UpdateArrayList<Immobile>();
        this.mapToDraw = updater3.update(mapToDraw);
        updater3 = null;
    }

    /**
     * An organisational method that sections off part of the waitscreen updates for the game 
     * The waitscreen is the stage where a player has either lost or won, and is on the finishing screen 
     * This method waits for some keyPressed input to occur, then it will restart from level 1. 
     */
    private void waitScreen(){
        if(restart && keyPressed && universalCounter > 3*FPS){
            won = false;
            this.passedLvl1 = false;
            this.deaths = 0;
            this.setup();
            this.restart = false;
            this.setPowerUpSpawned(false);
        }
        universalCounter += 1;
    }
    
    /**
     * An organisational method that sections off part of the powerup spawning mechanics for the game 
     * The powerup spawn method determines where and how to spawn a powerup 
     * @param seconds the number of seconds that have elapsed since the game has begun. 
     * This is used as a reference point for when to spawn the powerups. 
     */
    private void powerUpSpawn(int seconds){
        if(seconds > 3){
            if(powerUpCoords[0] == 999 || powerUpCoords[1] == 999){
                this.powerUpConsumedOnce = true;
            }

            if(!powerUpSpawned && this.innateCooldown == 0 && this.powerUpConsumedOnce && w.getX()%20 == 0 && w.getY()%20 == 0){
                int[] coords = FreezePotion.spawn(this);
                if(coords[0] != 0 && coords[1] != 0){
                    p = new FreezePotion(coords[0], coords[1], this.powerUp);
                    powerUpSpawned = true;
                    this.innateCooldown = 20 * FPS;
                }
            }

            else if(!powerUpSpawned && this.innateCooldown == 0 && !this.powerUpConsumedOnce && w.getX()%20 == 0 && w.getY()%20 == 0){
                p = new FreezePotion(powerUpCoords[0], powerUpCoords[1], this.powerUp);
                powerUpSpawned = true; 
                this.innateCooldown = 20*FPS;
                this.powerUpConsumedOnce = true;
            }
        }

        if(p != null){
            if(!p.getConsumed() && !added){
                mapToDraw.add(p); 
                added = true;
            }
            
            else if(this.innateCooldown == 0){
                powerUpSpawned = false;
                this.setFreezeActive(false);
                p = null;
            }

            else if(p.getConsumed() && this.innateCooldown == 20*FPS){ 
                this.mapToDraw.set(mapToDraw.indexOf(p), null);
                this.powerUpSpawned = false;
                this.innateCooldown -= 1;
                this.added = false;
            }

            else if (p.getConsumed() && this.innateCooldown <= 20*FPS){
                this.innateCooldown -= 1;
            }
        }
        
    } 

    /**
     * An organisational method that checks what state to display the game in. 
     * These states include the failure screen, the win screen, or swapping to level 2. 
     * @param retVal a value returned from the wizard's tick method that essentially informs the method what has happened 
     * This includes things like retVal = 20, which means the wizard has hit an exit portal
     */
    public void checkGameState(int retVal){
        if(this.lives <= 0){
            this.restart = true;
            won = true;
            p = null;
            powerUpSpawned = false;
            kHit = false; 
            kCharge = 3;
            this.image(this.JTK, 0,0);
        }
 
        else if(retVal == 20 && passedLvl1){ // Win condition (pass lvl 2)
            background(219, 160, 9);
            textSize(40);
            fill(107, 9, 219);
            text("YOU WIN", WIDTH*0.2f, HEIGHT*0.4f);
            won = true;
            this.restart = true;
            kHit = false; 
            kCharge = 3;
            
        }

        else if(retVal == 20){  // win condition (pass lvl 1)
            won = true;
            this.passedLvl1 = true;
            int liveSave = this.lives;
            this.setup();
            this.lives = liveSave;
            won = false;
            p = null;
            powerUpSpawned = false;
            kHit = false; 
            kCharge = 3;
        }
    }

    /**
     * An organisational method that checks for whether to update the state of the powerup 
     * spawned onscreen. 
     */
    public void powerUpUpdates(){
        if(p != null){
            p.update(this);
        }
    }

    /**
     * An organisational method that handles the kamehameha beam's mechanics and UI implementations 
     */
    public void kamehameha(){
        if(k != null){
            k.update(this);
            if(k.getLifeSpan() > 60){
                k.setLifespan(0);
                k.setDrawing(false);
                k = null;
            }
        }
        Kamehameha.stuffUI(this);
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
            if(!won){
                if(!instructionsUp){
                    background(192,164,132);
                    textSize(20);
                    text("Lives: ", 20, HEIGHT - 35);
                    String lvlMessage = ("Level " + this.level + "/2");
                    text(lvlMessage, 240, HEIGHT - 35);
                    text("h for help", 140, App.)
                    fill(255, 255, 255);
                    lvlMessage = null;
                    if(p != null){
                        if(p.getConsumed() && this.freezeActive){
                            this.image(this.powerUp, 375, 673);
                            this.rect(400,680, 50, 6);
                            this.fill(0,0,255);
                            this.rect(400, 680, (float) (50 - (p.getCooldown()*10/FPS)), 6);
                            this.fill(255,255,255);
                        }
                    }
        
                    this.powerUpSpawn(seconds);
                    int retVal = this.wizardGraphics();
                    w.fireballMechanics(this);
                    this.kamehameha();
                    this.mapGraphics();
                    this.gremlinGraphics();
                    this.powerUpUpdates();
                    this.arrayListUpdates();
                    if(universalCounter == 60){
                        seconds += 1;
                        universalCounter = 0;
                        
                        if(seconds == 60){
                            seconds = 0;
                        }
                    }
                    universalCounter += 1;
                    this.checkGameState(retVal);
                    return;
                }

                else if(instructionsUp){
                    this.rect(0,0,App.WIDTH, App.HEIGHT);
                    fill(255,255,4);
        
                    textSize(25);
                    text("Welcome to Gremlins!!", 280, 80);
                    text("Use arrow keys to move, shoot fireballs with space", 0, 130); 
                    text("Avoid the gremlins and slime at all costs", 100, 170); 
                    text("Touch the pink portal to progress", 60, 210);
                    text("Touch the blue portal for a cool experience", 20, 250);
                    text("Be wary, frozen slime is harmless. ", 20, 290); 
                    text("Frozen gremlins still hurt though", 40, 330);
                    text("More instructions in the interface at the bottom", 10, 370);
                    fill(49,90,180);
                    return;
                }
            }

            
            this.waitScreen();
            p = null;
            powerUpSpawned = false;
        
        
        
    }

    /**
     * Main method to run the Sketch 
     * @param args 
     */
    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}