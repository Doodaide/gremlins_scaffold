package gremlins;

import java.util.ArrayList;

import processing.core.PImage;

/**
 * Wizard
 */
public class Wizard extends Mobile{

    /*
     * x - x coord 
     * y - y coord
     * 
     */
    private PImage imgCurrent, imgProjectile;
    private PImage[] wizardSprites;
    private int manaCooldown;
    private boolean poweredUp;
    private ArrayList<Fireball> existingFireballs;
    public Wizard(int x, int y, int speed, 
            PImage[] wizardSprites) {

        super(x, y, 2, 0, 0);
        this.wizardSprites = wizardSprites;
        this.imgCurrent = this.wizardSprites[3];
        this.imgProjectile = this.wizardSprites[4];
        this.xDir = 3;
        this.yDir = 0;
        this.manaCooldown = 0;
        this.poweredUp = false; 
        this.existingFireballs = new ArrayList<Fireball>();
    }
    
    public void setManaCooldown(int x){
        this.manaCooldown = x;
    }

    public int getManaCoolDown(){
        return this.manaCooldown;
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

    public Fireball shoot(){
        if((this.getX() % 20 == 0 || this.getY() % 20 == 0)){
            return new Fireball(this.getX(), this.getY(), this.imgProjectile);
        }
        return null;
    }

    // Wizard custom draw method, and how to update the sprite on the map. 
    public int draw(App app){
        app.image(this.imgCurrent, this.x, this.y);
        return direction;
    }

    public int tick(App app) {
        if(this.alive){
            this.collision(app);
            if(this.collisionEntity != null){
                if(this.collisionEntity.getClass().getName().equals("gremlins.Gremlin")){
                    System.out.println("Ouchie");
                    this.alive = false;
                }
            }
            
            if(this.collisionBlock!= null){
                if(this.collisionBlock.getClass().getName().equals("gremlins.ExitPortal")){
                    return 20;
                }
            }

            // Border behaviour 
    
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
            if(this.getManaCoolDown() <= app.getWizardCooldown()){
                this.setManaCooldown(this.getManaCoolDown() + 1);
                app.rect(600,680, 100, 5);
                app.fill(0,0,0);
                app.rect(600, 680, (float) (this.getManaCoolDown() * 100/app.getWizardCooldown()), 5);
                app.fill(255,255,255);
            }

        }
        
        else{
            return 10;
        }
        return 0;
        
    }
        
}