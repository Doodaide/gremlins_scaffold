package gremlins;

import processing.core.PImage;

/**
 * Fireball class: wizard's main projectile 
 */
public class Fireball extends Projectile{
    /**
     * A necessary boolean used to indicate whether the fireball 
     * has collided with a viable object
     */
    private boolean decaying;

    /**
     * Constructor for fireball 
     * @param x x coordinate of fireball 
     * @param y y coordinate of fireball 
     * @param sprite fireball sprite 
     */
    public Fireball(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.decaying = false;
    }

    /**
     * Getter method for whether the fireball is decaying 
     * @return boolean (true for decaying and hit an interactable object, false for not doing so)
     */
    public boolean getDecaying(){
        return this.decaying;
    }

    /**
     * Setter method for whether the fireball is decaying 
     * @param d boolean for whether the fireball is decaying or not 
     */
    public void setDecaying(Boolean d){
        this.decaying = d;
    }

    /**
     * Up Method sets the yVel, xVel, and direction of the fireball to correspond to moving up
     */
    @Override
    public void up() {
        this.yVel = -speed;
        this.xVel = 0;
        this.direction = 0;
    }

    /**
     * Right Method sets the xVel, yVel and direction of the fireball to correspond to moving right
     */
    @Override
    public void right() {
       this.xVel = speed;
       this.yVel = 0;
       this.direction = 1;
    }

    /**
     * Down method sets the xVel, yVel, and direction of the fireball to correspond to moving down
     */
    @Override
    public void down() {
        this.yVel = speed;
        this.xVel = 0;
        this.direction = 2;
    }

    /**
     * Left method sets the xVel, yVel, and direction of the fireball to correspond to moving left
     */
    @Override
    public void left() {
        this.xVel = -speed;
        this.yVel = 0;
        this.direction = 3;
    }

    /**
     * FireballHit is a method that evaluates whether the 'current' fireball considered has hit a valid destructable block on the map
     * @param app app is used to get the ArrayList of all blocks (destructable and indestructable) that are to be drawn 
     * @return boolean corresponding to whether a destructable block that has not been destroyed has been hit by the current fireball 
     */
    public boolean fireballHit(App app){
        for(Immobile i : app.getMap()){
            if(i != null){
                if(i == this.collisionBlock && i.getClass().getName().equals("gremlins.Destructable")){
                    return true; 
                }
            }
        }
        return false;
    }

    /**
     * FireballHitGremlin is a method that evaluates whether the 'current' fireball considered has hit a gremlin mobile entity on the screen 
     * @param app app is used to get the ArrayList of all currently active gremlins that are to be drawn 
     * @return boolean corresponding to whether a valid gremlin has been hit by the current fireball. 
     */
    public boolean fireballHitGremlin(App app){
        for(Gremlin g : app.getGArray()){
            if(g != null){
                if(g == this.collisionEntity){
                    return true; 
                }
            }
        }
        return false;
    }

    /**
     * Draw method to draw the fireball on screen 
     * @param app inherits the image() method from PApplet
     */
    @Override
    public int draw(App app){
        if(this.alive){
            app.image(this.sprite, this.x, this.y);
        }
        return 0;
    }

    /**
     * Tick method "updates" the fireball everytime called. 
     * Under the condition that the fireball is still "alive" - meaning it can still be displayed on the next frame and be considered for movement
     * the tick method will check if it has collided with anything
     * @implSpec bruh moment
     * @param app which is used in the mobile parent class to access all possible "collidable" objects 
     * @return integer 1 (corresponds to having hit something) or integer 0 (corresponds to not having hit something)
     */
    @Override
    public int tick(App app) {
        if(this.alive){
            if(this.collision(app) == 1){ // block collision 
                if((!this.sideAV.get(0) && this.direction == 0) 
                || (!this.sideAV.get(2) && this.direction == 2) 
                || (!this.sideAV.get(1) && this.direction == 1) 
                || (!this.sideAV.get(3) && this.direction == 3)){
                    return 1;
                }
            }
            this.x += this.xVel; 
            this.y += this.yVel;
        }
        return 0;
        
    }
}