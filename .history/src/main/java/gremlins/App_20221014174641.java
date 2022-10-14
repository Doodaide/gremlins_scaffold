package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.util.*;
import java.io.*;


public class App extends PApplet {

    // Window dimensions 
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    // Hardset boundaries
    public int leftBound = 20;
    public int rightBound = 700;
    public int upperBound = 20;
    public int lowerBound = 660;

    // ! Image variables 
    public PImage brickwall;
    public PImage stonewall;
    public PImage gremlin;
    public PImage portal;
    public PImage slime;
    public PImage powerUp;
    public PImage frozenG;
    public PImage frozenS;
    public PImage[] brickwallDestruction; // set of images of the brick wall in the process of 'destructing'
    public PImage[] wizardSprites;
    public PImage tPIcon;
    public PImage hBeam; 
    public PImage vBeam; 
    public PImage JTK; 

    // ! Object names
    public static final Random randomGenerator = new Random(); // random number generator 
    private JSONObject json; // config file 
    private JSONArray levelSpecs; // contents of said file 
    private Wizard w; // Player controlled character 
    private ArrayList<Immobile> mapToDraw; // map 
    private ArrayList<Gremlin> gArray; // array of gremlin objects 
    private ArrayList<Mobile> NPMobileEntities; // non projectile mobile entities
    private JSONReader jr;
    /**
     * Fireballs on screen at one time. 
     */
    private ArrayList<Fireball> existingFireballs; // fireballs on screen 
    private FreezePotion p;
    private Kamehameha k;

    // Other variables
    public static final int FPS = 60;
    private String configPath; 
    
    private int universalCounter = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int lives;
    private int level;
    private int deaths = 0;
    private int innateCooldown = 0;
    private int kCharge = 3;
    private int[] powerUpCoords;
    private double wizardCooldown;
    private double enemyCooldown;
    private boolean waitForKey = true; // ! TO IMPLEMENT 
    private boolean won;
    private boolean passedLvl2 = false;
    private boolean passedLvl1 = false;
    private boolean powerUpSpawned = false; 
    private boolean restart = false;
    private boolean freezeActive = false;
    private boolean powerUpConsumedOnce = false; 
    private boolean kHit = false; 

    /**
     * Constructor
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
        
        jr.readSpecs(2);
        System.out.println(jr.getSpecs().toString());

        json = loadJSONObject(new File(this.configPath));
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
            File f = new File(layout); // File f = new File("level0.txt"); //
            Scanner sc = new Scanner(f);
            
            while(sc.hasNextLine()){
                Character [] temp = new String(
                    sc.nextLine().trim().toCharArray()).chars()
                    .mapToObj(c-> (char) c).toArray(Character[]::new);
                mapContents.add(temp);
                    
            }

            sc.close();
            f = null;
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
            this.frozenG = loadImage(this.getClass().getResource("frozenGremlin.png").getPath().replace("%20", " "));
            this.frozenS = loadImage(this.getClass().getResource("frozenSlime.png").getPath().replace("%20", " "));
            this.tPIcon = loadImage(this.getClass().getResource("wizardTP.png").getPath().replace("%20", " "));
            this.hBeam = loadImage(this.getClass().getResource("Beam_Horizontal.png").getPath().replace("%20", " "));
            this.vBeam = loadImage(this.getClass().getResource("Beam_Vertical.png").getPath().replace("%20", " "));
            this.JTK = loadImage(this.getClass().getResource("JTK.png").getPath().replace("%20", " "));
            this.JTK.resize(HEIGHT, WIDTH);
       
        } catch (UnsupportedEncodingException e) {
            System.err.println("Could not find Wizard");
            e.printStackTrace();
        }

        // * Draws map
        mapToDraw = new ArrayList<Immobile>(); // ArrayList that contains all block objects to be drawn
        gArray = new ArrayList<Gremlin>();
        NPMobileEntities = new ArrayList<Mobile>();
        existingFireballs = new ArrayList<Fireball>();
        powerUpCoords = new int[2];
        powerUpCoords[0] = 999; 
        powerUpCoords[1] = 999; 
        this.powerUpConsumedOnce = false; 
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
                        gArray.add(new Gremlin(xCounter, yCounter, this.gremlin, this.slime, this.frozenG, this.frozenS));
                    }

                    else if (k == 'E'){ // ! Exit ... CHANGE LATER
                        mapToDraw.add(new ExitPortal(xCounter, yCounter, this.portal));
                    }

                    else if (k == 'P'){
                        powerUpCoords[0] = xCounter; 
                        powerUpCoords[1] = yCounter;
                    }
                    xCounter += incrementer; 
                }
                yCounter += incrementer;
                xCounter = 0;
            }
        // * Initialise objects 
        mapContents = null;
        // Wizard
        w = new Wizard(wizStartX, wizStartY, 2, this.wizardSprites);
        w.setManaCooldown((int)this.wizardCooldown);
        NPMobileEntities.add(w);
        NPMobileEntities.addAll(gArray);
        this.innateCooldown = 0;
        json = null;
        w.setTpCooldown(0);
    }
    
    public Kamehameha getK(){
        if(this.k != null){
            return this.k;
        }
        return null;
    }

    public boolean getFreezeActive(){
        return this.freezeActive;
    }

    public void setFreezeActive(boolean b){
        this.freezeActive = b;
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

    public boolean getPowerUpAvailable(){
        return this.powerUpSpawned;
    }
    
    public FreezePotion getPowerUp(){
        return this.p;
    }

    public Wizard getWizard(){
        return this.w;
    }
    
    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode(); 
        if(key == 37){ //left arrow 
            w.left();
            //System.out.println("left" + System.currentTimeMillis());
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
        
        if (key == 32 && k == null){ //space 
            if(w.getManaCoolDown() >= this.wizardCooldown){
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
                f = null;
                NPMobileEntities.addAll(existingFireballs);    
            }
            
            
        }
        
        else if (key == 84){ // t
            if(w.getTpCooldown() == 0){
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
    
    // ! Move this to the fireball class (Ask tutor about OOP paradigms)
    public void fireballMechanics(){
        for(Fireball f: existingFireballs){ // checks through every fireball
            if(f!= null){
                int collided = f.tick(this); // draws a non-null fireball
                f.draw(this);

                // a block was hit
                if((collided == 1 || f.getDecaying()) && f.getCollisionEntity() == null){ 
                    f.setDecaying(true); // set decay state (of fireball) to true
                    if(f.collisionBlock.getViable()){
                        mapToDraw.get(mapToDraw.indexOf(f.collisionBlock)).setViable(false);
                        f.stop();
                        f.hit();
                    }
                    if(f.getFrameCounter() == 16){ // always check if it's the end of the sequence 
                        if(mapToDraw.indexOf(f.collisionBlock) != -1){ // the block actually exists
                            if(f.fireballHit(this)){
                                f.collisionBlock.setViable(false);
                                mapToDraw.set(mapToDraw.indexOf(f.collisionBlock), null);
                                f.collisionBlock = null; // block will be deleted
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
                            if(f.fireballHitGremlin(this)){
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

    public int[] respawnMechanics(){ // generates a set of free coordinates 10 blocks away from the player
        int wX = w.getX(); 
        int wY = w.getY();
        while (wX % 20 != 0){
            wX += 1;
        }
        while (wY % 20 != 0){
            wY += 1;
        }
        boolean canRespawnX = false;
        boolean canRespawnY = false;
        int xTest = wX;
        int yTest = wY;
        while(!canRespawnX || !canRespawnY){
            if(!canRespawnX){
                xTest = randomGenerator.nextInt(34) * 20;
                //System.out.println("x Respawn looping");
                if(xTest == 0){
                    continue;
                }
                else if((xTest >= wX + w.WIDTH+ 200 || xTest <= wX - 200) || 
                ((xTest < wX + w.WIDTH + 200 && xTest > wX - 200) && ((yTest > wY + 200 + w.HEIGHT) || (yTest < wY - 200)))){ // too far left or right. Essentially both coords are valid. 
                    boolean canPass = true;
                    for(Immobile i : mapToDraw){
                        if(i != null){
                            if((i.getX() == xTest && i.getY() == yTest)){
                                canPass = false;
                            }
                        }
                    }
                    if(canPass){
                        //System.out.println("X: " + xTest + " Y: " + yTest);
                        canRespawnX = true;
                        canRespawnY = true;
                    }
                }
            }

            if(!canRespawnY){
                yTest = randomGenerator.nextInt(31) * 20; 
                //System.out.println("Y respawn looping");
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
                        //System.out.println("X: " + xTest + " Y: " + yTest);
                        canRespawnX = true;
                        canRespawnY = true;
                    }
                }
            }
            
        }

        int[] toReturn = new int[2];
        toReturn[0] = xTest;
        toReturn[1] = yTest;
        return toReturn;
    }

    public void gremlinRespawn(Gremlin g){
        int[] coords = this.respawnMechanics();
        g.setX(coords[0]); 
        g.setY(coords[1]);

    }

    private void instructions(){
            System.out.println("Entered");
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
                //System.out.println("Time left: " + ((p.getCooldown()/FPS)));
                this.image(this.powerUp, 375, 673);
                this.rect(400,680, 50, 6);
                this.fill(0,0,255);
                this.rect(400, 680, (float) (50 - (p.getCooldown()*10/FPS)), 6);
                this.fill(255,255,255);
            }
        }
    }

    // Draws wizard associated stuff
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
            return 0 ;
        }
        
        for(int i = 0; i < this.lives; i++){
            this.image(this.wizardSprites[1], 20 + i*20 +60, HEIGHT - 50);
        }
        textSize(15);
        text("T to teleport", 480, 710);
        this.image(this.tPIcon, 575, 700);
        this.rect(600,705, 100, 6);
        this.fill(0,255,255);
        this.rect(600, 705, (float) (100 - (w.getTpCooldown()*20/FPS)), 6);
        this.fill(255,255,255);
        if(w.getTeleported() && w.getTpCooldown() > 0){
            w.setTpCooldown(w.getTpCooldown() - 1);
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
        this.existingFireballs = updater1.update(existingFireballs);
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
        this.NPMobileEntities.addAll(this.existingFireballs);

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

            if(!powerUpSpawned && this.innateCooldown == 0 && this.powerUpConsumedOnce){
                boolean canBreak = false;
                int[] coords = {0, 0};
                while(!canBreak){
                    coords = this.respawnMechanics();
                    for(Gremlin g : gArray){
                        if(coords[0] == g.getX() && coords[1] == g.getY()){
                            break;
                        }
                        canBreak = true;
                    }
                }
                if(coords[0] != 0 && coords[1] != 0){
                    p = new FreezePotion(coords[0], coords[1], this.powerUp);
                    powerUpSpawned = true;
                    this.innateCooldown = 20 * FPS;
                }
            }

            else if(!powerUpSpawned && this.innateCooldown == 0 && !this.powerUpConsumedOnce){
                p = new FreezePotion(powerUpCoords[0], powerUpCoords[1], this.powerUp);
                powerUpSpawned = true; 
                this.innateCooldown = 20*FPS;
                this.powerUpConsumedOnce = true;
            }
        }
        if(p != null){
            if(!p.getConsumed()){
                mapToDraw.add(p); // ! CHECK P
            }
            
            if(this.innateCooldown == 0){
                System.out.println("p nulled");
                powerUpSpawned = false;
                this.setFreezeActive(false);
                p = null;
            }

            else if(p.getConsumed() && this.innateCooldown == 20*FPS){ // innateCooldown is spawn cooldown
                //System.out.println("p before " + mapToDraw.get(mapToDraw.indexOf(p)));
                this.mapToDraw.set(mapToDraw.indexOf(p), null);
                this.powerUpSpawned = false;
                //System.out.println("p after " + mapToDraw.get(mapToDraw.indexOf(p)));
                this.innateCooldown -= 1;
            }

            else if (p.getConsumed() && this.innateCooldown <= 20*FPS){
                this.innateCooldown -= 1;
                //System.out.println(this.innateCooldown);
            }
        }
        
    } 

    public void checkGameState(int retVal){
        if(this.lives <= 0){
            //background(219, 160, 9);
            //textSize(40);
            //fill(107, 9, 219);
            //text("Game over", WIDTH*0.2f, HEIGHT*0.4f);
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
            if(!this.powerUpSpawned && p.getConsumed() && this.getFreezeActive()){ // consumed and not on screen 
                if(p.getCooldown() == 5*FPS){
                    p.setCooldown(0);
                    this.setFreezeActive(false);
                }

                else if (p.getCooldown() < 5*FPS){
                    p.setCooldown(p.getCooldown() + 1);
                }
            }
        }
    }

    public void kamehameha(){
        if(k != null){
            if(!k.getDrawing()){
                k.tick(this);
                k.setLifespan(k.getLifeSpan() + 1);
                k.setDrawing(true);
            }
            
            if(k.getDrawing()){
                k.setLifespan(k.getLifeSpan() + 1);
                k.draw(this);
                
            }
            
            if(k.getLifeSpan() > 60){
                k.setLifespan(0);
                k.setDrawing(false);
                k = null;
            }
        }
        if(this.kCharge >= 0){
            text("R to clear all destructable blocks in front of you", 20, 710);
            for(int i = 0; i < this.kCharge; i++){
                this.image(this.hBeam, 375 + i*25, 695);
            }
            
            
            
        }
        
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        //System.out.println(innateCooldown);
        if(!won){
            
            //System.out.println("tp cooldown" + w.getTpCooldown());
            this.basicGraphics();
            this.powerUpSpawn(seconds);
            int retVal = this.wizardGraphics();
            this.fireballMechanics();
            this.kamehameha();
            this.mapGraphics();
            this.gremlinGraphics();

            this.powerUpUpdates();

            this.arrayListUpdates();

            if(universalCounter == 60){
                seconds += 1;
                //System.out.println(seconds);
                universalCounter = 0;
                if(seconds == 60){
                    minutes += 1;
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