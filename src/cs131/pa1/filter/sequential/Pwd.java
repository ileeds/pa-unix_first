package cs131.pa1.filter.sequential;

import java.util.LinkedList;

public class Pwd extends SequentialFilter{

	public Pwd(){
		output = new LinkedList<String>();
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	public void process(){
		output.add(SequentialREPL.currentWorkingDirectory);
	}

}
