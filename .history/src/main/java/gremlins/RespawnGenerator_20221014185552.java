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
    public static final Random randomGenerator = new Random(); // random number generator 
    public RespawnGenerator(Entity e){
        this.x = e.getX();
        this.y = e.getY();
        this.canRespawnX = false; 
        this.canRespawnY = false; 
    }

    public int[] respawnXBlocks(int a){
        int xTest = x; 
        int yTest = y; 

        while(!canRespawnX || !canRespawnY){
            if(!canRespawnX){
                xTest = randomGenerator.nextInt(34) * 20;
                //System.out.println("x Respawn looping");
                if(xTest == 0){
                    continue;
                }
                else if((xTest >= x + w.WIDTH+ 200 || xTest <= wX - 200) || 
                ((xTest < wX + w.WIDTH + 200 && xTest > wX - 200) && ((yTest > wY + 200 + w.HEIGHT) || (yTest < wY - 200)))){ // too far left or right. Essentially both coords are valid. 
                    boolean canPass = true;
                    for(Immobile i : mapToDraw){
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
                else if((yTest >= wY + w.HEIGHT + 200 || yTest <= wY - 200) || 
                (yTest < wY + w.HEIGHT + 200 && yTest > wY - 200) && (xTest > wX + w.WIDTH + 200 || xTest < wX - 200)){
                    boolean canPass = true;
                    for(Immobile i : mapToDraw){
                        if(i != null){
                            if(i.getX() == xTest && i.getY() == yTest){
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
            
        }

        int[] toReturn = new int[2];
        toReturn[0] = xTest;
        toReturn[1] = yTest;
        return toReturn;
    }
}