package views;

import java.util.ArrayList;

/**
 * Created by peacefrog on 12/20/17.
 * Time 10:37 AM
 */
public interface ClonedCodeViewUpdate {

	void changedSelectedFragment(String changedText);
	void changeCloneList(ArrayList<String[]> changeList);
}
