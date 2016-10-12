package cs131.pa1.filter.sequential;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Redirect extends SequentialFilter{

	private PrintStream ps;
	private String file;
	
	public Redirect(String file) {
		this.file=file;
	}

	public void process(){
		file=file.substring(2);
		file=SequentialREPL.currentWorkingDirectory+Filter.FILE_SEPARATOR+file;
		File fout = new File(file);
		try{
			ps = new PrintStream(fout);
		}catch (FileNotFoundException e){
		}
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}
	
	@Override
	protected String processLine(String line) {
		ps.println(line);
		isDone();
		return null;
	}
	
	public boolean isDone(){
		if (input.isEmpty()){
			ps.close();
			return true;
		}
		return false;
	}

}
