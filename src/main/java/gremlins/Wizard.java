package gremlins;
import java.util.ArrayList;
import processing.core.PImage;
/**
 * Wizard class: The player controlled character that extends a mobile entity
 */
public class Wizard extends Mobile{

    /**
     * The image to display in the current tick
     */
    private PImage imgCurrent;

    /**
     * An array of images that belongs to the wizard
     */
    private PImage[] wizardSprites;

    /**
     * The delay between each fireBall shot - depends on level specificiations 
     */
    private int manaCooldown;

    /**
     * The delay between each Teleport cycle
     */
    private int tpC;

    /**
     * Whether the wizard has teleported 
     */
    private boolean teleported;

    /**
     * An ArrayList of the current, non-collided fireballs the wizard has shot (and are still shown onscreen)
     */
    private ArrayList<Fireball> existingFireballs;

    /**
     * Constructor for wizard object 
     * @param x x coordinate 
     * @param y y coordinate 
     * @param speed how many pixels it moves per increment 
     * @param wizardSprites An array of sprites the wizard will use 
     */
    public Wizard(int x, int y, int speed, PImage[] wizardSprites) {
        super(x, y, 2, 0, 0);
        this.wizardSprites = wizardSprites;
        this.imgCurrent = this.wizardSprites[3];
        this.xDir = 3;
        this.yDir = 0;
        this.manaCooldown = 0;
        this.tpC = 0;
        this.teleported = false; 
        this.existingFireballs = new ArrayList<Fireball>();
    }

    /**
     * Getter method for the curent displayed Wizard image 
     * @return a PImage that corresponds to the direction the wizard is facing 
     */
    public PImage getCurrentImg(){
        return this.imgCurrent;
    }
    
    /**
     * Setter method for the mana Cooldown (delay between fireball shots)
     * @param x an integer to set the mana cooldown to 
     */
    public void setManaCooldown(int x){
        this.manaCooldown = x;
    }

    /**
     * Getter method to get the amount of time until the wizard can next shoot a fireball 
     * @return integer that corresponds to the mana cooldown 
     */
    public int getManaCoolDown(){
        return this.manaCooldown;
    }

    /**
     * Setter method to set the amount of time between the previous valid teleport request, and the next valid teleport 
     * @param x an integer that corresponds with a new increment. 
     */
    public void setTpCooldown(int x){
        this.tpC = x;
    }

    /**
     * Getter method for the teleport cooldown - the amount of time between valid teleports 
     * @return an integer that corresponds to the time between the previous and next valid teleport 
     */
    public int getTpCooldown(){
        return this.tpC;
    }

    /**
     * Setter method that sets whether the wizard has teleported in a teleport cycle. Used in conjunction with the teleport times
     * @param b boolean corresponding to whether the wizard has or has not teleported 
     */
    public void setTeleported(boolean b){
        this.teleported = b;
    }

    /**
     * Getter method that gets whether the wizard has teleported in a teleport cycle. Used in conjunction with the teleport times
     * @return boolean corresponding to whether the wizard has or has not teleported 
     */
    public boolean getTeleported(){
        return this.teleported;
    }

    /**
     * Getter method that returns the ArrayList of fireballs the wizard is in posession of (valid fireballs are those that 
     * have not hit any mobile or immobile entities and thus will still be drawn on screen)
     * @return ArrayList of fireballs 
     */
    public ArrayList<Fireball> getExistingFireballs(){
        return this.existingFireballs;
    }

    /**
     * Setter method that sets the ArrayList of fireballs the wizard is in posession of (valid fireballs are those that 
     * have not hit any mobile or immobile entities and thus will still be drawn on screen)
     * @param a An ArrayList of Fireball objects 
     */
    public void setExistingFireballs(ArrayList<Fireball> a){
        this.existingFireballs = a;
    }

    /**
     * Shoot method creates a new fireball, adds it to the exisitng list of fireballs, then
     * takes the newest one (indicated by the fact that it is the last one in the ArrayList)
     * and moves it in the direction that the wizard is facing. 
     */
    public void shoot(){
        existingFireballs.add(new Fireball(this.getX(), this.getY(), wizardSprites[4]));
        Fireball f = this.existingFireballs.get(this.existingFireballs.size() - 1); // ! Problem 
        if(f != null){
            if(this.direction == 0){
                f.up();
            }

            else if(this.direction == 1){
                f.right();
            }

            else if(this.direction == 2){
                f.down();
            }

            else if(this.direction == 3){
                f.left();
            }
        }
    }

    /**
     * Overriden Up method, that modifies the current wizard image, velocities, and direction of the wizard
     */
    @Override
    public void up() {
        this.imgCurrent = this.wizardSprites[0];
        this.yVel = -speed;
        this.yDir = 0; 
        this.direction = 0;
    }

    /**
     * Overriden right method, that modifies the current wizard image, velocities, and direction of the wizard
     */
    @Override
    public void right() {
        this.imgCurrent = this.wizardSprites[1];
        this.xVel = speed;
        this.xDir = 1; 
        this.direction = 1;
    }

    /**
     * Overriden down method, that modifies the current wizard image, velocities, and direction of the wizard
     */
    @Override
    public void down() {
        this.imgCurrent = this.wizardSprites[2];
        this.yVel = speed; 
        this.yDir = 2; 
        this.direction = 2;
    }

    /**
     * Overriden method, that modifies the current wizard image, velocities, and direction of the wizard
     */
    @Override
    public void left() {
        this.imgCurrent = this.wizardSprites[3];
        this.xVel = -speed;
        this.xDir = 3; 
        this.direction = 3;
    }

    /**
     * Teleport method that resets the wizard's current coordinates to new coordinates generated by the random generator. 
     * @param app The app class, used to get data such as the current map's configuration
     */
    public void tp(App app){
        int[] coords = (new RespawnGenerator(this)).respawnXBlocks(app);
        this.setX(coords[0]);
        this.setY(coords[1]);
    }

    /**
     * FireballMechanics controls all the interactions between fireballs, and all other objects in the game 
     * This method is located in the wizard as every fireball within its available fireballs ArrayList must be updated. 
     * This method checks every existing fireball whether it has collided, or interacted with. 
     * Split into two large sections (regarding mobile and immobile interactions)
     * @param app A referene to the App class, where useful methods such as getMap and getMobiles are located
     */
    public void fireballMechanics(App app){
        for(Fireball f : this.existingFireballs){
            if(f != null){
                int collided = f.tick(app);
                f.draw(app);
                // An immobile was hit
                if((collided == 1 || f.getDecaying()) && f.getCollisionEntity() == null){ 

                    f.setDecaying(true); // set decay state (of fireball) to true
                    if(f.collisionBlock.getViable()){

                        app.getMap().get(app.getMap().indexOf(f.collisionBlock)).setViable(false);
                        f.stop();
                        f.hit();
                    }
                    
                    if(f.getFrameCounter() == 16){ // always check if it's the end of the sequence 

                        if(app.getMap().indexOf(f.collisionBlock) != -1){ // the block actually exists

                            if(f.fireballHit(app)){
                                f.collisionBlock.setViable(false);
                                app.getMap().set(app.getMap().indexOf(f.collisionBlock), null);
                                f.collisionBlock = null; // block will be deleted
                            }
                        }
                        this.getExistingFireballs().set(this.getExistingFireballs().indexOf(f),null);
                        f = null;
                    }
                    
                    else if(f.getFrameCounter() % 4 == 0 && f.getFrameCounter() != 0){ // been either 4, 8, 12, 16 frames since firball hit 
                        f.collisionBlock.update();
                        //f.incFrameCounter();
                        //f.setFrameCounter(f.getFrameCounter() + 1);
                    } 

                    if(f != null){
                        //f.incFrameCounter(); // if the fireball has not hit an indestructable entity 
                        f.setFrameCounter(f.getFrameCounter() + 1);
                    }
                    
                }
                
                // A mobile entity was hit
                else if(f.getCollisionEntity() != null){
                    if(f.getCollisionEntity().getClass().getName().equals("gremlins.Gremlin")){

                        if(app.getGArray().indexOf(f.collisionEntity) != -1){

                            if(f.fireballHitGremlin(app)){

                                f.stop();
                                f.hit();
                                app.getGArray().get(app.getGArray().indexOf(f.collisionEntity)).gremlinRespawn(app);
                                this.getExistingFireballs().set(this.getExistingFireballs().indexOf(f), null);
                                f = null;
                            }
                        } 
                    }
                }
            }
        }
    }

    /**
     * Draw method for the wizard. Draws the image of the wizard (if it is not null), and draws the teleport mechanics
     * as all the variables associated with it are in this class 
     * @return An integer corresponding to the direction the wizard is facing. 
     */
    public int draw(App app){
        if(this.imgCurrent != null){
            app.image(this.imgCurrent, this.x, this.y);
            app.textSize(15);
            app.text("T to teleport", 480, 710);
            app.image(app.tPIcon, 575, 700);
            app.rect(600,705, 100, 6);
            app.fill(0,255,255);
            app.rect(600, 705, (float) (100 - (this.getTpCooldown()*20/App.FPS)), 6);
            app.fill(255,255,255);
            if(this.getTeleported() && this.getTpCooldown() > 0){
                this.setTpCooldown(this.getTpCooldown() - 1);
            }
        }
        return direction;
    }

    /**
     * Tick method for the wizard hosts all the logic to deal with fireball firing, entity collision and interactions, 
     * valid and invalid motion/movements, and wizard death. 
     * @param app the App class that hosts many useful parameters and methods such as getGArray()
     * @return returns an integer that corresponds to whether the wizard is alive (0), dead (10), or running into the level's exit(20)
     */
    public int tick(App app) {
        if(this.alive){
            // Mana cooldown bar 
            if(this.getManaCoolDown() <= app.getWizardCooldown()){
                app.image(this.wizardSprites[4], 575, 665);
                this.setManaCooldown(this.getManaCoolDown() + 1);
                app.rect(600,673, 100, 5);
                app.fill(0,0,0);
                app.rect(600, 673, (float) (this.getManaCoolDown() * 100/app.getWizardCooldown()), 5);
                app.fill(255,255,255);
            }

            // Runs collision algorithm 
            this.collision(app);
            

            // hits gremlin entity itself (not slimeball)
            if(this.collisionEntity != null){
                if(this.collisionEntity.getClass().getName().equals("gremlins.Gremlin")){
                    this.alive = false;
                }
            }

            // Hits a block
            if(this.collisionBlock != null){
                if(this.collisionBlock.getClass().getName().equals("gremlins.ExitPortal")){
                    return 20;
                }
                
                else if(this.collisionBlock.getClass().getName().equals("gremlins.FreezePotion")){
                    app.getPowerUp().setConsumed(true);
                }
            }
    
            // Stops diagonal motion 
            if(((this.direction == 0 || this.direction == 2) && this.xVel != 0 ) ||
                ((this.direction == 1 || this.direction == 3) && this.yVel != 0)
            ){
                this.stop();
            }
    
            // Valid motion commands
            else{
                if( (!this.sideAV.get(3) && (this.xVel < 0) && this.direction == 3) ||
                    (!this.sideAV.get(1) && (this.xVel > 0) && this.direction == 1)){
                    this.xStop();
                    this.xVel = 0;
                }
                
        
                if( (!this.sideAV.get(0) &&(this.yVel < 0) && this.direction == 0) ||
                    (!this.sideAV.get(2) && (this.yVel > 0) && this.direction == 2)){
                    this.yStop();
                    this.yVel = 0;
                }
                
                // Prevents diagonal movement 
                if(this.getXVel() == 0 || this.getYVel() == 0){
                    // Check direction corresponds to velocity. 
                    if(app.getK() != null){
                        if(!app.getK().getDrawing()){
                            this.x += this.xVel;
                            this.y += this.yVel;
                        }
                    }
                    else{
                        this.x += this.xVel;
                        this.y += this.yVel;
                    }
                }
                
                // full block movement 
                if (this.getX() % 20 != 0 && this.getXVel() == 0){
                    if(this.xDir == 3 && this.sideAV.get(3)){
                        this.setX(this.getX() - 1);
                    }
                    else if (this.xDir == 1 && this.sideAV.get(1)){
                        this.setX(this.getX() + 1);
                    }
                   
                }
                if (this.getY() % 20 != 0 && this.getYVel() == 0){
                    if(this.yDir == 0 && this.sideAV.get(0)){
                        this.setY(this.getY() - 1);
                    }
                    else if(this.yDir == 2 && this.sideAV.get(2)){
                        this.setY(this.getY() + 1);
                    }
                    
                }
            }

            
        }
        
        else{ // not alive any more
            return 10;
        }
        return 0;
        
    }
        
}