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
    public static final int leftBound = 20;
    public static final int rightBound = 700;
    public static final int upperBound = 20;
    public static final int lowerBound = 660;

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
     * Object references
     */
    private Wizard w; 
    private ArrayList<Immobile> mapToDraw; // map 
    private ArrayList<Gremlin> gArray; // array of gremlin objects 
    private ArrayList<Mobile> NPMobileEntities; // non projectile mobile entities
    private JSONReader jr;
    private FreezePotion p;
    private Kamehameha k;

    // Other variables
    public static final int FPS = 60;
    private String configPath; 
    
    private int universalCounter = 0;
    private int seconds = 0;
    private int lives;
    private int level;
    private int deaths = 0;


    private int innateCooldown = 0;
    private int kCharge = 3;
    private int[] powerUpCoords;
    private double wizardCooldown;
    private double enemyCooldown;

    
    private boolean won;
    private boolean passedLvl2 = false;
    private boolean passedLvl1 = false;
    private boolean powerUpSpawned = false; 
    private boolean restart = false;
    private boolean freezeActive = false;


    private boolean powerUpConsumedOnce = false; 
    private boolean kHit = false; 

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
            // TODO: handle exception
            
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

    public ArrayList<Mobile> getMobiles(){
        return this.NPMobileEntities;
    }

    public ArrayList<Gremlin> getGArray(){
        return this.gArray;
    }
    
    public double getEnemyCooldown(){
        return this.enemyCooldown;
    }

    public double getWizardCooldown(){
        return this.wizardCooldown;
    }

    public boolean getPowerUpAvailable(){
        return this.powerUpSpawned;
    }

    public void setPowerUpSpawned(boolean b){
        this.powerUpSpawned = b;
    }
    
    public FreezePotion getPowerUp(){
        return this.p;
    }

    public Wizard getWizard(){
        return this.w;
    }
    
    public int getSeconds(){
        return this.seconds;
    }

    public int getInnateCooldown(){
        return this.innateCooldown;
    }

    public int[] getPowerUpCoords(){
        return this.powerUpCoords;
    }

    public void setPowerUpCoords(int x, int y){
        this.powerUpCoords[0] = x;
        this.powerUpCoords[1] = y;
    }
    
    public void setPowerUpConsumedOnce(boolean t){
        this.powerUpConsumedOnce = t;
    }

    public boolean getPowerUpConsumedOnce(){
        return this.powerUpConsumedOnce;
    }

    public int getKCharge(){
        return this.kCharge;
    }

    /**
     * Receive key pressed signal from the keyboard.
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
        
        else if (key == 84){ // t
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

    private void instructions(){
            background(192,164,132);
            textSize(20);
            text("Welcome to Gremlins!!", 300, 80);
            text("Use arrow keys to move the wizard, shoot fireballs with space", 100, 110); 
            text("Avoid the gremlins and slime at all costs", 140, 140); 
            text("Touch the pink portal to progress, Touch the blue portal for a pretty cool experience", 90, 160);
            text("Be wary, frozen slime is harmless, frozen gremlins still hurt though", 90, 180); 
            text("More instructions are specified in the interface at the bottom", 120, 200);
            fill(0,0,0);
    }

    // Draws basic screen stuff
    private void basicGraphics(){
        background(192,164,132);
        textSize(20);
        text("Lives: ", 20, HEIGHT - 35);
        String lvlMessage = ("Level " + this.level + "/2");
        text(lvlMessage, 240, HEIGHT - 35);
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
    }

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

    private void mapGraphics(){
        for (Immobile i : mapToDraw) {
            if(i != null){
                i.draw(this);
            }
        }
    }
    
    private void gremlinGraphics(){
        for(Gremlin g : gArray){
            if(g != null){
                g.tick(this);
                g.draw(this);
            }
        }
    }
    
    private void arrayListUpdates(){
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

    private void waitScreen(){
        if(restart && keyPressed && universalCounter > 3*FPS){
            won = false;
            this.passedLvl1 = false;
            this.deaths = 0;
            this.setup();
            this.restart = false;
        }
        universalCounter += 1;
    }
    
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
            if(!p.getConsumed()){
                mapToDraw.add(p); 
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
            }

            else if (p.getConsumed() && this.innateCooldown <= 20*FPS){
                this.innateCooldown -= 1;
            }
        }
        
    } 

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

    public void powerUpUpdates(){
        if(p != null){
            p.update(this);
        }
    }

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
            this.basicGraphics();
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
        this.waitScreen();
        p = null;
        powerUpSpawned = false;
        
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}