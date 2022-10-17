package gremlins;
import java.util.ArrayList;
import processing.core.PImage;
/**
 * Wizard class: The player controlled character 
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
     * @param wizardSprites 
     */
    public Wizard(int x, int y, int speed, 
            PImage[] wizardSprites) {

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

    public PImage getCurrentImg(){
        return this.imgCurrent;
    }
    
    public void setManaCooldown(int x){
        this.manaCooldown = x;
    }

    public int getManaCoolDown(){
        return this.manaCooldown;
    }

    public void setTpCooldown(int x){
        this.tpC = x;
    }

    public int getTpCooldown(){
        return this.tpC;
    }

    public void setTeleported(boolean b){
        this.teleported = b;
    }

    public boolean getTeleported(){
        return this.teleported;
    }

    public ArrayList<Fireball> getExistingFireballs(){
        return this.existingFireballs;
    }

    public void setExistingFireballs(ArrayList<Fireball> a){
        this.existingFireballs = a;
    }

    public void shoot(){
        existingFireballs.add(new Fireball(this.getX(), this.getY(), wizardSprites[4]));
        Fireball f = existingFireballs.get(existingFireballs.size() - 1);
        if(f != null){
            if(this.direction == 0){
                //System.out.println("up");
                f.up();
            }

            else if(this.direction == 1){
                //System.out.println("right ");
                f.right();
            }

            else if(this.direction == 2){
                //System.out.println("down");
                f.down();
            }

            else if(this.direction == 3){
                //System.out.println("left");
                f.left();
            }
        }
        //f = null;


    }

    @Override
    public void up() {
        this.imgCurrent = this.wizardSprites[0];
        this.yVel = -speed;
        this.yDir = 0; 
        this.direction = 0;
    }

    @Override
    public void right() {
        this.imgCurrent = this.wizardSprites[1];
        this.xVel = speed;
        this.xDir = 1; 
        this.direction = 1;
    }

    @Override
    public void down() {
        this.imgCurrent = this.wizardSprites[2];
        this.yVel = speed; 
        this.yDir = 2; 
        this.direction = 2;
    }

    @Override
    public void left() {
        this.imgCurrent = this.wizardSprites[3];
        this.xVel = -speed;
        this.xDir = 3; 
        this.direction = 3;
    }

    public void tp(App app){
        int[] coords = (new RespawnGenerator(this)).respawnXBlocks(app);
        this.setX(coords[0]);
        this.setY(coords[1]);
    }

    public void fireballMechanics(App app){
        for(Fireball f : this.existingFireballs){
            if(f != null){
                int collided = f.tick(app);
                f.draw(app);
                if((collided == 1 || f.getDecaying()) && f.getCollisionEntity() == null){ 
                    f.setDecaying(true); // set decay state (of fireball) to true
                    if(f.collisionBlock.getViable()){
                        app.getMap().get(app.getMap().indexOf(f.collisionBlock)).setViable(false);
                        //mapToDraw.get(mapToDraw.indexOf(f.collisionBlock)).setViable(false);
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
                        //existingFireballs.set(existingFireballs.indexOf(f), null);
                        this.getExistingFireballs().set(this.getExistingFireballs().indexOf(f),null);
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
                        if(app.getGArray().indexOf(f.collisionEntity) != -1){
                            if(f.fireballHitGremlin(app)){
                                f.stop();
                                f.hit();
                                //System.out.println("Fireball hit gremlin passed");
                                app.getGArray().get(app.getGArray().indexOf(f.collisionEntity)).gremlinRespawn(app);
                                //gArray.set(gArray.indexOf(f.collisionEntity), null); // !RESPAWN MECHANICS GO HERE. 
                                //System.out.println(gArray.get(gArray.indexOf(f.collisionEntity)));
                                //existingFireballs.set(existingFireballs.indexOf(f), null);
                                this.getExistingFireballs().set(this.getExistingFireballs().indexOf(f), null);
                                f = null;
                            }
                        }
                        
                    }
                }
            }
        }
    }

    // Wizard custom draw method, and how to update the sprite on the map. 
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
                    //System.out.println("Ouchie");
                    this.alive = false;
                }
            }

            // Hits a block
            if(this.collisionBlock != null){
                if(this.collisionBlock.getClass().getName().equals("gremlins.ExitPortal")){
                    return 20;
                }
                
                else if(this.collisionBlock.getClass().getName().equals("gremlins.FreezePotion")){
                    //System.out.println("potion touch in wizard");
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
                    // if direction != velocity direction 
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