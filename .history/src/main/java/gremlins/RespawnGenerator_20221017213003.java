package gremlins;
import java.util.Random;
/**
 * RespawnGenerator class: a helper class that uses the Random library to generate a set of random coordinates 
 * That are at least a 10 block radius away from a specified entity. 
 */
public class RespawnGenerator {
    /**
     * x coordinate of the entity at the epicentre 
     */
    private int x; 

    /**
     * y coordinate of the entity at the epicentre 
     */
    private int y;

    /**
     * Whether the x coordinate randomly generated is far enough from the entity's x coordinate 
     */
    private boolean canRespawnX;

    /**
     * Whether the y coordinate randomly generated is far enough from the entity's y coordinate
     */
    private boolean canRespawnY;

    /**
     * Static random generator object, that produces random numbers 
     */
    private static final Random randomGenerator = new Random(); // random number generator 

    /**
     * Entity e the entity at the epicentre, that the random generator will attempt to find coordinates that are 10 blocks away from checks
     */
    private Entity e;

    /**
     * Constructor for RandomGenerator Object 
     * @param e 
     */
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
                        canRespawnX = true;
                        canRespawnY = true;
                    }
                }
            }

            if(!canRespawnY){
                yTest = randomGenerator.nextInt(31) * 20; 
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