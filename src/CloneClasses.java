import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubair on 10/18/17.
 */
public class CloneClasses {
    public ArrayList<CloneClass> cloneClasses;

    public CloneClasses(){
        cloneClasses = new ArrayList<>();
    }

    public void setCloneClasses(ArrayList<CloneClass> classes){
        this.cloneClasses = classes;
    }

    public ArrayList<CloneClass> getCloneClasses(){
        return this.cloneClasses;
    }
}
