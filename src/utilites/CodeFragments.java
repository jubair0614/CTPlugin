package utilites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by jubair on 12/18/17.
 * Time 4:12 PM
 */
public class CodeFragments {
	private static CodeFragments codeFragments = null;
	private ArrayList<CodeFragment> fragments;

	public static CodeFragments getInstance(){
		if(codeFragments == null){
			codeFragments = new CodeFragments();
		}
		return codeFragments;
	}

	public void setFragments(ArrayList<CloneClass> cloneClasses) {
		this.fragments = new ArrayList<>();
		for (CloneClass cloneClass:
				cloneClasses) {
			for (CloneFragment singleFragment:
					cloneClass.cloneFiles) {
				String content = "";
				for (int i=singleFragment.startLine; i<=singleFragment.endLine; i++){
					try (Stream<String> lines = Files.lines(Paths.get(singleFragment.path))) {
						String currentLine = lines.skip(i-1).findFirst().get();
						content += currentLine;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				CodeFragment codeFragment = new CodeFragment();
				codeFragment.setFragment(singleFragment, content);
				this.fragments.add(codeFragment);
			}
		}
	}
}
