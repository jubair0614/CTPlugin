import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubair on 10/18/17.
 */
public class CloneClass {
    public int classId;
    public int numOfClones;
    public int numOfLines;
    public int similarity;
    public ArrayList<CloneFragment> cloneFiles;

    public CloneClass(){
        this.classId = 0;
        this.numOfClones = 0;
        this.similarity = 0;
        this.numOfLines = 0;
        this.cloneFiles = new ArrayList<>();
    }

    public ArrayList<CloneFragment> getCloneFiles(){
        return this.cloneFiles;
    }

    public ArrayList<String> getCloneFilePaths(){
        ArrayList<String> paths = new ArrayList<>();
        for (CloneFragment fragment:
             this.cloneFiles) {
            String path = fragment.path;
            paths.add(path);
        }
        return paths;
    }
}
