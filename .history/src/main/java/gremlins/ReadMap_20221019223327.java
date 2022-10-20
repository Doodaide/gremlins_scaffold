package gremlins;
import java.util.*;
import java.io.*;
/**
 * ReadMap class: A helper class that reads the map specified in the config file
 */

public class ReadMap {
    /**
     * An ArrayList of Character arrays that essentially mirrors the .txt level layout file. 
     */
    public ArrayList<Character[]> mapContents;
    
    /**
     * Constructor for ReadMap object
     * Each object only contains the ArrayList of characters 
     */
    public ReadMap(){
        this.mapContents = new ArrayList<Character[]>(); // temporary list
    }

    /**
     * Getter method for the singular ArrayList attribute 
     * @return an ArrayList of MapContents that is an arrayList of Character arrays 
     */
    public ArrayList<Character[]> getMapContents(){
        return this.mapContents;
    }

    /**
     * The parseLayout method takes the layout specified in the json configuration file, and reads that. 
     * Afterwards, it parses through it with the scanner, and turns the trimmed string into a char array (primitive datatype)
     * Then, the char (primitive) is mapped to the wrapper class Characters, and put into a new array called temp 
     * Then, temp is added to the mapContents ArrayList, as ArrayLists must contain reference datatypes. 
     * @param layout a string that corresponds to the path of the level configuration file
     * @throws Exception
     */
    public void parseLayout(String layout){
        try {
            if(layout.equals("")){
                throw new FileNotFoundException();
            }
            File f = new File(layout); // File f = new File("level0.txt"); //
            Scanner sc = new Scanner(f);
            
            while(sc.hasNextLine()){
                Character [] temp = new String(
                    sc.nextLine().trim().toCharArray()).chars()
                    .mapToObj(c -> (char) c).toArray(Character[]::new);
                this.mapContents.add(temp);
                    
            }
    
            sc.close();
            f = null;
        } catch (FileNotFoundException e) {
            System.err.println("Map file could not be located");
            throw new FileNotFoundException();
        } catch (IndexOutOfBoundsException e){
            System.err.println("Map is too large");
            throw new Exception();
        }
    }
}
