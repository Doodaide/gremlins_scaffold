package gremlins;

import java.util.ArrayList;

/**
 * UpdateArrayList class: a generic helper class used to remove any null entries from an array given. 
 */
public class UpdateArrayList <T> {
    
    /**
     * Constructor
     */
    public UpdateArrayList(){
    }

    /**
     * Update method: deletes any null objects in a given Arraylist (and maintains order)
     * @param toBeUpdated an ArrayList (type T) with potential null entries 
     * @return returns an ArrayList (type T) with null entries removed and order maintained 
     */
    public ArrayList<T> update(ArrayList<T> toBeUpdated){
        ArrayList<T> temp = new ArrayList<T>();
        for(T e : toBeUpdated){
            if(e != null){
                temp.add(e);
            }
        }
        return temp;
    }
}