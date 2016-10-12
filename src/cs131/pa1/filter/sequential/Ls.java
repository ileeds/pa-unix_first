package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.LinkedList;

public class Ls extends SequentialFilter {

	public Ls(){
		output = new LinkedList<String>();
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	public void process(){
		File direct = new File (SequentialREPL.currentWorkingDirectory);
		String[]contents = direct.list();
		if (contents==null){
			return;
		}
		for (String content: contents){
			output.add(content);
		}
	}

}
