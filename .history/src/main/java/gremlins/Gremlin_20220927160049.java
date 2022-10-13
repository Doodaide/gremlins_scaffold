package gremlins;

import processing.core.PImage;

/**
 * Gremlin
 */
public class Gremlin extends Mobile{
    private boolean moved;
    private PImage sprite; 
    public Gremlin(int x, int y, PImage img) {
        super(x, y, 1, 0, 0);
        this.sprite = img;
        this.moved = false;
    }

    @Override
    public void up() {
        this.direction = 0;
        this.yVel = -speed;
        this.yDir = 10;
        
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
    
    public int gremlinAI(int Dir){
        int x = (int) (Math.random() * (4));
        boolean canMove = false;
        int nonIdealDirection = this.getDirection();
        boolean[] travelOptions = this.getSideAV();
        for (boolean b : travelOptions) {
            System.out.print(b + " ");
        }
        System.out.println();
        int prevDir = 5;
        if(Dir == 0){prevDir = 2;}
        else if(Dir == 1){prevDir = 3;}
        else if(Dir == 2){prevDir = 0;}
        else if(Dir == 3){prevDir = 1;}

        while(!canMove){
            System.out.println("Previous direction " + prevDir);
            if(travelOptions[x] && x != prevDir){
                canMove = true;
            }
            if(!travelOptions[x]){
                System.out.println("rerolling");
                x = (int) (Math.random() * (4));
            }
            if(!travelOptions[x] && x == prevDir){
                System.out.println("EdgeCase");
                x = prevDir; 
                canMove = true;
            }
        }
        System.out.println("can go in " + x);
        return x;
    }

    public int draw(App app){
        app.image(this.sprite, this.x, this.y);
        this.collision(app);
        this.gremlinAI(this.direction);
        
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
                this.moved = true;
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

        
        
        //System.out.println(this.getX() + " "+ this.getY());
        return 0;

        // return 0 or 1
    }



    
}