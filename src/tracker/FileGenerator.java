package tracker;

import java.io.*;

/**
 * Created by jubair on 11/23/17.
 * Time 12:51 AM
 */
public class FileGenerator {
	private static FileGenerator fileGenerator = null;
	public File original;

	public File changed;

	public FileGenerator(){
		this.original = new File("/home/jubair/IdeaProjects/CTPlugin/original.txt");
		this.changed = new File("/home/jubair/IdeaProjects/CTPlugin/changed.txt");
	}

	public static FileGenerator getInstance(){
		if(fileGenerator == null){
			fileGenerator = new FileGenerator();
		}
		return fileGenerator;
	}

	public File getOriginal() {
		return original;
	}

	public File getChanged() {
		return changed;
	}

	public void setOriginal(String text){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(this.original));
			out.write(text);
			out.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setChanged(String text){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(this.changed));
			out.write(text);
			out.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
