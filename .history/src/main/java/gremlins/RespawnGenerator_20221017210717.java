package gremlins;

import java.util.Random;

/**
 * RespawnGenerator
 */
public class RespawnGenerator {
    private int x; 
    private int y;
    private boolean canRespawnX;
    private boolean canRespawnY;
    private static final Random randomGenerator = new Random(); // random number generator 
    private Entity e;
    public RespawnGenerator(Entity e){
        this.x = e.getX();
        this.y = e.getY();
        this.canRespawnX = false; 
        this.canRespawnY = false; 
        this.e = e;

    }

    public int[] respawnXBlocks(App app){
        int xTest = x; 
        int yTest = y; 

        while(!canRespawnX || !canRespawnY){
            if(!canRespawnX){
                xTest = randomGenerator.nextInt(34) * 20;
                if(xTest == 0){
                    continue;
                }
                else if((xTest >= x + this.e.WIDTH+ 200 || xTest <= x - 200) || 
                ((xTest < x + this.e.WIDTH + 200 && xTest > x - 200) && ((yTest > y + 200 + this.e.HEIGHT) || (yTest < y - 200)))){ // too far left or right. Essentially both coords are valid. 
                    boolean canPass = true;
                    for(Immobile i : app.getMap()){
                        if(i != null){
                            if((i.getX() == xTest && i.getY() == yTest)){
                                canPass = false;
                            }
                        }
                    }
                    if(canPass){
                        //System.out.println("X: " + xTest + " Y: " + yTest);
                        canRespawnX = true;
                        canRespawnY = true;
                    }
                }
            }

            if(!canRespawnY){
                yTest = randomGenerator.nextInt(31) * 20; 
                //System.out.println("Y respawn looping");
                if(yTest == 0){
                    continue;
                }
                else if((yTest >= y + this.e.HEIGHT + 200 || yTest <= y - 200) || 
                (yTest < x + this.e.HEIGHT + 200 && yTest > y - 200) && (xTest > x + this.e.WIDTH + 200 || xTest < x - 200)){
                    boolean canPass = true;
                    for(Immobile i : app.getMap()){
                        if(i != null){
                            if(i.getX() == xTest && i.getY() == yTest){
                                canPass = false;
                            }
                        }
                    }
                    if(canPass){
                        canRespawnX = true;
                        canRespawnY = true;
                    }
                }
            }
            
        }

        int[] toReturn = new int[2];
        toReturn[0] = xTest;
        toReturn[1] = yTest;
        return toReturn;
    }
}