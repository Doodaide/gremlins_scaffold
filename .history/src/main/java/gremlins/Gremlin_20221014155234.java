package gremlins;

import java.util.ArrayList;

import processing.core.PImage;

/**
 * Gremlin class: An extension of mobile entity that continuously moves, shoots, and respawns on a given map
 */
public class Gremlin extends Mobile{

    /**
     * Gremlin sprite 
     */
    private PImage sprite; 

    /**
     * Each gremlin has a set of 'available projectiles', as within an interval, they can fire repeately 
     */
    private ArrayList<SlimeBall> availableSlimeBalls;

    /**
     * Each gremlin has a 'rate' at which they can fire slime
     */
    private int slimeCooldown;

    /**
     * Slimeball Sprite
     */
    private PImage slimeballImg;

    /**
     * Frozen gremlin (for freeze potion implementation)
     */
    private PImage frozenG;

    /**
     * Frozen Slime ball (for freeze potion implementation)
     */
    private PImage frozenS;

    /**
     * Constructor for gremlin 
     * @param x x coordinate 
     * @param y y coordinate 
     * @param img Gremlin sprite 
     * @param slimeball Slimeball projectile sprite 
     * @param frozenG Gremlin sprite when freeze potion is active 
     * @param frozenS Slimeball Sprite when freeze potion is active 
     */
    public Gremlin(int x, int y, PImage img, PImage slimeball, PImage frozenG, PImage frozenS) {
        super(x, y, 1, 0, 0);
        this.sprite = img;
        this.availableSlimeBalls = new ArrayList<SlimeBall>();
        this.slimeCooldown = 0;
        this.slimeballImg = slimeball;
        this.frozenG = frozenG;
        this.frozenS = frozenS;
    }

    /**
     * getter method for a singular gremlin's available slimeballs 
     * @return ArrayList of slimeballs that the current gremlin has available (not hit anything) 
     */
    public ArrayList<SlimeBall> getAvailableSlimeballs(){
        return this.availableSlimeBalls;
    }

    /**
     * Setter method for a singular gremlin's available slimeballs 
     * @param arr an arrayList of updated slimeballs to assign to the current slimeballs 
     */
    public void setAvailableSlimeballs(ArrayList<SlimeBall> arr){
        this.availableSlimeBalls = arr;
    }

    /**
     * setter method for the current slime's shooting cooldown
     * @param x integer that the current slime's cooldown should be set to 
     */
    public void setSlimeCooldown(int x){
        this.slimeCooldown = x;
    }

    /**
     * getter method for the current slim'e shooting cooldown 
     * @return integer that corresponds to the current slime's cooldown
     */
    public int getSlimeCoolDown(){
        return this.slimeCooldown;
    }

    /**
     * Method that "shoots" a slimeball by creating a new slimeball object, and sending it in a direction (after adding it to the 
     * current gremlin's available slimeballs ArrayList attribute)
     * @return a new ArrayList with the new slimeball added in. 
     */
    public ArrayList<SlimeBall> shootSlimeBall(){
        this.availableSlimeBalls.add(new SlimeBall(this.x, this.y, this.slimeballImg, this.frozenS));
        SlimeBall s = this.availableSlimeBalls.get(this.availableSlimeBalls.size() - 1); // gets last slimeball added 
        if(s != null){
            if(this.getDirection() == 0){ // up
                s.up();
            }
            else if (this.getDirection() == 1){
                s.right();
            }
            else if (this.getDirection() == 2){
                s.down();
            }
            else if (this.getDirection() == 3){
                s.left();
            }
        }
        return this.availableSlimeBalls;
    }

    /**
     * Slimeball mechanics dictates all the behaviour a slimeball should express
     * This is featured in the gremlin class as each gremlin has a set of fireballs, which makes it easier to control and 
     * modify then if it were in the slimeball class. 
     * @param app used to get various attributes such as the map, and other mobile entities
     */
    public void slimeballMechanics(App app){
        for(SlimeBall s : this.availableSlimeBalls){
            if(s != null){
                int collision = s.tick(app);
                s.draw(app);

                // Hits entity 
                if((collision == 1) && s.collisionEntity != null){
                    if(s.collisionEntity.getClass().getName().equals("gremlins.Fireball") && s.collisionEntity.collisionBlock == null){
                        s.stop();
                        s.collisionEntity.stop();
                        app.getExistingFireballs().set(app.getExistingFireballs().indexOf(s.collisionEntity), null);
                    }
                    else if(s.collisionEntity.getClass().getName().equals("gremlins.Wizard")){
                        s.stop();
                        s.collisionEntity.alive = false;
                        
                    }
                    s.alive = false;
                    this.availableSlimeBalls.set(this.availableSlimeBalls.indexOf(s), null);
                }
                // Hits block 
                else if(collision == 1 && s.collisionBlock != null && s.collisionBlock.getViable()){
                        s.stop();
                        s.alive = false;
                        this.availableSlimeBalls.set(this.availableSlimeBalls.indexOf(s), null);
                    
                }
            }
            
        }

        UpdateArrayList<SlimeBall> updater = new UpdateArrayList<SlimeBall>();
        this.availableSlimeBalls = updater.update(this.availableSlimeBalls);
        updater = null;
    }

    /**
     * Up method that sets various parameters of the gremlin to correspond to up 
     * yVel = -speed = -1, direction = 0, and yDir = 0 
     */
    @Override
    public void up() {
        this.direction = 0;
        this.yVel = -speed;
        this.yDir = 0;
        
    }
    /**
     * Right method that sets various parameters of the gremlin to correspond to right 
     * xVel = speed = 1, direction = 1, and xDir = 1
     */
    @Override
    public void right() {
        this.direction = 1;
        this.xVel = speed;
        this.xDir = 1;
    }

     /**
     * Down method that sets various parameters of the gremlin to correspond to down 
     * yVel = speed = 1, direction = 2, and yDir = 2 
     */
    @Override
    public void down() {
        this.direction = 2;
        this.yVel = speed; 
        this.yDir = 2;
    }

     /**
     * Left method that sets various parameters of the gremlin to correspond to left 
     * xVel = -speed = -1, direction = 3, and xDir = 3
     */
    @Override
    public void left() {
        this.direction = 3;
        this.xVel = -speed;
        this.xDir = 3;
    }

    /**
     * Gremlin AI method dictates all the movement a gremlin object can experience. 
     * The gremlin's current travelling direction, collisions, and surroundings are considered. 
     * Under the condition that the gremlin collides with something, a random direction is generated 
     * that is not in the same direction it just collided with the block in (i.e. no reversals)
     * (with specific exceptions such as a 3 way blockage). 
     * @param app used as a parameter for simulating collisions in the mobile collision method 
     * @return returns an integer n, which corresponds to the direction the gremlin should move in (0 - up, 1 - right, 2 - down ,3 - left)
     */
    public int gremlinAI(App app){
        int originalDirection = this.getDirection();
        boolean canMove = false;
        boolean[] travelOptions = this.getSideAV();
        boolean[] feasibleDirections = new boolean[4];
        for(int i = 0; i < 4; i++){
            feasibleDirections[i] = true;
        }
        
        int prevLoc = 5;
        if(this.direction == 0){prevLoc = 2;}
        else if(this.direction == 1){prevLoc = 3;}
        else if(this.direction == 2){prevLoc = 0;}
        else if(this.direction == 3){prevLoc = 1;}

        feasibleDirections[prevLoc] = false; // mark previous direction as false
        int n = 5;

        while(!canMove){
            n = (int) (Math.random() * (4)); // generate random direction
            //System.out.println("random number generated " + n);
            this.setDirection(n);
            this.collision(app);
            travelOptions = this.getSideAV();

            if(feasibleDirections[n]){
                //System.out.println("Entering into else if loop");
                if(feasibleDirections[n] && travelOptions[n]){
                    canMove = true; // return x;
                    //System.out.println("Proper direction detected: " + n);
                    //return x;
                }
                else{
                    feasibleDirections[n] = false;
                }
            }

            else if((!feasibleDirections[0] && !feasibleDirections[1] && !feasibleDirections[2] && !feasibleDirections[3] )){
                n = prevLoc;
                //System.out.println("Enters u realm " + n);
                canMove = true;
            }
        }

        this.setDirection(originalDirection);
        this.collision(app);
        //System.out.println("Direction to go 1" + x);
        travelOptions = null;
        feasibleDirections = null;
        return n;
    }

    /**
     * Draw method displays the gremlin's sprite onscreen. 
     * Under the condition that the freeze potion is active, the gremlin's sprite is changed, but still drawn on screen. 
     * @return the direction the gremlin was last drawn in. 
     */
    @Override
    public int draw(App app){
        if(!app.getFreezeActive()){
            app.image(this.sprite, this.x, this.y);
        }
        else if(app.getFreezeActive()){
            app.image(this.frozenG, this.x, this.y);
        }
        return this.direction;
        
    }

    /**
     * 
     */
    @Override
    public int tick(App app){
        if(!app.getFreezeActive()){
            if(this.collisionEntity != null){
                if(this.collisionEntity.getClass().getName().equals("gremlins.Wizard")){
                    //System.out.println("GOTCHA");
                    this.collisionEntity.alive = false; // kill wizard behaviour
                    return 10;
                }
            }
            int toGo = this.getDirection();
            if(this.collision(app) == 1){
                toGo = this.gremlinAI(app);
            }
            if(toGo == 0){
                this.up();
            }
            else if(toGo == 1){
                this.right();
            }
            else if(toGo == 2){
                this.down();
            }
            else if(toGo == 3){
                this.left();
            }
            
            if(((this.direction == 0 || this.direction == 2) && this.xVel != 0 ) ||
                ((this.direction == 1 || this.direction == 3) && this.yVel != 0)
            ){
                this.stop();
            }
    
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
                    this.x += this.xVel;
                    this.y += this.yVel;
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
            
            if((this.getSlimeCoolDown() >= app.getEnemyCooldown()) && (this.getX() % 20 == 0 || this.getY() % 20 == 0)){
                this.shootSlimeBall();
                this.setSlimeCooldown(0);
            }
    
            else if(this.getSlimeCoolDown() <= app.getEnemyCooldown()){
                this.setSlimeCooldown(this.getSlimeCoolDown() + 1);
            }
        }
            this.slimeballMechanics(app);
        return 0;
    }
}