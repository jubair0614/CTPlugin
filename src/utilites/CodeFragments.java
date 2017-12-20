package utilites;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
				/*for (int i=singleFragment.startLine; i<=singleFragment.endLine; i++){
					try (Stream<String> lines = Files.lines(Paths.get(singleFragment.path))) {
						String currentLine = lines.skip(i-1).findFirst().get();
						content = content.concat(currentLine).concat("\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/
				try(BufferedReader br = new BufferedReader(new FileReader(new File(singleFragment.path)))) {
					int i=1;
					for(String line; (line = br.readLine()) != null; ) {
						if(i >= singleFragment.startLine && i<= singleFragment.endLine){
							content = content.concat(line).concat("\n");
						}
						if(i>singleFragment.endLine){
							break;
						}
						i++;
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				CodeFragment codeFragment = new CodeFragment();
				codeFragment.setFragment(singleFragment, content);
				this.fragments.add(codeFragment);
			}
		}
	}

	public void printFragments(){
		for (CodeFragment codeFragment:
			 this.fragments) {
			System.out.println(codeFragment.cloneFragment.path + "\n" + codeFragment.content);
		}
	}

	public ArrayList<CodeFragment> getCodeFragments(){
		return this.fragments;
	}

	/*public CodeFragment getFragmentWithContent(String path, int position){
		ArrayList<CodeFragment> fragments = CodeFragments.getInstance().fragments;
		for (int i=0; i<fragments.size(); i++){
			if(fragments.get(i).cloneFragment.path.equals(path) && fragments.get(i).cloneFragment.startLine <= position && fragments.get(i).cloneFragment.endLine >= position)
				return fragments.get(i);
		}
		return null;
	}*/

	public CodeFragment getFragmentWithContent(CloneFragment cloneFragment) {
		ArrayList<CodeFragment> fragments = CodeFragments.getInstance().fragments;
		for (int i=0; i<fragments.size(); i++){
			if(fragments.get(i).cloneFragment.equals(cloneFragment))
				return fragments.get(i);
		}
		return null;
	}
}
