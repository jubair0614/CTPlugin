package utilites;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jubair on 11/27/17.
 * Time 6:42 PM
 */
public class CloneMapper {
	private static CloneMapper cloneMapper = null;
	Map<File, ArrayList<CloneFragment>> mapper;

	public CloneMapper(){

	}

	public static CloneMapper getInstance(){
		if(cloneMapper == null)
			cloneMapper = new CloneMapper();
		return cloneMapper;
	}

	public Map<File, ArrayList<CloneFragment>> getMapper() {
		return mapper;
	}

	public void setMapper(CloneClasses cloneClasses) {
		for (CloneClass cloneClass: cloneClasses.getCloneClasses()) {
			for (CloneFragment singleFragment:
					cloneClass.cloneFiles) {
				if(mapper.containsKey(singleFragment.path)){
					mapper.get(singleFragment.path).add(singleFragment);
				}
				mapper.put(new File(singleFragment.path),  new ArrayList<CloneFragment>());
			}
		}
	}

	public void printMap(){
		System.out.println(mapper.entrySet());
	}
}
