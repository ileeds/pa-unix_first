package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.IOException;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Cd extends SequentialFilter {
	
	private String direct;
	
	public Cd(String line, String subCommand){
		String cwd=SequentialREPL.currentWorkingDirectory;
		File path=new File(line);
		try {
			direct = new File(cwd+FILE_SEPARATOR+path.getPath()).getCanonicalPath();
		} catch (Exception e) {
			System.out.println(Message.DIRECTORY_NOT_FOUND.with_parameter(subCommand));
		}
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	public void process(){
		SequentialREPL.currentWorkingDirectory=direct;
	}
	
}
