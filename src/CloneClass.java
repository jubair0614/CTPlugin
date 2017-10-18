import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubair on 10/18/17.
 */
public class CloneClass {
    public int classId;
    public int numOfClones;
    public int similarity;
    public ArrayList<File> cloneFiles;

    public CloneClass(){
        this.classId = 0;
        this.numOfClones = 0;
        this.similarity = 0;
        this.cloneFiles = new ArrayList<>();
    }

    public ArrayList<File> getCloneFiles(){
        return this.cloneFiles;
    }

    public ArrayList<String> getCloneFilePathes(){
        ArrayList<String> paths = new ArrayList<>();
        for (File file:
             this.cloneFiles) {
            String path = file.getPath();
            paths.add(path);
        }
        return paths;
    }

    public ArrayList<String> getCloneFileNames(){
        ArrayList<String> names = new ArrayList<>();
        for (File file:
                this.cloneFiles) {
            String name = file.getName();
            names.add(name);
        }
        return names;
    }


}
