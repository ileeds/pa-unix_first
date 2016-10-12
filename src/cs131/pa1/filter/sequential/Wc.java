package cs131.pa1.filter.sequential;

import java.util.LinkedList;

public class Wc extends SequentialFilter {
	
	private int lines=0;
	private int words=0;
	private int chars=0;
	
	public Wc(){
		output = new LinkedList<String>();
	}
	
	@Override
	protected String processLine(String line) {
		lines+=1;
		String trim=line.trim();
		if (!trim.isEmpty()){
			words+=trim.split("\\s+").length;
		}
		chars+=line.length();
		if (this.isDone()){
			output.add(lines+" "+words+" "+chars);
		}
		return null;
	}

}
