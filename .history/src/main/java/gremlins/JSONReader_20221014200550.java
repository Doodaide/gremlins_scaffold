package gremlins;
import processing.data.JSONObject;
import processing.core.PApplet;
import processing.data.JSONArray;
import java.io.*;
import java.util.HashMap;
import processing.core.*;

/**
 * JSONReader class: a helper class that reads a JSON file. 
 * An object of this class has a hashmap attribute which contains the content in the json file. 
 */
public class JSONReader{
    /**
     * Json object 
     */
    private JSONObject json;

    /**
     * The JSONArray that is inside the json file
     */
    private JSONArray levelSpecs; 

    /**
     * Hashmap that contains the contents of the json file
     */
    private HashMap<String, Double> specs; 

    /**
     * String of the level file to be read
     */
    private String layout; 

    /**
     * Constructor for JSON reader 
     * @param path the file path of the JSON object
     */
    public JSONReader(String path){
        this.json = loadJSONObject(new File(path));
        this.specs = new HashMap<String, Double>();
        this.specs.put("lives", (double) (json.getInt("lives")));
        this.layout = "";
    }
    
    /**
     * getter method for the level Specs (a hashmap of key String, containing Double)
     * @return Hashmap key string containing double that contains the level file contents. 
     */
    public HashMap<String, Double> getSpecs(){
        return this.specs;
    }

    /**
     * getter method for the name of the level file 
     * @return returns a string of the name of the level file
     */
    public String getLayout(){
        return this.layout;
    }

    /**
     * Read specs method reads the contents of the json file
     * @param level level is the integer corresponding to the level to read
     * @throws Exception Runtime exception expected if the index or any part of the JSON file is unreadable
     */
    public void readSpecs(int level) throws Exception{
        try {  
            levelSpecs = json.getJSONArray("levels");
            JSONObject lvl = levelSpecs.getJSONObject(level);
            this.layout = lvl.getString("layout");
            this.specs.put("enemy_cooldown", lvl.getDouble("enemy_cooldown"));
            this.specs.put("wizard_cooldown", lvl.getDouble("wizard_cooldown"));
        } catch (Exception e) {
            throw new Exception("Invalid input");
        }
        


    }
}