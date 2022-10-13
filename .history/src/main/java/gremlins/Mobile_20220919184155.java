package gremlins;

import processing.core.*;
import processing.event.KeyEvent;
public abstract class Mobile extends Entity{

    protected int speed; // travel speed for each entity
    protected int direction; // 0 up, 1 right, 2 down, 3 left 
    protected int xDir, yDir; 
    protected boolean alive;
    protected int xVel, yVel; // essentially allows mob to move
    public boolean collided = false; 
    protected boolean upAV = true, downAV = true, rightAV = true, leftAV = true;
    //private int velMult = 2;

    public Mobile(int x, int y, int speed, int xVel, int yVel) {
        super(x, y, 20, 20);
        this.speed = speed; 
        this.alive = true;
        this.direction = 0; 
        this.xVel = xVel;
        this.yVel = yVel;
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

    public void stop(){
        System.out.println("Stop loop entered");
            System.out.println("x: " + this.x);
            System.out.println("y: "+ this.y);
            System.out.println("direction: " + this.direction);
            System.out.println("xdir: " + this.xDir);
            System.out.println("ydir: " + this.yDir);
            this.xVel = 0;
            this.yVel = 0;
            /* 
            while (this.x % 20 != 0 || this.y % 20 != 0){
                // facing up
                     if(this.yDir == 0){
                         this.setY(this.getY() - 2);
                         //System.out.println("1 up");
                     }
                     // facing right
                     else if (this.xDir == 1){
                         this.setX(this.getX() + 2);
                         //System.out.println("1 right");
                     }
                     // facing down
                     else if (this.yDir == 2){
                         this.setY(this.getY() + 2);
                         //System.out.println("1 down");
                     }
                     // facing left 
                     else if (this.xDir == 3){
                         this.setX(this.getX() - 2);
                         //System.out.println("1 left");
                     }
                 }
                 System.out.println("Stop loop exited");
            
            */     /*

             * 
             */
        
        
    }
    
    public int collision(App app) {
        // !
        if(collides(this.x - 1, this.y, app)){ // top left corner, hit left 
            leftAV = false;
            ;
        }

        if(collides(this.x, this.y - 1, app)){ // top left corner, hit up
            upAV = false;
            ;
        }

        // !
        if(collides(this.x+this.WIDTH + 1, this.y, app)){// Top right corner, hit right 
            rightAV = false;
            ;
        }

        if(collides(this.x+this.WIDTH, this.y - 1, app)){// Top right corner, hit up
            upAV = false;
            ;
        }

        // !
        if(collides(this.x, this.y + this.HEIGHT + 1, app)){// Bottom left corner, hit down
            downAV = false;
            ;
        }
        if(collides(this.x - 1, this.y + this.HEIGHT, app)){// Bottom left corner, hit left
            leftAV = false;
            ;
        }

        // !
        if(collides(this.x + this.WIDTH + 1, this.y + this.HEIGHT + 1, app)){// Bottom right corner, hit down
            downAV = false;
            ;
        }
        if(collides(this.x + this.WIDTH + 1, this.y + this.HEIGHT, app)){// Bottom right corner, hit right
            ;
        }
        
/*

// left side
        if(collides(this.x - 1, this.y, app)// top left corner
            ||
            collides(this.x - 1, this.y+this.HEIGHT - 1, app) // bottom left corner 
            ){
                System.out.println("left side bumped");
                leftAV = false;
                xStop();
            }

        if(!collides(this.x - 1, this.y, app)// top left corner
            &&
            !collides(this.x - 1, this.y+this.HEIGHT - 1, app) // bottom left corner 
            ){
                System.out.println("left side clear");
                leftAV = true;
            }

        

        if(collides(this.x , this.y+this.HEIGHT + 1, app) // bottom left corner 
            ||
            collides(this.x+this.WIDTH - 1, this.y+this.HEIGHT + 1, app) // bottom right corner
            ){
                System.out.println("bottom side bumped");
                downAV = false;
                yStop();
            }  
        
        if(!collides(this.x , this.y+this.HEIGHT + 1, app) // bottom left corner 
            &&
            !collides(this.x+this.WIDTH - 1, this.y+this.HEIGHT + 1, app) // bottom right corner
            ){
                System.out.println("bottom side clear");
                downAV = true;
            }       


        

        if(collides(this.x+this.WIDTH + 1, this.y + 1, app)// Top right corner 
            ||
            collides(this.x+this.WIDTH + 1, this.y+this.HEIGHT, app) // bottom right corner
            ){
                System.out.println("right side bumped");
                rightAV = false;
                xStop();
            }    
        
        if(!collides(this.x+this.WIDTH + 1, this.y + 1, app)// Top right corner 
            &&
            !collides(this.x+this.WIDTH + 1, this.y+this.HEIGHT, app) // bottom right corner
            ){
                System.out.println("right side clear");
                rightAV = true;
            }       

        
        
        if (collides(this.x+this.WIDTH, this.y - 1, app)// Top right corner 
            ||
            collides(this.x + 1, this.y - 1, app)// top left corner
            ){
               System.out.println("top side bumped");   
               upAV = false;  
               yStop();
            }
        
        if (!collides(this.x+this.WIDTH, this.y - 1, app)// Top right corner 
            &&
            !collides(this.x + 1, this.y - 1, app)// top left corner
            ){
               System.out.println("top side clear");   
               upAV = true;  
            }    
        else{
            ;
        }




*/        
        
        
        return 0;
    }

    public boolean collides(int xCoord, int yCoord, App app) {
        for (Immobile i : app.mapToDraw) { // i = block
            // ! THINK HERE FOR COLLISION RULES
            if (
                (
                (xCoord >= i.getX()
                && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY()
                && yCoord <= i.getY() + i.HEIGHT)
                )
                )
                {
                //System.out.println("bonk");
                return true;
            }
        }
        
        return false;
    }


}