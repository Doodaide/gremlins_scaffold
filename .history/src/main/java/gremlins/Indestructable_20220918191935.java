package gremlins;

import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random; 


/**
 * Indestructable
 */
public class Indestructable extends Immobile{
    private PImage texture = loadImage(URLDecoder.decode(this.getClass().getResource("wizard2.png").getPath(), StandardCharsets.UTF_8.toString()));
    public Indestructable(int x, int y) {
        super(x, y, );
        
    }

    

    
}