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

    public ArrayList<ArrayList<SlimeBall>> slimeBallUpdate(ArrayList<ArrayList<SlimeBall>> toUpdate){
        ArrayList<ArrayList<SlimeBall>> temp = new ArrayList<ArrayList<SlimeBall>>();
        if(toUpdate)
        for(ArrayList<SlimeBall> e : toUpdate){
            if(e.size() != 0){
                temp.add(e);
            }
        }
        return temp;
    }
}