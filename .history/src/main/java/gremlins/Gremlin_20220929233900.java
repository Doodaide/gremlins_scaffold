package gremlins;

import processing.core.PImage;

/**
 * Gremlin
 */
public class Gremlin extends Mobile{
    private boolean moved;
    private PImage sprite; 
    private int frameCounter; 
    public Gremlin(int x, int y, PImage img) {
        super(x, y, 1, 0, 0);
        this.sprite = img;
        this.moved = false;
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
    
    /*
     * boolean canMove = false;
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
        if(this.getX() % 20 == 0 && this.getX() % 20 == 0){
            int x = (int) (Math.random() * (4));
            while(!canMove){
                System.out.println("Previous direction " + prevDir);
                if(travelOptions[x] && x != prevDir){
                    System.out.println("Enters in first");
                    int temp = this.direction;
                    this.direction = x;
                    if(this.getSideAV()[x]){
                        this.direction = temp;
                        canMove = true;
                    }
                    
                }
                if(!travelOptions[x] || x != prevDir){
                    System.out.println("Enters in second");
                    x = (int) (Math.random() * (4));
                }
                if(!travelOptions[x] || x == prevDir){
                    System.out.println("Enters in third");
                    x = prevDir; 
                    canMove = true;
                }
            }
            System.out.println("can go in " + x);
            return x;
        }
        else{
            return x;
        }
     */

    public int gremlinAI(App app){
        //System.out.println(this.getFrameCounter());
        int originalDirection = this.getDirection();
        boolean canMove = false;
        boolean[] travelOptions = this.getSideAV();
        boolean[] feasibleDirections = new boolean[4];
        for(int i = 0; i < 4; i++){
            feasibleDirections[i] = travelOptions[i];
        }
        
        int prevLoc = 5;
        if(this.direction == 0){prevLoc = 2;}
        else if(this.direction == 1){prevLoc = 3;}
        else if(this.direction == 2){prevLoc = 0;}
        else if(this.direction == 3){prevLoc = 1;}
        
        int n = 5;

        while(!canMove){
            n = (int) (Math.random() * (4)); // generate random direction
            //System.out.println("random number generated " + x);
            if(!feasibleDirections[0] && !feasibleDirections[1] && !feasibleDirections[2] && !feasibleDirections[3]){
                System.out.println("Bruh moment");
                n = prevLoc;
            }

            else if(feasibleDirections[n]){
                this.setDirection(n);
                if(this.collision(app) == 1){
                    System.out.println(this + "Collided");
                }
                travelOptions = this.getSideAV();

                if(travelOptions[n]){
                    canMove = true; // return x;
                    //return x;
                }
                for(int i = 0; i < 4; i++){
                    if(!travelOptions[i]){
                        feasibleDirections[i] = travelOptions[i];
                    }
                }
                
            }
        }
        this.setDirection(originalDirection);
        //System.out.println("Direction to go" + x);
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
        //this.gremlinAI(this.direction);
        /*
         * int x = gArray.get(i).gremlinAI(gArray.get(i).getDirection());
                x = 3;
                if(x == 0){
                    gArray.get(i).up();
                }
                else if(x == 1){
                    gArray.get(i).right();
                }
                else if(x == 2){
                    gArray.get(i).down();
                }
                else if(x == 3 && universalCounter < 10){
                    gArray.get(i).left();
                    universalCounter+=1;
                    
                }
         */
        // Border behaviour 

            int toGo = this.gremlinAI(app);
        System.out.println("Direction to go: " + toGo);
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

            this.incFrameCounter();
            if(this.getX() % 20 == 0 && this.getY() % 20 == 0){
                this.setFrameCounter(0);
            } 
            
        }
        
        
        return 0;
    }



    
}