package cs131.pa1.filter.sequential;

import java.util.LinkedList;

public class Grep extends SequentialFilter {
	
	private String search;

	public Grep(String search){
		output = new LinkedList<String>();
		this.search=search;
	}
	
	@Override
	protected String processLine(String line) {
		if (line.contains(search)){
			output.add(line);
		}
		return null;
	}

}
