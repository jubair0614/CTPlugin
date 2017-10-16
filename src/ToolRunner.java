/**
 * Created by jubair on 10/6/17.
 */
public class ToolRunner {
	private String scriptPath;
	public String tool;

	public ToolRunner(){
		this.scriptPath = null;
		this.tool = null;
	}

	public void setScriptPath(String path){
		this.scriptPath = path;
	}

	public String getScriptPath(){
		return this.scriptPath;
	}

	public void toolChooser(String tool){
		this.tool = tool;
		String toolName = tool.toLowerCase();
		switch (toolName){
			case "nicad-4.0":
				System.out.println("nicad-4.0");
				this.scriptPath = "nicadPath";
				break;
			case "sourcerercc":
				System.out.println("SourcererCC");
				this.scriptPath = "sourcererccPath";
				break;
			default:
				System.out.println("nicad-4.0");
				this.scriptPath = "nicadPath";
		}
	}

	public void runScript(){
		System.out.println("Tool " + this.tool + "'s script is running");
	}
}
