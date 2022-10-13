package gremlins;

import java.util.ArrayList;

import processing.core.PImage;

/**
 * Gremlin
 */
public class Gremlin extends Mobile{
    private PImage sprite; 
    private ArrayList<SlimeBall> availableSlimeBalls;
    private int slimeCooldown;
    private PImage slimeballImg;

    public Gremlin(int x, int y, PImage img, PImage slimeball) {
        super(x, y, 1, 0, 0);
        this.sprite = img;
        this.availableSlimeBalls = new ArrayList<SlimeBall>();
        this.slimeCooldown = 0;
        this.slimeballImg = slimeball;
    }

    public ArrayList<SlimeBall> getAvailableSlimeballs(){
        return this.availableSlimeBalls;
    }

    public void setAvailableSlimeballs(ArrayList<SlimeBall> arr){
        this.availableSlimeBalls = arr;
    }

    public void setSlimeCooldown(int x){
        this.slimeCooldown = x;
    }

    public int getSlimeCoolDown(){
        return this.slimeCooldown;
    }

    public ArrayList<SlimeBall> shootSlimeBall(){
        this.availableSlimeBalls.add(new SlimeBall(this.x, this.y, this.slimeballImg));
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

    public void slimeballMechanics(App app){
        //System.out.println(this.availableSlimeBalls.size());
        for(SlimeBall s : this.availableSlimeBalls){
            if(s != null){
                int collision = s.tick(app);
                s.draw(app);

                // Hits entity 
                if((collision == 1) && s.collisionEntity != null){
                    //System.out.println("Collision entity " + s.collisionEntity);
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

    @Override
    public void up() {
        this.direction = 0;
        this.yVel = -speed;
        this.yDir = 0;
        
    }

    @Override
    public void right() {
        this.direction = 1;
        this.xVel = speed;
        this.xDir = 1;
    }

    @Override
    public void down() {
        this.direction = 2;
        this.yVel = speed; 
        this.yDir = 2;
    }

    @Override
    public void left() {
        this.direction = 3;
        this.xVel = -speed;
        this.xDir = 3;
    }

    public int gremlinAI(App app){
        //System.out.println(this.getFrameCounter());
        //System.out.println("Entered AI phase");
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

    public int draw(App app){
        if(this.alive){
            app.image(this.sprite, this.x, this.y);
        }
        return this.direction;
        
    }

    @Override
    public int tick(App app) {
        if(app.getFreezeActive()){

        }
        
        
        return 0;
    }
}