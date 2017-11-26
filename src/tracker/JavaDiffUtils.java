package tracker;

import java.io.*;
import java.util.*;
import difflib.*;

public class JavaDiffUtils {
	// Helper method for get the file content
	private static List<String> fileToLines(String filename) {
		List<String> lines = new LinkedList<String>();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	public void run() {

		List<String> original = fileToLines(FileGenerator.getInstance().getOriginal().getPath());
		List<String> revised  = fileToLines(FileGenerator.getInstance().getChanged().getPath());

		StringBuilder sb = new StringBuilder();
		DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
		DiffRowGenerator dfg = builder.build();
		List<DiffRow> rows = dfg.generateDiffRows(original, revised);
		for (final DiffRow diffRow : rows)
		{
			if (diffRow.getTag().equals(DiffRow.Tag.INSERT)) // or use switch*
			{
				sb.append("<div class='insert'>\n" + diffRow.getNewLine() + "</div>");
			}
			else if (diffRow.getTag().equals(DiffRow.Tag.DELETE))
			{
				sb.append("<div class='del'>\n" + diffRow.getOldLine() + "</div>");
			}
			else if (diffRow.getTag().equals(DiffRow.Tag.CHANGE))
			{
				sb.append("<div class='mod'>\n");
				sb.append("\t<div class='mc'>" + diffRow.getOldLine() + "</div>\n");
				sb.append("\t<div class='mc'>" + diffRow.getNewLine() + "</div>\n");
				sb.append("</div>\n");
			}
		}

		System.out.println(sb);
	}
}