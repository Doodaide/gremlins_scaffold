package gremlins;

import java.util.ArrayList;

/**
 * UpdateArrayList
 */
public class UpdateArrayList <T> {
    public UpdateArrayList(){
    }

    public ArrayList<T> update(ArrayList<T> toBeUpdated){
        ArrayList<T> temp = new ArrayList<T>();
        for(T e : toBeUpdated){
            if(e != null){
                temp.add(e);
            }
        }
        return temp;
    }

    public ArrayList<> nullUpdate(ArrayList<ArrayList<Slimeball>> toUpdate){
        ArrayList<ArrayList<T>> temp = new ArrayList<ArrayList<T>>();
        for(ArrayList<Slimeball> e : toUpdate){
            if(e.size() != 0){
                temp.add(e);
            }
        }
        return temp;
    }
}