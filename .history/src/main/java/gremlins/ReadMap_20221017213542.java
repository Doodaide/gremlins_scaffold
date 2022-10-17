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
    ArrayList<Character[]> mapContents;
    
    /**
     * Constructor for ReadMap object
     * Each object only contains the ArrayList of characters 
     */
    public ReadMap(){
        this.mapContents = new ArrayList<Character[]>(); // temporary list
    }

    /**
     * Getter method for the singular ArrayList attribute
     * @return
     */
    public ArrayList<Character[]> getMapContents(){
        return this.mapContents;
    }

    public void parseLayout(String layout){
        try {
            if(layout.equals("")){
                throw new FileNotFoundException("File not found");
            }
            File f = new File(layout); // File f = new File("level0.txt"); //
            Scanner sc = new Scanner(f);
            
            while(sc.hasNextLine()){
                Character [] temp = new String(
                    sc.nextLine().trim().toCharArray()).chars()
                    .mapToObj(c-> (char) c).toArray(Character[]::new);
                this.mapContents.add(temp);
                    
            }
    
            sc.close();
            f = null;
        } catch (FileNotFoundException e) {
            System.err.println("Map file could not be located");
        } catch (IndexOutOfBoundsException e){
            System.err.println("Map is too large");
        }
    }
}
