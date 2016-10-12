package cs131.pa1.filter.sequential;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.Filter;

public class SequentialCommandBuilder {
	
	private final static Set <String> pipedInput = new HashSet <String> (Arrays.asList("Grep", "Wc", "Redirect"));
	private final static Set <String> pipedOutput = new HashSet <String> (Arrays.asList("Pwd", "Ls", "Head", "Grep", "Wc"));
	private static boolean go=true;
	private static String prevCommand=null;
	
	public static List<SequentialFilter> createFiltersFromCommand(String command) {
		go=true;
		SequentialFilter finalFilter = determineFinalFilter(command);
		command = adjustCommandToRemoveFinalFilter(command);
		if (go==false){
			return null;
		}
		String[]subCommands=command.split("\\|");
		List<SequentialFilter> filters = new ArrayList();
		for (String a:subCommands){
			SequentialFilter b = constructFilterFromSubCommand(a.trim());
			if (go==false){
				return null;
			}
			filters.add(b);
			checkPiping(filters, a);
			prevCommand=a;
		}
		if (finalFilter!=null){
			filters.add(finalFilter);
		}
		linkFilters(filters);
		if (go==false){
			return null;
		}
		for (SequentialFilter a:filters){
			a.process();
		}
		while (filters.get(filters.size()-1).output!=null && !filters.get(filters.size()-1).output.isEmpty()){
			System.out.println(filters.get(filters.size()-1).output.remove());
		}
		return filters;
	}
	
	private static void checkPiping(List<SequentialFilter> filters, String a) {
		if (!pipedOutput.contains(filters.get(0).getClass().getSimpleName()) && filters.size()>1){
			System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(prevCommand));
			go=false;
			return;
		}
		if (pipedInput.contains(filters.get(0).getClass().getSimpleName())){
			System.out.print(Message.REQUIRES_INPUT.with_parameter(a));
			go=false;
		}
		for (int i=0; i<filters.size()-1; i++){
			if (!pipedInput.contains(filters.get(i+1).getClass().getSimpleName())){
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(a));
				go=false;
			}else if(!pipedOutput.contains(filters.get(i).getClass().getSimpleName().toString())){
				System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(prevCommand));
				go=false;
			}
		}
		return;
	}

	private static SequentialFilter determineFinalFilter(String command){
		if (command.contains(">")){
			if (command.substring(command.lastIndexOf(">")+1).equals("")){
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(">"));
				go=false;
				return null;
			}
			return new Redirect(command.substring(command.lastIndexOf(">")));
		}else{
			return null;
		}
	}
	
	private static String adjustCommandToRemoveFinalFilter(String command){
		if (command.contains(">")){
			if (command.charAt(0)=='>'){
				System.out.print(Message.REQUIRES_INPUT.with_parameter(command));
				go=false;
				return null;
			}
			if (command.substring(command.indexOf(">")+1).contains(">") || command.substring(command.indexOf(">")).contains("\\|")){
				System.out.println(Message.CANNOT_HAVE_OUTPUT.with_parameter(command));
				go=false;
				return null;
			}
			return command.substring(0, command.lastIndexOf(">")-1);
		}else{
			return command;
		}
	}
	
	private static SequentialFilter constructFilterFromSubCommand(String subCommand) {
		String arr[] = subCommand.split(" ", 2);
		String filter=arr[0];
		if (filter.equals("head")){
			if (arr.length==1){
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				go=false;
				return null;
			}
			if (arr[1].charAt(0)=='-'){
				try{
					String arrNew[]=arr[1].split(" ", 2);
					Integer.parseInt(arrNew[0].substring(1));
					if (arrNew.length==1){
						System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
						go = false;
						return null;
					}
				}catch (Exception e){
					System.out.print(Message.INVALID_PARAMETER.with_parameter(subCommand));
					go = false;
					return null;
				}
			}
			return new Head(arr[1], subCommand);
		}else if (filter.equals("pwd")){
			return new Pwd();
		}else if (filter.equals("ls")){
			return new Ls();
		}else if (filter.equals("cd")){
			try{
				arr[1].equals("test");
			}catch(Exception e){
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				go=false;
				return null;
			}
			File path=new File(arr[1]);
			if (path.exists()){
			}else{
				System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(subCommand));
				go=false;
				return null;
			}
			return new Cd(arr[1], subCommand);
		}else if (filter.equals("grep")){
			try{
				return new Grep(arr[1]);
			}catch (Exception e){
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				go = false;
				return null;
			}
		}else if (filter.equals("wc")){
			try{
				return new Wc();
			}catch (Exception e){
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				go = false;
				return null;
			}
		}else{
			System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
			go = false;
		}
		return null;
	}

	private static void linkFilters(List<SequentialFilter> filters){
		for (int i=0; i<filters.size()-1; i++){
			filters.get(i).setNextFilter(filters.get(i+1));
		}
	}
}
