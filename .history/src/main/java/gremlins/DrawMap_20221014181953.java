package gremlins;

import java.util.ArrayList;

/**
 * DrawMap
 */
public class DrawMap {

    private ArrayList<Immobile> mapToDraw;
    private ArrayList<Gremlin> gArray; 
    private int xCounter = 0; 
    private int yCounter = 0; 
    private int incrementer = 10; 
    int wizStartX = 0, wizStartY = 0;
    public DrawMap(){
        this.mapToDraw = new ArrayList<Immobile>();
        this.gArray = new ArrayList<>()
    }
}