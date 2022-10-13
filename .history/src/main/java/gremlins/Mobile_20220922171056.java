package gremlins;

public abstract class Mobile extends Entity{

    protected int speed; // travel speed for each entity
    protected int direction; // 0 up, 1 right, 2 down, 3 left 
    protected int xDir, yDir; 
    protected boolean alive; // whether the mobile should be displayed on the next frame. 
    protected int xVel, yVel; // essentially allows mob to move

    public Immobile collisionBlock; // block the mobile has collided with. Mainly used with projectiles 
    protected boolean upAV = true, downAV = true, rightAV = true, leftAV = true;

    public Mobile(int x, int y, int speed, int xVel, int yVel) {
        super(x, y, 20, 20);
        this.speed = speed; 
        this.alive = true;
        this.direction = 0; 
        this.xVel = xVel;
        this.yVel = yVel;
        this.collisionBlock = null;
    }

    public int getSpeed(){
        return this.speed;
    }

    public int getDirection(){
        return this.direction; 
    }

    public void setDirection(int dir){
        this.direction = dir;
    }

    // Custom movement instructions 

    public abstract void up();

    public abstract void right();

    public abstract void down(); 

    public abstract void left();

    /* Consider how to modify this, so that if the mobile is not on a multiple of 20
     * it will continue moving until it stops on a multiple of 20. 
     */ 

    // ! FIX STOP MECHANICS
     
    public void xStop(){
        this.xVel = 0;
    }
    public void yStop(){
        this.yVel = 0;
    }

    // ! Tester function
    public void stop(){
            //System.out.println("Stop loop entered");
            //System.out.println("x: " + this.x);
            //System.out.println("y: "+ this.y);
            //System.out.println("direction: " + this.direction);
            //System.out.println("xdir: " + this.xDir);
            //ystem.out.println("ydir: " + this.yDir);
            this.xVel = 0;
            this.yVel = 0;
    }
    

    public int collision(App app) { // mob to block collision 
        // ! Left side collision
        int toReturn = 0;

        if(collides(this.x, this.y + 2, app) || // top left corner 
            collides(this.x, this.y + this.HEIGHT - 2, app)){ // bottom left corner, hit left 
            leftAV = false;
            toReturn = 1;
            //System.out.println("Left side");
        }

        else if(!collides(this.x, this.y + 2, app) &&
            !collides(this.x, this.y + this.HEIGHT - 2, app)){ // top left corner, hit left 
            leftAV = true;
            toReturn = 0;
            //System.out.println("Left side free");
        }

        // ! Upper side collision
        if(collides(this.x + 2, this.y - 1, app) || // top left corner, hit up
            collides(this.x+this.WIDTH - 2, this.y - 1 , app)){ // top right cornder hit up 
            upAV = false;
            toReturn = 1;
            //System.out.println("Up side");
        }

        else if(!collides(this.x + 2, this.y - 1, app) && 
            !collides(this.x+this.WIDTH - 2, this.y - 1, app)){ // top left corner, hit up
            upAV = true;
            toReturn = 0;
            //System.out.println("Up side free");
        }

        // ! Right side collision 
        if(collides(this.x+this.WIDTH, this.y + 2, app) || // Top right corner, hit right 
            collides(this.x + this.WIDTH, this.y + this.HEIGHT - 2, app)){
            rightAV = false;
            toReturn = 1;
            //System.out.println("Right side");
        }

        else if(!collides(this.x+this.WIDTH, this.y + 2, app) && // Top right corner, hit right 
            !collides(this.x + this.WIDTH, this.y + this.HEIGHT - 2, app)){
            rightAV = true;
            toReturn = 0;
            //System.out.println("Right side free");
        }

        // ! Lower side collision 
        if(collides(this.x + 2, this.y + this.HEIGHT, app) || // Bottom left corner, hit down
            collides(this.x + this.WIDTH - 2, this.y + this.HEIGHT, app)){ // Bottom right corner, hit down
            downAV = false;
            toReturn = 1;
            //System.out.println("Bottom side");
        }

        else if(!collides(this.x + 2, this.y + this.HEIGHT, app) && 
            !collides(this.x + this.WIDTH - 2, this.y + this.HEIGHT, app)){// Bottom left corner, hit down
            downAV = true;
            toReturn = 0;
            //System.out.println("Bottom side free");
        }
        return toReturn;
    }

    public boolean collides(int xCoord, int yCoord, App app) {
        Immobile[] arr = new Immobile[4];  
        for (Immobile i : app.mapToDraw) { // i = block
            // ! THINK HERE FOR COLLISION RULES
            if ((xCoord >= i.getX() && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY() && yCoord <= i.getY() + i.HEIGHT)){
                
                if(!upAV){
                    arr[0] = i;
                }
                else if (upAV){
                    arr[0] = null;
                }

                if(!rightAV){
                    arr[1] = i;
                }
                else if (rightAV){
                    arr[1] = null;
                }

                else if(!downAV){
                    arr[2] = i;
                }
                else if (downAV){
                    arr[2] = null;
                }

                else if(!leftAV){
                    arr[3] = i;
                }
                else if (leftAV){
                    arr[3] = null;
                }
                for (Immobile immobile : arr) {
                    System.out.println(immobile);
                }
                System.out.println();
                this.collisionBlock = arr[this.direction]; // finds the object the mobile collided with. 
                // ! this is the current problem. The code is saving the "last registered hit", which happens to be the 
                //System.out.println("bruh " + i.getX() + " "+ i.getY());
                return true;
            }
        }
        this.collisionBlock = null;
        return false;
    }


}