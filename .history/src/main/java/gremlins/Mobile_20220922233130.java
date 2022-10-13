package gremlins;

public abstract class Mobile extends Entity{

    protected int speed; // travel speed for each entity
    protected int direction; // 0 up, 1 right, 2 down, 3 left 
    protected int xDir, yDir; 
    protected boolean alive; // whether the mobile should be displayed on the next frame. 
    protected int xVel, yVel; // essentially allows mob to move
    public Immobile[] arr = new Immobile[4];  // blocks collided
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
        for (int i = 0; i < this.arr.length; i++) {
            this.arr[i] = null;
        }
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
    
    public int verticalCollision(App app){
        int toReturn = 0; // no collision 
    }

    public int collision(App app) { // mob to block collision 
        // ! Left side collision
        int toReturn = 0;
        int tempCounter = 0;
        this.leftAV = true; 
        this.rightAV = true; 
        this.upAV = true; 
        this.downAV = true;

        if(collides(this.getX(), this.getY() + 2, app) || // top left corner 
            collides(this.getX(), this.getY() + this.HEIGHT - 2, app)){ // bottom left corner, hit left 
            leftAV = false;
            toReturn = 1;
            System.out.println("left 3");
        }

        else if(!collides(this.getX(), this.getY() + 2, app) &&
            !collides(this.getX(), this.getY() + this.HEIGHT - 2, app)){ // top left corner, hit left 
            leftAV = true;
            toReturn = 0;
            //System.out.println("Left side free");
        }

        // ! Right side collision 
        if(collides(this.getX()+this.WIDTH, this.getY() + 2, app) || // Top right corner, hit right 
            collides(this.getX() + this.WIDTH, this.getY() + this.HEIGHT - 2, app)){
            rightAV = false;
            toReturn = 1;
            System.out.println("right 1");
        }

        else if(!collides(this.getX() +this.WIDTH, this.getY() + 2, app) && // Top right corner, hit right 
            !collides(this.getX() + this.WIDTH, this.getY() + this.HEIGHT - 2, app)){
            rightAV = true;
            toReturn = 0;
            //System.out.println("Right side free");
        }
        // ! Upper side collision
        if(collides(this.getX() + 2, this.getY(), app) || // top left corner, hit up
            collides(this.getX()+this.WIDTH - 2, this.getY(), app)){ // top right cornder hit up 
            upAV = false;
            toReturn = 1;
            System.out.println("up 0");
        }

        else if(!collides(this.getX() + 2, this.getY(), app) && 
            !collides(this.getX()+this.WIDTH - 2, this.getY(), app)){ // top left corner, hit up
            upAV = true;
            toReturn = 0;
            //System.out.println("Up side free");
        }

        // ! Lower side collision 
        if(collides(this.getX() + 2, this.getY() + this.HEIGHT, app) || // Bottom left corner, hit down
            collides(this.getX() + this.WIDTH - 2, this.getY() + this.HEIGHT, app)){ // Bottom right corner, hit down
            downAV = false;
            toReturn = 1;
            System.out.println("bottom 2");
            
        }

        else if(!collides(this.getX() + 2, this.getY() + this.HEIGHT, app) && 
            !collides(this.getX() + this.WIDTH - 2, this.getY() + this.HEIGHT, app)){// Bottom left corner, hit down
            downAV = true;
            toReturn = 0;
            //System.out.println("Bottom side free");
        }
        
        return toReturn;
    }

    public boolean collides(int xCoord, int yCoord, App app) {
        int dir = this.direction;
        for (Immobile i : app.mapToDraw) { // i = block
            // ! THINK HERE FOR COLLISION RULES
            if ((xCoord >= i.getX() && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY() && yCoord <= i.getY() + i.HEIGHT)){
                
                  
                if(!upAV && dir == 0){
                    arr[0] = i;
                }

                if(!rightAV && dir == 1){
                    arr[1] = i;
                }

                if(!downAV && dir == 2){
                    arr[2] = i;
                }

                if(!leftAV && dir == 3){
                    arr[3] = i;
                }
                
                for (Immobile immobile : arr) {
                    System.out.println(immobile);
                }
                
                System.out.println();
                this.collisionBlock = arr[dir]; // finds the object the mobile collided with. 
                //System.out.println("bruh" + this.collisionBlock);
                // ! this is the current problem. The code is saving the "last registered hit", which happens to be the 
                //System.out.println("bruh " + i.getX() + " "+ i.getY());
                return true;
            }
        }
        this.collisionBlock = null;
        return false;
    }


}