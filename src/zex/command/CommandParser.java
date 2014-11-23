package zex.command;

import zex.explorer.FileExplorer;

public class CommandParser {
	private static String[] history = new String[20];
	private static int pointer = 0;
	private static int max = 0;
	
	public static String parse(String command){
		String[] parameters = command.split(" ");
		ReturnValue value = null;
		
		switch(parameters[0]){
		case "ls":
			value = CommandList.list(parameters);
			break;	
		case "encrypt":
		case "decrypt":
			value = CommandEncrypt.encrypt_manager(parameters);
			break;
		case "open":
			FileExplorer.getFile(true);
			break;
		case "save":
			FileExplorer.saveToFile(true);
			break;
		case "cd":
			value = CommandCallDirectory.calldirectory(parameters);
			break;
		case "help":
			value = CommandHelp.help(parameters);
			break;
		case "czure":
			value = CommandCZure.czure(parameters);
			break;
		case "clear":
			value = CommandClear.clear(parameters);
			break;
		case "exit":
			System.exit(0);
			break;
		default:
			return "\"" + parameters[0]+ "\" is not recognized as an internal command. For help, enter \"help\"";
		}
		if(value != null){ 
			if(value.isSuccess())
				history(command);
			String ret_str = null;
			try{
				ret_str = value.getMessage();
			}catch(Exception e){
				e.printStackTrace();
			}
			return ret_str;	
		}else
			return null;
			
	}
	
	private static void history(String last) {
		for (int i = (history.length - 1); i > 1; i--) {
			history[i] = history[i - 1];
		}
		history[1] = last;
		history[0] = "";
		if(max < history.length) max++;
	}

	public static void pointer_increase() {
		if (pointer < max) {
			pointer++;
		}
		System.err.println(pointer);
	}

	public static void pointer_decrease() {
		if (pointer > 0) {
			pointer--;
		}
		System.err.println(pointer);
	}

	public static void pointer_reset() {
		pointer = 0;
	}

	public static String get_current() {
		if(pointer < 0){
			pointer = 0;
		}
		return history[pointer];
	}
}
