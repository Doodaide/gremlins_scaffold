package gremlins;

/**
 * RespawnGenerator
 */
public class RespawnGenerator {
    private int x; 
    private int y;
    private boolean canRespawnX;
    private boolean canRespawnY;
    public RespawnGenerator(Entity e){
        this.x = e.getX();
        this.y = e.getY();
        this.canRespawnX = false; 
        this.canRespawnY = false; 
    }

    public int[] respawnXBlocks(int a){

    }
}