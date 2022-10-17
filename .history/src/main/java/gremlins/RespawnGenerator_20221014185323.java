package gremlins;

/**
 * RespawnGenerator
 */
public class RespawnGenerator {
    private int x; 
    private int y;
    
    public RespawnGenerator(Entity e){
        this.x = e.getX();
        this.y = e.getY();
    }
}