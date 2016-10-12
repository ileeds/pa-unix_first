package cs131.pa1.filter.sequential;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cs131.pa1.filter.Message;

public class Head extends SequentialFilter {
	
	private String file;
	private int lines=10;
	private String command;
	
	public Head(String line, String command){
		output = new LinkedList<String>();
		file=line;
		this.command=command;
		if (line.charAt(0)=='-'){
			String[]a = line.split(" ");
			lines=Math.abs(Integer.parseInt(a[0]));
			file=a[1];
		}
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	public void process() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String nextLine;
			int i = 0;
			while ((nextLine = reader.readLine()) != null && i<lines) {
				output.add(nextLine);
				i++;
			}
			reader.close();
		} catch (Exception e) {
			System.out.print(Message.FILE_NOT_FOUND.with_parameter(command));
		}
	}
	
}
