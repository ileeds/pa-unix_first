package cs131.pa1.filter.sequential;

import java.util.List;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class SequentialREPL {

	static String currentWorkingDirectory;
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner console = new Scanner(System.in);
		SequentialCommandBuilder build = new SequentialCommandBuilder();
		System.out.print(Message.WELCOME);
		System.out.print(Message.NEWCOMMAND);
		String command = console.nextLine();
		while (!command.equals("exit")){
			List<SequentialFilter> a=null;
			a = build.createFiltersFromCommand(command);
			System.out.print(Message.NEWCOMMAND);
			command = console.nextLine();
		}
		console.close();
		System.out.print(Message.GOODBYE);
	}

}
