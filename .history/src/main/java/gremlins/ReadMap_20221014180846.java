package gremlins;
import java.util.*;
import java.io.*;

/**
 * ReadMap
 */
public class ReadMap {
    ArrayList<Character[]> mapContents;
    
    
    public ReadMap(){
        this.mapContents = new ArrayList<Character[]>(); // temporary list
    }

    public ArrayList<Character[]> parseLayout(File f){

    }

    // * Parse file
    try {
        if(layout.equals("")){
            throw new FileNotFoundException("Bruh moment");
        }
        File f = new File(layout); // File f = new File("level0.txt"); //
        Scanner sc = new Scanner(f);
        
        while(sc.hasNextLine()){
            Character [] temp = new String(
                sc.nextLine().trim().toCharArray()).chars()
                .mapToObj(c-> (char) c).toArray(Character[]::new);
            mapContents.add(temp);
                
        }

        sc.close();
        f = null;
    } catch (FileNotFoundException e) {
        System.out.println("bruh moment");
    } catch (IndexOutOfBoundsException e){
        System.out.println("map too big bro");
    }
}
